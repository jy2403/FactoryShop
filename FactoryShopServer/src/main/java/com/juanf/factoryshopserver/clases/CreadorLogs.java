/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopserver.clases;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Esta clase se encarga de crear registros de logs en un archivo CSV. 
 * Cada registro contiene información sobre una operación, la IP del cliente 
 * que realizó la operación y un detalle adicional.
 * 
 * @author juanf
 */
public class CreadorLogs {
    /**
     * Registra una entrada de log en un archivo CSV.
     * El registro incluye la fecha y hora de la operación, la operación realizada,
     * la IP del cliente y un detalle adicional proporcionado como argumento.
     * 
     * @param operacion La operación que se está registrando (por ejemplo, "Agregar Producto").
     * @param ipCliente La IP del cliente que realizó la operación.
     * @param detalle Información adicional sobre la operación (por ejemplo, detalles de la transacción).
     */
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
