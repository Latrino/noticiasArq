import java.rmi.RemoteException;
import java.rmi.Naming;
import java.util.*;
import java.rmi.server.UnicastRemoteObject;



public class RecomendacionesImpl extends UnicastRemoteObject implements Recomendaciones {
    private Map<String, List<String>> recomendaciones;

    public RecomendacionesImpl() throws RemoteException {
        super();
        recomendaciones = new HashMap<>();
        
        recomendaciones.put("Ciencia Ficción", Arrays.asList("Dune", "Neuromante"));
        recomendaciones.put("Fantasía", Arrays.asList("El Señor de los Anillos", "Harry Potter"));
        recomendaciones.put("Misterio", Arrays.asList("El Código Da Vinci", "La chica del tren"));
        
    }

    @Override
    public List<String> recomendarPorGenero(String genero) throws RemoteException {
        return recomendaciones.getOrDefault(genero, Collections.emptyList());
    }

    @Override
    public List<String> recomendarPorAutor(String autor) throws RemoteException {
        return recomendaciones.getOrDefault(autor, Collections.emptyList());
    }

    public static void main(String[] args) {
        try {
           
            // Crear instancia del Broker
            RecomendacionesImpl recomendaciones = new RecomendacionesImpl();
            
            // Registrar el broker en RMI
            Naming.rebind("rmi://localhost/Servidor_043", recomendaciones); // Usa tu nombre único
            System.out.println("Servidor Recomendaciones listo y registrado");
            
        } catch (Exception e) {
            System.err.println("Error en Recomendaciones:");
            e.printStackTrace();
        }
    }
}
