package ConexionMysql;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
public class Conexion { 
    //metodo singleton
    
    private static Conexion instancia;
    private static Connection conexion = null;//nosotros no podremos iniciarlo desde afuera
    private String bd = "dbcuadrado";
    private final String url = "jdbc:mysql://192.168.1.116:3306/";
    private final String user = "remoto";
    private final String password = "130602632";
    
    
    /*
    private String bd = "cuadrado5";
    private final String url = "jdbc:mysql://monorail.proxy.rlwy.net:32501/";
    private final String user = "root";
    private final String password = "A66cAg4c2hb1gD5EECAdBFCA2df4fE2g";
    */
    /*
    private String bd = "dbcuadrado";
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String user = "root";
    private final String password = "75069452";
    
    
    
    
    */
    //conexion de prueba localhost
    
    
    
    //conexion prueba local red
    //private final String url = "jdbc:mysql://192.168.0.113:3307/";
    //private final String user = "REMOTE";
    //private final String password = "130602";
    private final String driver = "com.mysql.cj.jdbc.Driver";
    private Conexion(){
        
        try{
            Class.forName(driver);
            conexion = DriverManager.getConnection(url+bd,user,password);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public static Conexion getInstancia(){
        if(instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }
    public Connection getConexion(){
        return conexion;
    }
    public void setBdName(String BdName){
        this.bd = BdName;
    }
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                JOptionPane.showMessageDialog(null, "Conexión cerrada exitosamente");
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
