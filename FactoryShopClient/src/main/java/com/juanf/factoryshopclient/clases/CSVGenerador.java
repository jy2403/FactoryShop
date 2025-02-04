/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopclient.clases;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author juanf
 */
public class CSVGenerador {

        public static void guardarProductosCSV(String respuesta) {
        if (respuesta == null || respuesta.isEmpty()) {
            System.out.println("Respuesta vacía o nula.");
            return;
        }

        try (FileWriter fw = new FileWriter("productos.csv")) {
            fw.write("ID,Nombre,Descripción,Precio,Cantidad\n");


            String[] productos = respuesta.split(";");

            for (String productoStr : productos) {
                String[] atributos = productoStr.split("\\|");
                
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

