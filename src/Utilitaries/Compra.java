/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaries;

import ConexionMysql.queries;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Paul
 */
public class Compra {
    private int id_compra;
    private int id_tipo_comprobante;
    private int correlativo;
    private int id_tipo_condicion;
    private int id_clienteproveedor;
    private int id_usuario;
    private int id_almacen;
    private int id_tipo_pago;
    private LocalDate fecha;
    private int garantia;
    private double costo_dolar;
    private double costo_soles;
    private double tipo_cambio;
    private double gravada;
    private double impuesto;
    private double total;
    private String nota;
    private String url_pdf;
    private DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private ArrayList<Producto> lista;
    
    public Compra(int ID){
        this.id_compra = ID;
        String[] Datos = queries.querieReturnDatosFila("select * from compra where id_compra = ?", ID);
        this.id_tipo_comprobante = Integer.parseInt(Datos[1]);
        this.correlativo = Integer.parseInt(Datos[2]);
        this.id_tipo_condicion = Integer.parseInt(Datos[3]);
        this.id_clienteproveedor = Integer.parseInt(Datos[4]);
        this.id_usuario = Integer.parseInt(Datos[5]);
        this.id_almacen = Integer.parseInt(Datos[6]);
        this.fecha = LocalDate.parse(Datos[7]);
        this.garantia = Integer.parseInt(Datos[8]);
        this.costo_dolar = Double.parseDouble(Datos[9]);
        this.costo_soles = Double.parseDouble(Datos[10]);
        this.tipo_cambio = Double.parseDouble(Datos[11]);
        this.gravada = Double.parseDouble(Datos[12]);
        this.impuesto = Double.parseDouble(Datos[13]);
        this.total = Double.parseDouble(Datos[14]);
        this.nota = Datos[15];
        this.lista = new ArrayList<Producto>();
        ArrayList<Object[]> rpta = queries.QueryReturnArrayListObject("call DetallesDeCompraPorIDCompra(?)", ID);
        for(Object[] item : rpta){
            String SN = item[1].toString();
            Double precio = Double.parseDouble(item[4].toString());
            int id = Integer.parseInt(queries.querieReturnCell("select id_producto from productos_series where sn = ?", "id_producto", SN));
            int contador = 0;
            boolean encontrado = false;
            if(id != 0){
                for(Producto prd : lista){
                    if(id == prd.getId_producto()){
                        encontrado = true;
                        break;
                    }
                    contador++;
                }
                if(encontrado){
                    lista.get(contador).InsertarSN(SN);
                }else{
                    Producto nuevo = new Producto(SN);
                    nuevo.setPrecio(precio);
                    lista.add(nuevo);
                }
            }
        }
    }
    public Compra(){
        this.id_compra = 0;
        this.id_tipo_comprobante = 0;
        this.correlativo = 0;
        this.id_tipo_condicion = 0;
        this.id_clienteproveedor = 0;
        this.id_usuario = 0;
        this.id_almacen = 0;
        this.fecha = LocalDate.now();
        this.garantia = 0;
        this.costo_dolar = 0;
        this.costo_soles = 0;
        this.tipo_cambio = 0;
        this.gravada = 0;
        this.impuesto = 0;
        this.total = 0;
        this.nota = "";
        this.url_pdf = "";
        this.lista = new ArrayList<Producto>();
    }
    public static double redondear(double valor, int decimales) {
        if (decimales < 0) {
            throw new IllegalArgumentException("El número de decimales no puede ser negativo");
        }

        BigDecimal bd = new BigDecimal(Double.toString(valor));
        bd = bd.setScale(decimales, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }
    public void Registrar(){
        //String fechaPagoFormat = (fechaPago != null) ? fechaPago.format(formatoFecha) : null;
        /*
        if(queries.querieReturnINT("select CompraExiste(?)", id_compra) != 1){
        }
        */
        id_compra = queries.querieReturnINT("call InsertarCompra(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{
            id_tipo_comprobante, correlativo, id_tipo_condicion,id_clienteproveedor,id_usuario,id_almacen,id_tipo_pago,fecha, garantia,
            costo_dolar, costo_soles, tipo_cambio, gravada, impuesto, total, nota
            });
            for(Producto item : lista){
                for(String SN : item.getSeries()){
                    boolean flag = queries.ejecutarProcedimiento("call Insertar_Producto_Serie(?,?,?,?,?)", 5, new Object[]{SN, item.getId_producto(),1,id_almacen});
                    if(flag){
                        queries.QuerieNormal("call InsertarDetalleCompra(?,?,?,?,?)", new Object[]{
                        id_compra,SN,item.getValor_unitario(),item.getImpuesto_unitario(), item.getPrecio()
                        });
                    }
                }
            }
            
    }
    /**
     * @return the id_compra
     */
    public int getId_compra() {
        return id_compra;
    }

