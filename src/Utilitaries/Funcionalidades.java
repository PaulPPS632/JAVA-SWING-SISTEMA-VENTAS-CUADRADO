/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaries;

import static Frames.frmPrincipal.tbn_escritorio;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONObject;
/**
 *
 * @author Paul
 */
public class Funcionalidades {
    public static void goToURL(String URL){
           if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                try {
                    java.net.URI uri = new java.net.URI(URL);
                    desktop.browse(uri);
                } catch (URISyntaxException | IOException ex) {
                }
            }
        }
    }
    public static void CentrarVentana(JDesktopPane tbn_escritorio , JInternalFrame internalFrame) {
        int x = (tbn_escritorio.getWidth() / 2) - internalFrame.getWidth() / 2;
        int y = (tbn_escritorio.getHeight() / 2) - internalFrame.getHeight() / 2;
        if (internalFrame.isShowing()) {
            internalFrame.setLocation(x, y);
        } else {
            tbn_escritorio.add(internalFrame);
            internalFrame.setLocation(x, y);
            internalFrame.show();
            internalFrame.toFront();
        }
    }   
    
    public static String ConsumoAPi(String format, String dni) {
        try {
            // Configura la URL y el token
            String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImplZmZlcnRzYXBAaG90bWFpbC5jb20ifQ.zo6DfpopBiuuE37p0ubL7Rn06DgOt8ADXNu3WKTzvuY";
            String apiUrl = "https://dniruc.apisperu.com/api/v1/"+format.toLowerCase() +"/" + dni + "?token=" + token;
            JOptionPane.showMessageDialog(null, apiUrl);
            // Crea la URL de la API
            URL url = new URL(apiUrl);

            // Abre la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configura el método de solicitud y las propiedades
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            // Lee la respuesta de la API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            // Procesa la respuesta
            String apiResponse = response.toString();
            System.out.println(apiResponse);
            
            
            // Obtén los valores específicos de la respuesta
            String nombres = "", apellidoPaterno = "", apellidoMaterno = "";
            boolean success = Boolean.parseBoolean(apiResponse.split("\"success\":")[1].split(",")[0]);
            if (success) {
                nombres = apiResponse.split("\"nombres\":\"")[1].split("\",")[0];
                apellidoPaterno = apiResponse.split("\"apellidoPaterno\":\"")[1].split("\",")[0];
                apellidoMaterno = apiResponse.split("\"apellidoMaterno\":\"")[1].split("\",")[0];
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE EL DNI");
            }
            // Cierra la conexión
            connection.disconnect();
            return nombres + " " + apellidoPaterno + " " + apellidoMaterno;
        } catch (IOException e) {
            return "";
        }
    }
    public static String[] ConsumoAPI2(String format, String documento) {
        try {
            // Configura la URL y el token
            String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImplZmZlcnRzYXBAaG90bWFpbC5jb20ifQ.zo6DfpopBiuuE37p0ubL7Rn06DgOt8ADXNu3WKTzvuY";
            String apiUrl = "https://dniruc.apisperu.com/api/v1/"+format.toLowerCase() +"/" + documento + "?token=" + token;
            //JOptionPane.showMessageDialog(null, apiUrl);
            // Crea la URL de la API
            URL url = new URL(apiUrl);

            // Abre la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configura el método de solicitud y las propiedades
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            // Lee la respuesta de la API
            
            StringBuilder response = new StringBuilder();
            Scanner sc = new Scanner(url.openStream());
            
            while(sc.hasNext()){
                response.append(sc.nextLine());
            }
            sc.close();
            
            // IMPRIME JSON COMPLETO
            String apiResponse = response.toString();
            System.out.println(apiResponse);
            
            //uso de JSONObject
            JSONObject dataObject = new JSONObject(String.valueOf(response));
            
            // Cierra la conexión
            connection.disconnect();
            
            //System.out.println("RESPUESTAS: " + dataObject.getString("nombres"));
            String[] Respuesta = new String[3];
            if(format.equals("dni")){
                Respuesta[0] = dataObject.get("nombres").toString();
                Respuesta[1] = dataObject.get("apellidoPaterno").toString();
                Respuesta[2] = dataObject.get("apellidoMaterno").toString();
            }
            if(format.equals("ruc")){
                Respuesta[0] = dataObject.get("razonSocial").toString();
                Respuesta[1] = dataObject.get("direccion").toString();
            }
            System.out.println(Arrays.toString(Respuesta));
            return Respuesta;
        } catch (IOException e) {
            return null;
        }
    }
    public static Object[] extraerRegistrosColumna(DefaultTableModel model, int columna) {
        int rowCount = model.getRowCount();
        Object[] registros = new Object[rowCount];

        for (int i = 0; i < rowCount; i++) {
            registros[i] = model.getValueAt(i, columna);
        }

        return registros;
    }
    
}
