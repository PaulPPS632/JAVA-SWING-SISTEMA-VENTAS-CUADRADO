/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaries;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Paul
 */
public class Documento {
    private Document doc;
    private PdfWriter writer;
    private String rutaarchivo;
    private Venta vnt;
    
    public void generar(){
        rutaarchivo = Ruta();
        initDocument();
        header();
        footer();
        closeDocument();
    }
    private void initDocument(){
        try{
            doc = new Document();
            //doc.setMargins(-50.0F, -50.0F, 20.0F, 0.0F);
            writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaarchivo));
            doc.open();
        }catch (DocumentException | FileNotFoundException | NumberFormatException  e) {
            System.err.println(e.getMessage());
        }
    }
    private void header(){
        try {
            
            String texto = "RUC 20609242575\nFACTURA ELECTRÓNICA\nF001-003447";
            dibujarRectangulo(420, 720, 155, 90, 10, new BaseColor(238, 238, 238));
            Paragraph text = new Paragraph(texto, FontFactory.getFont("calibri", 14, Font.BOLD, BaseColor.BLACK));
            text.setAlignment(Element.ALIGN_CENTER);
            colocarFrase((Phrase)text,420,490,Element.ALIGN_CENTER,15);
            
            String Datos_empresa = "SUPER LAPTOP S.A.C.\nAV. INCA GARCILAZO NRO. 1261 INT. 355\nLIMA - LIMA - LIMA\nTelefono: 01-4805994";
            Paragraph empresa = new Paragraph(Datos_empresa, FontFactory.getFont("calibri", 10, Font.NORMAL, BaseColor.BLACK));
            empresa.setAlignment(Element.ALIGN_LEFT);
            colocarFrase((Phrase)empresa, 10, 500,Element.ALIGN_LEFT,10);
            
        } catch (DocumentException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void agregarImagenEnDocumento(Image imagen, BaseColor colorFondo, float x, float y) {
        PdfContentByte canvas = writer.getDirectContent();

        // Ajusta las coordenadas y el tamaño según tus necesidades
        imagen.setAbsolutePosition(x, y);

        // Rectángulo con esquinas redondeadas detrás de la imagen
        canvas.setColorFill(colorFondo);
        canvas.roundRectangle(x - 5, y - 5, imagen.getWidth() + 10, imagen.getHeight() + 10, 5);
        canvas.fill();

        // Agregar la imagen al documento
        try {
            canvas.addImage(imagen);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    private void dibujarRectangulo(float x, float y, float width, float height, float radius, BaseColor color) {
        PdfContentByte canvas = writer.getDirectContent();
        canvas.setColorFill(color);
        canvas.roundRectangle(x, y, width, height, radius);
        canvas.fill();
    }
    private void colocarFrase(Phrase frase, float x, float y, int alineacion, int interlineado) throws DocumentException {
        ColumnText columnText = new ColumnText(writer.getDirectContent());
        columnText.setSimpleColumn(frase, x, y, doc.right(), doc.top(), interlineado, alineacion);
        columnText.go();
    }
    private void footer() {
        
    }
    private void closeDocument(){
        doc.close();
    }
    private static com.itextpdf.text.Image createImage(String path) {
        // Crea la imagen desde el archivo en la ruta especificada
        try {
            return com.itextpdf.text.Image.getInstance(path);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            return null;
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

    
}
