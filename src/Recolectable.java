/**
 * Objeto que el jugador puede recoger para obtener puntos o vida.
 */
public class Recolectable extends EntidadVideojuego {
    public Recolectable(String id, int x, int y) {
        super(id, "Recolectable", x, y, 1, 1, 0, "coin.png");
    }

    @Override
    public void actualizar(MotorJuego motor) {
        // No se mueve; simplemente existe en el mundo
    }

    @Override
    public void onColision(MotorJuego motor, EntidadVideojuego otro) {
        // Manejado por Player
    }
}
