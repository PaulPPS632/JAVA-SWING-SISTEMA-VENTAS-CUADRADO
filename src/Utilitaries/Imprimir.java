/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaries;

import ConexionMysql.queries;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Paul
 */
public class Imprimir {

    private byte[] Portada;
// comentario
    private String Ruta() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione la ruta del archivo");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos PDF", new String[]{"pdf"}));
        String rutaArchivo = "";
        int seleccion = fileChooser.showSaveDialog(null);
        if (seleccion == 0) {
            rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            if (!rutaArchivo.contains(".pdf")) {
                rutaArchivo = rutaArchivo + ".pdf";
            }
        } else {
            System.out.println("No se seleccionninguna ruta de archivo.");
        }
        return rutaArchivo;
    }

    public void GenerarPdf(String query, Object... params) {
        Connection conn = null;
        Statement stmt = null;
        BaseColor colorfila = new BaseColor(1, 1, 1);
        try {
            String rutaArchivo = Ruta();
            ResultSet resultset = queries.queriedefault("select * from view_lista");
            String[] datos = new String[7];
            Document doc = new Document();
            doc.setMargins(-50.0F, -50.0F, 20.0F, 0.0F);
            try {
                PdfWriter.getInstance(doc, new FileOutputStream(rutaArchivo));
                doc.open();
                PdfPTable pdftable = new PdfPTable(7);
                int[] anchosColumnas = {80, 80, 100, 300, 80, 80, 80};
                pdftable.setWidths(anchosColumnas);
                Image port = Image.getInstance(this.Portada);
                AgregarCeldaConImagen(pdftable, port, colorfila, 7);
                System.out.println("Supero Creacion name column de Tabla");
                String tituloSubCategoria = "", marca = "";
                while (resultset.next()) {
                    datos[0] = resultset.getString(1);
                    String marcaact = resultset.getString(2);
                    datos[1] = marcaact + " " + resultset.getString(3);
                    datos[2] = resultset.getString(4);
                    datos[3] = resultset.getString(5);
                    datos[4] = resultset.getString(6);
                    datos[5] = resultset.getString(7);
                    datos[6] = resultset.getString(9).strip();
                    BufferedImage buffered = null;
                    byte[] imageData = resultset.getBytes(8);
                    Image ima = null;
                    if (imageData != null && imageData.length > 0) {
                        ima = Image.getInstance(imageData);
                        ima.scaleAbsolute(50.0F, 50.0F);
                    }
                    if (!tituloSubCategoria.equals(datos[6])) {
                        tituloSubCategoria = datos[6];
                        AgregarCeldainTabla(pdftable, tituloSubCategoria, 20, new BaseColor(255, 215, 0), 7, 50);
                        AgregarCeldainTabla(pdftable, "CODIGO PRODUCTO", 10, new BaseColor(180, 198, 231), 1, 50);
                        AgregarCeldainTabla(pdftable, "MARCA Y MODELO", 10, new BaseColor(180, 198, 231), 1, 50);
                        AgregarCeldainTabla(pdftable, "CODIGO", 10, new BaseColor(180, 198, 231), 1, 50);
                        AgregarCeldainTabla(pdftable, "DESCRIPCION", 10, new BaseColor(180, 198, 231), 1, 50);
                        AgregarCeldainTabla(pdftable, "PRECIO", 10, new BaseColor(180, 198, 231), 1, 50);
                        AgregarCeldainTabla(pdftable, "STOCK", 10, new BaseColor(180, 198, 231), 1, 50);
                        AgregarCeldainTabla(pdftable, "REFERENCIA", 10, new BaseColor(180, 198, 231), 1, 50);
                    }
                    if (!marca.equals(marcaact)) {
                        colorfila = ColorAleatorio();
                        marca = marcaact;
                    }
                    for (int column = 0; column < 7; column++) {
                        if (column != 6) {
                            AgregarCeldainTabla(pdftable, datos[column], 7, colorfila, 1, 50);
                        } else {
                            AgregarCeldaConImagen(pdftable, ima, colorfila, column);
                        }
                    }
                }
                doc.add((Element) pdftable);
                doc.close();
                System.out.println("El PDF ha sido generado correctamente.");
            } catch (Exception e) {
                System.err.println("Error al generar el Estado de Cuenta: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void GenerarPdf2(String query, Object... params) {
        BaseColor colorfila = new BaseColor(1, 1, 1);
        ResultSet resultset = queries.queriedefault(query, params);
        try {
            String rutaArchivo = Ruta();
            String[] datos = new String[7];
            Document doc = new Document();
            doc.setMargins(-50.0F, -50.0F, 20.0F, 0.0F);
            PdfWriter.getInstance(doc, new FileOutputStream(rutaArchivo));
            doc.open();
            PdfPTable pdftable = new PdfPTable(7);
            int[] anchosColumnas = {80, 80, 100, 300, 80, 80, 80};
            pdftable.setWidths(anchosColumnas);
            Image port = Image.getInstance(this.Portada);
            AgregarCeldaConImagen(pdftable, port, colorfila, 7);
            System.out.println("Supero Creacion name column de Tabla");
            String tituloSubCategoria = "", marca = "";
            while (resultset.next()) {
                datos[0] = resultset.getString(1);
                String marcaact = resultset.getString(2);
                datos[1] = marcaact + " " + resultset.getString(3);
                datos[2] = resultset.getString(4);
                datos[3] = resultset.getString(5);
                datos[4] = resultset.getString(6);
                datos[5] = resultset.getString(7);
                datos[6] = resultset.getString(9);
                byte[] imageData = resultset.getBytes(8);
                Image ima = null;
                if (imageData != null && imageData.length > 0) {
                    ima = Image.getInstance(imageData);
                    ima.scaleAbsolute(50.0F, 50.0F);
                }
                if (!tituloSubCategoria.equals(datos[6])) {
                    tituloSubCategoria = datos[6];
                    AgregarCeldainTabla(pdftable, tituloSubCategoria, 20, new BaseColor(255, 215, 0), 7, 50);
                    AgregarCeldainTabla(pdftable, "CODIGO PRODUCTO", 10, new BaseColor(180, 198, 231), 1, 50);
                    AgregarCeldainTabla(pdftable, "MARCA Y MODELO", 10, new BaseColor(180, 198, 231), 1, 50);
                    AgregarCeldainTabla(pdftable, "CODIGO", 10, new BaseColor(180, 198, 231), 1, 50);
                    AgregarCeldainTabla(pdftable, "DESCRIPCION", 10, new BaseColor(180, 198, 231), 1, 50);
                    AgregarCeldainTabla(pdftable, "PRECIO", 10, new BaseColor(180, 198, 231), 1, 50);
                    AgregarCeldainTabla(pdftable, "STOCK", 10, new BaseColor(180, 198, 231), 1, 50);
                    AgregarCeldainTabla(pdftable, "REFERENCIA", 10, new BaseColor(180, 198, 231), 1, 50);
                }
                if (!marca.equals(marcaact)) {
                    colorfila = ColorAleatorio();
                    marca = marcaact;
                }
                for (int column = 0; column < 7; column++) {
                    if (column != 6) {
                        AgregarCeldainTabla(pdftable, datos[column], 7, colorfila, 1, 50);
                    } else {
                        AgregarCeldaConImagen(pdftable, ima, colorfila, column);
                    }
                }
            }
            doc.add((Element) pdftable);
            doc.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void Reporte_Cobranza() {
        BaseColor colorfila = new BaseColor(1, 1, 1);
        ResultSet resultset = queries.queriedefault("select ID,FECHA_VENT,SEUDONIMO, CLIENTE, TOTAL_VENTA, DEUDA_VENTA from registrosventas where FECHA_VENT between '2024-01-20' and '2024-01-25' order by SEUDONIMO, CLIENTE;");
        try {
            String rutaArchivo = Ruta();
            String[] datos = new String[7];
            Document doc = new Document();
            doc.setMargins(-50.0F, -50.0F, 20.0F, 0.0F);
            PdfWriter.getInstance(doc, new FileOutputStream(rutaArchivo));
            doc.open();
            PdfPTable pdftable = new PdfPTable(6);
            int[] anchosColumnas = {80, 80, 200, 300, 80, 80};
            pdftable.setWidths(anchosColumnas);

            System.out.println("Supero Creacion name column de Tabla");
            String titulo_seudonimo = "", cliente = "";

            while (resultset.next()) {
                datos[0] = resultset.getString(1);
                datos[1] = resultset.getString(2);
                datos[2] = resultset.getString(3);
                datos[3] = resultset.getString(4);
                datos[4] = resultset.getString(5);
                datos[5] = resultset.getString(6);
                String cliente_actual = datos[3];
                if (!titulo_seudonimo.equals(datos[2])) {
                    titulo_seudonimo = datos[2];
                    AgregarCeldainTabla(pdftable, titulo_seudonimo, 20, new BaseColor(255, 215, 0), 6, 50);
                    AgregarCeldainTabla(pdftable, "ID", 14, new BaseColor(34, 160, 222), 1, 50);
                    AgregarCeldainTabla(pdftable, "FECHA", 14, new BaseColor(34, 160, 222), 1, 50);
                    AgregarCeldainTabla(pdftable, "SEUDONIMO", 14, new BaseColor(34, 160, 222), 1, 50);
                    AgregarCeldainTabla(pdftable, "CLIENTE", 14, new BaseColor(34, 160, 222), 1, 50);
                    AgregarCeldainTabla(pdftable, "TOTAL", 14, new BaseColor(34, 160, 222), 1, 50);
                    AgregarCeldainTabla(pdftable, "DEUDA", 14, new BaseColor(34, 160, 222), 1, 50);
                }
                if (!cliente.equals(cliente_actual)) {
                    colorfila = ColorAleatorio();
                    cliente = cliente_actual;
                }
                for (int column = 0; column < 6; column++) {
                    AgregarCeldainTabla(pdftable, datos[column], 7, colorfila, 1, 50);
                }
            }
            doc.add((Element) pdftable);
            doc.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void Reporte_Cobranza2(Object... params) {
        BaseColor colorfila = new BaseColor(1, 1, 1);
        
        ResultSet resultset = queries.queriedefault("select ID,FECHA_VENT,SEUDONIMO, CLIENTE, TOTAL_VENTA, DEUDA_VENTA from registrosventas where DEUDA_VENTA > 0 AND FECHA_VENT between ? and ? order by SEUDONIMO, CLIENTE;", params);
        //ResultSet resultset = queries.queriedefault("select ID,FECHA_VENT,SEUDONIMO, CLIENTE, TOTAL_VENTA, DEUDA_VENTA, CODIGO, FORMA_PAGO, SUM(DEUDA_VENTA) OVER (PARTITION BY SEUDONIMO) AS DEUDA_TOTAL_SEUDONIMO from registrosventas where FECHA_VENT between ? and ? and SEUDONIMO = 'KATIA COMPUPLAZA/GARACILAZO' order by SEUDONIMO, CLIENTE;", params);

        try {
            String rutaArchivo = Ruta();
            String[] datos = new String[7];
            Document doc = new Document();
            doc.setMargins(-50.0F, -50.0F, 20.0F, 0.0F);
            PdfWriter.getInstance(doc, new FileOutputStream(rutaArchivo));
            doc.open();
            PdfPTable pdftable = new PdfPTable(30);
            int[] anchosColumnas = new int[30];
            Arrays.fill(anchosColumnas, 30);
            pdftable.setWidths(anchosColumnas);

            System.out.println("Supero Creacion name column de Tabla");
            String titulo_seudonimo = "", cliente = "";
            int cont = 1;
            while (resultset.next()) {
                datos[0] = resultset.getString(1);
                datos[1] = resultset.getString(2);
                datos[2] = resultset.getString(3);
                datos[3] = resultset.getString(4);
                datos[4] = resultset.getString(5);
                datos[5] = resultset.getString(6);
                String cliente_actual = datos[3];
                if (!titulo_seudonimo.equals(datos[2])) {
                    titulo_seudonimo = datos[2];
                    //228, 124, 93
                    BaseColor color_seudonimo = new BaseColor(227, 45, 64);

                    AgregarCeldainTabla(pdftable, titulo_seudonimo, 20, color_seudonimo, 30, 50);
                    cont = 1;
                }
                BaseColor color_bardatosventa = new BaseColor(228, 124, 93);
                BaseColor ColorDatoGeneralVenta = new BaseColor(240, 221, 170);

                AgregarCeldainTabla(pdftable, "VENTA " + cont + " DE " + titulo_seudonimo, 16, color_bardatosventa, 30, 30);
                AgregarCeldainTabla(pdftable, "ID", 10, ColorDatoGeneralVenta, 3, 15);
                AgregarCeldainTabla(pdftable, "FECHA", 10, ColorDatoGeneralVenta, 3, 15);
                AgregarCeldainTabla(pdftable, "SEUDONIMO", 10, ColorDatoGeneralVenta, 9, 15);
                AgregarCeldainTabla(pdftable, "RUC/DNI", 10, ColorDatoGeneralVenta, 9, 15);
                AgregarCeldainTabla(pdftable, "TOTAL", 10, ColorDatoGeneralVenta, 3, 15);
                AgregarCeldainTabla(pdftable, "DEUDA", 10, ColorDatoGeneralVenta, 3, 15);
                if (!cliente.equals(cliente_actual)) {
                    colorfila = ColorAleatorio();
                    cliente = cliente_actual;
                }
                // 1 1 3 3 1 1
                //253, 249, 196

                AgregarCeldainTabla(pdftable, datos[0], 9, ColorDatoGeneralVenta, 3, 30);
                AgregarCeldainTabla(pdftable, datos[1], 9, ColorDatoGeneralVenta, 3, 30);
                AgregarCeldainTabla(pdftable, datos[2], 9, ColorDatoGeneralVenta, 9, 30);
                AgregarCeldainTabla(pdftable, datos[3], 9, ColorDatoGeneralVenta, 9, 30);
                AgregarCeldainTabla(pdftable, datos[4], 9, ColorDatoGeneralVenta, 3, 30);
                AgregarCeldainTabla(pdftable, datos[5], 9, ColorDatoGeneralVenta, 3, 30);
                int id = Integer.parseInt(datos[0]);

                ArrayList<Producto> DetallesVenta = Venta.DetallesVenta(id);
                if (!DetallesVenta.isEmpty()) {
                    //27, 195, 87
                    //marca, modelo, descripcion cantidad unitario total series
                    BaseColor DetalleColor = new BaseColor(89, 179, 144);
                    AgregarCeldainTabla(pdftable, "PRODUCTOS VENDIDOS", 10, DetalleColor, 30, 20);
                    AgregarCeldainTabla(pdftable, "MODELO", 7, DetalleColor, 3, 20);
                    AgregarCeldainTabla(pdftable, "DESCRIPCION", 7, DetalleColor, 12, 20);
                    AgregarCeldainTabla(pdftable, "CANTIDAD", 7, DetalleColor, 3, 20);
                    AgregarCeldainTabla(pdftable, "PRECIO UND", 7, DetalleColor, 3, 20);
                    AgregarCeldainTabla(pdftable, "TOTAL", 7, DetalleColor, 3, 20);
                    AgregarCeldainTabla(pdftable, "SERIES", 7, DetalleColor, 6, 20);

                    for (Producto prd : DetallesVenta) {
                        AgregarCeldainTabla(pdftable, prd.getModelo(), 7, DetalleColor, 3, 20);
                        AgregarCeldainTabla(pdftable, prd.getDescripcion(), 7, DetalleColor, 12, 20);
                        AgregarCeldainTabla(pdftable, (prd.getCantidadVenta() + ""), 7, DetalleColor, 3, 20);
                        AgregarCeldainTabla(pdftable, (prd.getPrecio() + ""), 7, DetalleColor, 3, 20);
                        AgregarCeldainTabla(pdftable, (prd.getPrecioNeto() + ""), 7, DetalleColor, 3, 20);
                        AgregarCeldainTabla(pdftable, prd.ListadoSeries(), 7, DetalleColor, 6, 20);
                    }
                }
                
                ArrayList<Object[]> pagos = queries.QueryReturnArrayListObject("select fecha,forma_pago,cantidad from pagos_venta where id_venta = ?", id);
                if (!pagos.isEmpty()) {
                    // 44, 107, 116
                    BaseColor pagoColor = new BaseColor(44, 107, 116);
                    AgregarCeldainTabla(pdftable, "PAGOS REALIZADOS", 10, pagoColor, 30, 20);
                    AgregarCeldainTabla(pdftable, "FECHA", 7, pagoColor, 6, 20);
                    AgregarCeldainTabla(pdftable, "FORMA DE PAGO", 7, pagoColor, 18, 20);
                    AgregarCeldainTabla(pdftable, "CANTIDAD PAGADA", 7, pagoColor, 6, 20);
                    for (Object[] pago : pagos) {
                        AgregarCeldainTabla(pdftable, pago[0].toString(), 7, pagoColor, 6, 20);
                        AgregarCeldainTabla(pdftable, pago[1].toString(), 7, pagoColor, 18, 20);
                        AgregarCeldainTabla(pdftable, pago[2].toString(), 7, pagoColor, 6, 20);
                    }
                }
                AgregarCeldainTabla(pdftable, "", 7, new BaseColor(255, 255, 255), 30, 4);
                //detalles de la venta
                cont++;
            }
            doc.add((Element) pdftable);
            doc.close();
            AbrirArchivo(rutaArchivo);
        } catch (DocumentException | FileNotFoundException | NumberFormatException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public void Reporte_Cobranza2_estyle_Ricardo(Object... params) {
        BaseColor colorfila = new BaseColor(1, 1, 1);
        ResultSet resultset = queries.queriedefault("select ID,FECHA_VENT,SEUDONIMO, CLIENTE, TOTAL_VENTA, DEUDA_VENTA, CODIGO, FORMA_PAGO, DEUDA_VENTA ,SUM(DEUDA_VENTA) OVER (PARTITION BY SEUDONIMO) AS DEUDA_TOTAL_SEUDONIMO from registrosventas where DEUDA_VENTA > 0 AND FECHA_VENT between ? and ? and SEUDONIMO = 'ALEXANDER LETICIA' order by SEUDONIMO, CLIENTE;", params);
        //ResultSet resultset = queries.queriedefault("select ID,FECHA_VENT,SEUDONIMO, CLIENTE, TOTAL_VENTA, DEUDA_VENTA, CODIGO, FORMA_PAGO, DEUDA_VENTA, SUM(DEUDA_VENTA) OVER (PARTITION BY SEUDONIMO) AS DEUDA_TOTAL_SEUDONIMO from registrosventas where DEUDA_VENTA > 0 AND FECHA_VENT between ? and ? order by SEUDONIMO, CLIENTE;", params);
        try {
            String rutaArchivo = Ruta();
            String[] datos = new String[10];
            Document doc = new Document(PageSize.A4.rotate());
            doc.setMargins(-50.0F, -50.0F, 20.0F, 0.0F);
            PdfWriter.getInstance(doc, new FileOutputStream(rutaArchivo));
            doc.open();
            PdfPTable pdftable = new PdfPTable(30);
            int[] anchosColumnas = new int[30];
            Arrays.fill(anchosColumnas, 30);
            pdftable.setWidths(anchosColumnas);

            System.out.println("Supero Creacion name column de Tabla");
            String titulo_seudonimo = "", cliente = "";
            int cont = 1;
            while (resultset.next()) {
                datos[0] = resultset.getString(1);
                datos[1] = resultset.getString(2);
                datos[2] = resultset.getString(3);
                datos[3] = resultset.getString(4);
                datos[4] = resultset.getString(5);
                datos[5] = resultset.getString(6);
                datos[6] = resultset.getString(7);
                datos[7] = resultset.getString(8);
                
                datos[8] = resultset.getString(9);
                datos[9] = resultset.getString(10);
                String cliente_actual = datos[3];
                if (!titulo_seudonimo.equals(datos[2])) {
                    titulo_seudonimo = datos[2];
                    //228, 124, 93
                    BaseColor color_seudonimo = new BaseColor(138, 210, 176);

                    AgregarCeldainTabla(pdftable, titulo_seudonimo + " --> su DEUDA ACTUAL es: "+ datos[9], 15, color_seudonimo, 30, 25);
                    BaseColor ColorDatoGeneralVenta = new BaseColor(183, 225, 205);

                AgregarCeldainTabla(pdftable, "FECHA", 8, ColorDatoGeneralVenta, 2, 10);
                AgregarCeldainTabla(pdftable, "SEUDONIMO", 8, ColorDatoGeneralVenta, 3, 10);
                AgregarCeldainTabla(pdftable, "RUC/DNI", 8, ColorDatoGeneralVenta, 3, 10);
                AgregarCeldainTabla(pdftable, "MODELO", 8, ColorDatoGeneralVenta, 3, 10);
                AgregarCeldainTabla(pdftable, "DESCRIPCION", 8, ColorDatoGeneralVenta, 5, 10);
                AgregarCeldainTabla(pdftable, "COMPROBANTE", 8, ColorDatoGeneralVenta, 2, 10);
                AgregarCeldainTabla(pdftable, "METODO DE PAGO", 8, ColorDatoGeneralVenta, 4, 10);
                AgregarCeldainTabla(pdftable, "CANT", 8, ColorDatoGeneralVenta, 2, 10);
                AgregarCeldainTabla(pdftable, "TOTAL", 8, ColorDatoGeneralVenta, 2, 10);
                AgregarCeldainTabla(pdftable, "DEUDA", 8, ColorDatoGeneralVenta, 2, 10);
                AgregarCeldainTabla(pdftable, "SERIES", 8, ColorDatoGeneralVenta, 2, 10);
                    cont = 1;
                }
                BaseColor color_bardatosventa = new BaseColor(228, 124, 93);
                
                if (!cliente.equals(cliente_actual)) {
                    colorfila = ColorAleatorio();
                    cliente = cliente_actual;
                }
                int id = Integer.parseInt(datos[0]);

                ArrayList<Producto> DetallesVenta = Venta.DetallesVenta(id);
                if (!DetallesVenta.isEmpty()) {
                    
                    BaseColor DetalleColor = new BaseColor(235, 238, 242);
                    for (Producto prd : DetallesVenta) {
                        AgregarCeldainTabla(pdftable, datos[1], 6, DetalleColor, 2, 30);
                        AgregarCeldainTabla(pdftable, datos[2], 6, DetalleColor, 3, 30);
                        AgregarCeldainTabla(pdftable, datos[3], 6, DetalleColor, 3, 30);
                        AgregarCeldainTabla(pdftable, prd.getModelo(), 6, DetalleColor, 3, 20);
                        AgregarCeldainTabla(pdftable, prd.getDescripcion(), 6, DetalleColor, 5, 20);
                        AgregarCeldainTabla(pdftable, datos[6], 6, DetalleColor, 2, 30);
                        AgregarCeldainTabla(pdftable, datos[7], 6, DetalleColor, 4, 30);
                        AgregarCeldainTabla(pdftable, (prd.getCantidadVenta() + ""), 6, DetalleColor, 2, 20);
                        AgregarCeldainTabla(pdftable, (prd.getPrecioNeto() + ""), 6, DetalleColor, 2, 20);
                        AgregarCeldainTabla(pdftable, datos[8], 6, DetalleColor, 2, 20);
                        AgregarCeldainTabla(pdftable, prd.ListadoSeries(), 6, DetalleColor, 2, 20);
                        
                    }
                }
                cont++;
            }
            doc.add((Element) pdftable);
            doc.close();
            AbrirArchivo(rutaArchivo);
        } catch (DocumentException | FileNotFoundException | NumberFormatException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public void header(){
        
    }
    public void GenerarDocumento() throws DocumentException {
        try {
            String rutaArchivo = Ruta();
            Document doc = new Document();
            doc.setMargins(-50.0F, -50.0F, 20.0F, 0.0F);
            PdfWriter.getInstance(doc, new FileOutputStream(rutaArchivo));
            doc.open();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Imprimir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void AbrirArchivo(String ruta) {
        try {
            File objetofile = new File(ruta);
            Desktop.getDesktop().open(objetofile);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
        }
    }

    public void AgregarCeldainTabla(PdfPTable tabla, String texto, int tama, BaseColor colorfondo, int cantcell, float height) {
        PdfPCell celda = new PdfPCell((Phrase) new Paragraph(texto, FontFactory.getFont("calibri", tama, 0, BaseColor.BLACK)));
        celda.setColspan(cantcell);
        celda.setBackgroundColor(colorfondo);
        celda.setHorizontalAlignment(1);
        celda.setVerticalAlignment(5);
        celda.setMinimumHeight(height);
        tabla.addCell(celda);
    }

    public void AgregarCeldaConImagen(PdfPTable tabla, Image imagen, BaseColor colorfondo, int cantcell) {
        PdfPCell celda = new PdfPCell((Phrase) new Paragraph());
        celda.addElement((Element) imagen);
        celda.setColspan(cantcell);
        celda.setBackgroundColor(colorfondo);
        celda.setHorizontalAlignment(1);
        celda.setVerticalAlignment(5);
        celda.setMinimumHeight(50.0F);
        tabla.addCell(celda);
    }

    public BaseColor ColorAleatorio() {
        String[] coloreshex = {"#ffe4e1", "#77dd77", "#fdfd96", "#84b6f4", "#fcb7af", "#b2e2f2", "#b0c2f2", "#ffda9e", "#fdf9c4", "#a2c8cc"};
        Random rand = new Random();
        int ind_rand = rand.nextInt(coloreshex.length);
        Color colRGB = Color.decode(coloreshex[ind_rand]);
        return new BaseColor(colRGB.getRed(), colRGB.getGreen(), colRGB.getBlue());
    }

    public byte[] getPortada() {
        return this.Portada;
    }

    public void setPortada(byte[] aPortada) {
        this.Portada = aPortada;
    }
}
