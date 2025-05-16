package broker;

@FunctionalInterface
public interface ConsumerCallback {
    void procesarMensaje(String message);
}