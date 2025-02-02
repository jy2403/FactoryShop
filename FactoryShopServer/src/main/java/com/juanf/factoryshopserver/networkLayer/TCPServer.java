/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopserver.networkLayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanf
 */
public class TCPServer {
      private static final int PORT = 5000;
    private static ArrayList<Producto> inventory = new ArrayList<>();
    
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado en el puerto " + PORT);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static class ClientHandler extends Thread {
        private Socket socket;
        
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        
        public void run() {
            try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                
                while (true) {
                    String request = (String) in.readObject();
                    System.out.println("Solicitud recibida: " + request);
                    
                    String[] parts = request.split(";");
                    String action = parts[0];
                    
                    switch (action) {
                        case "ADD":
                            int id = Integer.parseInt(parts[1]);
                            String nombre = parts[2];
                            String descripcion = parts[3];
                            double precio = Double.parseDouble(parts[4]);
                            int cantidad = Integer.parseInt(parts[5]);
                            inventory.add(new Producto(id, nombre, descripcion, precio, cantidad));
                            out.writeObject("Producto agregado");
                            break;
                        case "LIST":
                            out.writeObject(inventory);
                            break;
                        case "EXIT":
                            out.writeObject("Desconectado");
                            socket.close();
                            return;
                        default:
                            out.writeObject("Comando no reconocido");
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            }        }
    }
}

class Producto implements Serializable {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidad;
    
    public Producto(int id, String nombre, String descripcion, double precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    
    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Descripción: " + descripcion +
               ", Precio: $" + precio + ", Cantidad: " + cantidad;
    }   
}
