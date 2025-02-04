/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopserver.networkLayer;
import com.juanf.factoryshopserver.clases.CSVExportador;
import com.juanf.factoryshopserver.clases.CreadorLogs;
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
            String clientIP = clientSocket.getInetAddress().getHostAddress();
            // Handle client requests
            String request = (String) objectInput.readObject();
            switch (request) {
                case "ADD":
                    Producto producto = (Producto) objectInput.readObject();
                    almacen.agregarProducto(producto);
                    CreadorLogs.log("ADD", clientIP, "Producto ID: " + producto.getId());
                    break;
                case "DELETE":
                    String productoid = (String) objectInput.readObject();
                    int id=Integer.parseInt(productoid);
                    almacen.eliminarProducto(id);
                    CreadorLogs.log("DELETE", clientIP, "Producto ID: " + id);
                    break;
                case "UPDATE":    
                    int idActualizar = Integer.parseInt((String) objectInput.readObject());
                    Producto nuevosDatos = (Producto) objectInput.readObject();
                    almacen.actualizarProducto(
                        idActualizar, 
                        nuevosDatos.getNombre(), 
                        nuevosDatos.getDescripcion(), 
                        nuevosDatos.getPrecio(), 
                        nuevosDatos.getCantidad()
                    );
                    CreadorLogs.log("UPDATE", clientIP, "Producto ID: " + idActualizar);
                    break;
                case "CSV":
                    String inventario= CSVExportador.generarCSVInventario(almacen.getProductos());
                    objectOutput.writeObject(inventario);
                    CreadorLogs.log("UPDATE", clientIP,"Inventario Generado");
            }

            // Send response back to client
            objectOutput.writeObject("Operacion exitosa");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
