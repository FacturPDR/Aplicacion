package com.facturpdr.aplicacion.inicio.controladores;

import com.facturpdr.aplicacion.general.extensiones.VentanaExtension;
import com.facturpdr.aplicacion.general.utilidades.AlertaUtilidad;
import com.facturpdr.aplicacion.sesiones.utilidades.ConfiguracionUtilidad;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class LateralControlador {

    @FXML
    public void manejarInicio(ActionEvent event) {
        VentanaExtension ventana = VentanaExtension.obtenerInstancia();

        ventana.cambiarEscena("inicio/inicio.fxml");
        ventana.cambiarTitulo("FacturPDR - Inicio");
    }

    @FXML
    public void manejarCerrarSesion(ActionEvent event) {
        VentanaExtension ventana = VentanaExtension.obtenerInstancia();

        Optional<ButtonType> cerrarSesion = AlertaUtilidad.confirmacion("Cerrar Sesión", null, "¿Estás seguro de que deseas cerrar sesión?", ButtonType.YES, ButtonType.NO);
        if (cerrarSesion.isPresent() && cerrarSesion.get() == ButtonType.YES) {
            ConfiguracionUtilidad.eliminarPrefencias();
            ventana.cerrar();
        }
    }
    @FXML
    public void manejarConfiguracion(ActionEvent event) {
        VentanaExtension ventana = VentanaExtension.obtenerInstancia();

        ventana.cambiarEscena("configuracion/configuracion.fxml");
        ventana.cambiarTitulo("FacturPDR - Configuracion");
    }
}
