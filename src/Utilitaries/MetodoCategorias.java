/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaries;

import ConexionMysql.queries;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Paul
 */
public class MetodoCategorias {
    public static DefaultTableModel BuscarCategorias(String Buscado) {
        return queries.QueryReturnTable("call procedure_BuscarCategorias(?)", new Object[]{Buscado});
    }
    public static String[] SubcategoriasAsosiadas(int Categoria){
        ArrayList<String> listaSubcategoria = new ArrayList<>();
        try{
            ResultSet rs = queries.queriedefault("call procedure_BuscarSubcategoriaAsociada("+Categoria+");");
            while(rs.next()){
                listaSubcategoria.add(rs.getString(1));
            }
        }catch(SQLException ex){
            Logger.getLogger(MetodoCategorias.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        String[] subcategorias = new String[listaSubcategoria.size()];
        subcategorias = listaSubcategoria.toArray(subcategorias);
        return subcategorias;
    }
    public static ArrayList<Object[]> SubCategoriaAsociada(int id){
        return queries.QueryReturnArrayListObject("call procedure_BuscarSubcategoriaAsociada(?)", id);
    }
    public static ArrayList<Object[]> SubCategoriasAsociadaConIDSubcategoria(int id){
        return queries.QueryReturnArrayListObject("call SubCategoriasAsociadaConIDSubcategoria(?)", id);
    }
    public static String[] TodasSubCategorias(String buscado){
        return queries.querieReturnDatosColumn("call procedure_BuscarSubcategorias(?)", 1, buscado);
    }
    public static void RegistraCategoria(String Nombre, String Descripcion){
        queries.QuerieMessage("call InsertarNuevaCategoria(?,?)", new Object[]{Nombre, Descripcion});
    }
    public static void ModificarCategoria(String ID, String Nombre, String Descripcion){
        queries.QuerieMessage("call ModificarCategoria(?,?,?)", new Object[]{ID, Nombre, Descripcion});
    }
    public static void EliminarCategoria(String ID){
        queries.QuerieMessage("call EliminarCategoria(?)", ID);
    }
    /*
    public static void EliminarCategoria(String ID){
        try{
            ResultSet rs = queries.queriedefault("call EliminarCategoria('"+ID+"');");
            String mensaje = "";
            while(rs.next()){
                mensaje = rs.getString(1);
            }
            JOptionPane.showMessageDialog(null,mensaje);
        }catch(SQLException ex){
            Logger.getLogger(MetodoCategorias.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
*/
}
