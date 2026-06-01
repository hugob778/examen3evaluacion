import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Núcleo del juego: gestiona estado, entidades y bucle.
 */
public class MotorJuego {
    public enum Estado { MENU, JUGANDO, PAUSA, GAME_OVER }

    private Estado estado = Estado.MENU;
    private List<EntidadVideojuego> entidades = new ArrayList<>();
    private InputManager inputManager;
    private Player jugador;

    public MotorJuego(InputManager im) {
        this.inputManager = im;
    }

    public void iniciarPartida() {
        if (estado == Estado.JUGANDO) {
            System.out.println("Ya hay una partida en curso");
            return;
        }
        entidades.clear();
        jugador = new Player("p1", 5, 5);
        entidades.add(jugador);
        // Generar algunos enemigos y objetos
        entidades.add(new Enemigo("e1", 2, 2));
        entidades.add(new Enemigo("e2", 8, 1));
        entidades.add(new Recolectable("c1", 6, 5));
        estado = Estado.JUGANDO;
        System.out.println("Partida iniciada");
    }

    public void pausar() { if (estado == Estado.JUGANDO) estado = Estado.PAUSA; }
    public void reanudar() { if (estado == Estado.PAUSA) estado = Estado.JUGANDO; }
    public void forzarGameOver() { estado = Estado.GAME_OVER; System.out.println("Game Over forzado"); }

    public Estado getEstado() { return estado; }
    public Player getJugador() { return jugador; }
    public InputManager getInputManager() { return inputManager; }
    public SistemaPuntuacion getPuntuacion() { return inputManager.getPuntuacion(); }

    public void addEntidad(EntidadVideojuego e) { entidades.add(e); }
    public void removerEntidad(EntidadVideojuego e) { entidades.remove(e); }

    public void actualizar() {
        if (estado != Estado.JUGANDO) {
            System.out.println("Motor en estado: " + estado);
            return;
        }

        // Procesar inputs simples
        while (inputManager.hasCommands()) {
            String cmd = inputManager.poll();
            procesarComando(cmd);
        }

        // Actualizar entidades
        for (EntidadVideojuego e : new ArrayList<>(entidades)) {
            e.actualizar(this);
        }

        // Detectar colisiones
        detectarColisiones();

        // Limpiar entidades inactivas
        Iterator<EntidadVideojuego> it = entidades.iterator();
        while (it.hasNext()) {
            EntidadVideojuego e = it.next();
            if (!e.isActivo()) {
                it.remove();
            }
        }

        System.out.println("[Motor] Entidades activas: " + entidades.size() + " | Puntos: " + getPuntuacion().getPuntos());
    }

    private void procesarComando(String cmd) {
        System.out.println("[Input] " + cmd);
        if (cmd.startsWith("MOVER ")) {
            String dir = cmd.substring(6);
            desplazarJugador(dir);
        } else if (cmd.equals("PAUSA")) {
            pausar();
        } else if (cmd.equals("REANUDAR")) {
            reanudar();
        } else if (cmd.equals("SAVE")) {
            String s = quickSave();
            System.out.println("[QuickSave] " + s);
        } else if (cmd.equals("GAMEOVER")) {
            forzarGameOver();
        } else if (cmd.equals("ACCION")) {
            System.out.println("[Accion] Interacción realizada");
        }
    }

    public void desplazarJugador(String direccion) {
        if (jugador == null) return;
        switch (direccion) {
            case "ARRIBA": jugador.setY(jugador.getY() - 1); break;
            case "ABAJO": jugador.setY(jugador.getY() + 1); break;
            case "IZQ": jugador.setX(jugador.getX() - 1); break;
            case "DER": jugador.setX(jugador.getX() + 1); break;
            default: System.out.println("Direccion desconocida");
        }
        System.out.println("[Jugador] Nueva pos: " + jugador.getX() + "," + jugador.getY());
    }

    // Colisiones AABB simples
    public void detectarColisiones() {
        for (int i = 0; i < entidades.size(); i++) {
            EntidadVideojuego a = entidades.get(i);
            for (int j = i + 1; j < entidades.size(); j++) {
                EntidadVideojuego b = entidades.get(j);
                if (colisionan(a, b)) {
                    System.out.println("[Colision] " + a.getId() + " <-> " + b.getId());
                    a.onColision(this, b);
                    b.onColision(this, a);
                }
            }
        }
    }

    public boolean colisionan(EntidadVideojuego a, EntidadVideojuego b) {
        int ax1 = a.getX();
        int ay1 = a.getY();
        int ax2 = ax1 + a.getW();
        int ay2 = ay1 + a.getH();

        int bx1 = b.getX();
        int by1 = b.getY();
        int bx2 = bx1 + b.getW();
        int by2 = by1 + b.getH();

        return ax1 < bx2 && ax2 > bx1 && ay1 < by2 && ay2 > by1;
    }

    public String quickSave() {
        StringBuilder sb = new StringBuilder();
        sb.append("estado=").append(estado).append(";puntos=").append(getPuntuacion().getPuntos()).append(";entities=[");
        for (EntidadVideojuego e : entidades) {
            sb.append(String.format("%s:%s:%d:%d:%d|", e.getId(), e.getTipo(), e.getX(), e.getY(), e.getVida()));
        }
        sb.append("]");
        return sb.toString();
    }
}
