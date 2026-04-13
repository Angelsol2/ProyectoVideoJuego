module org.example.proyectovideojuego {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.proyectovideojuego to javafx.fxml;
    exports org.example.proyectovideojuego;
}