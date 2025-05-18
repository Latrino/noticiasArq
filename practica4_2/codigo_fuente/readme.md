Para ejecutar el programa necesitaremos seguir los siguientes pasos:

1. Compilar el código en tu máquina
   ~/javac broker/_.java cliente/_.java

2. Ejecutar el cliente:
   ~/java cliente/ClienteAutomatizado

El cliente declara en un hilo al broker, y este mismo declara a los productores y consumidores.
Para el tercer test, dejar pasar 5 minutos para que expire el mensaje.
