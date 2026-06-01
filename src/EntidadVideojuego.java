import java.util.Objects;

/**
 * Clase base para todas las entidades del videojuego.
 */
public abstract class EntidadVideojuego {
    private String id;
    private String tipo;
    private int x, y, w, h;
    private int vida;
    private String imagen; // placeholder para futura GUI
    private boolean activo = true;

    public EntidadVideojuego(String id, String tipo, int x, int y, int w, int h, int vida, String imagen) {
        this.id = id;
        this.tipo = tipo;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.vida = vida;
        this.imagen = imagen;
    }

    public abstract void actualizar(MotorJuego motor);

    public abstract void onColision(MotorJuego motor, EntidadVideojuego otro);

    // Getters y setters
    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getW() { return w; }
    public int getH() { return h; }
    public int getVida() { return vida; }
    public String getImagen() { return imagen; }
    public boolean isActivo() { return activo; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setVida(int vida) { this.vida = vida; }
    public void setActivo(boolean a) { this.activo = a; }

    @Override
    public String toString() {
        return String.format("%s(%s) @[%d,%d,%dx%d] HP=%d", id, tipo, x, y, w, h, vida);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntidadVideojuego that = (EntidadVideojuego) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
