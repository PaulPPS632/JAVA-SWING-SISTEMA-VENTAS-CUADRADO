/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaries;

import ConexionMysql.queries;
import static Frames.frmVentas.Render;
import static Frames.frmVentas.getLista;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
/**
 *
 * @author Paul
 */
public class Venta {

    private int id_venta;
    private int id_tipo_comprobante;
    private int correlativo;
    private int id_clienteproveedor;
    private int id_usuario;
    private int id_tipo_condicion;
    private int id_tipo_pago;
    private int id_tipo_moneda;
    private double tipo_cambio;
    private LocalDate fecha;
    private String nota;
    private double gravada;
    private double impuesto;
    private double total;
    private int id_estadoVenta;
    private LocalDate fechaPago;
    private String formaPago;
    private String url_pdf;
    private int id_pseudonimo;
    private double deuda_independiente;
    private DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private ArrayList<Producto> lista;
    
    public Venta(int ID){
        this.id_venta = ID;
        String[] Datos = queries.querieReturnDatosFila("select * from venta where id_venta = ?", ID);
        this.id_tipo_comprobante = Integer.parseInt(Datos[1]);
        this.correlativo = Integer.parseInt(Datos[2]);
        this.id_clienteproveedor = Integer.parseInt(Datos[3]);
        this.id_usuario = Integer.parseInt(Datos[4]);
        this.id_tipo_condicion = Integer.parseInt(Datos[5]);
        this.id_tipo_pago = Integer.parseInt(Datos[6]);
        this.fecha = LocalDate.parse(Datos[7].split(" ")[0]);
        //this.fecha = LocalDate.now();
        this.nota = Datos[8];
        this.gravada = Double.parseDouble(Datos[9]);
        this.impuesto = Double.parseDouble(Datos[10]);
        this.total = Double.parseDouble(Datos[11]);
        this.id_estadoVenta = Integer.parseInt(Datos[12]);
        //this.fechaPago = LocalDate.parse(Datos[13]);
        this.fechaPago = null;
        this.formaPago = Datos[14];
        this.url_pdf = Datos[15];
        this.id_tipo_moneda = Integer.parseInt(Datos[16]);
        this.tipo_cambio = Double.parseDouble(Datos[17]);
        this.id_pseudonimo = Integer.parseInt(Datos[18]);
        this.deuda_independiente = Double.parseDouble(Datos[19]);
        this.lista = new ArrayList<Producto>();
        ArrayList<Object[]> rpta = queries.QueryReturnArrayListObject("call detallesdeventaporidventa(?)", ID);
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
    public static ArrayList<Producto> DetallesVenta (int ID){
        ArrayList<Producto> lista = new ArrayList<Producto>();
        ArrayList<Object[]> rpta = queries.QueryReturnArrayListObject("call detallesdeventaporidventa(?)", ID);
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
        return lista;
    }
    public Venta(){
        this.id_venta = 0;
        this.id_tipo_comprobante = 0;
        this.correlativo = 0;
        this.id_clienteproveedor = 0;
        this.id_usuario = 0;
        this.id_tipo_condicion = 0;
        this.id_tipo_pago =0;
        this.fecha = LocalDate.now();
        this.nota = "";
        this.gravada = 0;
        this.impuesto = 0;
        this.total = 0;
        this.id_estadoVenta = 0;
        this.fechaPago = null;
        this.formaPago = "";
        this.url_pdf = "";
        this.id_tipo_moneda = 1;
        this.tipo_cambio = 0;
        this.id_pseudonimo = 0;
        this.deuda_independiente = 0;
        this.lista = new ArrayList<Producto>();
    }
    public boolean VentaExiste(int ID){
        return queries.querieReturnINT("select ventaexiste(?)", ID) == 1;
    }
    public static boolean VentaExisteStatic(int ID){
        return queries.querieReturnINT("select ventaexiste(?)", ID) == 1;
    }
    public String[] DatosCliente(){
        return queries.querieReturnDatosFila("select * from clientesyproveedor where id_clienteproveedor = ?", id_clienteproveedor);
    }
    public void Registrar(){
        //String fechaPagoFormat = (fechaPago != null) ? fechaPago.format(formatoFecha) : null;
        if(!VentaExiste(id_venta)){
            
            id_venta = queries.querieReturnINT("call insertarventa(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{
            id_tipo_comprobante, correlativo, id_clienteproveedor,id_usuario,id_tipo_condicion,id_tipo_pago,
            fecha, nota, gravada,impuesto, total, getId_estadoVenta(), 
            fechaPago,formaPago,url_pdf, id_tipo_moneda, tipo_cambio, getId_pseudonimo(),deuda_independiente
            });
            for(Producto item : lista){
                int cont = 0;
                for(String SN : item.getSeries()){
                    queries.QuerieNormal("call insertardetalleventa(?,?,?,?,?)", new Object[]{
                    id_venta,SN,item.getValor_unitario(),item.getImpuesto_unitario(), item.getPrecio()
                    });
                    cont++;
                }
                if(cont != 0) queries.querieUpdate("Update productos set stock = stock - "+cont+" where id_producto = " + item.getId_producto());
            }
        }else{
            
            queries.QuerieNormal("call modificarventa(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{
            id_venta, id_tipo_comprobante, correlativo, id_clienteproveedor,id_usuario,id_tipo_condicion,
            id_tipo_pago, fecha, nota, gravada,impuesto, total, getId_estadoVenta(), fechaPago,formaPago,url_pdf, id_tipo_moneda, tipo_cambio, getId_pseudonimo()
            });
            for(Producto item : lista){
                for(String SN : item.getSeries()){
                    queries.QuerieNormal("call modificardetalleventa(?,?,?,?,?)", new Object[]{
                    id_venta,SN,item.getValor_unitario(),item.getImpuesto_unitario(), item.getPrecio()
                    });
                }
            }
        }
        
    }
    public void ingresar_deuda_seudonimo(double cantidad){
        queries.QuerieNormal("call realizar_ingreso_deuda(?,?)", new Object[]{id_venta, cantidad});
    }
    public void Devoluciones(ArrayList<String> SeriesDevueltas){
        
        for(String series : SeriesDevueltas){
            int cont = 0;
            for(Producto prdc : lista){
                if(prdc.getId_producto() == Producto.IDProductoPorSerie(series)){
                    break;
                }else{
                    cont++;
                }
            }
            int cantventact = lista.get(cont).getCantidadVenta();
            int id_prodc = lista.get(cont).getId_producto();
            lista.get(cont).setCantidadVenta(cantventact-1);
            lista.get(cont).getSeries().remove(series);
            queries.QuerieNormal("delete from detalle_venta where id_venta = ? and sn = ?", new Object[]{id_venta, series});
            queries.QuerieNormal("call cambiarestadoproducto(?,?,?)", new Object[]{1,series,id_prodc});
            
        }
        setEstadoVenta(6);
        RecalculoPrecios();
        Registrar();
    }
    public void DevolucionTotal(){
        for(Producto prdc : lista){
            for(String serie : prdc.getSeries()){
                
                queries.QuerieNormal("call cambiarestadoproducto(?,?,?)", new Object[]{1,serie,prdc.getId_producto()});
            }
        }
        queries.querieUpdate("UPDATE venta SET id_estadoventa = 5 where id_venta = " + id_venta);
        queries.QuerieNormal("call quitar_deuda(?)", id_venta);
    }
    public void anular_registro_venta(){
        queries.QuerieNormal("call eliminar_registro_venta(?)", id_venta);
    }
    public void GenerarFacturacionAPI(String NOTAFACTURA, int id_razonsocial){
        String Prefijo = "";
        switch (id_tipo_comprobante) {
            case 1:
                Prefijo = "F001";
                break;
            case 2:
                Prefijo = "B001";
                break;
            case 3:
                Prefijo = "T001";
                break;
        }
        String[] Datosrazonsocial = queries.querieReturnDatosFila("select ruta_api, token from razonsocial where id_razonsocial = ?", id_razonsocial);
        String[] DatosCliente = queries.querieReturnDatosFila("select * from clientesyproveedor where id_clienteproveedor = ?", id_clienteproveedor);
        ConsumeAPI generar = new ConsumeAPI();
        generar.setRUTA(Datosrazonsocial[0]);
        generar.setTOKEN(Datosrazonsocial[1]);
        generar.setTipo_comprobante(id_tipo_comprobante);
        generar.setSeriePrefijo(Prefijo);
        generar.setNumeroCorrelativo(correlativo);
        generar.setCliente_Tipo_Documento(DatosCliente[1]);
        generar.setCliente_Numero_Documento(DatosCliente[2]);
        generar.setCliente_Denominacion(DatosCliente[3]);
        generar.setCliente_Direccion(DatosCliente[5]);
        generar.setCliente_Email(DatosCliente[6]);
        generar.setFecha_Emision(LocalDate.now());
        generar.setTipo_Modena(id_tipo_moneda);
        if(id_tipo_moneda != 1) generar.setTipo_Cambio(tipo_cambio);
        
        generar.setTotal_Gravada(gravada);
        generar.setTotal_Igv(impuesto);
        generar.setTotal(total);
        
        generar.setObservaciones(NOTAFACTURA);
        generar.setMedio_Pago(formaPago);
        
        for(Producto item : getLista()){
            generar.InsertarItem("NIU", item.getPad_interno(), 
                    item.getDescripcion() + "S/N: " + item.ListadoSeries(), item.getCantidadVenta(), 
                    item.getValor_unitario(), item.getPrecio(), 0, item.getPrecioBruto(), 1, 
                    item.getImpuesto(), item.getPrecioNeto(), false);
        }
        
        generar.Generar_Fact_Bole_Nota();
        generar.EXECUTE();
        if(generar.getEjecucionNormal()){
            Object[] rpta = generar.Respuesta_Fact_Bole_Nota();
            JOptionPane.showMessageDialog(null, "DOCUMENTO GENERADO CORRECTAMENTE");
            setUrl_pdf(rpta[14].toString());
            Funcionalidades.goToURL(rpta[14].toString());
            queries.QuerieNormal("update venta set correlativo = ?, url_pdf = ?, id_estadoventa = ? where id_venta = ?", new Object[]{correlativo,rpta[14].toString(),3,id_venta});
            queries.QuerieNormal("update correlativos set numero = numero + 1 where id_tipo_comprobante = ? and id_razonsocial = ?", new Object[]{generar.getTipo_comprobante(),id_razonsocial});
            //queries.QuerieNormal("update venta set url_pdf = ? where id_venta = ?",new Object[]{rpta[14].toString(), id_venta} );
            
        }
    }
    public void GenerarNOTACREDITO(String NOTAFACTURA, int id_razonsocial){
        String Prefijo = "";
        switch (id_tipo_comprobante) {
            case 1:
                Prefijo = "FC01";
                break;
            case 2:
                Prefijo = "BC01";
                break;
        }
        String PrefijoModificar = "";
        switch (id_tipo_comprobante) {
            case 1:
                PrefijoModificar = "F001";
                break;
            case 2:
                PrefijoModificar = "B001";
                break;
        }
        String[] Datosrazonsocial = queries.querieReturnDatosFila("select ruta_api, token from razonsocial where id_razonsocial = ?", id_razonsocial);
        String[] DatosCliente = queries.querieReturnDatosFila("select * from clientesyproveedor where id_clienteproveedor = ?", id_clienteproveedor);
        ConsumeAPI generar = new ConsumeAPI();
        generar.setRUTA(Datosrazonsocial[0]);
        generar.setTOKEN(Datosrazonsocial[1]);
        generar.setTipo_comprobante(3);
        generar.setSeriePrefijo(Prefijo);
        int correlativo_nota = queries.querieReturnINT("select numero from correlativos where id_tipo_comprobante = ? and id_razonsocial = ?", new Object[]{3,id_razonsocial});
        generar.setNumeroCorrelativo(correlativo_nota);
        generar.setCliente_Tipo_Documento(DatosCliente[1]);
        generar.setCliente_Numero_Documento(DatosCliente[2]);
        generar.setCliente_Denominacion(DatosCliente[3]);
        generar.setCliente_Direccion(DatosCliente[5]);
        generar.setCliente_Email(DatosCliente[6]);
        generar.setFecha_Emision(LocalDate.now());
        generar.setTipo_Modena(id_tipo_moneda);
        if(id_tipo_moneda != 1) generar.setTipo_Cambio(tipo_cambio);
        
        generar.setTotal_Gravada(gravada);
        generar.setTotal_Igv(impuesto);
        generar.setTotal(total);
        
        generar.setObservaciones(NOTAFACTURA);
        generar.setMedio_Pago(formaPago);
        
        for(Producto item : getLista()){
            generar.InsertarItem("NIU", item.getPad_interno(), 
                    item.getDescripcion() + "S/N: " + item.ListadoSeries(), item.getCantidadVenta(), 
                    item.getValor_unitario(), item.getPrecio(), 0, item.getPrecioBruto(), 1, 
                    item.getImpuesto(), item.getPrecioNeto(), false);
        }
        generar.setTipo_Documento_Que_Se_Modifica(id_tipo_comprobante);
        generar.setSeriePrefijo_Documento_Que_Se_Modifica(PrefijoModificar);
        generar.setNumeroCorrelativo_Documento_Que_Se_Modifica(correlativo);
        generar.setTipo_Nota_Credito(1);
        generar.Generar_Fact_Bole_Nota();
        generar.EXECUTE();
        if(generar.getEjecucionNormal()){
            Object[] rpta = generar.Respuesta_Fact_Bole_Nota();
            JOptionPane.showMessageDialog(null, "DOCUMENTO GENERADO CORRECTAMENTE");
            setUrl_pdf(rpta[14].toString());
            Funcionalidades.goToURL(rpta[14].toString());
            DevolucionTotal();
            queries.QuerieNormal("update correlativos set numero = numero + 1 where id_tipo_comprobante = ? and id_razonsocial = ?", new Object[]{generar.getTipo_comprobante(),id_razonsocial});
            //queries.QuerieNormal("update venta set url_pdf = ? where id_venta = ?",new Object[]{rpta[14].toString(), id_venta} );
            queries.QuerieNormal("update venta set correlativo = ?, url_pdf = ?, id_estadoventa = ? where id_venta = ?", new Object[]{correlativo_nota,rpta[11].toString(),8,id_venta});
            
        }
    }
    public boolean AnulacionAPI(String Motivo, int id_razonsocial){
        String[] Datosrazonsocial = queries.querieReturnDatosFila("select ruta_api, token from razonsocial where id_razonsocial = ?", id_razonsocial);
        ConsumeAPI nuevo = new ConsumeAPI();
        nuevo.setRUTA(Datosrazonsocial[0]);
        nuevo.setTOKEN(Datosrazonsocial[1]);
        nuevo.setTipo_comprobante(getId_tipo_comprobante());
        String Prefijo = "";
        switch (id_tipo_comprobante) {
            case 1:
                Prefijo = "F001";
                break;
            case 2:
                Prefijo = "B001";
                break;
            case 3:
                Prefijo = "T001";
                break;
        }
        nuevo.setSeriePrefijo(Prefijo);
        nuevo.setNumeroCorrelativo(getCorrelativo());
        nuevo.setMotivo(Motivo);
        nuevo.Generar_Anulacion();
        nuevo.EXECUTE();
        if(nuevo.getEjecucionNormal()){
            Object[] rpta = nuevo.Respuesta_Generar_Anulacion();
            JOptionPane.showMessageDialog(null, "DOCUMENTO ANULADO CORRECTAMENTE");
            queries.QuerieNormal("update venta set correlativo = ?, url_pdf = ?, id_estadoventa = ? where id_venta = ?", new Object[]{correlativo,rpta[11].toString(),7,id_venta});
        }
        return nuevo.getEjecucionNormal();
    }
    
    public static double redondear(double valor, int decimales) {
        if (decimales < 0) {
            throw new IllegalArgumentException("El número de decimales no puede ser negativo");
        }
        BigDecimal bd = new BigDecimal(Double.toString(valor));
        bd = bd.setScale(decimales, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public void RecalculoPrecios(){
        double gravada = 0, impuesto = 0, total = 0; 
        for(Producto pdc : lista){
            gravada += pdc.getPrecioBruto();
            impuesto += pdc.getImpuesto();
            total += pdc.getPrecioNeto();
        }
        this.gravada = redondear(gravada, 2);
        this.impuesto = redondear(impuesto, 2);
        this.total = redondear(total, 2);
    }
    /**
     * @return the id_venta
     */
    public int getId_venta() {
        return id_venta;
    }

    /**
     * @param id_venta the id_venta to set
     */
    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
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
    
    /**
     * @return the gravada
     */
    public double getGravada() {
        return impuesto;
    }

    /**
     * @param Gravada the gravada to set
     */
    public void setGravada(double Gravada) {
        this.gravada = Gravada;
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
        this.impuesto = impuesto;
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
        this.total = total;
    }

    /**
     * @return the estadoVenta
     */
    public int getEstadoVenta() {
        return getId_estadoVenta();
    }

    /**
     * @param estadoVenta the estadoVenta to set
     */
    public void setEstadoVenta(int estadoVenta) {
        this.setId_estadoVenta(estadoVenta);
    }

    /**
     * @return the fechaPago
     */
    public LocalDate getFechaPago() {
        return fechaPago;
    }

    /**
     * @param fechaPago the fechaPago to set
     */
    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    /**
     * @return the formaPago
     */
    public String getFormaPago() {
        return formaPago;
    }

    /**
     * @param formaPago the formaPago to set
     */
    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
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
     * @return the id_tipo_moneda
     */
    public int getId_tipo_moneda() {
        return id_tipo_moneda;
    }

    /**
     * @param id_tipo_moneda the id_tipo_moneda to set
     */
    public void setId_tipo_moneda(int id_tipo_moneda) {
        this.id_tipo_moneda = id_tipo_moneda;
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
        this.tipo_cambio = tipo_cambio;
    }

    /**
     * @return the id_estadoVenta
     */
    public int getId_estadoVenta() {
        return id_estadoVenta;
    }

    /**
     * @param id_estadoVenta the id_estadoVenta to set
     */
    public void setId_estadoVenta(int id_estadoVenta) {
        this.id_estadoVenta = id_estadoVenta;
    }

    /**
     * @return the deuda_independiente
     */
    public double getDeuda_independiente() {
        return deuda_independiente;
    }

    /**
     * @param deuda_independiente the deuda_independiente to set
     */
    public void setDeuda_independiente(double deuda_independiente) {
        this.deuda_independiente = deuda_independiente;
    }

    /**
     * @return the id_pseudonimo
     */
    public int getId_pseudonimo() {
        return id_pseudonimo;
    }

    /**
     * @param id_pseudonimo the id_pseudonimo to set
     */
    public void setId_pseudonimo(int id_pseudonimo) {
        this.id_pseudonimo = id_pseudonimo;
    }
}
