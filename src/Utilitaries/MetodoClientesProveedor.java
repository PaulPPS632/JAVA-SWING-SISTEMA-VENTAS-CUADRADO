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
public class MetodoClientesProveedor {
    public static DefaultTableModel BuscarPorDocumento(String Documento){
        return queries.QueryReturnTable("call buscarClientepordocumento(?)", Documento);
    }
    public static DefaultTableModel BuscarPorNombre(String Nombre){
        return queries.QueryReturnTable("call buscarclientepornombre(?)", Nombre);
    }
    public static DefaultTableModel clientes_asociados_a_pseudonimo(int psudonimo){
        return queries.QueryReturnTable("call clientes_proveedor_asociados_seudonimo(?)", psudonimo);
    }
    //public static 
    public static void InsertarCliente(int TipoIdentidad, String NroDocumento, String Nombres, String Contacto, String Direccion, String Email){
        queries.QuerieMessage("call insertarnuevocliente(?,?,?,?,?,?)", 
                new Object[]{TipoIdentidad,NroDocumento,Nombres, Contacto, Direccion, Email});
    }
    public static void EliminarCliente(int ID){
        queries.QuerieMessage("call eliminarcliente(?)", new Object[]{ID});
    }
    public static DefaultTableModel Buscar_Seudonimo(String seudonimo){
        return queries.QueryReturnTable("call buscar_seudonimo(?)", seudonimo);
    }
    public static void Asociar_ClienteProveedor_Seudonimo(int id_cliente_proveedor, int id_seudonimo ){
        queries.QuerieNormal("update clientesyproveedor set id_seudonimo = ? where id_clienteproveedor = ?", new Object[]{id_seudonimo,id_cliente_proveedor});
    }
}
