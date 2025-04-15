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
        servidores.put(nombre, hostIpPuerto);
        System.out.println("Servidor registrado: " + nombre + " en " + hostIpPuerto);
    }


    @Override
    public Object ejecutar_servicio(String nombreServicio, Vector<Object> args) throws RemoteException {
        try {
            switch(nombreServicio) {
                case "buscarLibro":
                    Biblioteca biblioteca = (Biblioteca) Naming.lookup(servidores.get("Biblioteca"));
                    return biblioteca.buscarLibroPorTitulo((String) args.get(0));
                case "buscarLibroPorAutor":
                    Biblioteca bibliotecaAutor = (Biblioteca) Naming.lookup(servidores.get("Biblioteca"));
                    return bibliotecaAutor.buscarLibroPorAutor((String) args.get(0));
                case "listarLibros":
                    Biblioteca bibliotecaListar = (Biblioteca) Naming.lookup(servidores.get("Biblioteca"));
                    return bibliotecaListar.listarLibros();
                default:
                    return "Servicio no encontrado";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Error al ejecutar el servicio: " + e.getMessage();
        }
    }
}
