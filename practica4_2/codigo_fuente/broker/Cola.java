package broker;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Cola {
    private String nombre;
    private BlockingQueue<Mensaje> mensajes;
    private Set<String> productores;
    private List<ConsumerCallback> consumidores;
    private AtomicInteger roundRobinIndex;
    
    private Thread dispatcherThread;

    // constructor
    public Cola(String _nombre) {
        this.nombre = _nombre;
        this.mensajes = new LinkedBlockingQueue<>();
        this.productores = ConcurrentHashMap.newKeySet();
        this.consumidores = new ArrayList<>();
        this.roundRobinIndex = new AtomicInteger(0);
        this.dispatcherThread = null;
        startMessageDispatcher();
    }

    // ------------------------- metodos de productores --------------------------
    // para registrar un productor
    public void registrarProductor(String nombreProd) {
        if (!productores.contains(nombreProd)){
            productores.add(nombreProd);   
            System.out.println("Productor registrado: " + nombreProd);
        }
        else {
            System.out.println("El productor '" + nombreProd + "' ya estaba registrado.");
        }
    }

    public Set<String> getProductores() {
        return Collections.unmodifiableSet(productores);
    }

    // ------------------------- metodos de consumidores --------------------------
    public void registrarConsumidor(ConsumerCallback consumidor) {
        if (!consumidores.contains(consumidor)){
            consumidores.add(consumidor);
        }
    }

    // ------------------------- metodos de mensajes --------------------------
    public void agregarMensaje(String contenido) {
        mensajes.offer(new Mensaje(contenido));
    }

    // ------------------------- Message Dispatcher --------------------------
    private void startMessageDispatcher() {
    this.dispatcherThread = new Thread(() -> {
        while(!Thread.currentThread().isInterrupted()) {  // Mejor condición de salida
            try {
                Mensaje mensaje = mensajes.poll(1, TimeUnit.SECONDS);
                if (mensaje != null) {
                    long edad = System.currentTimeMillis() - mensaje.getTimestamp();
                    
                    // Mensaje expirado
                    if (edad > 5 * 60 * 1000) {
                        System.out.println("Mensaje descartado por expiración: " + mensaje.getContenido());
                        continue;
                    }
                    
                    // Hay consumidores disponibles
                    if (!consumidores.isEmpty()) {
                        int idx = roundRobinIndex.getAndIncrement() % consumidores.size();
                        try {
                            consumidores.get(idx).procesarMensaje(mensaje.getContenido());
                        } catch (Exception e) {
                            System.err.println("Error al procesar mensaje: " + e.getMessage());
                        }
                    } 
                    // No hay consumidores, reinsertar si aún es válido
                    else if (System.currentTimeMillis() - mensaje.getTimestamp() < 5 * 60 * 1000) {
                        mensajes.offer(mensaje);
                        Thread.sleep(1000);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;  // Salir del bucle si nos interrumpen
            }
        }
    });
    dispatcherThread.start();  // Iniciar el hilo después de crearlo
}

    public void shutdown(){
        if (dispatcherThread != null){
            dispatcherThread.interrupt();
        }
    }
}
