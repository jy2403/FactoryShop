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
 * Esta clase representa un cliente TCP que se conecta a un servidor a través de una conexión SSL.
 * Permite enviar operaciones y productos al servidor y recibir respuestas.
 * 
 * @author juanf
 */
public class TCPClient {
    
    /**
     * La dirección del servidor al que se conectará el cliente.
     */
    private String serverAddress;
    /**
     * El puerto en el que el servidor está escuchando.
     */
    private int port;
    /**
     * El socket SSL utilizado para la conexión con el servidor.
     */
    private SSLSocket clientSocket;
    /**
     * El flujo de salida para enviar objetos al servidor.
     */
    private ObjectOutputStream outputStream;
    
    /**
     * El flujo de entrada para recibir objetos del servidor.
     */
    private ObjectInputStream inputStream;
    
    /**
     * Crea una nueva instancia de TCPClient con la dirección del servidor y el puerto proporcionados.
     * 
     * @param serverAddress Dirección del servidor al que se conectará el cliente.
     * @param port Puerto en el que el servidor está escuchando.
     */
    public TCPClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }
    
    /**
     * Establece una conexión SSL con el servidor y prepara los flujos de entrada y salida para la comunicación.
     * 
     * @throws IOException Si ocurre un error al intentar conectar con el servidor o al abrir los flujos.
     */
    public void connect() throws IOException {
        // Obtiene la fábrica de sockets SSL predeterminada.
        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        
        // Crea un socket SSL y lo conecta al servidor en la dirección y puerto especificados.
        clientSocket = (SSLSocket) socketFactory.createSocket(serverAddress, port);
        System.out.println("Conexión SSL establecida con el servidor.");
        
        // Inicializa la entrada y salida para la comunicación con el servidor.
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    /**
     * Envía una operación y un objeto Producto al servidor y recibe la respuesta.
     * 
     * @param operation Operación a realizar en el servidor.
     * @param producto Producto a enviar al servidor.
     * @return La respuesta del servidor, en formato String.
     */    
    public String sendOperation(String operation, Producto producto) {
        try {
            connect();
            
            // Envía la operación y el objeto Producto al servidor.
            outputStream.writeObject(operation);
            outputStream.writeObject(producto);
            outputStream.flush();  // Asegurar que se envíe

            String respuesta = (String) inputStream.readObject();

            // Si la respuesta es nula, devuelve "NOT_FOUND" para evitar errores por valores nulos.
            if (respuesta == null) {
                return "NOT_FOUND";  // Nunca devolver null
            }
            return respuesta;
        } catch (IOException | ClassNotFoundException e) {
            return "Error: " + e.getMessage();
        } finally {
            closeConnection();
        }
    }

    /**
     * Cierra la conexión con el servidor y libera los recursos utilizados.
     */   
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
