/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopserver.clases;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author juanf
 */
public class CSVExportador {
    public static void exportarInventario(ArrayList<Producto> productos, String ruta) throws IOException {
        try (FileWriter fw = new FileWriter(ruta)) {
            fw.write("ID,Nombre,Descripción,Precio,Cantidad\n");
            for (Producto p : productos) {
                fw.write(String.format("%d,%s,%s,%.2f,%d\n",
                        p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getCantidad()));
            }
        }
    }
}
