import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.util.*;

public class BrokerImpl extends UnicastRemoteObject implements Broker{
    private Map<String, String> servidores;

    public BrokerImpl() throws RemoteException {
        super();
        servidores = new HashMap<>();
    }

    @Override
    public void registrar_servidor(String nombre, String hostIpPuerto) throws RemoteException {
        // Asegúrate que la URL incluya rmi://
        String urlCompleta = hostIpPuerto.startsWith("rmi://") ? hostIpPuerto : "rmi://" + hostIpPuerto;
        servidores.put(nombre, urlCompleta);
        System.out.println("Servidor registrado: " + nombre + " en " + urlCompleta);
    }


    @Override
    public Object ejecutar_servicio(String nombreServicio, Vector<Object> args) throws RemoteException {
        try {

            String serverName = nombreServicio.startsWith("recomendar") ? "Recomendaciones" : "Biblioteca";
            String fullUrl = servidores.get(serverName);

            switch(nombreServicio) {
                // Servicios de Biblioteca
                case "buscarLibro":
                    Biblioteca biblioteca = (Biblioteca) Naming.lookup(servidores.get("Biblioteca"));
                    return biblioteca.buscarLibroPorTitulo((String) args.get(0));

                case "listarLibrosPorAutor":
                    Biblioteca bibliotecaAutor = (Biblioteca) Naming.lookup(servidores.get("Biblioteca"));
                    return bibliotecaAutor.listarLibrosPorAutor((String) args.get(0));

                case "listarLibros":
                    Biblioteca bibliotecaListar = (Biblioteca) Naming.lookup(servidores.get("Biblioteca"));
                    return bibliotecaListar.listarLibros();

                // Servicios de Recomendaciones
                case "recomendarPorGenero":
                    Recomendaciones rec = (Recomendaciones) Naming.lookup(servidores.get("Recomendaciones"));
                    return rec.recomendarPorGenero((String) args.get(0));

                default:
                    return "Servicio no encontrado";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Error al ejecutar el servicio: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        try {
           
            // Crear instancia del Broker
            BrokerImpl broker = new BrokerImpl();
            
            // Registrar el broker en RMI
            Naming.rebind("rmi://localhost/Broker_043", broker); // Usa tu nombre único
            System.out.println("Broker listo y registrado");
            
        } catch (Exception e) {
            System.err.println("Error en Broker:");
            e.printStackTrace();
        }
    }
}
