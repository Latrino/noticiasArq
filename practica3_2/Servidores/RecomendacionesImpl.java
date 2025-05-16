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

    public static void main(String[] args) {
        try {
            RecomendacionesImpl rec = new RecomendacionesImpl();
            String url = "rmi://155.210.154.192:1101/Recomendaciones_043";
            Naming.rebind(url, rec);
            
            Broker broker = (Broker) Naming.lookup("rmi://155.210.154.191:1099/Broker_043");
            broker.registrar_servidor("Recomendaciones", "rmi://155.210.154.192:1101/Recomendaciones_043");
            
            System.out.println("Servidor Recomendaciones listo");
        } catch (Exception e) {
            System.err.println("Error en Recomendaciones:");
            e.printStackTrace();
        }
    }
}
