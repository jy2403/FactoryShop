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
 *
 * @author juanf
 */
public class FactoryShopClient {

    public static void main(String[] args) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(new File("config.properties")));
            
            String sslRoute = p.getProperty("SSL_CERTIFICATE_ROUTE");
            String sslPassword = p.getProperty("SSL_PASSWORD");
            
            System.setProperty("javax.net.ssl.keyStore", sslRoute);
            System.setProperty("javax.net.ssl.keyStorePassword", sslPassword);
            System.setProperty("javax.net.ssl.trustStore", sslRoute);
            System.setProperty("javax.net.ssl.trustStorePassword", sslPassword);
            
            java.awt.EventQueue.invokeLater(() -> {
                new tienda("172.24.32.140",9090).setVisible(true);
            });
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar configuración: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