    /**
     * @param id_compra the id_compra to set
     */
    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    /**
     * @return the id_tipo_comprobante
     */
    public int getId_tipo_comprobante() {
        return id_tipo_comprobante;
    }

    /**
     * @param id_tipo_comprobante the id_tipo_comprobante to set
     */
    public void setId_tipo_comprobante(int id_tipo_comprobante) {
        this.id_tipo_comprobante = id_tipo_comprobante;
    }

    /**
     * @return the correlativo
     */
    public int getCorrelativo() {
        return correlativo;
    }

    /**
     * @param correlativo the correlativo to set
     */
    public void setCorrelativo(int correlativo) {
        this.correlativo = correlativo;
    }

    /**
     * @return the id_tipo_condicion
     */
    public int getId_tipo_condicion() {
        return id_tipo_condicion;
    }

    /**
     * @param id_tipo_condicion the id_tipo_condicion to set
     */
    public void setId_tipo_condicion(int id_tipo_condicion) {
        this.id_tipo_condicion = id_tipo_condicion;
    }

    /**
     * @return the id_clienteproveedor
     */
    public int getId_clienteproveedor() {
        return id_clienteproveedor;
    }

    /**
     * @param id_clienteproveedor the id_clienteproveedor to set
     */
    public void setId_clienteproveedor(int id_clienteproveedor) {
        this.id_clienteproveedor = id_clienteproveedor;
    }

    /**
     * @return the id_usuario
     */
    public int getId_usuario() {
        return id_usuario;
    }

    /**
     * @param id_usuario the id_usuario to set
     */
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    /**
     * @return the id_almacen
     */
    public int getId_almacen() {
        return id_almacen;
    }

    /**
     * @param id_almacen the id_almacen to set
     */
    public void setId_almacen(int id_almacen) {
        this.id_almacen = id_almacen;
    }

    /**
     * @return the fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the garantia
     */
    public int getGarantia() {
        return garantia;
    }

    /**
     * @param garantia the garantia to set
     */
    public void setGarantia(int garantia) {
        this.garantia = garantia;
    }

    /**
     * @return the costo_dolar
     */
    public double getCosto_dolar() {
        return costo_dolar;
    }

    /**
     * @param costo_dolar the costo_dolar to set
     */
    public void setCosto_dolar(double costo_dolar) {
        this.costo_dolar = redondear(costo_dolar, 2);
    }

    /**
     * @return the costo_soles
     */
    public double getCosto_soles() {
        return costo_soles;
    }

    /**
     * @param costo_soles the costo_soles to set
     */
    public void setCosto_soles(double costo_soles) {
        this.costo_soles = redondear(costo_soles, 2);
    }

    /**
     * @return the tipo_cambio
     */
    public double getTipo_cambio() {
        return tipo_cambio;
    }

    /**
     * @param tipo_cambio the tipo_cambio to set
     */
    public void setTipo_cambio(double tipo_cambio) {
        this.tipo_cambio = redondear(tipo_cambio, 2);
    }

    /**
     * @return the gravada
     */
    public double getGravada() {
        return gravada;
    }

    /**
     * @param gravada the gravada to set
     */
    public void setGravada(double gravada) {
        this.gravada = redondear(gravada, 2);
    }

    /**
     * @return the impuesto
     */
    public double getImpuesto() {
        return impuesto;
    }

    /**
     * @param impuesto the impuesto to set
     */
    public void setImpuesto(double impuesto) {
        this.impuesto = redondear(impuesto, 2);
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = redondear(total, 2);
    }

    /**
     * @return the url_pdf
     */
    public String getUrl_pdf() {
        return url_pdf;
    }

    /**
     * @param url_pdf the url_pdf to set
     */
    public void setUrl_pdf(String url_pdf) {
        this.url_pdf = url_pdf;
    }

    /**
     * @return the formatoFecha
     */
    public DateTimeFormatter getFormatoFecha() {
        return formatoFecha;
    }

    /**
     * @param formatoFecha the formatoFecha to set
     */
    public void setFormatoFecha(DateTimeFormatter formatoFecha) {
        this.formatoFecha = formatoFecha;
    }

    /**
     * @return the lista
     */
    public ArrayList<Producto> getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(ArrayList<Producto> lista) {
        this.lista = lista;
    }

    /**
     * @return the id_tipo_pago
     */
    public int getId_tipo_pago() {
        return id_tipo_pago;
    }

    /**
     * @param id_tipo_pago the id_tipo_pago to set
     */
    public void setId_tipo_pago(int id_tipo_pago) {
        this.id_tipo_pago = id_tipo_pago;
    }

    /**
     * @return the nota
     */
    public String getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(String nota) {
        this.nota = nota;
    }
}
