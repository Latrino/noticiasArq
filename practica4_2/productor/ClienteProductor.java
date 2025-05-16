package productor;

import broker.Broker;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ClienteProductor {
    private static final AtomicInteger messageCounter = new AtomicInteger(1);
    
    public static void main(String[] args) {
        Broker broker = Broker.getInstance();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Cliente de Prueba para Broker MOM ===");
        System.out.println("Comandos disponibles:");
        System.out.println("1. Declarar cola"   );
        System.out.println("2. Publicar mensaje");
        System.out.println("3. Salir");
        
        while (true) {
            System.out.print("\nIngrese comando (1-3): ");
            int comando = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            
            switch (comando) {
                case 1:
                    declararCola(broker, scanner);
                    break;
                case 2:
                    publicarMensaje(broker, scanner);
                    break;
                case 3:
                    System.out.println("Saliendo del cliente de prueba...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Comando no válido");
            }
        }
    }
    
    private static void declararCola(Broker broker, Scanner scanner) {
        System.out.print("Ingrese nombre de la cola: ");
        String nombreCola = scanner.nextLine();
        broker.declararCola(nombreCola);
        System.out.println("Cola '" + nombreCola + "' declarada exitosamente");
    }
    
    private static void publicarMensaje(Broker broker, Scanner scanner) {
        System.out.print("Ingrese nombre de la cola: ");
        String nombreCola = scanner.nextLine();
        
        System.out.print("Ingrese nombre del productor: ");
        String productor = scanner.nextLine();
        
        System.out.print("Ingrese mensaje (o dejar vacío para mensaje automático): ");
        String mensaje = scanner.nextLine();
        
        if (mensaje.isEmpty()) {
            mensaje = "Mensaje #" + messageCounter.getAndIncrement() + " de " + productor;
        }
        
        boolean exito = broker.publicar(nombreCola, mensaje, productor);
        if (exito) {
            System.out.println("Mensaje publicado exitosamente en cola '" + nombreCola + "'");
        } else {
            System.out.println("Error: La cola '" + nombreCola + "' no existe");
        }
    }
    
}