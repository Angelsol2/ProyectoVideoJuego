package org.example.proyectovideojuego;

public class Asesino extends PersonajesEstructura {
    private int acumulacionPasiva=0;
    private int PasivasActivadas=0;
    private int disminucionDefensa;
    private int disminucionVelocidad;
    private int aumentoDaño;
    private int aumentoVelocidad;
    private int contratoTiempo=0;
    private boolean contratoActivo=false;
    public Asesino(String nombre, int vidaMaxima, int daño, int defensa, int velocidad, String nombrePasiva, String nombreHab1, String nombreHab2, String nombreHab3, String nombreHab4 ,int probEsquivar  ) {
        super(nombre, vidaMaxima, daño, defensa, velocidad, nombrePasiva, nombreHab1, nombreHab2, nombreHab3, nombreHab4,  probEsquivar);

    }

    @Override
    public void atacar(PersonajesEstructura enemigo) {
             enemigo.RecibirDaño(this.daño);
    }

    @Override
    public void pasivaActiva(int dato) {
    }

    @Override
    public void pasiva(PersonajesEstructura enemigo) {
        if (acumulacionPasiva >3) {
            enemigo.setTipoDebuff("Sangrado");
            enemigo.setDuracionDebuff(1);
            PasivasActivadas++;
        }
        if ("Sangrado".equals(enemigo.getTipoDebuff())) {
            for (int i = 0; i <6 ; i++) {
           enemigo.RecibirDaño((int) (this.daño*.10));
            }
        }
    }

    @Override
    public void habilidad1(PersonajesEstructura enemigo) {
        if (acumulacionPasiva == 3) {
            for (int i = 0; i < 2; i++) {
                int daño= 100 + (int) (this.daño * .20);
                enemigo.setVelocidad(enemigo.velocidad- (int) (this.daño*.15));
                enemigo.RecibirDaño(daño);
            }
            acumulacionPasiva++;
            pasiva(enemigo);
        }
        else{
            for (int i = 0; i < 2; i++) {
                int daño= 50 + (int) (this.daño * .20);
                enemigo.RecibirDaño(daño);
            }
            acumulacionPasiva++;
        }

    }

    @Override
    public void habilidad2(PersonajesEstructura enemigo) {
        if (acumulacionPasiva == 3) {
            int daño= 80 + (int) (this.daño * .3);
            enemigo.RecibirDaño(daño);
            enemigo.setDefensa(enemigo.defensa- (int) (this.daño*.15));
            acumulacionPasiva++;
            pasiva(enemigo);
        }
        else{
            int daño= 80;
            enemigo.RecibirDaño(daño);
            acumulacionPasiva++;
        }

    }

    @Override
    public void habilidad3(PersonajesEstructura enemigo) {
        if (acumulacionPasiva == 3) {
            contratoActivo=true;
            int daño = 5 + (int) (this.daño*.10);
            disminucionDefensa = (int) (this.daño*.30);
            disminucionVelocidad = (int) (this.daño*.40);
          enemigo.RecibirDaño(daño);
          enemigo.setVelocidad(enemigo.velocidad-disminucionVelocidad);
          enemigo.setDefensa(enemigo.defensa-disminucionDefensa);
            acumulacionPasiva++;
            pasiva(enemigo);
        }
        else{
            contratoActivo=true;
            int daño = 5 + (int) (this.daño*.10);
            disminucionDefensa = (int) (this.daño*.20);
            disminucionVelocidad = (int) (this.daño*.25);
           enemigo.RecibirDaño(daño);
            enemigo.setVelocidad(enemigo.velocidad-disminucionVelocidad);
            enemigo.setDefensa(enemigo.defensa-disminucionDefensa);
            acumulacionPasiva++;
        }

    }

    @Override
    public void ultimate(PersonajesEstructura enemigo) {
        this.probEsquivar = (int) (this.daño*.40);
        if (probEsquivar >= 100) {
            probEsquivar=60;
        }
        aumentoVelocidad=(int) (this.velocidad*.40) * PasivasActivadas;
        aumentoDaño=(int) (this.daño*.40) * PasivasActivadas ;

        this.daño+=aumentoDaño;
        this.velocidad+=aumentoVelocidad;
    }
    @Override
    public void terminarTurno(PersonajesEstructura enemigo) {
      Verificacion(enemigo);
    }

