import java.util.LinkedList;
import java.util.Queue;

/**
 * Simula entradas de jugador y sistema de puntuación simple.
 */
public class InputManager {
    private Queue<String> comandos = new LinkedList<>();
    private SistemaPuntuacion puntuacion = new SistemaPuntuacion();

    public void push(String cmd) { comandos.add(cmd); }

    public String poll() { return comandos.poll(); }

    public boolean hasCommands() { return !comandos.isEmpty(); }

    public SistemaPuntuacion getPuntuacion() { return puntuacion; }
}

class SistemaPuntuacion {
    private int puntos = 0;

    public void sumarPuntos(int p) { puntos += p; }
    public int getPuntos() { return puntos; }

    @Override
    public String toString() { return String.valueOf(puntos); }
}
