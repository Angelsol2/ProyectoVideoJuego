package org.example.proyectovideojuego;

public class PeleadorZombie extends PersonajesEstructura {
    int dañoextra;
    int velocidadExtra;
    int defensaExtra;
    boolean ultimateActiva = false;
    int duracionUltimate = 0;
    int duracionDebuff = 0;

    public PeleadorZombie(String nombre, int vidaMaxima, int daño, int defensa, int velocidad, String nombrePasiva, String nombreHab1, String nombreHab2, String nombreHab3, String nombreHab4, int probEsquivar) {
        super(nombre, vidaMaxima, daño, defensa, velocidad, nombrePasiva, nombreHab1, nombreHab2, nombreHab3, nombreHab4, probEsquivar);
    }

    @Override
    public void atacar(PersonajesEstructura enemigo) {
        int daño = enemigo.RecibirDaño(this.daño);
        pasivaActiva(daño);
        if (ultimateActiva) {
            pasivaActiva(this.daño * 2);
        }
    }

    @Override
    public void pasivaActiva(int dato) {
        double curacion = dato * 0.40;
        this.vidaActual += (int) curacion;
        if (this.vidaActual > this.vidaMaxima) {
            this.vidaActual = this.vidaMaxima;
        }
    }

    @Override
    public void pasiva(PersonajesEstructura enemigo) {
    }

    @Override
    public void habilidad1(PersonajesEstructura enemigo) {//mordisco

        int daño = this.daño + 80;
        int Curacion = enemigo.RecibirDaño(daño);
        pasivaActiva(Curacion);

        if (ultimateActiva) {
            pasivaActiva(this.daño * 2);
        }


    }

    @Override
    public void habilidad2(PersonajesEstructura enemigo) {  //alientro putrefacto

        enemigo.debuff = true;
    }

    @Override
    public void habilidad3(PersonajesEstructura enemigo) {    // festin de cerebro
        int daño = this.daño + 120;
        int curacion = enemigo.RecibirDaño(daño);
        enemigo.velocidad -= (int) (this.daño * .40);
        this.vidaActual += curacion;
        pasivaActiva(curacion);

        if (ultimateActiva) {
            pasivaActiva(this.daño * 2);
        }


    }

    @Override
    public void ultimate(PersonajesEstructura enemigo) {
        //canibalismo
        ultimateActiva = true;
        this.vidaActual = vidaActual / 4;
        dañoextra = this.daño * 2;
        defensaExtra = this.defensa * 2;
        velocidadExtra = this.velocidad / 2;

        this.defensa += defensaExtra;
        this.velocidad -= velocidadExtra;
        this.daño += dañoextra;

    }

    @Override
    public void terminarTurno(PersonajesEstructura enemigo) {
        Comprobacion(enemigo);

    }

    @Override
    public void alRecibirDaño(int dañoRecibido, PersonajesEstructura atacante) {

    }


    public void Comprobacion(PersonajesEstructura enemigo) {
        if (enemigo.debuff) {
            enemigo.vidaActual -= (int) (this.daño * 0.10);
            if (enemigo.vidaActual < 0) enemigo.vidaActual = 0;

            duracionDebuff++;
            if (duracionDebuff >= 4) {
                enemigo.debuff = false;
                duracionDebuff = 0;
            }
        }

        if (ultimateActiva) {
            duracionUltimate++;
            if (duracionUltimate >= 5) {
                ultimateActiva = false;
                duracionUltimate = 0;

                this.daño -= dañoextra;
                this.defensa -= defensaExtra;
                this.velocidad += velocidadExtra;
                dañoextra = 0;
                defensaExtra = 0;
                velocidadExtra = 0;
            }
        }
    }

    public String getDescPasiva() {
        return "El zombie se cura 40% del daño que inflinge.";
    }

    public String getDescHabilidad1Dinamica() {
        int dañoTotal = this.daño + 80;
        return "Mordisco: Ataca con " + dañoTotal + " de potencia y cura un 40% del impacto.";
    }

    public String getDescHabilidad2Dinamica() {
        int dañoTotal = (int) (this.daño * 10);
        return "Aliento putrefacto: Envenena el ambiente con su aliento putrefacto que inflige" + dañoTotal + "por 4 turnos";
    }

    public String getDescHabilidad3Dinamica() {
        int dañoTotal = this.daño + 120;
        return "Festin de carne: Embesti a el adversario infligiendo " + dañoTotal + " de potencia y se cura todo el daño hecho.";
    }

    public String getDescHabilidad4inamica() {
        int dañoTotal = this.daño + 80;
        return "Canibalismo: El zombie se come a si mismo perdiendo" + vidaActual / 4 + "de vida pero a cambio tiene una gran mejora de estadisticas por 5 turnos y aumentando sus curaciones" + "daño:" + this.daño * 2 + "Defensa:" + this.defensa * 2 + "Velocidad:" + this.velocidad / 2
                ;
    }

    public String getNombreHab1() {
        return nombreHab1;
    }

    public String getNombreHab2() {
        return nombreHab2;
    }

    public String getNombreHab3() {
        return nombreHab3;
    }

    public String getNombreHab4() {
        return nombreHab4;
    }
}
