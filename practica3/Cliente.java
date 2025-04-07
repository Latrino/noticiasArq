// TODO : imports necesarios
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

public class Cliente
{
    public static void main ( String [] args ) {
    // TODO : Fijar el directorio donde se encuentra el java . policy
        /*System.setProperty("java.security.policy", "./java.policy");
        if ( System.getSecurityManager() == null ) {
        // TODO : Crear administrador de seguridad
            System.setSecurityManager(new SecurityManager());
        }*/
        try {
            // Paso 1 - Obtener una referencia al objeto servidor creado anteriormente
            // Nombre del host servidor o su IP. Es dó nde se buscar á al objeto remoto
            /*String hostname = " IPremotehost "; // se puede usar "IP: puerto "
            Collection server = ( Collection ) Naming . lookup ("//"+ hostname + "/ MyCollection ") ;*/
            String hostname = "localhost:32000";
            Collection server = (Collection) Naming.lookup("//" + hostname + "/MyCollection");




            // Paso 2 - Invocar remotamente los metodos del objeto servidor :
            // TODO : obtener el nombre de la colecci ón y el nú mero de libros
            System.out.println(" Nombre de la coleccion : " + server.name_of_collection());
            System.out.println(" Numero de libros : " + server.number_of_books());
            // TODO : cambiar el nombre de la coleccion y ver que ha funcionado
            server.name_of_collection("Nueva coleccion") ;
            System.out.println(" Nombre de la coleccion : " + server.name_of_collection());
            server.add_books(5);
            System.out.println(" Numero de libros : " + server.number_of_books());
        }
        catch ( Exception ex ) {
            System.out.println(ex);
        }
    }
}