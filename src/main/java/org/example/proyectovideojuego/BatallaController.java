package org.example.proyectovideojuego;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class BatallaController {

    // --- BARRAS DE VIDA (Los rectángulos verdes) ---
    @FXML private Rectangle rectVidaJugador;
    @FXML private Rectangle rectVidaEnemigo;

    // --- TEXTOS E IMÁGENES ---
    @FXML private Label lblRonda;
    @FXML private TextArea txtRegistroCombate;
    @FXML private ImageView imgJugador;
    @FXML private ImageView imgEnemigo;

    // --- MENÚ INFERIOR Y BOTONES ---
    @FXML private GridPane panelMenuPrincipal;
    @FXML private Button btnBatalla;
    @FXML private Button btnEquipo;
    @FXML private Button btnRendirse;

    @FXML private Button btnHab1;
    @FXML private Button btnHab2;
    @FXML private Button btnHab3;
    @FXML private Button btnHab4;
     PeleadorZombie jugador = new PeleadorZombie("Zombie",850,45,30,15,"Cerebros" , "Mordisco" , "Aliento putrefacto" , "Festin de carne" , "Canibalismo" , 0);
    Asesino enemigo = new Asesino(
            "Sombra",
            350,
            65,
            10,
            85,
            "Marca del Asesino",
            "Corte Doble",
            "Golpe Certero",
            "Contrato Mortal",
            "Danza de las Sombras",
            15
    );



    @FXML
    public void initialize() {
        txtRegistroCombate.setEditable(false);
        txtRegistroCombate.setText("Comienza la batalla entre: " + jugador + "VS "  + enemigo +", ¿Quien saldra vivo de este combate?");
          btnHab1.setText(jugador.getNombreHab1());
        btnHab2.setText(jugador.getNombreHab2());
        btnHab3.setText(jugador.getNombreHab3());
        btnHab4.setText(jugador.getNombreHab4());
    }

    @FXML
    public void botonHab1(){
        btnHab1.setOnAction(event -> {

        });
    }
    @FXML
    public void botonHab2(){
        btnHab2.setOnAction(event -> {

        });
    }
    @FXML
    public void botonHab3(){
        btnHab3.setOnAction(event -> {

        });
    }
    @FXML
    public void botonHab4(){
        btnHab4.setOnAction(event -> {

        });
    }

    private void actualizarBarrasDeVida() {
        // Calculamos y aplicamos la vida del Jugador (usando Math.max para evitar barras negativas)
        double porcentajeJugador = (double) jugador.getVidaActual() / jugador.getVidaMaxima();
        // Math.max asegura que si el porcentaje es negativo (vida bajo cero), se quede en 0
        rectVidaJugador.setWidth(Math.max(0, 95 * porcentajeJugador));

        // Calculamos y aplicamos la vida del Enemigo
        double porcentajeEnemigo = (double) enemigo.getVidaActual() / enemigo.getVidaMaxima();
        rectVidaEnemigo.setWidth(Math.max(0, 95 * porcentajeEnemigo));
    }

}

