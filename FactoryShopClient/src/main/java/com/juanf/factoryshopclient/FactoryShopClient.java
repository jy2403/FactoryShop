/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.juanf.factoryshopclient;

import com.juanf.factoryshopclient.gui.Producto;
import java.awt.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author juanf
 */
public class FactoryShopClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {
            
            System.out.println("Conectado al servidor de inventario");
            
            while (true) {
                System.out.println("Seleccione una opción: \n1. Agregar Producto \n2. Listar Productos \n3. Salir");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consumir salto de línea
                
                switch (option) {
                    case 1:
                        System.out.print("Ingrese ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Ingrese Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese Descripción: ");
                        String descripcion = scanner.nextLine();
                        System.out.print("Ingrese Precio: ");
                        double precio = scanner.nextDouble();
                        System.out.print("Ingrese Cantidad: ");
                        int cantidad = scanner.nextInt();
                        scanner.nextLine();
                        
                        out.writeObject("ADD;" + id + ";" + nombre + ";" + descripcion + ";" + precio + ";" + cantidad);
                        out.flush();
                        System.out.println("Servidor: " + in.readObject());
                        break;
                    case 2:
                        
                        // gran problema encuentro aca 
                        
                        //out.writeObject("LIST");
                        //out.flush();
                        //ArrayList<Producto> inventory = new ArrayList<>());
                        //System.out.println("Inventario:");
                        //for (Object product : inventory) {
                        //    System.out.println(product);
                        //}
                        //break;
                    case 3:
                        out.writeObject("EXIT");
                        out.flush();
                        System.out.println("Desconectado del servidor");
                        return;
                    default:
                        System.out.println("Opción no válida");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
