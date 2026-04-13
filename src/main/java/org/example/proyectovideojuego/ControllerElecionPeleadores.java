package org.example.proyectovideojuego;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ControllerElecionPeleadores {
    // --- CONTENEDOR PRINCIPAL ---
    @FXML
    private BorderPane panelSeleccionPrincipal;

    // --- CUADRÍCULA DE SELECCIÓN (Los 6 botones) ---
    @FXML
    private Button btnSeleccion1;
    @FXML
    private Button btnSeleccion2;
    @FXML
    private Button btnSeleccion3;
    @FXML
    private Button btnSeleccion4;
    @FXML
    private Button btnSeleccion5;
    @FXML
    private Button btnSeleccion6;

    // --- VISOR DEL PERSONAJE (Derecha) ---
    @FXML
    private ImageView imgPersonajeDestacado;

    // Etiquetas de valores dinámicos
    @FXML
    private Label lblValorAtaque;
    @FXML
    private Label lblValorDefensa;
    @FXML
    private Label lblValorCritico;
    @FXML
    private Label lblValorVida;
    @FXML
    private Label lblValorVelocidad;

    // --- BARRA INFERIOR (Equipo/Enemigos) ---
    @FXML
    private ImageView imgEquipo1;
    @FXML
    private ImageView imgEquipo2;
    @FXML
    private ImageView imgEquipo3;
    @FXML
    private ImageView imgEquipo4;
    @FXML
    private ImageView imgEquipo5;


    public void botonHab1(){
        btnSeleccion1.setOnAction(event -> {
            PeleadorZombie zombie = new PeleadorZombie("Zombie",850,45,30,15,"Cerebros" , "Mordisco" , "Aliento putrefacto" , "Festin de carne" , "Canibalismo" , 0);
            lblValorAtaque.setText(String.valueOf(zombie.getDaño()));
            lblValorDefensa.setText(String.valueOf(zombie.getDefensa()));
            lblValorCritico.setText("0%");
            Image nuevaImagen = new Image(getClass().getResourceAsStream("imagenes\\perrozombiecompleto.png"));
            imgPersonajeDestacado.setImage(nuevaImagen);
            lblValorVida.setText(String.valueOf(zombie.getVidaMaxima()));
            lblValorVelocidad.setText(String.valueOf(zombie.getVelocidad()));
            generarEnemigoAleatorio("Zombie");
        });


    }
    @FXML
    public void botonHab2(){
        btnSeleccion2.setOnAction(event -> {
            Asesino Talon = new Asesino("Sombra", 350, 65, 10, 85, "Marca del Asesino", "Corte Doble", "Golpe Certero", "Contrato Mortal", "Danza de las Sombras", 15);
                    lblValorAtaque.setText(String.valueOf(Talon.getDaño()));
            lblValorDefensa.setText(String.valueOf(Talon.getDefensa()));
            lblValorCritico.setText(String.valueOf(Talon.getProbEsquivar()) + "%");
            lblValorVida.setText(String.valueOf(Talon.getVidaMaxima()));
            lblValorVelocidad.setText(String.valueOf(Talon.getVelocidad()));
            generarEnemigoAleatorio("Asesino");

        });

    }


    private PersonajesEstructura generarEnemigoAleatorio(String claseExcluida) {
        PersonajesEstructura enemigoGenerado = null;
        boolean enemigoValido = false;

        while (enemigoValido == false) {

            int dado = (int) (Math.random() * 3);

            switch (dado) {
                case 0:
                    // Si el jugador NO eligió Zombie, el enemigo puede ser Zombie
                    if (!claseExcluida.equals("Zombie")) {
                        enemigoGenerado = new PeleadorZombie("Gargantúa Enemigo", 850, 45, 30, 15, "Infección", "Mordisco", "Golpe Tóxico", "Festín", "Canibalismo", 0);
                        enemigoValido = true;
                    }
                    break;
                case 1:
                    if (!claseExcluida.equals("Asesino")) {
                        enemigoGenerado = new Asesino("Sombra Enemiga", 350, 65, 10, 85, "Marca", "Corte", "Golpe", "Contrato", "Danza", 15);
                        enemigoValido = true;
                    }
                    break;
            }
        }
        return enemigoGenerado;
    }
}
