/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaries;

import javax.swing.table.DefaultTableModel;
import ConexionMysql.queries;
/**
 *
 * @author Paul
 */
public class MetodoTipoIdentidad {
    public static DefaultTableModel TodosTipoIdentidad(){
        System.out.println("todostipoidentidad");
        return queries.QueryReturnTable("Select * from to dos_tipoidentidad");
    }
}
