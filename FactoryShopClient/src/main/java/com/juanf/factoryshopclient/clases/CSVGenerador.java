/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopclient.clases;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase encargada de generar y guardar un archivo CSV con información de productos.
 * @author juanf
 */
public class CSVGenerador {

    /**
     * Guarda la lista de productos en un archivo CSV.
     * 
     * @param respuesta Cadena con la información de los productos, separada por ';' y atributos por '|'.
     */
    public static void guardarProductosCSV(String respuesta) {
        
        // Verifica si la respuesta es nula o vacía para evitar errores.
        if (respuesta == null || respuesta.isEmpty()) {
            System.out.println("Respuesta vacía o nula.");
            return;
        }

        // Se utiliza un bloque try-with-resources para asegurar que FileWriter se cierre automáticamente.
        try (FileWriter fw = new FileWriter("productos.csv")) {
            fw.write("ID,Nombre,Descripción,Precio,Cantidad\n");

            String[] productos = respuesta.split(";");
            
            // Verifica que el producto tenga exactamente 5 atributos antes de escribirlo en el archivo.
            for (String productoStr : productos) {
                String[] atributos = productoStr.split("\\|");
                
                // Une los atributos con comas y los escribe en el archivo CSV.                
                if (atributos.length == 5) {
                    fw.write(String.join(",", atributos) + "\n");
                }
            }

            System.out.println("Archivo CSV generado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}