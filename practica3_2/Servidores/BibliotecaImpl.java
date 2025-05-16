import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.util.stream.Collectors;
import java.util.*;

public class BibliotecaImpl extends UnicastRemoteObject implements Biblioteca, Recomendaciones {
    private Map<String, String> libros; // mapa para almacenar libros (título, autor)
    // Constructor
    public BibliotecaImpl() throws RemoteException {
        super();
        libros = new HashMap<>();

        libros.put("El Quijote", "Miguel de Cervantes");
        libros.put("Cien años de soledad", "Gabriel García Márquez");
        libros.put("1984", "George Orwell");
        libros.put("El Principito", "Antoine de Saint-Exupéry");
        libros.put("Crónica de una muerte anunciada", "Gabriel García Márquez");
        libros.put("El amor en los tiempos del cólera", "Gabriel García Márquez");
        libros.put("Rayuela", "Julio Cortázar");
        libros.put("La casa de los espíritus", "Isabel Allende");
    }

    @Override
    public String buscarLibroPorTitulo(String titulo) throws RemoteException {
        // Implementación del método para buscar un libro por su título
        return libros.getOrDefault(titulo, "Libro no encontrado");
    }

    @Override
    public List<String> listarLibrosPorAutor(String autor) throws RemoteException {
        // Implementación del método para buscar un libro por su autor
        return libros.entrySet().stream()
        .filter(entry -> entry.getValue().equalsIgnoreCase(autor))
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
    }

    @Override
    public List<String> listarLibros() throws RemoteException {
        List<String> listaLibros = new ArrayList<>();
        for (Map.Entry<String, String> entry : libros.entrySet()) {
            listaLibros.add("Título: " + entry.getKey() + ", Autor: " + entry.getValue());
        }
        return listaLibros;
    }

    @Override
    public List<String> recomendarPorGenero(String genero) throws RemoteException {
        // Implementación del método para recomendar libros por género
        return List.of("Recomendación1", "Recomendación2");
    }


    public static void main(String[] args) {
        try {
            BibliotecaImpl biblioteca = new BibliotecaImpl();
            String url = "rmi://155.210.154.192:1101/Biblioteca_043";
            Naming.rebind(url, biblioteca);
            
            // Registrar en el Broker
            Broker broker = (Broker) Naming.lookup("rmi://155.210.154.191:1099/Broker_043");
            broker.registrar_servidor("Biblioteca", "155.210.154.192:1101/Biblioteca_043");
            
            System.out.println("Servidor Biblioteca listo");
        } catch (Exception e) {
            System.err.println("Error en Biblioteca:");
            e.printStackTrace();
        }
    }
    
}
