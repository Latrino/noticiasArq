import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface Broker extends Remote{
    void registrar_servidor(String nombre, String hostIpPuerto) throws RemoteException;
    Object ejecutar_servicio(String nombreServicio, Vector<Object> args) throws RemoteException;  
}