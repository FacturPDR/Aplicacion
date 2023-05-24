package com.facturpdr.aplicacion.auth.controladores;

import com.facturpdr.aplicacion.auth.servicios.AuthServicio;
import com.facturpdr.aplicacion.general.extensiones.VentanaExtension;
import com.facturpdr.aplicacion.general.utilidades.AlertaUtilidad;
import com.facturpdr.aplicacion.sesiones.servicios.SesionServicio;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class IniciarSesionControlador {
    public TextField correoElectronico;

    public PasswordField contrasena;

    private final AuthServicio authServicio = new AuthServicio();
    private final SesionServicio sesionServicio = new SesionServicio();
    private final VentanaExtension ventana = VentanaExtension.obtenerInstancia();

    @FXML
    public void manejarBotonAcceder() {
        if (correoElectronico.getText().isEmpty()) {
            AlertaUtilidad.error("Debes introducir un correo electrónico", "Por favor, introduce tu correo electrónico.");
            return;
        }

        boolean correoValido = correoElectronico.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        if (!correoValido) {
            AlertaUtilidad.error("El correo electrónico debe ser válido", "Por favor, introduce un correo electrónico válido.");
            return;
        }

        if (contrasena.getText().isEmpty()) {
            AlertaUtilidad.error("Debes introducir una contraseña", "Por favor, introduce una contraseña.");
            return;
        }

        /*
        boolean contresenaValido = contrasena.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)([A-Za-z\\d$@!%*?&]|[^ ]){8,}$");
        if (!contresenaValido) {
            AlertaUtilidad.error("La contraseña debe ser valida", "La contraseña debe tener al menos 8 caracteres, una mayúscula como mínimo, un número como mínimo.");
            return;
        }
        */

        int IDUsuario = authServicio.iniciarSesion(correoElectronico.getText(), contrasena.getText());
        if (IDUsuario == -1) {
            AlertaUtilidad.error("Error de autenticación", "El correo electronico o la contraseña no son validos, vuelve a intentarlo.");
            return;
        }

        sesionServicio.iniciarSesion(IDUsuario);
    }

    @FXML
    public void manejarEscenaRegistrarse() {
        ventana.cambiarEscena("auth/registrarse");
    }

    @FXML
    public void manejarEscenaOlvidarContrasena() {
        ventana.cambiarEscena("auth/olvidar-contrasena");
    }
}
