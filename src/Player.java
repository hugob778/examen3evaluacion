/**
 * Entidad jugador.
 */
public class Player extends EntidadVideojuego {
    public Player(String id, int x, int y) {
        super(id, "Player", x, y, 1, 1, 10, "player.png");
    }

    @Override
    public void actualizar(MotorJuego motor) {
        // Player updates are driven by InputManager; here we just log status
        System.out.println("[Player] Estado: " + this);
    }

    @Override
    public void onColision(MotorJuego motor, EntidadVideojuego otro) {
        if (otro.getTipo().equals("Enemigo")) {
            setVida(getVida() - 2);
            System.out.println("[Player] Colisionado con enemigo. Vida ahora: " + getVida());
            if (getVida() <= 0) {
                motor.forzarGameOver();
            }
        } else if (otro.getTipo().equals("Recolectable")) {
            setVida(getVida() + 1);
            System.out.println("[Player] Recolectó un objeto. Vida ahora: " + getVida());
            otro.setActivo(false);
            motor.removerEntidad(otro);
            motor.getPuntuacion().sumarPuntos(10);
        }
    }
}
