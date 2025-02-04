/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juanf.factoryshared.clases;

import java.io.Serializable;

/**
 *  Representa un producto en el inventario
 * @author Julian andres
 */
public class Producto implements Serializable {

    /**
     * Identificador único del producto.
     */
    private int id;
    
    /**
     * Nombre del producto.
     */
    private String nombre;
    
    /**
     * Descripción del producto.
     */
    private String descripcion;
    
    /**
     * Precio del producto.
     */
    private double precio;
    
    /**
     * Cantidad disponible del producto en el inventario.
     */
    private int cantidad;
    
    /**
     * Constructor para crear un nuevo producto.
     * 
     * @param id Identificador único del producto.
     * @param nombre Nombre del producto.
     * @param descripcion Descripción del producto.
     * @param precio Precio del producto.
     * @param cantidad Cantidad disponible en el inventario.
     */
    public Producto(int id, String nombre, String descripcion, double precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el identificador del producto.
     * 
     * @return El ID del producto.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del producto.
     * 
     * @param id El nuevo ID del producto.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del producto.
     * 
     * @return El nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     * 
     * @param nombre El nuevo nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del producto.
     * 
     * @return La descripción del producto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     * 
     * @param descripcion La nueva descripción del producto.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el precio del producto.
     * 
     * @return El precio del producto.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     * 
     * @param precio El nuevo precio del producto.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la cantidad disponible del producto en el inventario.
     * 
     * @return La cantidad disponible del producto.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad disponible del producto en el inventario.
     * 
     * @param cantidad La nueva cantidad del producto.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    /**
     * Devuelve una representación en cadena del producto.
     * 
     * @return Una cadena con la información del producto.
     */
    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Descripción: " + descripcion +
               ", Precio: $" + precio + ", Cantidad: " + cantidad;
    }
}