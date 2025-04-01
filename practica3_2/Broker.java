class Broker {

    public:
        void registrar_servidor(String nombre_servidor, String host_remoto_IP_puerto);
        Respuesta ejecutar_servicio(String nom_servicio, Vector parametros_servicio);
}