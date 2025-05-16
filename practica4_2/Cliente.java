package broker;

public class ClienteAutomatizado {
    public static void main(String[] args) throws InterruptedException {
        Broker broker = Broker.getInstance();
        
        // Escenario 1: Cola simple con un productor y un consumidor
        System.out.println("\n=== Escenario 1: Productor-Consumidor básico ===");
        broker.declararCola("test1");
        
        // Registrar consumidor
        broker.consumir("test1", mensaje -> 
            System.out.println("[Consumidor1] Recibido: " + mensaje));
        
        // Publicar mensajes
        for (int i = 1; i <= 3; i++) {
            broker.publicar("test1", "Mensaje " + i, "Productor1");
            Thread.sleep(300);
        }
        Thread.sleep(1000); // Esperar a que se procesen
        
        // Escenario 2: Múltiples consumidores (round-robin)
        System.out.println("\n=== Escenario 2: Round-robin con 2 consumidores ===");
        broker.declararCola("test2");
        
        // Registrar dos consumidores
        broker.consumir("test2", mensaje -> 
            System.out.println("[ConsumidorA] Recibido: " + mensaje));
        broker.consumir("test2", mensaje -> 
            System.out.println("[ConsumidorB] Recibido: " + mensaje));
        
        // Publicar 4 mensajes
        for (int i = 1; i <= 4; i++) {
            broker.publicar("test2", "Msg " + i, "Productor2");
            Thread.sleep(300);
        }
        Thread.sleep(1000);
        
        // Escenario 3: Mensajes expirados (sin consumidores)
        System.out.println("\n=== Escenario 3: Mensajes expirados ===");
        broker.declararCola("test3");
        
        // Publicar mensajes que expirarán
        broker.publicar("test3", "Este mensaje expirará", "Productor3");
        System.out.println("Mensaje publicado en cola sin consumidores. Esperar 5 minutos para verlo expirar.");
        
        // Escenario 4: Cola no existente
        System.out.println("\n=== Escenario 4: Publicar en cola no existente ===");
        boolean resultado = broker.publicar("no_existe", "Mensaje perdido", "Productor4");
        System.out.println("Resultado de publicar en cola inexistente: " + resultado);
    }
}