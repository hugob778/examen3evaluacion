/**
 * Enemigo con comportamiento simple (Patrulla/Perseguir).
 */
public class Enemigo extends EntidadVideojuego {
    public enum Estado { PATRULLA, PERSEGUIR }

    private Estado estado = Estado.PATRULLA;
    private int dir = 1; // dirección horizontal de patrulla

    public Enemigo(String id, int x, int y) {
        super(id, "Enemigo", x, y, 1, 1, 5, "enemy.png");
    }

    @Override
    public void actualizar(MotorJuego motor) {
        Player p = motor.getJugador();
        int dx = Math.abs(p.getX() - getX());
        int dy = Math.abs(p.getY() - getY());
        int distancia = dx + dy;
        if (distancia <= 3) {
            if (estado != Estado.PERSEGUIR) {
                System.out.println("[Enemigo] Cambia a PERSEGUIR");
            }
            estado = Estado.PERSEGUIR;
            // Mover hacia el jugador
            if (p.getX() > getX()) setX(getX() + 1);
            else if (p.getX() < getX()) setX(getX() - 1);
            if (p.getY() > getY()) setY(getY() + 1);
            else if (p.getY() < getY()) setY(getY() - 1);
        } else {
            if (estado != Estado.PATRULLA) {
                System.out.println("[Enemigo] Regresa a PATRULLA");
            }
            estado = Estado.PATRULLA;
            // Movimiento de patrulla simple
            setX(getX() + dir);
            if (getX() > 10 || getX() < 0) {
                dir *= -1;
                setX(getX() + dir);
            }
        }
        System.out.println("[Enemigo] " + this + " Estado:" + estado);
    }

    @Override
    public void onColision(MotorJuego motor, EntidadVideojuego otro) {
        if (otro.getTipo().equals("Player")) {
            setVida(getVida() - 1);
            System.out.println("[Enemigo] Atacado por jugador. Vida: " + getVida());
            if (getVida() <= 0) {
                setActivo(false);
                motor.getPuntuacion().sumarPuntos(50);
                motor.removerEntidad(this);
            }
        }
    }
}
