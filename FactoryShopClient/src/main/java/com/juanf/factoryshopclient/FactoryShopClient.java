/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.juanf.factoryshopclient;

import com.juanf.factoryshopclient.gui.tienda;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 * Clase principal para el cliente de la tienda.
 * Se encarga de cargar la configuración SSL y lanzar la interfaz gráfica.
 * @author juanf
 */
public class FactoryShopClient {

    /**
     * Método principal que inicia la aplicación.
     * 
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(new File("config.properties")));
            
            String sslRoute = p.getProperty("SSL_CERTIFICATE_ROUTE");
            String sslPassword = p.getProperty("SSL_PASSWORD");
            
            // Configura las propiedades del sistema para SSL/TLS.
            System.setProperty("javax.net.ssl.keyStore", sslRoute);
            System.setProperty("javax.net.ssl.keyStorePassword", sslPassword);
            System.setProperty("javax.net.ssl.trustStore", sslRoute);
            System.setProperty("javax.net.ssl.trustStorePassword", sslPassword);
            
            System.setProperty("java.net.ssl.trustStoreType","PKCS12");
            
            // Inicia la interfaz gráfica en el hilo de eventos de Swing.
            java.awt.EventQueue.invokeLater(() -> {
                new tienda("172.24.32.140", 443).setVisible(true);
            });
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar configuración: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

