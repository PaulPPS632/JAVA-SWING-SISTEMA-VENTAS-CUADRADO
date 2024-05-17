/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaries;
import ConexionMysql.queries;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Paul
 */
public class MetodoMarcas {
    
    public static DefaultTableModel BuscarMarcas(String buscado) {
        return queries.QueryReturnTable("call procedure_BuscarMarcas(?)", buscado);
    }
    public static void RegistraMarca(String Nombre){
        queries.QuerieMessage("call InsertarNuevaMarca(?)", Nombre);
    }
    public static void ModificarMarca(String ID, String Descripcion){
        queries.QuerieMessage("call ModificarMarca(?,?)", new Object[]{ID, Descripcion});
    }
    public static void EliminarMarca(String ID){

        queries.QuerieMessage("call EliminarMarca(?)", ID);
    }
    public static String[] BuscarMarcasString(String Busca){
        return queries.querieReturnDatosColumn("call procedure_BuscarMarcas(?)", 2, Busca);
    }
    public static String[] BuscarCategoriaMarcaAsociada(int ID){
        return queries.querieReturnDatosColumn("call BuscarCategoriaMarcaAsociada(?)", 1,ID);
    }
    public static ArrayList<Object[]> SubCategoriaMarcaAsociada(int ID){
        return queries.QueryReturnArrayListObject("call BuscarCategoriaMarcaAsociada(?)", ID);
    }
}
