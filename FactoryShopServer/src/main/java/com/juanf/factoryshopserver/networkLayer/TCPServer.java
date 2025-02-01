/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopserver.networkLayer;

import com.juanf.factoryshopserver.clases.FactoryShop;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author juanf
 */
public class TCPServer {
    private int port=12345;
    private FactoryShop almacen;

    public TCPServer() {
        this.almacen = new FactoryShop();
    }
    
    public void start(){
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ControlCliente(clientSocket, almacen).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
