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
public class MetodoTipos {
    // ------------------------- TIPOS CONDICION -----------------------------------------
    public static DefaultTableModel MostrarTodosTiposCondicion(){
        return queries.QueryReturnTable("select * from todostiposcondicion");
    }
    public static void InsertarTipoCondicion(String ID, int DiasCredito, String Descripcion){
        queries.QuerieMessage("CALL insertartipocondicion(?,?,?)", new Object[]{ID, DiasCredito, Descripcion});
    }
    public static void ActualizarTipoCondicion(String ID, int DiasCredito, String Descripcion){
        queries.QuerieMessage("CALL actualizartipocondicion(?,?,?)", new Object[]{ID, DiasCredito, Descripcion});
    }
    public static void EliminarTipoCondicion(String ID){
        queries.QuerieMessage("CALL eliminartipocondicion(?)", new Object[]{ID});
    }
    public static String[] BuscarCondicion(String Condicion){
        return queries.querieReturnDatosColumn("call buscartipocondicion(?)", 2, Condicion);
    }
    
    // ------------------------- TIPOS COMPROBANTES -----------------------------------------
    public static DefaultTableModel MostrarTodosTipoComprobante(){
        return queries.QueryReturnTable("SELECT * FROM todostipocomprobante");
    }
    public static void InsertarTipoComprobante(String ID, String Descripcion){
        queries.QuerieMessage("CALL insertartipocomprobante(?,?)", new Object[]{ID, Descripcion});
    }
    public static void ActualizarTipoComprobante(String ID, String Descripcion){
        queries.QuerieMessage("CALL actualizartipocomprobante(?,?)", new Object[]{ID, Descripcion});
    }
    public static void EliminarTipoComprobante(String ID){
        queries.QuerieMessage("CALL eliminartipocomprobante(?)", new Object[]{ID});
    }
    public static String[] NombresComprobantes(){
        return queries.querieReturnDatosColumnSinParam("Select * from tipo_comprobante",3); 
    }
    public static String[] PrefijosComprobantes(){
        return queries.querieReturnDatosColumnSinParam("Select * from tipo_comprobante",2); 
    }
    
    // ------------------------- TIPOS PAGOS -----------------------------------------
    public static DefaultTableModel MostrarTodosTipoPago(){
        return queries.QueryReturnTable("SELECT * FROM todostipopago");
    }
    public static void InsertarTipoPago(String ID, String Descripcion){
        queries.QuerieMessage("CALL insertartipopago(?,?)", new Object[]{ID,Descripcion});
    }
    public static void ActualizarTipoPago(String ID, String Descripcion){
        queries.QuerieMessage("CALL actualizartipopago(?,?)", new Object[]{ID, Descripcion});
    }
    public static void EliminarTipoPago(String ID){
        queries.QuerieMessage("CALL eliminartipopago(?)", new Object[]{ID});
    }
    public static String[] NombrePagos(){
        return queries.querieReturnDatosColumnSinParam("SELECT NOMBRE FROM todostipopago", 1);
    }
    // ------------------------- TIPOS ESTADO VENTA -----------------------------------------
    
    public static String[] NombresEstadoVenta(){
        return queries.querieReturnDatosColumnSinParam("SELECT nombre FROM estadoventa", 1);
    }
    
    // ------------------------- TIPOS ALMACENES -----------------------------------------
    public static String[] BuscarAlmacenes(String almacen){
        return queries.querieReturnDatosColumn("call buscaralmacenes(?)", 1, almacen);
    }
    
    
    
}
