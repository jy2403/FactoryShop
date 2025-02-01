/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopserver.networkLayer;
import com.juanf.factoryshopserver.clases.FactoryShop;
import com.juanf.factoryshopserver.clases.Producto;
import java.io.*;
import java.net.Socket;
/**
 *
 * @author juanf
 */


public class ControlCliente extends Thread {
    private Socket clientSocket;
    private FactoryShop almacen;

    public ControlCliente(Socket socket,FactoryShop almacen) {
        this.clientSocket = socket;
        this.almacen = almacen;
    }

    public void run() {
        try (InputStream input = clientSocket.getInputStream();
             OutputStream output = clientSocket.getOutputStream();
             ObjectInputStream objectInput = new ObjectInputStream(input);
             ObjectOutputStream objectOutput = new ObjectOutputStream(output)) {

            // Handle client requests
            String request = (String) objectInput.readObject();
            switch (request) {
                case "ADD":
                    Producto producto = (Producto) objectInput.readObject();
                    almacen.agregarProducto(producto);
                    break;
                case "DELETE":
                    String productoid = (String) objectInput.readObject();
                    int id=Integer.parseInt(productoid);
                    almacen.eliminarProducto(id);
                    break;
                case "UPDATE":
                    String name = (String) objectInput.readObject();
                    break;
            }

            // Send response back to client
            objectOutput.writeObject("Operacion exitosa");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
