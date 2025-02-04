/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopserver.networkLayer;

import com.juanf.factoryshopserver.clases.FactoryShop;
import java.io.IOException;
import javax.net.ssl.*;

/**
 * La clase implementa un servidor de comunicaciones seguro utilizando SSL
 * para recibir y procesar solicitudes de clientes a través de un socket.
 * 
 * @author juanf
 */
public class TCPServer {
    /**
     * El puerto en el que el servidor escucha las conexiones entrantes de los clientes.
     */
    private int port;
    /**
     * La instancia del sistema de inventario que gestiona los productos.
     */
    private FactoryShop almacen;
    
    /**
     * Constructor de la clase que inicializa el puerto y el almacén.
     */
    public TCPServer() {
        this.port = 443;
        this.almacen = new FactoryShop();
    }
    /**
     * Método que inicia el servidor SSL, habilitando la aceptación de conexiones
     * entrantes de clientes en el puerto especificado.
     * @throws IOException Si ocurre un error al crear el socket o aceptar conexiones.
     */
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
