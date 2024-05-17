/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaries;

import ConexionMysql.queries;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Paul
 */
public class MetodoUsuarios {

    private static String hashContrasena(String contrasena) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(contrasena.getBytes());
            // Convertir los bytes del hash a una representación hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MetodoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contrasena;
    }
    public static void insertarUsuario(String UserName, String contrasena, String Nombre, String DNI, int ID_Cargo){
        //queries.QuerieMessage("CALL insertarnuevousuario(?,?,?,?,?,?)", new Object[]{UserName, contrasena, hashContrasena(contrasena), Nombre, DNI, ID_Cargo});
        queries.QuerieMessage("CALL insertarnuevousuario(?,?,?,?,?,?)", new Object[]{UserName, contrasena, "cuadrado", Nombre, DNI, ID_Cargo});
    }
    public static boolean ValidadUsuario(String UserName, String contrasena){
        //return queries.querieReturnBoolean("SELECT validarusuario(?,?,?) AS RESPUESTA", new Object[]{UserName, contrasena, hashContrasena(contrasena)});
        return queries.querieReturnBoolean("SELECT validarusuario(?,?,?) AS RESPUESTA", new Object[]{UserName, contrasena, "cuadrado"});
    }
    public static DefaultTableModel DatosUsuario(String UserName, String contrasena){
        //return queries.QueryReturnTable("CALL buscarusuario(?,?,?)", new Object[]{UserName, contrasena, hashContrasena(contrasena)});       
        return queries.QueryReturnTable("CALL buscarusuario(?,?,?)", new Object[]{UserName, contrasena, "cuadrado"});  
    }
    public static DefaultTableModel TodosUsuarios(){
        return queries.QueryReturnTable("SELECT * FROM todosusuarios");
    }
    public static String[] BuscarDatosUsuario(String UserName, String contrasena){
        //return queries.querieReturnDatosFila("CALL buscarusuario(?,?,?)", new Object[]{UserName,contrasena,hashContrasena(contrasena)});
        return queries.querieReturnDatosFila("CALL buscarusuario(?,?,?)", new Object[]{UserName,contrasena,"cuadrado"});
    }
}
