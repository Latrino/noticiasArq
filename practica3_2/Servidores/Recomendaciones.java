import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Recomendaciones extends Remote {
    List<String> recomendarPorGenero(String genero) throws RemoteException;
}