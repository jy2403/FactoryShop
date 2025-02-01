/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.juanf.factoryshopserver;

import com.juanf.factoryshopserver.networkLayer.TCPServer;

/**
 *
 * @author juanf
 */
public class FactoryShopServer {

    public static void main(String[] args) {
        new TCPServer().start();
    }
}
