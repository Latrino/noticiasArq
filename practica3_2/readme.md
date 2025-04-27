Para ejecutar el programa necesitaremos seguir los siguientes pasos (uso el nip a778043, cambiarlo al correspondiente):

1. Compilar el código en tu máquina (puedes compilar en las máquinas tras pasar el código, pero así lo haces todo de una)
   ~/javac Broker/_.java Servidores/_.java Cliente/\*.java

2. Enviar el broker, los servidores y el cliente a las máquinas correspondientes:
   // El broker necesitará el .class de Biblioteca y Recomendaciones, ya que necesita conocer las interfaces
   ~/scp -r Broker/\* a778043@155.210.154.191:~/broker_043/
   ~/scp Servidores/Biblioteca.class Servidores/Recomendaciones.class a778043@155.210.154.191:~/broker_043/

   //Los servidores tambien necesitan el interfaz del broker Broker.class
   ~/scp -r Servidores/\* a778043@155.210.154.194:~/servidores_043/
   ~/scp Broker/Broker.class a778043@155.210.154.194:~/servidores_043/

   //El cliente tambien necesita el interfaz del broker Broker.class
   ~/scp -r Cliente/\* a778043@155.210.154.193:~/cliente_043/
   ~/scp Broker/Broker.class a778043@155.210.154.193:~/cliente_043/

3. Hacer ssh a cada una de las máquinas, y situarse en el directorio correspondiente:

- En la 191 se encuentra el broker, habrá que hacer: ~/cd broker_043
- En la 193 se encuentran los servidores, habrá que hacer: ~/cd broker_043
- En la 194 se encuentra el cliente, habrá que hacer: ~/cd broker_043

4. En la máquina 191, ejecutaremos el rmiregistry en el puerto 1099, y tras ejecutarlo, pondremos en marcha el broker:
   ~/rmiregistry 1099 &
   ~/java BrokerImpl

5. En la máquina 194, ejecutaremos el rmiregistry en el puerto 1101 para los servidores, y tras ejecutarlo, pondremos en marcha los mismos:
   ~/rmiregistry 1099 &
   ~/java RecomendacionesImpl &  
   ~/java BibliotecaImpl &

6. En la máquina 193, ejecutaremos el cliente directamente:
   ~/java Cliente
