/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopserver.networkLayer;

import com.juanf.factoryshopserver.clases.FactoryShop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanf
 */
import javax.net.ssl.*;

public class TCPServer {
    private int port;
    private FactoryShop almacen;

    public TCPServer() {
        this.port = 9090;
        this.almacen = new FactoryShop();
    }

    public void start() {
        try {
            SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(port);
            System.out.println("SSL Server started on port " + port);
            
            while (true) {
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                new ControlCliente(clientSocket, almacen).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
