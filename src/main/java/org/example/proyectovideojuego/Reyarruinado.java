package org.example.proyectovideojuego;

public class Reyarruinado extends PersonajesEstructura {

  private   int aumentoDañoIlusion = 200;
    private  int aumentoDefensaIlusion = 200;
    private  int aumentovelocidadIlusion = 200;
    private int aumentovidaMaximaIlusion = 200;

    private boolean pasivaActiva = true;
    private int sintoniaMemoria = 100;
    private  boolean ultiActiva = false;
    private  boolean habilidad3usada = false;
    private  int turnosUltimate = 0;
    private  boolean recordandoPasado = false;
    private  boolean escudoActivo = false;
    private   boolean debilidato = false;
    private    int duracionLastima = 0;

    public Reyarruinado(String nombre, int vidaMaxima, int daño, int defensa, int velocidad, String nombrePasiva, String nombreHab1, String nombreHab2, String nombreHab3, String nombreHab4, int probEsquivar) {
        super(nombre, vidaMaxima, daño, defensa, velocidad, nombrePasiva, nombreHab1, nombreHab2, nombreHab3, nombreHab4, probEsquivar);
    }



    @Override
    public void habilidad1(PersonajesEstructura enemigo) {
        if (debilidato) {
            int dañoRey = 20 + (int) (this.daño * 0.02);
            enemigo.RecibirDaño(dañoRey);
        } else if (ultiActiva) {
            int dañoLanza = 130 + (int) (this.daño * 0.35);
            enemigo.RecibirDaño(dañoLanza);
            this.velocidad += 10;
        } else {
            Recordar();
            if (recordandoPasado) return;
            int dañoRey = 80 + (int) (this.daño * 0.22);
            int dañoReyna = (this.daño / 2);
            enemigo.RecibirDaño(dañoRey);
            enemigo.setVidaActual(enemigo.vidaActual - dañoReyna);
            sintoniaMemoria -= 10;
        }
    }

    @Override
    public void habilidad2(PersonajesEstructura enemigo) {
        if (debilidato) {
            if (!"lastima".equals(enemigo.getTipoDebuff())) {
                enemigo.setTipoDebuff("lastima");
                enemigo.setDaño(enemigo.getDaño() / 2);
                this.duracionLastima = 0;
            }
        } else if (ultiActiva) {
            this.escudoActivo = true;
            this.defensa += 15;
            this.sintoniaMemoria = 100;
        } else {
            Recordar();
            if (recordandoPasado) return;
            this.sintoniaMemoria -= 25;
            this.escudoActivo = true;
        }
    }

    @Override
    public void habilidad3(PersonajesEstructura enemigo) {
        if (debilidato) {
            int dañoRey = 40 + (int) (this.daño * 0.05);
            enemigo.RecibirDaño(dañoRey);
        } else if (ultiActiva) {
            int dañoCarga = 210;
            enemigo.RecibirDaño(dañoCarga);
            enemigo.setVelocidad(enemigo.getVelocidad() - 10);
        } else {
            habilidad3usada = true;
            Recordar();
            if (recordandoPasado) return;
            int dañoLanza = 100;
            int dañoCarga = this.daño / 4;
            enemigo.RecibirDaño(dañoLanza);
            sintoniaMemoria += 5;
            for (int i = 0; i < 4; i++) {
                sintoniaMemoria += 5;
                this.defensa += 1;
                enemigo.setVidaActual(enemigo.getVidaActual() - dañoCarga);
            }
            Recordar();
        }
    }

    @Override
    public void ultimate(PersonajesEstructura enemigo) {
        if (debilidato) sintoniaMemoria += 5;
        if (sintoniaMemoria < 30) {
            ultiActiva = true;
            sintoniaMemoria = 100;
        }
    }

