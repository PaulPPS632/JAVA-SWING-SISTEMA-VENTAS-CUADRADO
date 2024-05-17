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
public class MetodoProveedores {
    public static DefaultTableModel BuscarPorDocumento(String Documento){
        return queries.QueryReturnTable("call BuscarProveedorPorDocumento(?)", Documento);
    }
    public static DefaultTableModel BuscarPorNombre(String Nombre){
        return queries.QueryReturnTable("call BuscarProveedorPorRazonSocial(?)", Nombre);
    }
    public static void InsertarCliente(String TipoIdentidad, String NroDocumento, String Nombres, String Contacto, String Direccion, String Email){
        queries.QuerieMessage("call InsertarProveedor(?,?,?,?,?,?)", 
                new Object[]{TipoIdentidad,NroDocumento,Nombres, Contacto, Direccion, Email});
    }
    public static void EliminarCliente(int ID){
        queries.QuerieMessage("call EliminarProveedor(?)", new Object[]{ID});
    }
}
