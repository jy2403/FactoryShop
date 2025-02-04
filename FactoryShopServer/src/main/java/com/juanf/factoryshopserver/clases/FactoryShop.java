/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.juanf.factoryshopserver.clases;

import com.juanf.factoryshared.clases.Producto;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Esta clase representa una tienda que maneja un inventario de productos. Permite leer, agregar, eliminar y actualizar productos
 * en un archivo, así como generar un reporte en formato CSV del inventario.
 * 
 * @author Julian Andres
 */
public class FactoryShop {
    
    // Lista que almacena los productos de la tienda
    private ArrayList<Producto> productos = new ArrayList<>();

    /**
     * Obtiene la lista de productos de la tienda.
     * 
     * @return La lista de productos.
     */
    public ArrayList<Producto> getProductos() {
        return productos;
    }
    
    /**
     * Constructor que inicializa la tienda cargando los productos desde un archivo.
     * Si no se encuentra el archivo de productos, se crea uno nuevo vacío.
     */
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
    /**
     * Crea el archivo de productos si no existe. Crea el directorio correspondiente
     * si es necesario.
     */
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
    /**
     * Agrega un nuevo producto al inventario de la tienda.
     * 
     * @param producto El producto a agregar.
     */
    public void agregarProducto(Producto producto) {
        productos.add(producto);
        guardarCambios();
    }
    /**
     * Elimina un producto del inventario dado su ID.
     * 
     * @param id El ID del producto a eliminar.
     * @return true si el producto fue eliminado exitosamente, false si no se encontró.
     */
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
    /**
     * Actualiza la información de un producto dado su ID.
     * 
     * @param id El ID del producto a actualizar.
     * @param nombre El nuevo nombre del producto.
     * @param descripcion La nueva descripción del producto.
     * @param precio El nuevo precio del producto.
     * @param cantidad La nueva cantidad disponible del producto.
     * @return true si el producto fue actualizado exitosamente, false si no se encontró.
     */
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
    /**
     * Guarda los cambios realizados en el inventario en el archivo de productos.
     * Si hay modificaciones, se escriben en el archivo.
     */
    private void guardarCambios() {
        try {
            Writer.guardarProductos("data/productos", new ArrayList<>(productos));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Lista todos los productos en el inventario. Si el inventario está vacío, 
     * se muestra un mensaje indicando que no hay productos.
     */
    public void listarProductos() {
        if (productos.isEmpty()) {
            System.out.println("Inventario vacío");
        } else {
            productos.forEach(System.out::println);
        }
    }
    /**
     * Genera un inventario en formato CSV a partir de la lista de productos.
     * 
     * @return El inventario en formato CSV como una cadena.
     */
    public String generarInventario(){
        return CSVExportador.generarCSVInventario(getProductos());
    }
}
