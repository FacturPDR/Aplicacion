package com.facturpdr.aplicacion.facturas.controladores;

import com.facturpdr.aplicacion.general.extensiones.BDExtension;
import com.facturpdr.aplicacion.sesiones.utilidades.PreferenciaUtilidad;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ControladorCrearFactura {

    @FXML
    private ChoiceBox<String> textTipoPieza;
    @FXML
    private ChoiceBox<Integer> textTipoTamanno;
    @FXML
    private ChoiceBox<String> listadoClientes;
    @FXML
    private TextField textTipoMaterial;
    @FXML
    private TextField textTipoPintura;
    @FXML
    private TextField textTipoAluminio;
    @FXML
    private ChoiceBox<String> listadoEmpleados;

    @FXML
    private TextField textMatricula;
    @FXML
    private TextField textPrecio;
    @FXML
    private TextField textNotaExterna;
    @FXML
    private TextField textNotaInterna;
    @FXML
    private TextField textCantidad;
    @FXML
    private TextField textManoDeObra;

    @FXML
    private DatePicker textFecha;

    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAnnadir;

    private void rellenapiezas() {
        // Obtener la lista de elementos del ChoiceBox
        ObservableList<String> items = textTipoPieza.getItems();

        // Añadir las piezas al ChoiceBox
        items.addAll("ADI", "PDI", "PTI", "MI", "ATI", "ADD", "PDD", "PTD", "MD", "ATP", "T", "C", "M");
    }

    private void rellenatamano() {
        ObservableList<Integer> items = textTipoTamanno.getItems();

        // Añadir los valores de tamaño en secuencia de 10 en 10 hasta 80
        for (int i = 10; i <= 40; i += 10) {
            items.add(i);
        }
    }

    public void initialize() {
        try {
            BDExtension.conectarse();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Connection conn = BDExtension.conexion;
        rellenapiezas();
        rellenatamano();

        try {
            String query = "SELECT NOMBRE_COMPLETO FROM clientes";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            listadoClientes.getItems().clear();
            while (resultSet.next()) {
                String nombreCliente = resultSet.getString("NOMBRE_COMPLETO");
                listadoClientes.getItems().add(nombreCliente);
            }
            String query2 = "SELECT NIF FROM EMPLEADOS";
            Statement statement2 = conn.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(query2);
            listadoEmpleados.getItems().clear();
            while (resultSet2.next()) {
                String nombreEmpleado = resultSet2.getString("NIF");
                listadoEmpleados.getItems().add(nombreEmpleado);
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickCancelar() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void clickAnnadir() throws SQLException {
        String tipoPieza = textTipoPieza.getValue();
        Integer tipoTamanno = textTipoTamanno.getValue();
        String tipoMaterial = textTipoMaterial.getText();
        String tipoPintura = textTipoPintura.getText();
        String tipoAluminio = textTipoAluminio.getText();
        String nombreCliente = listadoClientes.getValue();
        String nifEmpleado = listadoEmpleados.getValue();
        String matricula = textMatricula.getText();
        String precio = textPrecio.getText();
        String cantidad = textCantidad.getText();
        String manoDeObra = textManoDeObra.getText();
        String notaInterna = textNotaInterna.getText();
        String notaExterna = textNotaExterna.getText();
        LocalDate localDate = textFecha.getValue();
        java.util.Date fechaActual = new java.util.Date();
        java.sql.Date fechaCreacion = new java.sql.Date(fechaActual.getTime());
        int valorPintura = tipoPintura.equalsIgnoreCase("SI") ? 1 : 0;
        int valorAluminio = tipoAluminio.equalsIgnoreCase("SI") ? 1 : 0;
        int valorMaterial = tipoMaterial.equalsIgnoreCase("SI") ? 1 : 0;
        BDExtension.conectarse();
        Connection conn = BDExtension.conexion;

        // Realizar la consulta para obtener el ID del cliente
        String query = "SELECT id FROM clientes WHERE nombre_completo = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, nombreCliente);
        ResultSet rs = stmt.executeQuery();

        // Obtener el ID del cliente
        int idCliente = 0;
        if (rs.next()) {
            idCliente = rs.getInt("id");
        } else {
            System.out.println("No se encontró ningún cliente con el nombre completo especificado.");
            return;
        }

        String insertSQL = "INSERT INTO FACTURAS (id_cliente, NIF_empleado,matricula, coste_mano_obra, nota_interna, nota_externa, fecha_creacion, fecha_vencimiento, tipo, material, aluminio, pintura, tamano, cantidad, precio_unitario) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        java.sql.Date fecha = null;
        if (localDate != null) {
            fecha = java.sql.Date.valueOf(localDate);
        }

        stmt = conn.prepareStatement(insertSQL);
        stmt.setInt(1, idCliente);
        stmt.setString(2, nifEmpleado);
        stmt.setString(3, manoDeObra);
        stmt.setString(4, matricula);
        stmt.setString(5, notaInterna);
        stmt.setString(6, notaExterna);
        stmt.setDate(7, fechaCreacion);
        stmt.setDate(8, fecha); // Aquí debes proporcionar la fecha de vencimiento correspondiente
        stmt.setString(9, tipoPieza);
        stmt.setInt(10, valorMaterial);
        stmt.setInt(11, valorAluminio);
        stmt.setInt(12, valorPintura);
        stmt.setInt(13, tipoTamanno);
        stmt.setString(14, cantidad);
        stmt.setString(15, precio);

        try {
            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " filas insertadas.");
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar los objetos ResultSet, PreparedStatement o Connection: " + e.getMessage());
            }
        }

        Stage stage = (Stage) btnAnnadir.getScene().getWindow();
        stage.close();
    }
}
