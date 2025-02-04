/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopserver.clases;

import com.juanf.factoryshared.clases.Producto;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Esta clase se encarga de generar un archivo CSV a partir de una lista de productos.
 * La información de cada producto se organiza en formato de texto delimitado por tuberías ('|') 
 * y los registros están separados por punto y coma (';').
 * 
 * @author juanf
 */
public class CSVExportador {
    /**
     * Genera un string que representa los datos de un inventario en formato CSV.
     * Cada producto de la lista se convierte en una línea con el formato:
     * ID|Nombre|Descripción|Precio|Cantidad;
     *
     * @param productos La lista de productos que se desea exportar.
     * @return Una cadena de texto en formato CSV representando el inventario.
     */
    public static String generarCSVInventario(ArrayList<Producto> productos) {
        StringWriter sw = new StringWriter();
        for (Producto p : productos) {
            sw.write(String.format("%d|%s|%s|%.2f|%d;",
                    p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getCantidad()));
        }
        return sw.toString();
    }
}
