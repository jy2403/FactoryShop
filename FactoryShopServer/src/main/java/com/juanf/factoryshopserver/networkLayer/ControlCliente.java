/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopserver.networkLayer;
import com.juanf.factoryshared.clases.Producto;
import com.juanf.factoryshopserver.clases.*;
import java.io.*;
import java.net.Socket;

/**
 * La clase maneja las solicitudes de los clientes que se conectan
 * a un servidor a través de un socket. Cada cliente se maneja en un hilo separado, lo que
 * permite procesar múltiples solicitudes de manera concurrente.
 * 
 * @author juanf
 */

public class ControlCliente extends Thread {
    /**
     * El socket del cliente con el cual se establece la comunicación.
     */
    private Socket clientSocket;
    /**
     * La instancia del sistema de inventario donde se realizarán las operaciones.
     */
    private FactoryShop almacen;
    /**
     * Constructor de la clase que inicializa el socket del cliente
     * y la instancia del sistema de inventario.
     * 
     * @param socket El socket del cliente con el que se establece la comunicación.
     * @param almacen La instancia del almacén de productos donde se realizarán las operaciones.
     */
    public ControlCliente(Socket socket,FactoryShop almacen) {
        this.clientSocket = socket;
        this.almacen = almacen;
    }
    /**
     * Método que se ejecuta cuando el hilo es iniciado. Este método maneja
     * las solicitudes del cliente mediante la lectura y escritura de objetos a través
     * de streams.
     * Después de procesar la solicitud, el método envía una respuesta de operación exitosa
     * al cliente.
     */
    @Override
    public void run() {
        
        try (InputStream input = clientSocket.getInputStream();
             OutputStream output = clientSocket.getOutputStream();
             ObjectInputStream objectInput = new ObjectInputStream(input);
             ObjectOutputStream objectOutput = new ObjectOutputStream(output)) {
            String clientIP = clientSocket.getInetAddress().getHostAddress();
            // Handle client requests
            String request = (String) objectInput.readObject();
            switch (request) {
                case "ADD" -> {
                    Producto producto = (Producto) objectInput.readObject();
                    almacen.agregarProducto(producto);
                    CreadorLogs.log("ADD", clientIP, "Producto ID: " + producto.getId());
                }
                case "DELETE" -> {
                    Producto producto = (Producto) objectInput.readObject();
                    int id=producto.getId();
                    almacen.eliminarProducto(id);
                    CreadorLogs.log("DELETE", clientIP, "Producto ID: " + id);
                }
                case "UPDATE" -> {    
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
                }
                case "CSV" -> {
                    String inventario= almacen.generarInventario();
                    System.out.println(inventario);
                    objectOutput.writeObject(inventario);
                    CreadorLogs.log("UPDATE", clientIP,"Inventario Generado");
                }
            }

            // Send response back to client
            objectOutput.writeObject("Operacion exitosa");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
