import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Biblioteca extends Remote {
    String buscarLibroPorTitulo(String titulo) throws RemoteException;
    String buscarLibroPorAutor(String autor) throws RemoteException;
    List<String> listarLibros() throws RemoteException;
}