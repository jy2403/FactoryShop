/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.juanf.factoryshopserver.clases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julian andres
 */
public class FactoryShop {
    private CopyOnWriteArrayList<Producto> productos = new CopyOnWriteArrayList<>();

    public FactoryShop() {
        try {
            ArrayList<Producto> productosLeidos = Reader.leerProductos("../data/productos");
            productos.addAll(productosLeidos);
        } catch (IOException ex) {
            System.err.println("Inventario inicializado vacío.");
        }
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        guardarCambios();
    }

    public boolean eliminarProducto(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                productos.remove(p);
                guardarCambios();
                return true;
            }
        }
        return false;
    }

    public boolean actualizarProducto(int id, String nombre, String descripcion, double precio, int cantidad) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                p.setNombre(nombre);
                p.setDescripcion(descripcion);
                p.setPrecio(precio);
                p.setCantidad(cantidad);
                guardarCambios();
                return true;
            }
        }
        return false;
    }

    private void guardarCambios() {
        try {
            Writer.guardarProductos("../data/productos", new ArrayList<>(productos));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listarProductos() {
        if (productos.isEmpty()) {
            System.out.println("Inventario vacío");
        } else {
            productos.forEach(System.out::println);
        }
    }
}
