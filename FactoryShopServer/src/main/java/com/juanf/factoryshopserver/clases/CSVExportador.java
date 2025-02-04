/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopserver.clases;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 *
 * @author juanf
 */
public class CSVExportador {
    
    public static String generarCSVInventario(ArrayList<Producto> productos) {
        StringWriter sw = new StringWriter();
        sw.write("ID,Nombre,Descripción,Precio,Cantidad\n");
        for (Producto p : productos) {
            sw.write(String.format("%d,%s,%s,%.2f,%d\n",
                    p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getCantidad()));
        }
        return sw.toString();
    }
}