    @Override
    public void alRecibirDaño(int dañoRecibido, PersonajesEstructura atacante) {

    }

    public void Verificacion(PersonajesEstructura enemigo){
        if (acumulacionPasiva>3){
            enemigo.setTipoDebuff("ninguno");
            enemigo.setDuracionDebuff(0);
            acumulacionPasiva=0;
        }
        if (contratoActivo){
            contratoTiempo++;
        }

        if (contratoTiempo>=3){
            contratoActivo=false;
            enemigo.setVelocidad(enemigo.velocidad+disminucionVelocidad);
            enemigo.setDefensa(enemigo.defensa+disminucionDefensa);
            disminucionDefensa = 0;
            disminucionVelocidad =0;

        }

    }

    public String getDescPasiva() {
        return "Marca del Asesino: " + getNombre() + " marca a su enemigo con cada habilidad que lance. Al llegar a 3 marcas, la siguiente habilidad tendrá un efecto mejorado y reventará las marcas, causando un desangrado intenso (6 golpes de " + (int)(this.daño * 0.10) + " de daño).";
    }

    public String getDescHabilidad1Dinamica() {
        int bonoDanoNormal = (int) (this.daño * 0.20);
        int totalNormal = 50 + bonoDanoNormal;
        int totalMejorado = 100 + bonoDanoNormal;
        int roboVelocidad = (int) (this.daño * 0.15);

        return "Corte Doble: Lanza 2 golpes rápidos. Cada uno inflige 50 + " + bonoDanoNormal + " (" + totalNormal + " daño total). \n" +
                " CON 3 MARCAS: El daño base sube a 100 + " + bonoDanoNormal + " (" + totalMejorado + " daño total) por golpe y además reduce la velocidad enemiga en " + roboVelocidad + ".";
    }

    public String getDescHabilidad2Dinamica() {
        int bonoDanoMejorado = (int) (this.daño * 0.30);
        int totalMejorado = 80 + bonoDanoMejorado;
        int reduccionDefensa = (int) (this.daño * 0.15);

        return "Golpe Certero: Un ataque preciso que inflige 80 de daño base directo. \n" +
                " CON 3 MARCAS: El ataque se potencia infligiendo 80 + " + bonoDanoMejorado + " (" + totalMejorado + " daño total) y penetra armadura, reduciendo la defensa del enemigo en " + reduccionDefensa + ".";
    }

    public String getDescHabilidad3Dinamica() {
        int bonoDano = (int) (this.daño * 0.10);
        int totalDano = 5 + bonoDano;

        int defNormal = (int) (this.daño * 0.20);
        int velNormal = (int) (this.daño * 0.25);

        int defMejorada = (int) (this.daño * 0.30);
        int velMejorada = (int) (this.daño * 0.40);

        return "Contrato Mortal: Inflige " + totalDano + " de daño y debilita al enemigo por 3 turnos, bajando su defensa en " + defNormal + " y velocidad en " + velNormal + ". \n" +
                "CON 3 MARCAS: La maldición se vuelve paralizante, reduciendo la defensa en " + defMejorada + " y la velocidad en " + velMejorada + ".";
    }

    public String getDescHabilidad4Dinamica() {
        int esquivaCalculada = (int) (this.daño * 0.40);
        int esquivaFinal = esquivaCalculada >= 100 ? 60 : esquivaCalculada;

        int bonoVelocidadTotal = (int) (this.velocidad * 0.40) * PasivasActivadas;
        int bonoDanoTotal = (int) (this.daño * 0.40) * PasivasActivadas;

        return "Danza de las Sombras (Ultimate): " + getNombre() + " se vuelve inalcanzable, aumentando su probabilidad de esquivar a " + esquivaFinal + "%. \n" +
                "Además, por cada vez que reventó marcas en la pelea (" + PasivasActivadas + " veces), aumenta permanentemente su daño en +" + bonoDanoTotal + " y velocidad en +" + bonoVelocidadTotal + ".";
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
