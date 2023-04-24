package com.facturpdr.aplicacion.general.apariencia;

import com.facturpdr.aplicacion.inicio.controladores.lateralControlador;
import com.facturpdr.aplicacion.inicio.controladores.cabeceroControlador;
import com.facturpdr.aplicacion.inicio.utilidades.estableceInicio;

public class Temas {

    static private String temaActual;

    public static void Seleccion(String tema) {
        switch (tema) {
            case "Dark":
                temaActual = "Dark";
                 Temas.Dark(lateralControlador.getControlador() , cabeceroControlador.getControlador() );
                break;
            case "Light":
                temaActual = "Light";
                Temas.Light(lateralControlador.getControlador() , cabeceroControlador.getControlador());
                break;
            default:
                temaActual = "Default";
                Temas.Default(lateralControlador.getControlador() ,  cabeceroControlador.getControlador());
                break;
        }
    }


    public static void setTemaActual(String temaActual) {
        Temas.temaActual = temaActual;
    }


    public static String getTemaActual() {
        return temaActual;
    }


    public static void establecerColor(lateralControlador pl , cabeceroControlador cabecero ,
                                      String superior , String Lateral , String Botones,String Texto){

        estableceInicio.setColorCabecero(cabecero.getCabecero(), superior ) ;

        //Colores del Panel Lateral.
        estableceInicio.setColorPanelLateral(pl.getPanel_Lateral() , Lateral );

        estableceInicio.setColorBotones(pl.getBoton_Home() , Botones );
        estableceInicio.setColorBotones(pl.getBoton_ListadoFacturas() , Botones );
        estableceInicio.setColorBotones(pl.getBoton_ListadoEmpleados() , Botones );
        estableceInicio.setColorBotones(pl.getBoton_ListadoClientes() , Botones );
        estableceInicio.setColorBotones(pl.getBoton_Configuracion() , Botones );

        //Color de los textos del panel lateral.
        estableceInicio.setColorTexto(pl.getTextGestionClientes() , Texto );
        estableceInicio.setColorTexto(pl.getTextGestionEmpleados(), Texto);
        estableceInicio.setColorTexto(pl.getTextGestionFacturas() , Texto);
    }

    public static void Default(lateralControlador pl , cabeceroControlador cabecero) {
        establecerColor(pl, cabecero, Colores.ColorVerde , Colores.ColorClaroGris, Colores.ColorVerde ,Colores.ColorBlanco);
    }

    public static void Light(lateralControlador pl, cabeceroControlador cabecero) {
        establecerColor(pl, cabecero, Colores.ColorBlancoG, Colores.ColorGrisClaroG, Colores.ColorGrey, Colores.ColorGrisOscuroG);
    }

    public static void Dark(lateralControlador pl , cabeceroControlador cabecero) {
        establecerColor(pl , cabecero , Colores.ColorGrey , Colores.ColorGrey , Colores.ColorGreen , Colores.ColorNegro);
        estableceInicio.ImagenesOscuras(pl.getImagenHome() , pl.getImagenCliente() , pl.getImagenConfiguracion() , pl.getImagenEmpleados() , pl.getImagenFactura());
    }

}