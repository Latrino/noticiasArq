package consumidor;

import broker.Broker;
import broker.ConsumerCallback;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ClienteConsumidor {
    private static final AtomicInteger messageCounter = new AtomicInteger(1);
    
    public static void main(String[] args) {
        Broker broker = Broker.getInstance();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Cliente de Prueba Consumidor para Broker MOM ===");
        System.out.println("Comandos disponibles:");
        System.out.println("1. Registrar consumidor");
        System.out.println("2. Salir");
        
        while (true) {
            System.out.print("\nIngrese comando (1-2): ");
            int comando = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            
            switch (comando) {
                case 1:
                    registrarConsumidor(broker, scanner);
                    break;
                case 2:
                    System.out.println("Saliendo del cliente de prueba...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Comando no válido");
            }
        }
    }
    
    private static void registrarConsumidor(Broker broker, Scanner scanner) {
        System.out.print("Ingrese nombre de la cola: ");
        String nombreCola = scanner.nextLine();
        
        System.out.print("Ingrese nombre del consumidor: ");
        final String nombreConsumidor = scanner.nextLine();
        
        ConsumerCallback consumidor = mensaje -> {
            System.out.println("[" + nombreConsumidor + "] Procesando mensaje: " + mensaje);
            // Simular tiempo de procesamiento
            try {
                Thread.sleep(500 + (long)(Math.random() * 1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        
        boolean exito = broker.consumir(nombreCola, consumidor);
        if (exito) {
            System.out.println("Consumidor '" + nombreConsumidor + 
                             "' registrado exitosamente en cola '" + nombreCola + "'");
        } else {
            System.out.println("Error: La cola '" + nombreCola + "' no existe");
        }
    }
    
}