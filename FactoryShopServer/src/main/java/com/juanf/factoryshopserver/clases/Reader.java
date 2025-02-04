/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopserver.clases;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author juanf
 */
public class Reader {

    public static ArrayList leerProductos(String file) throws IOException {
        ArrayList<Producto> productos = new ArrayList<>();
        Path path = Paths.get(file);
        
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
            return productos; // Retorna lista vacía
        }

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] campos = line.split(",");
                    if (campos.length != 5) {
                        System.err.println("Línea inválida, se esperaban 5 campos: " + line);
                        continue;
                    }
                    int id = Integer.parseInt(campos[0].trim());
                    String nombre = campos[1].trim();
                    String descripcion = campos[2].trim();
                    double precio = Double.parseDouble(campos[3].trim());
                    int cantidad = Integer.parseInt(campos[4].trim());

                    Producto producto = new Producto(id, nombre, descripcion, precio, cantidad);
                    productos.add(producto);
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir número en la línea: " + line);
                }
            }
        }

        return productos;
    }
}
