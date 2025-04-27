import java.rmi.Naming;
import java.util.Vector;
import java.util.List;

public class Cliente {
    public static void main(String[] args) {
        try {
            Broker broker = (Broker) Naming.lookup("rmi://155.210.154.191:1099/Broker_043");
            
            // Prueba servicios de Biblioteca
            System.out.println("\n=== Pruebas Biblioteca ===");
            pruebaServicio(broker, "buscarLibro", "1984");
            pruebaServicio(broker, "listarLibrosPorAutor", "Gabriel García Márquez");
            pruebaServicio(broker, "listarLibros", null);
            
            // Prueba servicios de Recomendaciones
            System.out.println("\n=== Pruebas Recomendaciones ===");
            pruebaServicio(broker, "recomendarPorGenero", "Ciencia Ficción");
            
        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void pruebaServicio(Broker broker, String servicio, String parametro) throws Exception {
        Vector<Object> args = new Vector<>();
        if(parametro != null) args.add(parametro);
        
        System.out.println("\nEjecutando servicio: " + servicio);
        Object resultado = broker.ejecutar_servicio(servicio, args);
        
        if(resultado instanceof List) {
            System.out.println("Resultado (" + ((List)resultado).size() + " elementos):");
            ((List)resultado).forEach(System.out::println);
        } else {
            System.out.println("Resultado: " + resultado);
        }
    }
}