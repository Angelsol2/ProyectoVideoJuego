package org.example.proyectovideojuego;

abstract class PersonajesEstructura {
    protected int vidaActual;
    protected int vidaMaxima;
    protected int daño;
    protected int defensa;
    protected int velocidad;
    protected String nombrePasiva;
    protected String nombreHab1;
    protected String nombreHab2;
    protected String nombreHab3;
    protected String nombreHab4;
    protected String nombre;
    protected boolean debuff;
    protected String descHab1;
    protected String descHab2;
    protected String descHab3;
    protected String descHab4;
    protected int probEsquivar;

    protected String tipoDebuff = "Ninguno";
    protected int duracionDebuff = 0;

    public PersonajesEstructura(String nombre, int vidaMaxima, int daño, int defensa, int velocidad,
                                String nombrePasiva, String nombreHab1, String nombreHab2, String nombreHab3, String nombreHab4, int probEsquivar) {
        this.vidaMaxima = vidaMaxima;
        this.vidaActual = vidaMaxima;
        this.daño = daño;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.debuff = false;
        this.nombre = nombre;
        this.nombrePasiva = nombrePasiva;
        this.nombreHab1 = nombreHab1;
        this.nombreHab2 = nombreHab2;
        this.nombreHab3 = nombreHab3;
        this.nombreHab4 = nombreHab4;
        this.probEsquivar = probEsquivar;
    }

    public int RecibirDaño(int cantidadDeDanoEntrante) {
        int dado = (int)(Math.random() * 100) + 1;
        if (dado <= this.probEsquivar) {
            return 0;
        }
        double multiplicador = 100.0 / (100.0 + this.defensa);
        int dañoFinal = (int) (cantidadDeDanoEntrante * multiplicador);
        this.vidaActual -= dañoFinal;
        if (this.vidaActual <= 0) {
            this.vidaActual = 0;
        }
        if (dañoFinal >0) {
            alRecibirDaño(dañoFinal, null);
        }
        return dañoFinal;
    }



    public String getNombre() {
        return nombre;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getDaño() {
        return daño;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public boolean tieneDebuff() {
        return debuff;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

    public void setDaño(int daño) {
        this.daño = daño;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public void setDebuff(boolean debuff) {
        this.debuff = debuff;
    }


    public void setDuracionDebuff(int duracionDebuff) {
        this.duracionDebuff = duracionDebuff;
    }

    public String getTipoDebuff() {
        return tipoDebuff;
    }

    public void setTipoDebuff(String tipoDebuff) {
        this.tipoDebuff = tipoDebuff;
    }

    public void setProbEsquivar(int probEsquivar) {
        this.probEsquivar = probEsquivar;
    }

    public String getNombrePasiva() {
        return nombrePasiva;
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

    public int getDuracionDebuff() {
        return duracionDebuff;
    }

    public String getDescHab1() {
        return descHab1;
    }

    public String getDescHab2() {
        return descHab2;
    }

    public String getDescHab3() {
        return descHab3;
    }

    public String getDescHab4() {
        return descHab4;
    }

    public int getProbEsquivar() {
        return probEsquivar;
    }



    public abstract void atacar(PersonajesEstructura enemigo);

    public abstract void pasivaActiva(int dato);


    public abstract void pasiva(PersonajesEstructura enemigo);

    public abstract void habilidad1(PersonajesEstructura enemigo);

    public abstract void habilidad2(PersonajesEstructura enemigo);

    public abstract void habilidad3(PersonajesEstructura enemigo);

    public abstract void ultimate(PersonajesEstructura enemigo);

    public abstract void terminarTurno(PersonajesEstructura enemigo);
    public abstract void alRecibirDaño(int dañoRecibido, PersonajesEstructura atacante);

}
