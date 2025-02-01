/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.juanf.factoryshopclient;

import com.juanf.factoryshopclient.networkClient.TCPClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanf
 */
public class FactoryShopClient {

    public static void main(String[] args) {
         Properties p = new Properties();
        try {
            p.load(new FileInputStream(new File("configuration.properties")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FactoryShopClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FactoryShopClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sslRoute = p.getProperty("SSL_CERTIFICATE_ROUTE");
        String sslPassword = p.getProperty("SSL_PASSWORD");
        System.setProperty("javax.net.ssl.keyStore",sslRoute);
        System.setProperty("javax.net.ssl.keyStorePassword",sslPassword);
        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
        System.setProperty("javax.net.ssl.trustStore", sslRoute);
        System.setProperty("javax.net.ssl.trustStorePassword", sslPassword);
        System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
        Scanner in = new Scanner(System.in);
        System.out.println("Type your name");
        String name = in.nextLine();
        System.out.println("Type your last name");
        String lastName = in.nextLine();
        TCPClient client = new TCPClient("192.168.194.17", 9090);
        String response = client.sendMessage("Simon", "Gaviria");
        System.out.println(response);
    }
}
