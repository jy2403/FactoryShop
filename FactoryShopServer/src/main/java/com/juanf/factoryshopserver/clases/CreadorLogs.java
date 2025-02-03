/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopserver.clases;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 *
 * @author juanf
 */
public class CreadorLogs {

    public static void log(String operacion, String ipCliente, String detalle) {
        String registro = String.format("[%s] %s - IP: %s - %s",
                LocalDateTime.now(), operacion, ipCliente, detalle);
        
        try (FileWriter fw = new FileWriter("logs.csv", true)) {
            fw.write(registro + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
