/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshopclient.gui;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Julian andres
 */
class FactoryShop {
    
    private ArrayList<Producto> productos = new ArrayList<>();

    // Agregar producto
    public void agregarProducto(Producto producto) {
        productos.add(producto);
        JOptionPane.showMessageDialog(null, "Producto "+ producto.getNombre() +  " creado");
        listarProductos();
    }

    // Eliminar producto
    public boolean eliminarProducto(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                String nombre = p.getNombre();
                productos.remove(p);
                JOptionPane.showMessageDialog(null, "Producto "+ nombre +  " Eliminado");
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "Producto no encontrado");
        return false;
    }

    // Actualizar producto
    public boolean actualizarProducto(int id, String nombre, String descripcion, double precio, int cantidad) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                p.setNombre(nombre);
                p.setDescripcion(descripcion);
                p.setPrecio(precio);
                p.setCantidad(cantidad);
                JOptionPane.showMessageDialog(null, "Producto Actualizado");
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "Producto no encontrado");
        return false;
    }

    // Listar productos
    public void listarProductos() {
        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "sin productos");
        } else {
            for (Producto p : productos) {
                System.out.println(p);
            }
        }
    }
    
}
