/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.juanf.factoryshopserver.clases;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julian andres
 */
public class FactoryShop {
    
    private ArrayList<Producto> productos = new ArrayList<>();

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public FactoryShop() {
            Path path = Paths.get("data/productos");
        System.out.println("Buscando el archivo en: " + path.toAbsolutePath());
        try {
            // Ruta relativa directa: "data/productos"
            ArrayList<Producto> productosLeidos = Reader.leerProductos("data/productos");
            productos.addAll(productosLeidos);
        } catch (IOException ex) {
            System.err.println("Inventario inicializado vacío. Razón: " + ex.getMessage());
            crearArchivoProductos();
        }
    }

    private void crearArchivoProductos() {
        try {
            Path path = Paths.get("data/productos");
            Files.createDirectories(path.getParent()); 
            Files.createFile(path); 
            System.out.println("Archivo 'productos' creado en: " + path.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
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
            Writer.guardarProductos("data/productos", new ArrayList<>(productos));
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
