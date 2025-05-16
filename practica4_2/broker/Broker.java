package broker;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Broker {
    private static Broker instance;
    private final Map<String, Cola> colas;
    private final Lock colasLock;
    
    // Tiempo máximo de vida de un mensaje (5 minutos)
    private static final long MAX_MESSAGE_AGE_MS = 5 * 60 * 1000;

    // Constructor privado para el Singleton
    private Broker() {
        this.colas = new ConcurrentHashMap<>();
        this.colasLock = new ReentrantLock();
    }

    // Método Singleton para obtener la instancia
    public static synchronized Broker getInstance() {
        if (instance == null) {
            instance = new Broker();
        }
        return instance;
    }

// ------------------------- Métodos públicos del Broker -------------------------

    /**
     * Declara una cola (operación idempotente)
     * @param nombreCola Nombre de la cola a crear/verificar
     */
    public void declararCola(String nombreCola) {
        colasLock.lock();
        try {
            colas.putIfAbsent(nombreCola, new Cola(nombreCola));
        } finally {
            colasLock.unlock();
        }
    }

    /**
     * Publica un mensaje en una cola
     * @param nombreCola Nombre de la cola destino
     * @param contenido Contenido del mensaje
     * @param nombreProductor Identificador del productor
     * @return true si el mensaje fue aceptado, false si la cola no existe
     */
    public boolean publicar(String nombreCola, String contenido, String nombreProductor) {
        Cola cola = colas.get(nombreCola);
        if (cola == null) {
            return false; // Cola no existe, mensaje descartado
        }
        cola.registrarProductor(nombreProductor);
        cola.agregarMensaje(contenido);
        return true;
    }

    /**
     * Registra un consumidor en una cola
     * @param nombreCola Nombre de la cola
     * @param consumidor Callback del consumidor
     * @return true si el registro fue exitoso, false si la cola no existe
     */
    public boolean consumir(String nombreCola, ConsumerCallback consumidor) {
        Cola cola = colas.get(nombreCola);
        if (cola == null) {
            return false;
        }
        cola.registrarConsumidor(consumidor);
        return true;
    }

    public void shutdown() {
        colas.values().forEach(Cola::shutdown);
    }


}