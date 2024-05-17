/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaries;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
/**
 *
 * @author Paul
 */
public class impresionExcel {
    public static void exportarExcel(JTable t) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(ruta);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
                archivoXLS.createNewFile();
                Workbook libro = new HSSFWorkbook();
                FileOutputStream archivo = new FileOutputStream(archivoXLS);
                Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
                hoja.setDisplayGridlines(false);
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(f);
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        if (f == 0) {
                            celda.setCellValue(t.getColumnName(c));
                        }
                    }
                }
                int filaInicio = 1;
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(filaInicio);
                    filaInicio++;
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        if (t.getValueAt(f, c) instanceof Double) {
                            celda.setCellValue(Double.parseDouble(t.getValueAt(f, c).toString()));
                        } else if (t.getValueAt(f, c) instanceof Float) {
                            celda.setCellValue(Float.parseFloat((String) t.getValueAt(f, c)));
                        } else {
                            celda.setCellValue(String.valueOf(t.getValueAt(f, c)));
                        }
                    }
                }
                libro.write(archivo);
                archivo.close();
                Desktop.getDesktop().open(archivoXLS);
            } catch (IOException | NumberFormatException e) {
                throw e;
            }
        }
    }
    public static void exportarTablaCobranza(JTable t) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(ruta);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
                archivoXLS.createNewFile();
                Workbook libro = new HSSFWorkbook();
                FileOutputStream archivo = new FileOutputStream(archivoXLS);
                Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
                hoja.setDisplayGridlines(false);
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(f);
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        if (f == 0) {
                            celda.setCellValue(t.getColumnName(c));
                        }
                    }
                }
                int filaInicio = 1;
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(filaInicio);
                    filaInicio++;
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        if (t.getValueAt(f, c) instanceof Double) {
                            celda.setCellValue(Double.parseDouble(t.getValueAt(f, c).toString()));
                        } else if (t.getValueAt(f, c) instanceof Float) {
                            celda.setCellValue(Float.parseFloat((String) t.getValueAt(f, c)));
                        } else {
                            celda.setCellValue(String.valueOf(t.getValueAt(f, c)));
                        }
                    }
                }
                libro.write(archivo);
                archivo.close();
                Desktop.getDesktop().open(archivoXLS);
            } catch (IOException | NumberFormatException e) {
                throw e;
            }
        }
    }
    
    private static short getColorIndex(Workbook workbook, Color color) {
        short index = 0;
        if (workbook instanceof HSSFWorkbook) {
            HSSFWorkbook hssfWorkbook = (HSSFWorkbook) workbook;
            HSSFPalette palette = hssfWorkbook.getCustomPalette();
            HSSFColor hssfColor = palette.findColor((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
            if (hssfColor == null) {
                palette.setColorAtIndex(IndexedColors.AUTOMATIC.getIndex(), (byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
                hssfColor = palette.getColor(IndexedColors.AUTOMATIC.getIndex());
            }
            index = hssfColor.getIndex();
        }
        return index;
    }
    public static void exportarExcelImagen(DefaultTableModel model) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xlsx");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString().concat(".xlsx");
            try {
                File archivoXLSX = new File(ruta);
                if (archivoXLSX.exists()) {
                    archivoXLSX.delete();
                }
                archivoXLSX.createNewFile();
                Workbook libro = new HSSFWorkbook();
                FileOutputStream archivo = new FileOutputStream(archivoXLSX);
                Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
                hoja.setDisplayGridlines(false);
                exportarDatos(model, hoja);
                exportarImagenes(model, hoja);
                libro.write(archivo);
                archivo.close();
                Desktop.getDesktop().open(archivoXLSX);
            } catch (IOException | NumberFormatException e) {
                throw e;
            }
        }
    }

    private static void exportarDatos(DefaultTableModel model, Sheet hoja) {
        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();

        // Crear un modelo de tabla con los nombres de las columnas
        Row headerRow = hoja.createRow(0);
        for (int c = 0; c < columnCount; c++) {
            Cell cell = headerRow.createCell(c);
            cell.setCellValue(model.getColumnName(c));
        }

        // Agregar filas al modelo con los datos del ResultSet
        for (int r = 0; r < rowCount; r++) {
            Row fila = hoja.createRow(r + 1);
            for (int c = 0; c < columnCount; c++) {
                Cell celda = fila.createCell(c);
                Object valor = model.getValueAt(r, c);

                if (valor instanceof Double) {
                    celda.setCellValue((Double) valor);
                } else if (valor instanceof Float) {
                    celda.setCellValue((Float) valor);
                } else {
                    celda.setCellValue(String.valueOf(valor));
                }
            }
        }
    }
    private static void exportarImagenes(DefaultTableModel model, Sheet hoja) {
        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();

        for (int r = 0; r < rowCount; r++) {
            Row fila = hoja.getRow(r + 1);
            for (int c = 0; c < columnCount; c++) {
                if (fila != null) {
                    Cell celda = fila.getCell(c);
                    Object valor = model.getValueAt(r, c);

                    if (valor instanceof JLabel) {
                        JLabel label = (JLabel) valor;
                        if (label.getIcon() != null) {
                            insertarImagen((ImageIcon) label.getIcon(), hoja, celda);
                        }
                    }
                }
            }
        }
    }

    private static void insertarImagen(ImageIcon icono, Sheet hoja, Cell celda) {
        int filaIndex = celda.getRowIndex();
        int columnaIndex = celda.getColumnIndex();

        Drawing drawing = hoja.createDrawingPatriarch();
        ClientAnchor anchor = hoja.getWorkbook().getCreationHelper().createClientAnchor();

        anchor.setCol1(columnaIndex);
        anchor.setRow1(filaIndex);
        anchor.setCol2(columnaIndex + 1);
        anchor.setRow2(filaIndex + 1);

        Picture pict = drawing.createPicture(anchor, hoja.getWorkbook().addPicture(imageToByteArray(icono.getImage()), Workbook.PICTURE_TYPE_PNG));
        //pict.resize(50); // You can try different resize options as needed
    }

    private static byte[] imageToByteArray(java.awt.Image image) {
        // Convert the image to a byte array (format: PNG)
        // Implement this method based on your specific requirements
        // You may use ImageIO.write() to write the image to a ByteArrayOutputStream
        // and then convert it to byte array.
        // This implementation depends on your image handling library.
        return new byte[0];
    }
    private static String Ruta(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione la ruta del archivo");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos PDF", "pdf"));
        String rutaArchivo = "";
        
        int seleccion = fileChooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            if(!rutaArchivo.contains(".pdf")){
                rutaArchivo += ".pdf";
            }
        } else {
            System.out.println("No se seleccionó ninguna ruta de archivo.");
        }
        return rutaArchivo;
    }
}
