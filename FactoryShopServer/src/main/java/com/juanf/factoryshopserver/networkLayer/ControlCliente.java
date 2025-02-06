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
        
        
        try (
             // Obtiene la entrada y salida desde el socket para recibir datos del cliente   
             InputStream input = clientSocket.getInputStream();
             OutputStream output = clientSocket.getOutputStream();
             
             // Permite leer y enviar datos a través de la entrada y salida
             ObjectInputStream objectInput = new ObjectInputStream(input);
             ObjectOutputStream objectOutput = new ObjectOutputStream(output)) {
            
            // Se obtiene la dirección IP y la respuesta del cliente
            String clientIP = clientSocket.getInetAddress().getHostAddress();
            String request = (String) objectInput.readObject();
            
            // Se maneja la solicitud según el tipo de operación
            switch (request) {
                case "ADD" -> {
                    
                    // Se agrega el producto al almacén
                    Producto producto = (Producto) objectInput.readObject();
                    almacen.agregarProducto(producto);
                    CreadorLogs.log("ADD", clientIP, "Producto ID: " + producto.getId());
                }
                case "DELETE" -> {
                    
                    // Se elimina el producto por su ID
                    Producto producto = (Producto) objectInput.readObject();
                    int id=producto.getId();
                    almacen.eliminarProducto(id);
                    CreadorLogs.log("DELETE", clientIP, "Producto ID: " + id);
                }
                case "UPDATE" -> { 
                    
                    // Se recibe el ID  y los nuevos datos del producto a actualizar
                    int idActualizar = Integer.parseInt((String) objectInput.readObject());
                    Producto nuevosDatos = (Producto) objectInput.readObject();
                    
                    // Se actualiza el producto en el almacén
                    almacen.actualizarProducto(
                            idActualizar,
                            nuevosDatos.getNombre(),
                            nuevosDatos.getDescripcion(),
                            nuevosDatos.getPrecio(),
                            nuevosDatos.getCantidad()
                    );
                    CreadorLogs.log("UPDATE", clientIP, "Producto ID: " + idActualizar);
                }
                case "SEARCH" -> {
                    
                    // Se busca el producto en el almacén
                    Producto producto = (Producto) objectInput.readObject();
                    String producto_encontrado = almacen.buscar(producto);
                    System.out.println(producto_encontrado);
                    
                    // Se envía el resultado al cliente
                    objectOutput.writeObject(producto_encontrado);
                    CreadorLogs.log("SEARCH", clientIP, "Producto ID: " + producto.getId());
                }

                case "CSV" -> {
                    // Se envía el inventario al cliente en formato CSV
                    String inventario= almacen.generarInventario();
                    System.out.println(inventario);
                    objectOutput.writeObject(inventario);
                    CreadorLogs.log("CSV", clientIP,"Inventario Generado");
                }
            }

            // Se envía una confirmación de operación exitosa al cliente
            objectOutput.writeObject("Operacion exitosa");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
