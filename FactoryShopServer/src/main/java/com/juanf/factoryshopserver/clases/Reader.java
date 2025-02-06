/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopserver.clases;

import com.juanf.factoryshared.clases.Producto;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * La claseproporciona un método para leer productos desde un archivo
 * y devolver una lista de objetos que representan los productos leídos.
 * 
 * @author juanf
 */
public class Reader {
    /**
     * Lee los productos desde el archivo especificado y devuelve una lista de objetos
     * que representan los productos leídos.
     * 
     * @param file El nombre y la ruta del archivo desde el cual se deben leer los productos.
     * @return Una lista de objetos leídos desde el archivo.
     * @throws IOException Si ocurre un error al leer o crear el archivo.
     */
    public static ArrayList<Producto> leerProductos(String file) throws IOException {
        ArrayList<Producto> productos = new ArrayList<>();
        Path path = Paths.get(file);

        // Verifica si el archivo no existe,sino crea uno
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
            return productos; // Retorna lista vacía
        }
        
        // Se abre el archivo en modo lectura con codificación UTF-8
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] campos = line.split("\\|");  // Corrección aquí

                    // Se verifica que la línea tenga exactamente 5 campos esperados
                    if (campos.length != 5) {
                        System.err.println("Formato incorrecto en la línea: " + line);
                        continue;
                    }
                    
                    // Se convierten los valores de texto a los tipos adecuados
                    int id = Integer.parseInt(campos[0].trim());
                    String nombre = campos[1].trim();
                    String descripcion = campos[2].trim();
                    // Asegurar formato decimal
                    double precio = Double.parseDouble(campos[3].trim().replace(",", ".")); 
                    int cantidad = Integer.parseInt(campos[4].trim());

                    // Se crea un objeto Producto y se agrega a la lista
                    Producto producto = new Producto(id, nombre, descripcion, precio, cantidad);
                    productos.add(producto);
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir un número en la línea: " + line);
                }
            }
        }

        return productos;
    }

}
