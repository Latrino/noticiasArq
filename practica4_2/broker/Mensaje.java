package broker;

public class Mensaje {
    private String contenido;
    private long timestamp;

    public Mensaje(String contenido) {
        this.contenido = contenido;
        this.timestamp = System.currentTimeMillis();
    }

    public String getContenido() {
        return contenido;
    }

    public long getTimestamp() {
        return timestamp;
    }
}