/**
 * Clase principal que simula entradas y ejecuta el bucle.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        InputManager im = new InputManager();
        MotorJuego motor = new MotorJuego(im);

        // Simular flujo de trabajo: iniciar, mover, interaccion, guardar
        im.push("INICIAR");
        motor.iniciarPartida();

        // Secuencia de comandos simulados
        im.push("MOVER DER");
        im.push("MOVER DER");
        im.push("MOVER ARRIBA");
        im.push("ACCION");
        im.push("SAVE");

        // Ejecutar varias iteraciones del bucle
        for (int tick = 0; tick < 10; tick++) {
            System.out.println("\n--- Tick " + tick + " ---");
            motor.actualizar();
            Thread.sleep(200);
            if (motor.getEstado() == MotorJuego.Estado.GAME_OVER) break;
        }

        System.out.println("Simulación finalizada. Puntos finales: " + motor.getPuntuacion().getPuntos());
    }
}
