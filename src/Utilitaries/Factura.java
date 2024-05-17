/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaries;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author Paul
 */
public class Factura {
    private Document document;
    private Venta venta;
    private final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private final Font small = new Font(Font.FontFamily.TIMES_ROMAN, 10);
    private BaseFont bfBold, bf;
    
    public Factura(int ID){
        setVenta(new Venta(ID));
    }
    public void crearPDF(){
        try{
            String ruta = Ruta();
            System.out.println(ruta);
            PdfWriter docWriter = null;
            initializeFonts();
            document = new Document(PageSize.A4);
            docWriter = PdfWriter.getInstance(document, new FileOutputStream(ruta));
            document.open();
            PdfContentByte cb = docWriter.getDirectContent();
            addMetaData(document);
            //addLogo(document);
            generateHeader(cb);
            document.close();
            AbrirArchivo(ruta);
        }catch(Exception e){
            
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
    private void addMetaData(Document document) {
        document.addTitle("Factura Nº: " + venta.getCorrelativo());
        document.addSubject("Fecha:" + new SimpleDateFormat("yyyy-MM-dd").format(LocalDate.now()));
        document.addKeywords("Facturas PDF");
        document.addAuthor("jeffertsap@hotmail.com");
        document.addCreator("jeffertsap@hotmail.com");
    }
     private void addLogo(Document doc) throws DocumentException, Exception{
        try {
            Image companyLogo = Image.getInstance("C:/Users/Paul/Desktop/cuadrado/IMAGENES/logo_cuadrado.png");
            companyLogo.setAbsolutePosition(35,700);
            companyLogo.scalePercent(20);
            doc.add(companyLogo);
        }
        catch (DocumentException dex){
            dex.printStackTrace();
        }
        catch (Exception ex){
           ex.printStackTrace();
        }
    }
    private void createHeadings(PdfContentByte cb, float x, float y, String text, int tam){
        cb.beginText();
        cb.setFontAndSize(bfBold, tam);
        cb.setFontAndSize(bfBold, tam);
        cb.setTextMatrix(x,y);
        cb.showText(text.trim());
        cb.endText();
    }
    
    private void createCliente(PdfContentByte cb, float x, float y, String text, int tam){
        cb.beginText();
        cb.setFontAndSize(bf, tam);
        cb.setFontAndSize(bf, tam);
        cb.setTextMatrix(x,y);
        cb.showText(text.trim());
        cb.endText();
    }
    private void generateHeader(PdfContentByte cb) {
        try {

            createHeadings(cb, 150, 790, "CUADRADO TECHNOLOGY & ADVANCE EIRL", 10);
            createHeadings(cb, 150, 760, "RUC: 20606455543", 8);
            createHeadings(cb, 150, 745, "PJ. HERNAN VELARDE NRO. 172 INT. 109 CERCADO DE LIMA", 8);
            createHeadings(cb, 150, 730, "LIMA - LIMA - LIMA", 8);
            createHeadings(cb, 150, 715, "Teléfono: 000 000 000", 8);

            cb.setLineWidth(1f);
 
            // Invoice Header box layout
//            cb.rectangle(70,600,150,60);
            cb.roundRectangle(460, 770, 100, 50, 5 ); //x, y, ancho, alto, round
            cb.roundRectangle(460, 705, 100, 50, 5 ); //x, y, ancho, alto, round
            cb.moveTo(460,797); // (x,y)
            cb.lineTo(560,797);
            cb.moveTo(460,732);
            cb.lineTo(560,732);
            cb.stroke();
   
            createHeadings(cb, 480, 804, "FACTURA", 12);
            createHeadings(cb, 502, 780, "" + venta.getCorrelativo(), 14);
            createHeadings(cb, 488, 739, "FECHA", 12);
            createHeadings(cb, 470, 715, new SimpleDateFormat("dd / MM / yyyy").format(venta.getFecha()), 14);
            
            cb.setLineWidth(1f);
            cb.roundRectangle(25, 565, 540, 120, 5 ); //x, y, ancho, alto, round
            cb.stroke();
            
//            cb.setGrayStroke(1f);
//            cb.rectangle(25, 665, 299, 20); //x,y, ancho, alto
            
//            cb.setGrayStroke(20f);
            cb.moveTo(25,660); // (x,y)
            cb.lineTo(565,660);
            cb.stroke();
            
            String[] DatosCliente = venta.DatosCliente();
            createHeadings(cb, 265, 667, "CLIENTE" , 12);
            createCliente(cb, 35, 643, "Nombre:    " + DatosCliente[3], 11);
            createCliente(cb, 35, 626, "RUC/DNI:    " + DatosCliente[2], 11);
            createCliente(cb, 35, 608, "Dirección:  " + DatosCliente[5], 11);
            createCliente(cb, 35, 572, "Teléfono:   " + DatosCliente[4], 11);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
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
    private void initializeFonts() throws DocumentException , IOException {
        try {
            bfBold = BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @return the document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(Document document) {
        this.document = document;
    }

    /**
     * @return the venta
     */
    public Venta getVenta() {
        return venta;
    }

    /**
     * @param venta the venta to set
     */
    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    /**
     * @return the bfBold
     */
    public BaseFont getBfBold() {
        return bfBold;
    }

    /**
     * @param bfBold the bfBold to set
     */
    public void setBfBold(BaseFont bfBold) {
        this.bfBold = bfBold;
    }

    /**
     * @return the bf
     */
    public BaseFont getBf() {
        return bf;
    }

    /**
     * @param bf the bf to set
     */
    public void setBf(BaseFont bf) {
        this.bf = bf;
    }
    
}
