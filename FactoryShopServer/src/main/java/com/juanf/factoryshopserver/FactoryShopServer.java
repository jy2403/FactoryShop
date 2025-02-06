/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.juanf.factoryshopserver;

import com.juanf.factoryshopserver.networkLayer.TCPServer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.*;

/**
 * Clase principal para el servidor de la tienda.
 * Configura la conexión SSL utilizando los parámetros especificados en el archivo
 * de propiedades y luego inicia el servidor TCP.
 * 
 * @author juanf
 */
public class FactoryShopServer {

    /**
     * El método principal que inicia el servidor.
     *
     * @param args Argumentos de línea de comandos.
     */

    public static void main(String[] args) {
        
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(new File("config.properties")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sslRoute = p.getProperty("SSL_CERTIFICATE_ROUTE");
        String sslPassword = p.getProperty("SSL_PASSWORD");
        
        // Configura las propiedades del sistema para SSL/TLS.    
        System.setProperty("javax.net.ssl.keyStore", sslRoute);
        System.setProperty("javax.net.ssl.keyStorePassword", sslPassword);
        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
        System.setProperty("javax.net.ssl.trustStore", sslRoute);
        System.setProperty("javax.net.ssl.trustStorePassword", sslPassword);
        System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
        
        new TCPServer().start();
        
    }
}
