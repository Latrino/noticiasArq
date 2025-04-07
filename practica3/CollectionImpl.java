// TODO : imports necesarios
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CollectionImpl extends UnicastRemoteObject
implements Collection
{
    // Private member variables
    private int m_number_of_books = 10;
    private String m_name_of_collection = "MyCollection";
    // Constructor
    public CollectionImpl () throws RemoteException
    {
        super () ; // Llama al constructor de UnicastRemoteObject
        // TODO : inicializar las variables privadas
        this.m_number_of_books = 0;
        this.m_name_of_collection = "";
    }
    // TODO : Implementar todos los metodos de la interface remota
    public int number_of_books () throws RemoteException
    {
        return this.m_number_of_books;
    }

    public void add_books (int number) throws RemoteException
    {
        this.m_number_of_books = this.m_number_of_books + number;
    }

    public String name_of_collection () throws RemoteException
    {
        return this.m_name_of_collection;
    }

    public void name_of_collection ( String _new_value ) throws RemoteException
    {
        this.m_name_of_collection = _new_value;
    }

    public static void main (String args []) {
        // Fijar el directorio donde se encuentra el java . policy
        // El segundo argumento es la ruta al java . policy
        //System.setProperty(" java . security . policy ", "./ java . policy ");
        // Crear administrador de seguridad
        //System.setSecurityManager(new SecurityManager ());
        // Nombre o IP del host donde reside el objeto servidor
        //String hostName = " IPhostremoto "; // se puede usar "IPhostremoto : puerto "
        // Por defecto , RMI usa el puerto 1099
        try {
            // Crear objeto remoto
            CollectionImpl obj = new CollectionImpl ();
            System.out.println(" Creado !") ;
            // Registrar el objeto remoto
            String hostName = "localhost:32000";
            Naming.rebind ("//" + hostName + "/MyCollection", obj ) ;
            System.out.println(" Estoy registrado!");
        }
        catch ( Exception ex ) {
            System . out . println ( ex );
        }
    }
}

