/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopserver.clases;

import com.juanf.factoryshared.clases.Producto;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * La clase  proporciona un método para guardar una lista de productos
 * en un archivo especificado. Cada producto se guarda en una línea con sus atributos
 * formateados de manera que se separan por el carácter '|'.
 * 
 * Si el archivo no existe, se creará automáticamente en la ruta especificada.
 * 
 * @author juanf
 */
public class Writer {
    /**
     * Guarda la lista de productos en el archivo especificado.
     *
     * @param file El nombre y la ruta del archivo en el cual se deben guardar los productos.
     * @param productos La lista de objetos que se desea guardar en el archivo.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public static void guardarProductos(String file, ArrayList<Producto> productos) throws IOException {
        Path path = Paths.get(file);
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            for (Producto p : productos) {
                String linea = String.format("%d|%s|%s|%.2f|%d",
                        p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getCantidad());
                bw.write(linea);
                bw.newLine();
            }
        }
    }
}

