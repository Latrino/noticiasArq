import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Naming;
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
    public String buscarLibroPorAutor(String autor) throws RemoteException {
        // Implementación del método para buscar un libro por su autor
        return libros.getOrDefault(autor, "Autor no encontrado");
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

    @Override
    public List<String> recomendarPorAutor(String autor) throws RemoteException {
        // Implementación del método para recomendar libros por autor
        return List.of("Recomendación3", "Recomendación4");
    }

    public static void main(String[] args) {
        try {
           
            // Crear instancia del Broker
            BibliotecaImpl biblioteca = new BibliotecaImpl();
            
            // Registrar el broker en RMI
            
            Naming.rebind("rmi://155.210.154.194:1100/servidores_043", biblioteca); // Usa tu nombre único
            System.out.println("Servidor Biblioteca listo y registrado");
            
        } catch (Exception e) {
            System.err.println("Error en Biblioteca:");
            e.printStackTrace();
        }
    }
    
}
