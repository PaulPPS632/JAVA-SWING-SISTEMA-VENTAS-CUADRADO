package ConexionMysql;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class queries {
    private static Connection conn = null; 
    private static void initquerie(){
        Conexion nueva = Conexion.getInstancia();
        conn = nueva.getConexion();
    }
    public static ResultSet queriedefault(String querie){
        try {
            initquerie();
            Statement st;
            ResultSet rs;
            if (conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(querie);
                System.out.println("Execute querieDefault: " + querie);
                return rs;
            } else {
                System.out.println("No se pudo establecer la conexión con la base de datos.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            return null;
        } 
    }
    public static ResultSet queriedefault(String querie, Object... params){
        try {
            initquerie();
            if (conn != null) {
                PreparedStatement preparedStatement = conn.prepareStatement(querie);
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }
                ResultSet rs = preparedStatement.executeQuery();
                return rs;
            } else {
                System.out.println("No se pudo establecer la conexión con la base de datos.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            return null;
        } 
    }
    public static DefaultTableModel QueryReturnTable(String query, Object... params) {

        initquerie();
        DefaultTableModel model = new DefaultTableModel();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = preparedStatement.executeQuery()) {
                System.out.println("ejecutado QueryReturnTable: " + query);
                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Crear un modelo de tabla con los nombres de las columnas
                for (int column = 1; column <= columnCount; column++) {
                    model.addColumn(metaData.getColumnLabel(column));
                }

                // Agregar filas al modelo con los datos del ResultSet
                while (rs.next()) {
                    Object[] rowData = new Object[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        rowData[i] = rs.getObject(i + 1);
                    }
                    model.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            // Trata la excepción según tus necesidades
        }

        return model;
    }
    public static DefaultTableModel QueryReturnTable(String query) {
        initquerie();
        DefaultTableModel model = new DefaultTableModel();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                System.out.println("ejecutado QueryReturnTable: " + query);
                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Crear un modelo de tabla con los nombres de las columnas
                for (int column = 1; column <= columnCount; column++) {
                    model.addColumn(metaData.getColumnLabel(column));
                }

                // Agregar filas al modelo con los datos del ResultSet
                while (rs.next()) {
                    Object[] rowData = new Object[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        rowData[i] = rs.getObject(i + 1);
                    }
                    model.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            // Trata la excepción según tus necesidades
        }

        return model;
    }
    public static DefaultTableModel QueryReturnTableLista(String query, int ColumnImage){
        initquerie();
        DefaultTableModel model = new DefaultTableModel();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                System.out.println("ejecutado QueryReturnTable: " + query);
                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Crear un modelo de tabla con los nombres de las columnas
                for (int column = 1; column <= columnCount; column++) {
                    model.addColumn(metaData.getColumnLabel(column));
                }

                // Agregar filas al modelo con los datos del ResultSet
                while (rs.next()) {
                    Object[] rowData = new Object[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        //rowData[i] = rs.getObject(i + 1);
                        if (i == ColumnImage) {
                            byte[] imageData = rs.getBytes(i + 1);
                            if (imageData != null && imageData.length > 0) {
                                ImageIcon imageIcon = new ImageIcon(imageData);
                                BufferedImage buffered = null;
                                InputStream input = new ByteArrayInputStream(imageData);
                                buffered = ImageIO.read(input);
                                ImageIcon icono = new ImageIcon(buffered.getScaledInstance(60, 60, 0));
                                rowData[i] = new JLabel(icono);
                            } else {
                                rowData[i] = new JLabel("SIN IMAGEN"); // No hay imagen
                            }
                        } else {
                            rowData[i] = rs.getObject(i + 1);
                        }
                    }
                    model.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            // Trata la excepción según tus necesidades
        } catch(IOException e){
            System.out.println("Error al imprimir imagen: " + e.getMessage());
        }

        return model;
    }
    public static DefaultTableModel QueryReturnTableLista(String query, int ColumnImage,  Object... params){
        initquerie();
        DefaultTableModel model = new DefaultTableModel();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = preparedStatement.executeQuery()) {
                System.out.println("ejecutado QueryReturnTable: " + query);
                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Crear un modelo de tabla con los nombres de las columnas
                for (int column = 1; column <= columnCount; column++) {
                    model.addColumn(metaData.getColumnLabel(column));
                }

                // Agregar filas al modelo con los datos del ResultSet
                while (rs.next()) {
                    Object[] rowData = new Object[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        //rowData[i] = rs.getObject(i + 1);
                        if (i == ColumnImage) {
                            byte[] imageData = rs.getBytes(i + 1);
                            if (imageData != null && imageData.length > 0) {
                                ImageIcon imageIcon = new ImageIcon(imageData);
                                BufferedImage buffered = null;
                                InputStream input = new ByteArrayInputStream(imageData);
                                buffered = ImageIO.read(input);
                                ImageIcon icono = new ImageIcon(buffered.getScaledInstance(60, 60, 0));
                                rowData[i] = new JLabel(icono);
                            } else {
                                rowData[i] = new JLabel("SIN IMAGEN"); // No hay imagen
                            }
                        } else {
                            rowData[i] = rs.getObject(i + 1);
                        }
                    }
                    model.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            // Trata la excepción según tus necesidades
        } catch(IOException e){
            System.out.println("Error al imprimir imagen: " + e.getMessage());
        }

        return model;
    }
    public static ArrayList<Object[]> QueryReturnArrayListObject(String query,  Object... params) {
        initquerie();
        ArrayList<Object[]> rpt = new ArrayList<Object[]>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = preparedStatement.executeQuery()) {
                System.out.println("ejecutado QueryReturnTable: " + query);
                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Agregar filas al modelo con los datos del ResultSet
                while (rs.next()) {
                    Object[] rowData = new Object[columnCount];
                    
                    for (int i = 0; i < columnCount; i++) {
                        rowData[i] = rs.getObject(i + 1);
                    }
                    rpt.add(rowData);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            // Trata la excepción según tus necesidades
        }

        return rpt;
    }
    public static void QuerieNormal(String query, Object... params){
        initquerie();
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }
    public static void QuerieMessage(String query, Object... params){
        initquerie();
        String Respuesta = "";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Respuesta = rs.getString(1);
                }
                if(Respuesta != "") JOptionPane.showMessageDialog(null, Respuesta);
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }
    public static String querieReturnCell(String query, String Column ,Object... params){
        initquerie();
        String Respuesta = "";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Respuesta = rs.getString(Column);
                }
                if(Respuesta != ""){
                    //JOptionPane.showMessageDialog(null, Respuesta);
                    return Respuesta;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
        return null;
    }
    public static byte[] querieReturnByte(String query, Object... params){
        initquerie();
        byte[] Respuesta = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Respuesta = rs.getBytes(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
        return Respuesta;
    }
    public static int querieReturnINT(String query, Object... params){
        initquerie();
        int Respuesta = 0;
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Respuesta = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
        return Respuesta;
    }
    public static boolean querieReturnBoolean(String query, Object... params){
        return querieReturnINT(query, params) > 0;
    }
    public static boolean querieUpdate(String query){
        try{
            initquerie();
            PreparedStatement stmt;
            if (conn != null) {
                stmt = conn.prepareStatement(query);
                stmt.executeUpdate();
                System.out.println("Execute querieUpdate: " + query);
                return true;
            } else {
                System.out.println("No se pudo establecer la conexión con la base de datos.");
                return false;
            }
        }catch(Exception e){
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            return false;
        }
    }
    public static String[] querieReturnDatosFila(String query, Object... params){
        initquerie();
        String[] Respuesta;
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = preparedStatement.executeQuery()) {
                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                Respuesta = new String[columnCount];

                // Agregar filas al modelo con los datos del ResultSet
                while (rs.next()) {
                    for (int i = 0; i < columnCount; i++) {
                        Respuesta[i] = rs.getString(i + 1);
                    }
                }
                return Respuesta;
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            // Trata la excepción según tus necesidades
        }
        //return model;
        return null;   
    }
    public static ArrayList<String> querieReturnDatosColumnArray(String query, int column, Object... params){
        initquerie();
        ArrayList<String> lista = new ArrayList<String>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = preparedStatement.executeQuery()) {
                // Agregar filas al modelo con los datos del ResultSet
                while (rs.next()) {
                    lista.add(rs.getString(column));
                }
                return lista;
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            // Trata la excepción según tus necesidades
        }
        //return model;
        return null;   
    }
    public static String[] querieReturnDatosColumn(String query, int column, Object... params){
        initquerie();
        ArrayList<String> lista = new ArrayList<String>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = preparedStatement.executeQuery()) {
                // Agregar filas al modelo con los datos del ResultSet
                while (rs.next()) {
                    lista.add(rs.getString(column));
                }
                String[] resultArray = lista.toArray(new String[0]);
                return resultArray;
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            // Trata la excepción según tus necesidades
        }
        //return model;
        return null;   
    }
    public static String[] querieReturnDatosColumnSinParam(String query, int column){
        initquerie();
        ArrayList<String> lista = new ArrayList<String>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                // Agregar filas al modelo con los datos del ResultSet
                while (rs.next()) {
                    lista.add(rs.getString(column));
                }
                String[] resultArray = lista.toArray(new String[0]);
                return resultArray;
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            // Trata la excepción según tus necesidades
        }
        //return model;
        return null;   
    }
    public static boolean ejecutarProcedimiento(String query, int indexOut, Object... params) {
        try {
            initquerie(); 
            boolean resultado;
            try (CallableStatement stmt = conn.prepareCall(query)) {
                
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }   stmt.registerOutParameter(indexOut, java.sql.Types.BOOLEAN); 
                
                stmt.execute();
                
                //valor del parámetro OUT
                resultado = stmt.getBoolean(indexOut);
            }

            return resultado;

        } catch (SQLException e) {
            System.out.println("Error al ejecutar el procedimiento almacenado: " + e.getMessage());
            return false;
        }
    }
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
