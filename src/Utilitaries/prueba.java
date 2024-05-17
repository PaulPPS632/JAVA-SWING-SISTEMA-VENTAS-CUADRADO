/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaries;

import ConexionMysql.queries;
import com.itextpdf.text.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Paul
 */
public class prueba {
    private static byte[] imagen;
    public static void main(String[] args) {
        
        procesoIngresarDatosRazonsocial("","PJ. HERNAN VELARDE NRO. 172 INT. 109 CERCADO DE LIMA","LIMA - LIMA - LIMA",1);
        /*
        Documento nuevo = new Documento();
        nuevo.generar();
        
        
        byte[] imageData = resultset.getBytes(8);
        Image ima = null;
        if (imageData != null && imageData.length > 0) {
            ima = Image.getInstance(imageData);
            ima.scaleAbsolute(50.0F, 50.0F);
        }
*/
    }
    private static void procesoIngresarDatosRazonsocial(String telefono,String direccion, String zona, int razonsocial){
        insertarimagen();
        insertardatosDB(new Object[]{telefono,direccion,zona,imagen,razonsocial});
        
    }
    private static void insertardatosDB(Object... params){
        queries.QuerieNormal("update razonsocial set telefono = ?, direccion = ?,zona = ?, logo = ? where id_razonsocial = ?", params);
    }
    private static void insertarimagen(){
        JFileChooser fileChooser = new JFileChooser();
        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoImagen = fileChooser.getSelectedFile();
            imagen = leerDatosImagen(archivoImagen);
            if (imagen != null) {
                JOptionPane.showMessageDialog(null, "Imagen insertada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al leer la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private static byte[] leerDatosImagen(File archivoImagen) {
        try (FileInputStream fis = new FileInputStream(archivoImagen)) {
            byte[] buffer = new byte[(int) archivoImagen.length()];
            fis.read(buffer);
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