    @Override
    public void terminarTurno(PersonajesEstructura enemigo) {
        if (recordandoPasado) recordandoPasado = false;
        if (ultiActiva && turnosUltimate <= 3) turnosUltimate++;
        if (turnosUltimate > 3 && !debilidato) {
            pasivaActiva = false;
            this.daño -= aumentoDañoIlusion;
            this.defensa -= aumentoDefensaIlusion;
            this.velocidad -= aumentovelocidadIlusion;
            this.vidaMaxima -= aumentovidaMaximaIlusion;
            if (this.vidaActual > this.vidaMaxima) this.vidaActual = this.vidaMaxima;
            debilidato = true;
        }
        if ("lastima".equals(enemigo.getTipoDebuff())) duracionLastima++;
        if (duracionLastima >= 3) {
            if ("lastima".equals(enemigo.getTipoDebuff())) enemigo.setDaño(enemigo.getDaño() * 2);
            enemigo.setTipoDebuff("ninguno");
            duracionLastima = 0;
        }
    }

    @Override
    public int RecibirDaño(int dañoBase) {
        if (escudoActivo && !debilidato) {
            this.escudoActivo = false;
            return 0;
        }
        return super.RecibirDaño(dañoBase);
    }

    public void Recordar() {
        int azar = (int) (Math.random() * 100);
        if (azar < 40 && habilidad3usada) {
            sintoniaMemoria -= 20;
            habilidad3usada = false;
            recordandoPasado = true;
        } else if (azar < 20) {
            sintoniaMemoria -= 20;
            recordandoPasado = true;
        }
    }

    @Override public void pasivaActiva(int dato) {
        if (pasivaActiva) {
            this.daño += aumentoDañoIlusion;
            this.defensa += aumentoDefensaIlusion;
            this.velocidad += aumentovelocidadIlusion;
            this.vidaMaxima += aumentovidaMaximaIlusion;
        }
    }

    @Override public void atacar(PersonajesEstructura enemigo) {}
    @Override public void pasiva(PersonajesEstructura enemigo) { enemigo.RecibirDaño(this.daño); }
    @Override public void alRecibirDaño(int dañoRecibido, PersonajesEstructura atacante) {}



    public String getDescHabilidad1Dinamica() {
        int dañoDebil = 20 + (int) (this.daño * 0.02);
        int dañoReyLanza = 80 + (int) (this.daño * 0.22);
        int dañoReyReina = (this.daño / 2);
        int dañoGloria = 130 + (int) (this.daño * 0.35);

        return "Último Baile / Colmillo del Reino: \n\n" +
                "FORMA 1 (Rey): El Rey y la Reina embisten causando " + (dañoReyLanza + dañoReyReina) + " de daño. \n" +
                "FORMA 2 (Gloria): Estocadas perfectas de lanza que infligen " + dañoGloria + " y dan +10 Vel. \n" +
                "FORMA 3 (Debilitado): Dash lento de agonía causando " + dañoDebil + " de daño.";
    }

    public String getDescHabilidad2Dinamica() {
        return "Muro de Lealtad / Estandarte Invicto: \n\n" +
                "FORMA 1 (Rey): Invoca guardia para ser INMUNE al próximo golpe. \n" +
                "FORMA 2 (Gloria): Inmunidad total, +15 Def y restaura Sintonía al 100%. \n" +
                "FORMA 3 (Debilitado): Mirada de lástima que reduce el daño enemigo a la MITAD.";
    }

    public String getDescHabilidad3Dinamica() {
        int dañoDebil = 40 + (int) (this.daño * 0.05);
        int dañoReyLanza = 100;
        int dañoReyGuardia = (this.daño / 4) * 4;
        int dañoGloria = 210;

        return "Legión Eterna / Carga del Conquistador: \n\n" +
                "FORMA 1 (Rey): El ejército ilusorio embiste infligiendo " + (dañoReyLanza + dañoReyGuardia) + " de daño total. \n" +
                "FORMA 2 (Gloria): Carga montada definitiva de " + dañoGloria + " de daño. Reduce Vel enemiga. \n" +
                "FORMA 3 (Debilitado): Ataque errático de " + dañoDebil + " de daño.";
    }

    public String getDescHabilidad4Dinamica() {
        return "Aclamación de Gloria: \n\n" +
                "ACTIVA: Cuando la sintonía es baja (<30), el Rey recupera su gloria. \n" +
                "EFECTO: Restaura Sintonía al 100% y desbloquea las FORMAS 2 de las habilidades por 3 turnos. \n" +
                "POST-EFECTO: Al terminar, el Rey se debilita permanentemente.";
    }
}