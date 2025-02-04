/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopclient.networkClient;

import com.juanf.factoryshared.clases.Producto;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author juanf
 */
public class TCPClient {
    private String serverAddress;
    private int port;
    private SSLSocket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public TCPClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }
    
    public void connect() throws IOException {
        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        clientSocket = (SSLSocket) socketFactory.createSocket(serverAddress, port);
        System.out.println("Conexión SSL establecida con el servidor.");
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }
    
    public String sendOperation(String operation, Producto producto) {
        try {
            connect();
            outputStream.writeObject(operation);
            outputStream.writeObject(producto);
            return (String) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return "Error: " + e.getMessage();
        } finally {
            closeConnection();
        }
    }
    
    public void closeConnection() {
        try {
            if (outputStream != null) outputStream.close();
            if (inputStream != null) inputStream.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException ex) {
            System.err.println("Error al cerrar conexión: " + ex.getMessage());
        }
    }
}
