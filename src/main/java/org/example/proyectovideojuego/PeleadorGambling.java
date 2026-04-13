package org.example.proyectovideojuego;

public class PeleadorGambling extends PersonajesEstructura {

    private int gambling = 0;
    private int gamblingGuardado = 0;
    private int probgamblingGanador;


    private boolean ultiActiva = false;
    private int contadorUltimate = 0;

    private int mejoraGambling = 1;
    private double potenciadorUltimate = 2.5;

    public PeleadorGambling(String nombre, int vidaMaxima, int daño, int defensa, int velocidad, String nombrePasiva, String nombreHab1, String nombreHab2, String nombreHab3, String nombreHab4, int probEsquivar, int probgamblingGanador) {
        super(nombre, vidaMaxima, daño, defensa, velocidad, nombrePasiva, nombreHab1, nombreHab2, nombreHab3, nombreHab4, probEsquivar);
        this.probgamblingGanador = probgamblingGanador;
    }


    @Override
    public void pasivaActiva(int datoBase) {
        if (ultiActiva) {
            this.mejoraGambling = (int) (datoBase * potenciadorUltimate);
            this.vidaActual += probgamblingGanador;
            this.probgamblingGanador += 5; // Sube poco a poco la prob
        } else {
            // Lógica Normal: Requiere 3 cargas
            if (gambling >= 3) {
                this.mejoraGambling = (int) (datoBase * 1.5);
                this.vidaActual += probgamblingGanador;
                this.probgamblingGanador += 15;

                this.gambling = 0; // Gastamos las cargas
            } else {
                this.mejoraGambling = 1;
            }
        }

        if (this.vidaActual > this.vidaMaxima) {
            this.vidaActual = this.vidaMaxima;
        }
    }


    @Override
    public void habilidad1(PersonajesEstructura enemigo) {
        int dado = (int)(Math.random() * 100) + 1;
        int dañoDeEsteTurno = this.daño;

        if (dado <= this.probgamblingGanador) {
            System.out.println("Apuesta Ganada: Daño masivo.");
            // Calculamos el daño final (dado * multiplicador)
            dañoDeEsteTurno += (dado * mejoraGambling);
        } else {
            System.out.println("Apuesta Perdida: Golpe débil.");
            dañoDeEsteTurno = (int) (dañoDeEsteTurno * 0.5);
        }

        // Aplicamos el daño al enemigo
        enemigo.RecibirDaño(dañoDeEsteTurno);

        this.gamblingGuardado += 20;
    }


    @Override
    public void habilidad2(PersonajesEstructura enemigo) {
        int dado = (int)(Math.random() * 100) + 1;

        if (dado <= this.probgamblingGanador) {
            System.out.println("Apuesta Ganada: Defensa aumentada (Temporal).");
            this.defensa += (dado * mejoraGambling);
        } else {
            System.out.println("Apuesta Perdida: Defensa rota.");
            this.defensa -= 15;
            if (this.defensa < 0) this.defensa = 0;
        }
        this.gamblingGuardado += 20;
    }


    @Override
    public void habilidad3(PersonajesEstructura enemigo) {
        int dado = (int)(Math.random() * 100) + 1;

        if (dado <= this.probgamblingGanador) {
            this.probEsquivar += (dado * mejoraGambling);
        } else {
            this.probEsquivar -= 30;
            if (this.probEsquivar < 0) this.probEsquivar = 0;
        }
        this.gamblingGuardado += 20;
    }


    @Override
    public void ultimate(PersonajesEstructura enemigo) {
        this.ultiActiva = true;
        this.contadorUltimate = 0;
    }


    @Override
    public void terminarTurno(PersonajesEstructura enemigo) {
        if (gamblingGuardado >= 100) {
            gambling++;
            gamblingGuardado = 0;
        }
        pasivaActiva(1);
        if (ultiActiva) {
            contadorUltimate++;
            if (contadorUltimate > 4) {
                ultiActiva = false;
                contadorUltimate = 0;
            }
        }
    }

    public String getDescPasiva() {
        String estadoMultiplicador = (mejoraGambling > 1) ? "¡ACTIVO! (x" + mejoraGambling + ")" : "Normal (x1)";

        return "Suerte del Principiante: \n" +
                "CARGAS ACTUALES: " + gambling + "/3. (Medidor: " + gamblingGuardado + "/100). \n" +
                "PROBABILIDAD ACTUAL: " + probgamblingGanador + "% de ganar apuestas. \n" +
                "MULTIPLICADOR ACTUAL: " + estadoMultiplicador + ". \n" +
                "EFECTO: Al llegar a 3 cargas, te curas y tu próximo ataque se multiplica por 1.5.";
    }

    public String getDescHabilidad1Dinamica() {

        int dañoPerdedor = (int)(this.daño * 0.5);
        int dañoGanadorPromedio = this.daño + (50 * mejoraGambling);
        int dañoGanadorMax = this.daño + (100 * mejoraGambling);

        return "Golpe de Suerte: \n" +
                "PROBABILIDAD DE ÉXITO: " + probgamblingGanador + "%. \n" +
                "SI GANAS: Añades el valor de un dado (1-100) multiplicado por " + mejoraGambling + " a tu daño base. \n" +
                "   -> Daño esperado: " + dañoGanadorPromedio + " (Max: " + dañoGanadorMax + "). \n" +
                "SI PIERDES: El golpe es débil, infligiendo solo " + dañoPerdedor + " de daño.";
    }

    public String getDescHabilidad2Dinamica() {
        return "Apuesta Blindada: \n" +
                "PROBABILIDAD DE ÉXITO: " + probgamblingGanador + "%. \n" +
                "SI GANAS: Aumentas tu defensa base (" + this.defensa + ") sumando un dado multiplicado por " + mejoraGambling + ". \n" +
                "SI PIERDES: Tu guardia queda expuesta, perdiendo -15 de defensa permanentemente.";
    }

    public String getDescHabilidad3Dinamica() {
        return "Reflejos de Jugador: \n" +
                "PROBABILIDAD DE ÉXITO: " + probgamblingGanador + "%. \n" +
                "SI GANAS: Sumas un dado multiplicado por " + mejoraGambling + " a tu probabilidad de esquivar (" + this.probEsquivar + "%). \n" +
                "SI PIERDES: Tropiezas al intentar esquivar, perdiendo -30% de probabilidad.";
    }

    public String getDescHabilidad4Dinamica() {
        String estadoUlti = ultiActiva ? "¡ACTIVA! (Turno " + contadorUltimate + "/4)" : "Inactiva";

        return " Todo a el rojo: \n" +
                "ESTADO: " + estadoUlti + ".\n" +
                "EFECTO: La suerte te sonríe por completo. Durante 4 turnos, ganas curación pasiva, tu probabilidad de ganar apuestas sube constantemente y tu multiplicador se clava en x2.5.";
    }

    @Override public void atacar(PersonajesEstructura enemigo) {
    }
    @Override public void pasiva(PersonajesEstructura enemigo) {
    }
    @Override public void alRecibirDaño(int dañoRecibido, PersonajesEstructura atacante) {
    }
}