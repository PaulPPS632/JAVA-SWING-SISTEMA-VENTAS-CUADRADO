/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaries;

import ConexionMysql.queries;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 *
 * @author Paul
 */
public class Producto {
    
    private int id_producto;
    private String pad_interno;
    private int id_marca;
    private int id_categoria;
    private int id_subcategoria;
    private String modelo;
    private String codigofabricante;
    private String descripcion;
    private double precio;
    private double impuesto_unitario;
    private double valor_unitario;
    private int stock;
    private int garantia_cliente;
    private int garantia_total;
    private int estado_producto;
    private int cantidadVenta;
    private double precioBruto;
    private double impuesto;
    private double precioNeto;
    private byte[] imagen;
    private ArrayList<String> Series;
    public static boolean EnSTOCK(String SN){
        int i = queries.querieReturnINT("select id_estado_producto from productos_series where sn = ?", SN);
        return i == 1;
    }
    public static int IDProductoPorSerie(String SN){
        String ID = queries.querieReturnCell("select id_producto from productos_series where sn = ?", "id_producto", SN);
        if(ID != null){
            return Integer.parseInt(ID);
        }else{
            return 0;
        }
        
    }
    public static double redondear(double valor, int decimales) {
        if (decimales < 0) {
            throw new IllegalArgumentException("El número de decimales no puede ser negativo");
        }
        BigDecimal bd = new BigDecimal(Double.toString(valor));
        bd = bd.setScale(decimales, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public Producto(String SN){
        //String ID = queries.querieReturnCell("select id_producto from productos_series where sn = ?", "id_producto", SN);
        String[] datos = queries.querieReturnDatosFila("call DatosProductoPorSerie(?)", SN);
        this.id_producto = Integer.parseInt(datos[0]);
        this.pad_interno = datos[1];
        this.id_marca = Integer.parseInt(datos[2]);
        this.id_categoria = Integer.parseInt(datos[3]);
        this.id_subcategoria = Integer.parseInt(datos[4]);
        this.modelo = datos[5];
        this.codigofabricante = datos[6];
        this.descripcion = datos[7];
        this.precio = Double.parseDouble(datos[8]);
        this.stock = Integer.parseInt(datos[9]);
        this.garantia_cliente = Integer.parseInt(datos[11]);
        this.garantia_total = Integer.parseInt(datos[12]);
        this.estado_producto = Integer.parseInt(datos[13]);
        this.Series = new ArrayList<String>();
        Series.add(SN);
        this.cantidadVenta = 1;
        this.impuesto_unitario = redondear(precio*0.18,2);
        this.valor_unitario = redondear(precio / 1.18, 2);
        this.precioNeto = redondear(cantidadVenta * precio, 2);
        this.precioBruto = redondear(precioNeto / 1.18, 2);
        this.impuesto = redondear(precioBruto * 0.18, 2);
    }
    public Producto(int ID){
        String[] datos = queries.querieReturnDatosFila("call LeerProductoPorID(?)", ID);
        this.id_producto = Integer.parseInt(datos[0]);
        this.pad_interno = datos[1];
        this.id_marca = Integer.parseInt(datos[2]);
        this.id_categoria = Integer.parseInt(datos[3]);
        this.id_subcategoria = Integer.parseInt(datos[4]);
        this.modelo = datos[5];
        this.codigofabricante = datos[6];
        this.descripcion = datos[7];
        this.precio = Double.parseDouble(datos[8]);
        this.stock = Integer.parseInt(datos[9]);
        this.garantia_cliente = Integer.parseInt(datos[11]);
        this.garantia_total = Integer.parseInt(datos[12]);
        this.Series = new ArrayList<String>();
        this.cantidadVenta = 0;
        this.impuesto_unitario = redondear(precio*0.18,2);
        this.valor_unitario = redondear(precio / 1.18, 2);
        this.precioNeto = redondear(cantidadVenta * precio, 2);
        this.precioBruto = redondear(precioNeto / 1.18, 2);
        this.impuesto = redondear(precioBruto * 0.18, 2);
        
    }
    public void CargaImagen(){
        this.imagen = queries.querieReturnByte("select referencia from productos where id_producto = ?", id_producto);
    }
    public Producto(){
        
    }
    public void InsertarNuevoProductoDataBase(){
        queries.QuerieMessage("call InsertarNuevoProducto(?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{
        id_marca,id_categoria,id_subcategoria, pad_interno, modelo, codigofabricante, descripcion, precio, stock, imagen, garantia_cliente, garantia_total
        });
    }
    public void ActualizarProductoDatabase(){
        queries.QuerieMessage("call ActualizarProducto(?,?,?,?,?,?,?,?,?,?,?)", new Object[]{
        id_producto,id_marca,id_categoria,id_subcategoria, modelo, codigofabricante, descripcion, precio, imagen, garantia_cliente, garantia_total
        });
    }
    public String NombreMarca(){
        return queries.querieReturnCell("select Nombre_Marca from marcas where id_marca = ?", "Nombre_Marca", id_marca);
    }
    public String NombreCategoria(){
        return queries.querieReturnCell("select c.nombre_categoria from categoria as c, subcategoria as s  where c.id_categoria = s.id_categoria and s.id_subcategoria = ?", "nombre_categoria", id_subcategoria);
    }
    public void BorrarSN(){
        Series.clear();
        this.cantidadVenta = 0;
    }
    public String ListadoSeries(){
        return String.join(" / ", Series);
    }
    public void InsertarSN(String sn_nuevo){
        if(!Series.contains(sn_nuevo)){
            Series.add(sn_nuevo);
            cantidadVenta++;
        }
        this.precioNeto = redondear(cantidadVenta * precio, 2);
        this.precioBruto = redondear(precioNeto / 1.18, 2);
        this.impuesto = redondear(precioBruto * 0.18, 2);
    }
    public void setCantidadVenta(int cant){
        this.cantidadVenta = cant;
        this.precioNeto = redondear(cantidadVenta * precio, 2);
        this.precioBruto = redondear(precioNeto / 1.18, 2);
        this.impuesto = redondear(precioBruto * 0.18, 2);
    }
    public int getCantidadVenta(){
        return cantidadVenta;
    }
    public double getPrecioNeto(){
        return precioNeto;
    }
    public double getImpuesto(){
        return impuesto;
    }
    public double getPrecioBruto(){
        return precioBruto;
    }
    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getPad_interno() {
        return pad_interno;
    }

    public void setPad_interno(String pad_interno) {
        this.pad_interno = pad_interno;
    }

    public int getId_marca() {
        return id_marca;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public int getId_subcategoria() {
        return id_subcategoria;
    }

    public void setId_subcategoria(int id_subcategoria) {
        this.id_subcategoria = id_subcategoria;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCodigofabricante() {
        return codigofabricante;
    }

    public void setCodigofabricante(String codigofabricante) {
        this.codigofabricante = codigofabricante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
        this.valor_unitario = redondear(precio / 1.18, 2);
        this.impuesto_unitario = redondear(precio*0.18,2);
        this.precioNeto = redondear(cantidadVenta * precio, 2);
        this.precioBruto = redondear(precioNeto / 1.18, 2);
        this.impuesto = redondear(precioBruto * 0.18, 2);
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getGarantia_cliente() {
        return garantia_cliente;
    }

    public void setGarantia_cliente(int garantia_cliente) {
        this.garantia_cliente = garantia_cliente;
    }

    public int getGarantia_total() {
        return garantia_total;
    }

    public void setGarantia_total(int garantia_total) {
        this.garantia_total = garantia_total;
    }
    public void setSeries(ArrayList<String> series){
        this.Series = series;
    }
    
     public ArrayList<String> getSeries(){
        return Series;
    }
    /*
        create table Productos (
            id_producto int primary key not null,
            pad_interno varchar(200) unique not null,
            id_marca int not null,
            id_categoria_marca int not null,
            id_subcategoria int not null,
            modelo varchar(200) not null,
            codigofabricante varchar(200) not null,
            descripcion varchar(500),
            precio decimal(8,2) not null,
            stock integer,
            referencia mediumblob,
            garantia_cliente int not null, -- tiempo de garantia que se da al cliente (se define en la compra) se mide en meses
            garantia_total int not null, -- tiempo de garantia que obtenemos del proveedor (se define en la compra) se mide en meses
            foreign key (id_marca) references Marcas(id_marca),
            foreign key (id_subcategoria) references SubCategoria(id_subcategoria),
            foreign key (id_categoria_marca) references categorias_marcas(id_categoria_marca)
        );
    */

    /**
     * @return the valor_unitario
     */
    public double getValor_unitario() {
        return valor_unitario;
    }

    /**
     * @param valor_unitario the valor_unitario to set
     */
    public void setValor_unitario(double valor_unitario) {
        this.valor_unitario = valor_unitario;
    }

    /**
     * @return the impuesto_unitario
     */
    public double getImpuesto_unitario() {
        return impuesto_unitario;
    }

    /**
     * @param impuesto_unitario the impuesto_unitario to set
     */
    public void setImpuesto_unitario(double impuesto_unitario) {
        this.impuesto_unitario = impuesto_unitario;
    }

    /**
     * @return the estado_producto
     */
    public int getEstado_producto() {
        return estado_producto;
    }

    /**
     * @param estado_producto the estado_producto to set
     */
    public void setEstado_producto(int estado_producto) {
        this.estado_producto = estado_producto;
    }

    /**
     * @return the imagen
     */
    public byte[] getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
    
    
    
}
