/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaries;

import ConexionMysql.queries;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Paul
 */
public class MetodoProducto {
    public static DefaultTableModel BuscarProducto(String Producto){
        return queries.QueryReturnTable("call BuscarProductoDescripcion(?)", Producto);
    }
    public static DefaultTableModel BuscarProductoconFiltro(String Producto, int Marca, int Subcategoria){
        return queries.QueryReturnTable("call BuscarProductoDescripcionyFiltro(?,?,?)", new Object[]{Producto, Marca, Subcategoria});
    }
    public static DefaultTableModel BuscarProductoEnStock(String Producto){
        return queries.QueryReturnTable("call BuscarProductoDescripcionenstock(?)", Producto);
    }
    public static DefaultTableModel BuscarProductoconFiltroEnStock(String Producto, int Marca, int Subcategoria){
        return queries.QueryReturnTable("call BuscarProductoDescripcionyFiltroenstock(?,?,?)", new Object[]{Producto, Marca, Subcategoria});
    }
}
