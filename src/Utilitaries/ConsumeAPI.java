/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaries;

import ConexionMysql.queries;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.parser.JSONParser;
//import org.json.parser.ParseException;

/**
 *
 * @author Paul
 */
public class ConsumeAPI {

//  RUTA para enviar documentos
    //claves originales cuadrado:
    private String RUTA = "https://api.pse.pe/api/v1/70e5db2ba5a844508d17d5eec89df2d6482b55883cdc42c88f9c1b7341bc726d";
    private String TOKEN = "eyJhbGciOiJIUzI1NiJ9.IjU4YTIxZGJlOTY1OTQzZTU5Y2YwNWE4ZjQwNTU1YzJmZjFmYjQ4YjA1MGM1NDUwNjk3NzVlYzg5MDNkYzY1NjUi.G2Aoiobo9BYWEacstqmnjzEsSSY8158A98DNpGIOpH8";

    /*
    private final String RUTA = "https://api.nubefact.com/api/v1/83beaccb-e52a-4cb4-ab86-5177f913939b";
//  TOKEN para enviar documentos    
    private final String TOKEN = "7185e4d7c75b4482a778b29d5084270c8e493fbaf02a43268de2c65dedbb3196";
     */
    private boolean ejecucionNormal;

    private String Operacion;
    private int tipo_comprobante;
    private String SeriePrefijo;
    private int NumeroCorrelativo;
    private int sunat_transaction = 1;
    private String Cliente_Tipo_Documento;
    private String Cliente_Numero_Documento;
    private String Cliente_Denominacion;
    private String Cliente_Direccion;
    private String Cliente_Email;
    private String Cliente_Email1;
    private String Cliente_Email2;
    private LocalDate Fecha_Emision;
    private LocalDate Fecha_Vencimiento;
    private int Tipo_Modena;
    private double Tipo_Cambio;
    private double Porcentaje_Igv = 18.00;
    private double Descuento_Global;
    private double Total_Descuento;
    private double Total_Anticipo;
    private double Total_Gravada;
    private double Total_inafecta;
    private double Total_exonerada;
    private double Total_Igv;
    private double Total_Gratuita;
    private double Total_Otros_Cargos;
    private double Total_Isc;
    private double Total;
    private int Tipo_Percepcion;
    private double Percepcion_base_Imponible;
    private double Total_Percepcion;
    private double Total_Incluido_Percepcion;
    private int Tipo_Retencion;
    private double Retencion_Base_Imponible;
    private double Total_Retencion;
    private double Total_Impuestos_Bolsas;
    private String Observaciones;
    private int Tipo_Documento_Que_Se_Modifica;
    private String SeriePrefijo_Documento_Que_Se_Modifica;
    private int NumeroCorrelativo_Documento_Que_Se_Modifica;
    private int Tipo_Nota_Credito;
    private int Tipo_Nota_Debito;
    private boolean Enviar_Sunat;
    private boolean Envia_cliente;
    private String Codigo_Unico; // Usarlo sólo si deseas que controlemos la generación de documentos. Código único generado y asignado por tu sistema. Por ejemplo puede estar compuesto por el tipo de documento, serie y número correlativo.
    private String Condiciones_Pago;
    private String Medio_Pago; //Ejemplo: TARJETA VISA OP: 232231
    private String Placa_vehiculo;
    private String Orden_Compra_Servicio;
    private boolean Detraccion;
    private int Detraccion_Tipo;
    private double Detraccion_Total;
    private double Detraccion_Porcentaje;
    private int Medio_Pago_Detraccion;
    private int Ubigeo_Origen;
    private String Direccion_Origen;
    private int Ubigeo_Destino;
    private String Direccion_Destino;
    private String Detalle_Viaje;
    private double Val_Ref_Serv_trans;
    private double Val_Ref_Carga_Efec;
    private double Val_Ref_Carga_util;
    private String Detalle_Tramo_Vehiculo;
    private int Punto_Origen_Viaje; //SOLO EN TIPO DETRACCIÓN DE TRANSPORTE DE CARGA
    private int Punto_Destino_Viaje; //SOLO EN TIPO DETRACCIÓN DE TRANSPORTE DE CARGA
    private String Descripcion_Tramo; //SOLO EN TIPO DETRACCIÓN DE TRANSPORTE DE CARGA
    private double val_ref_carga_efec_tramo_virtual; //SOLO EN TIPO DETRACCIÓN DE TRANSPORTE DE CARGA
    private String Configuracion_Vehicular; //SOLO EN TIPO DETRACCIÓN DE TRANSPORTE DE CARGA
    private double carga_util_tonel_metricas; //SOLO EN TIPO DETRACCIÓN DE TRANSPORTE DE CARGA
    private double carga_efec_tonel_metricas; //SOLO EN TIPO DETRACCIÓN DE TRANSPORTE DE CARGA
    private double val_ref_tonel_metrica; //SOLO EN TIPO DETRACCIÓN DE TRANSPORTE DE CARGA
    private double val_pre_ref_carga_util_nominal; //SOLO EN TIPO DETRACCIÓN DE TRANSPORTE DE CARGA
    private boolean indicador_aplicacion_retorno_vacio; //SOLO EN TIPO DETRACCIÓN DE TRANSPORTE DE CARGA
    private String matricula_emb_pesquera; //SOLO EN TIPO DETRACCIÓN DE RECURSOS HIDROBIOLÓGICOS
    private String nombre_emb_pesquera; //SOLO EN TIPO DETRACCIÓN DE RECURSOS HIDROBIOLÓGICOS
    private String descripcion_tipo_especie_vendida; //SOLO EN TIPO DETRACCIÓN DE RECURSOS HIDROBIOLÓGICOS
    private String lugar_de_descarga;//SOLO EN TIPO DETRACCIÓN DE RECURSOS HIDROBIOLÓGICOS
    private double cantidad_especie_vendida;//SOLO EN TIPO DETRACCIÓN DE RECURSOS HIDROBIOLÓGICOS
    private LocalDate fecha_de_descarga;//SOLO EN TIPO DETRACCIÓN DE RECURSOS HIDROBIOLÓGICOS
    private String Formato_Pdf = "A4";
    private boolean generado_por_contingencia;
    private boolean bienes_region_selva;
    private boolean servicios_region_selva;
    private String nubecont_tipo_de_venta_codigo;
    private JSONArray items = new JSONArray();
    private JSONArray guias = new JSONArray();
    private JSONArray venta_al_credito = new JSONArray();
    private String Tabla_Personalizada_Codigo;
    private String Motivo;
    private JSONObject Cabecera;
    private JSONObject Respuesta;

    public ConsumeAPI() {

    }

    public void InsertarItem(String UnidadMedida, String CodigoInterno, String Descripcion,
            double Cantidad, double ValorUnitario, double PrecioUnitario, double Descuento,
            double Subtotal, int TipoIGV, double IGV, double Total, boolean AnticipoRegularizacion//,
    //String anticipo_documento_serie_prefijo, int anticipo_documento_numero_correlativo,
    //int guia_tipo, String guia_serie_numero, int credito_cuota, LocalDate credito_fecha_pago,
    //double credito_importe
    ) {
        JSONObject NuevoItem = new JSONObject();
        NuevoItem.put("unidad_de_medida", UnidadMedida);
        NuevoItem.put("codigo", CodigoInterno);
        NuevoItem.put("descripcion", Descripcion);
        NuevoItem.put("cantidad", Cantidad);
        NuevoItem.put("valor_unitario", ValorUnitario);
        NuevoItem.put("precio_unitario", PrecioUnitario);
        NuevoItem.put("descuento", Descuento);
        NuevoItem.put("subtotal", Subtotal);
        NuevoItem.put("tipo_de_igv", TipoIGV);
        NuevoItem.put("igv", IGV);
        NuevoItem.put("total", Total);
        NuevoItem.put("anticipo_regularizacion", AnticipoRegularizacion);
        //NuevoItem.put("anticipo_serie", "");
        //NuevoItem.put("anticipo_documento_numero", "");
        items.put(NuevoItem);
        //Valor Unintario-> Sin IGV
        //Precio Unitario-> Con IGV
        //Descuento antes de los Impuestos
        //Resultado de VALOR UNITARIO por la CANTIDAD menos el DESCUENTOOOO*/
        //NIU-> Producto, ZZ-> Servicio 

    }

    public void Generar_Fact_Bole_Nota() {
        setCabecera(new JSONObject());
        getCabecera().put("operacion", "generar_comprobante");
        getCabecera().put("tipo_de_comprobante", getTipo_comprobante());
        getCabecera().put("serie", getSeriePrefijo());
        getCabecera().put("numero", getNumeroCorrelativo());
        getCabecera().put("sunat_transaction", 1);
        getCabecera().put("cliente_tipo_de_documento", getCliente_Tipo_Documento());
        getCabecera().put("cliente_numero_de_documento", getCliente_Numero_Documento());
        getCabecera().put("cliente_denominacion", getCliente_Denominacion());
        getCabecera().put("cliente_direccion", getCliente_Direccion());
        getCabecera().put("cliente_email", getCliente_Email());
        getCabecera().put("fecha_de_emision", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        getCabecera().put("fecha_de_vencimiento", "");
        getCabecera().put("moneda", getTipo_Modena());
        if (getTipo_Modena() == 1) {
            getCabecera().put("tipo_de_cambio", "");
        } else {
            getCabecera().put("tipo_de_cambio", getTipo_Cambio());
        }

        getCabecera().put("porcentaje_de_igv", getPorcentaje_Igv());

        getCabecera().put("descuento_global", "");
        getCabecera().put("total_descuento", "");
        getCabecera().put("total_anticipo", "");
        getCabecera().put("total_gravada", getTotal_Gravada());
        getCabecera().put("total_inafecta", "");
        getCabecera().put("total_exonerada", "");
        getCabecera().put("total_igv", getTotal_Igv());
        getCabecera().put("total_gratuita", "");
        getCabecera().put("total_otros_cargos", "");
        getCabecera().put("total", getTotal());
        getCabecera().put("percepcion_tipo", "");
        getCabecera().put("percepcion_base_imponible", "");
        getCabecera().put("total_percepcion", "");
        getCabecera().put("total_incluido_percepcion", "");
        getCabecera().put("detraccion", "false");
        getCabecera().put("observaciones", getObservaciones());
        getCabecera().put("documento_que_se_modifica_tipo", getTipo_Documento_Que_Se_Modifica());
        getCabecera().put("documento_que_se_modifica_serie", getSeriePrefijo_Documento_Que_Se_Modifica());
        getCabecera().put("documento_que_se_modifica_numero", getNumeroCorrelativo_Documento_Que_Se_Modifica());
        getCabecera().put("tipo_de_nota_de_credito", getTipo_Nota_Credito());
        getCabecera().put("tipo_de_nota_de_debito", getTipo_Nota_Debito());
        getCabecera().put("enviar_automaticamente_a_la_sunat", "true");// debe ser true
        getCabecera().put("enviar_automaticamente_al_cliente", "false");
        getCabecera().put("codigo_unico", "");
        getCabecera().put("condiciones_de_pago", "");
        getCabecera().put("medio_de_pago", getMedio_Pago());
        getCabecera().put("placa_vehiculo", "");
        getCabecera().put("orden_compra_servicio", "");
        getCabecera().put("tabla_personalizada_codigo", "");
        getCabecera().put("formato_de_pdf", "A4");
        getCabecera().put("items", getItems());
    }

    public void Consultas_Fact_Bole_Nota() {
        Cabecera = new JSONObject();
        Cabecera.put("operacion", "consultar_comprobante");
        Cabecera.put("tipo_de_comprobante", getTipo_comprobante());
        Cabecera.put("serie", getSeriePrefijo());
        Cabecera.put("numero", getNumeroCorrelativo());
    }

    public Object[] Respuesta_Fact_Bole_Nota() {
        Object[] Rpta = new Object[17];
        Rpta[0] = Respuesta.get("tipo_de_comprobante");
        Rpta[1] = Respuesta.get("serie");
        Rpta[2] = Respuesta.get("numero");
        Rpta[3] = Respuesta.get("enlace");//enlace unico de NUBE FACT se agrega .pdf al final para descargar pdf
        Rpta[4] = Respuesta.get("aceptada_por_sunat");
        Rpta[5] = Respuesta.get("sunat_description");
        Rpta[6] = Respuesta.get("sunat_note");
        Rpta[7] = Respuesta.get("sunat_responsecode");
        Rpta[8] = Respuesta.get("sunat_soap_error");
        Rpta[9] = Respuesta.get("pdf_zip_base64");
        Rpta[10] = Respuesta.get("xml_zip_base64");
        Rpta[11] = Respuesta.get("cdr_zip_base64");
        Rpta[12] = Respuesta.get("cadena_para_codigo_qr");
        Rpta[13] = Respuesta.get("codigo_hash");
        Rpta[14] = Respuesta.get("enlace_del_pdf");
        Rpta[15] = Respuesta.get("enlace_del_xml");
        Rpta[16] = Respuesta.get("enlace_del_cdr");
        return Rpta;
    }

    public void Generar_Anulacion() {
        setCabecera(new JSONObject());
        Cabecera.put("operacion", "generar_anulacion");
        Cabecera.put("tipo_de_comprobante", getTipo_comprobante());
        Cabecera.put("serie", getSeriePrefijo());
        Cabecera.put("numero", getNumeroCorrelativo());
        Cabecera.put("motivo", getMotivo());
    }

    public Object[] Respuesta_Generar_Anulacion() {
        Object[] Rpta = new Object[14];
        Rpta[0] = Respuesta.get("numero");
        Rpta[1] = Respuesta.get("enlace");
        Rpta[2] = Respuesta.get("sunat_ticket_numero");
        Rpta[3] = Respuesta.get("aceptada_por_sunat");
        Rpta[4] = Respuesta.get("sunat_description");
        Rpta[5] = Respuesta.get("sunat_note");
        Rpta[6] = Respuesta.get("sunat_responsecode");
        Rpta[7] = Respuesta.get("sunat_soap_error");
        Rpta[8] = Respuesta.get("pdf_zip_base64");
        Rpta[9] = Respuesta.get("xml_zip_base64");
        Rpta[10] = Respuesta.get("cdr_zip_base64");
        Rpta[11] = Respuesta.get("enlace_del_pdf");
        Rpta[12] = Respuesta.get("enlace_del_xml");
        Rpta[13] = Respuesta.get("enlace_del_cdr");
        return Rpta;
    }

    public void Consultar_Anulacion() {
        Cabecera = new JSONObject();
        Cabecera.put("operacion", "consultar_anulacion");
        Cabecera.put("tipo_de_comprobante", getTipo_comprobante());
        Cabecera.put("serie", getSeriePrefijo());
        Cabecera.put("numero", getNumeroCorrelativo());
    }

    public Object[] Respuesta_Consultar_Anulacion() {
        Object[] Rpta = new Object[14];
        Rpta[0] = Respuesta.get("numero");
        Rpta[1] = Respuesta.get("enlace");
        Rpta[2] = Respuesta.get("sunat_ticket_numero");
        Rpta[3] = Respuesta.get("aceptada_por_sunat");
        Rpta[4] = Respuesta.get("sunat_description");
        Rpta[5] = Respuesta.get("sunat_note");
        Rpta[6] = Respuesta.get("sunat_responsecode");
        Rpta[7] = Respuesta.get("sunat_soap_error");
        Rpta[8] = Respuesta.get("pdf_zip_base64");
        Rpta[9] = Respuesta.get("xml_zip_base64");
        Rpta[10] = Respuesta.get("cdr_zip_base64");
        Rpta[11] = Respuesta.get("enlace_del_pdf");
        Rpta[12] = Respuesta.get("enlace_del_xml");
        Rpta[13] = Respuesta.get("enlace_del_cdr");
        return Rpta;
    }

    private JSONObject GENERARDOCUMENTO() {
        JSONObject objetoCabecera = new JSONObject(); // Instancear el  segundario
        objetoCabecera.put("operacion", "generar_comprobante");
        objetoCabecera.put("tipo_de_comprobante", "1");
        objetoCabecera.put("serie", "F001");
        objetoCabecera.put("numero", "1");
        objetoCabecera.put("sunat_transaction", "1");
        objetoCabecera.put("cliente_tipo_de_documento", "6");
        objetoCabecera.put("cliente_numero_de_documento", "20600695771");
        objetoCabecera.put("cliente_denominacion", "NUBEFACT SA");
        objetoCabecera.put("cliente_direccion", "CALLE LIBERTAD 116 MIRAFLORES - LIMA - PERU");
        objetoCabecera.put("cliente_email", "tucliente@gmail.com");
        objetoCabecera.put("cliente_email_1", "");
        objetoCabecera.put("cliente_email_2", "");
        objetoCabecera.put("fecha_de_emision", "12-05-2017");
        objetoCabecera.put("fecha_de_vencimiento", "");
        objetoCabecera.put("moneda", "1");
        objetoCabecera.put("tipo_de_cambio", "");
        objetoCabecera.put("porcentaje_de_igv", "18.00");
        objetoCabecera.put("descuento_global", "");
        objetoCabecera.put("total_descuento", "");
        objetoCabecera.put("total_anticipo", "");
        objetoCabecera.put("total_gravada", "600");
        objetoCabecera.put("total_inafecta", "");
        objetoCabecera.put("total_exonerada", "");
        objetoCabecera.put("total_igv", "108");
        objetoCabecera.put("total_gratuita", "");
        objetoCabecera.put("total_otros_cargos", "");
        objetoCabecera.put("total", "708");
        objetoCabecera.put("percepcion_tipo", "");
        objetoCabecera.put("percepcion_base_imponible", "");
        objetoCabecera.put("total_percepcion", "");
        objetoCabecera.put("total_incluido_percepcion", "");
        objetoCabecera.put("detraccion", "false");
        objetoCabecera.put("observaciones", "");
        objetoCabecera.put("documento_que_se_modifica_tipo", "");
        objetoCabecera.put("documento_que_se_modifica_serie", "");
        objetoCabecera.put("documento_que_se_modifica_numero", "");
        objetoCabecera.put("tipo_de_nota_de_credito", "");
        objetoCabecera.put("tipo_de_nota_de_debito", "");
        objetoCabecera.put("enviar_automaticamente_a_la_sunat", "true");
        objetoCabecera.put("enviar_automaticamente_al_cliente", "false");
        objetoCabecera.put("codigo_unico", "");
        objetoCabecera.put("condiciones_de_pago", "");
        objetoCabecera.put("medio_de_pago", "");
        objetoCabecera.put("placa_vehiculo", "");
        objetoCabecera.put("orden_compra_servicio", "");
        objetoCabecera.put("tabla_personalizada_codigo", "");
        objetoCabecera.put("formato_de_pdf", "");

        JSONArray lista = new JSONArray();
        JSONObject detalle_linea_1 = new JSONObject();

        detalle_linea_1.put("unidad_de_medida", "NIU");
        detalle_linea_1.put("codigo", "001");
        detalle_linea_1.put("descripcion", "DETALLE DEL PRODUCTO");
        detalle_linea_1.put("cantidad", "1");
        detalle_linea_1.put("valor_unitario", "500");
        detalle_linea_1.put("precio_unitario", "590");
        detalle_linea_1.put("descuento", "");
        detalle_linea_1.put("subtotal", "500");
        detalle_linea_1.put("tipo_de_igv", "1");
        detalle_linea_1.put("igv", "90");
        detalle_linea_1.put("total", "590");
        detalle_linea_1.put("anticipo_regularizacion", "false");
        detalle_linea_1.put("anticipo_serie", "");
        detalle_linea_1.put("anticipo_documento_numero", "");

        JSONObject detalle_linea_2 = new JSONObject();

        detalle_linea_2.put("unidad_de_medida", "ZZ");
        detalle_linea_2.put("codigo", "001");
        detalle_linea_2.put("descripcion", "DETALLE DEL SERVICIO");
        detalle_linea_2.put("cantidad", "5");
        detalle_linea_2.put("valor_unitario", "20");
        detalle_linea_2.put("precio_unitario", "23.60");
        detalle_linea_2.put("descuento", "");
        detalle_linea_2.put("subtotal", "100");
        detalle_linea_2.put("tipo_de_igv", "1");
        detalle_linea_2.put("igv", "18");
        detalle_linea_2.put("total", "118");
        detalle_linea_2.put("anticipo_regularizacion", "false");
        detalle_linea_2.put("anticipo_serie", "");
        detalle_linea_2.put("anticipo_documento_numero", "");

        lista.put(detalle_linea_1);
        lista.put(detalle_linea_2);

        objetoCabecera.put("items", lista);
        return objetoCabecera;
    }

    public void LEERCONSULTADOCUMENTO(JSONObject json) {
        System.out.println(json.get("tipo_de_comprobante"));
        System.out.println(json.get("serie"));
        System.out.println(json.get("numero"));
        System.out.println(json.get("enlace"));
        System.out.println(json.get("enlace_del_pdf"));
        System.out.println(json.get("enlace_del_xml"));
        System.out.println(json.get("enlace_del_cdr"));
        System.out.println(json.get("aceptada_por_sunat"));
        System.out.println(json.get("sunat_description"));
        System.out.println(json.get("sunat_note"));
        System.out.println(json.get("sunat_responsecode"));
        System.out.println(json.get("sunat_soap_error"));
        System.out.println(json.get("anulado"));
        System.out.println(json.get("cadena_para_codigo_qr"));
        System.out.println(json.get("codigo_hash"));
    }

    public void LEERGENERARDOCUMENTO(JSONObject json) {

        System.out.println(json.get("tipo_de_comprobante"));
        System.out.println(json.get("serie"));
        System.out.println(json.get("numero"));
        System.out.println(json.get("enlace"));
        System.out.println(json.get("aceptada_por_sunat"));
        System.out.println(json.get("sunat_description"));
        System.out.println(json.get("sunat_note"));
        System.out.println(json.get("sunat_responsecode"));
        System.out.println(json.get("sunat_soap_error"));
        System.out.println(json.get("pdf_zip_base64"));
        System.out.println(json.get("xml_zip_base64"));
        System.out.println(json.get("cdr_zip_base64"));
        System.out.println(json.get("cadena_para_codigo_qr"));
        System.out.println(json.get("codigo_hash"));

    }

    public void EXECUTE() {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(getRUTA());
            post.addHeader("Authorization", "Token token=" + getTOKEN());
            post.addHeader("Content-Type", "application/json");

            StringEntity params = new StringEntity(getCabecera().toString(), "UTF-8");
            post.setEntity(params);
            
            HttpResponse response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()))) {
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                JSONObject json_rspta = new JSONObject(result.toString());
                if (json_rspta.length() < 3) {
                    JOptionPane.showMessageDialog(null, "Error => " + json_rspta.get("errors"));
                    setEjecucionNormal(false);
                } else {
                    setEjecucionNormal(true);
                    setRespuesta(json_rspta);
                }
            }
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
    /*
    public void EXECUTE() {
        try {
            HttpClient cliente = new DefaultHttpClient();
            HttpPost post = new HttpPost(getRUTA());
            post.addHeader("Authorization", "Token token=" + getTOKEN()); // Cabecera del token
            post.addHeader("Content-Type", "application/json"); // Cabecera del Content-Type

            StringEntity parametros = new StringEntity(getCabecera().toString(), "UTF-8");
            post.setEntity(parametros);
            System.out.println(getCabecera().toString());
            HttpResponse response = cliente.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String linea = "";
            if ((linea = rd.readLine()) != null) {

                //JSONParser parsearRsptaJson = new JSONParser();
                JSONObject json_rspta = new JSONObject(linea);
                if (json_rspta.length() < 3) { //cuando existe un error retorna 2 parametros el JSON
                    JOptionPane.showMessageDialog(null, "Error => " + json_rspta.get("errors"));
                    setEjecucionNormal(false);
                } else {
                    //JSONParser parsearRsptaDetalleOK = new JSONParser();
                    //JSONObject json_rspta_ok = new JSONObject(json_rspta.get("invoice").toString());
                    //LEERCONSULTADOCUMENTO(json_rspta);
                    setEjecucionNormal(true);
                    setRespuesta(json_rspta);
                }

            }

        } catch (UnsupportedEncodingException ex1) {
            System.err.println("Error UnsupportedEncodingException: " + ex1.getMessage());
        } catch (IOException ex2) {
            System.err.println("Error IOException: " + ex2.getMessage());
        } catch (Exception ex3) {
            System.err.println("Exepction: " + ex3.getMessage());
        }
    }
    */
    

    /**
     * @return the RUTA
     */
    public String getRUTA() {
        return RUTA;
    }

    /**
     * @return the TOKEN
     */
    public String getTOKEN() {
        return TOKEN;
    }

    /**
     * @return the Operacion
     */
    public String getOperacion() {
        return Operacion;
    }

    /**
     * @param Operacion the Operacion to set
     */
    public void setOperacion(String Operacion) {
        this.Operacion = Operacion;
    }

    /**
     * @return the tipo_comprobante
     */
    public int getTipo_comprobante() {
        return tipo_comprobante;
    }

    /**
     * @param tipo_comprobante the tipo_comprobante to set
     */
    public void setTipo_comprobante(int tipo_comprobante) {
        this.tipo_comprobante = tipo_comprobante;
    }

    /**
     * @return the SeriePrefijo
     */
    public String getSeriePrefijo() {
        return SeriePrefijo;
    }

    /**
     * @param SeriePrefijo the SeriePrefijo to set
     */
    public void setSeriePrefijo(String SeriePrefijo) {
        this.SeriePrefijo = SeriePrefijo;
    }

    /**
     * @return the NumeroCorrelativo
     */
    public int getNumeroCorrelativo() {
        return NumeroCorrelativo;
    }

    /**
     * @param NumeroCorrelativo the NumeroCorrelativo to set
     */
    public void setNumeroCorrelativo(int NumeroCorrelativo) {
        this.NumeroCorrelativo = NumeroCorrelativo;
    }

    /**
     * @return the sunat_transaction
     */
    public int getSunat_transaction() {
        return sunat_transaction;
    }

    /**
     * @param sunat_transaction the sunat_transaction to set
     */
    public void setSunat_transaction(int sunat_transaction) {
        this.sunat_transaction = sunat_transaction;
    }

    /**
     * @return the Cliente_Tipo_Documento
     */
    public String getCliente_Tipo_Documento() {
        return Cliente_Tipo_Documento;
    }

    /**
     * @param Cliente_Tipo_Documento the Cliente_Tipo_Documento to set
     */
    public void setCliente_Tipo_Documento(String Cliente_Tipo_Documento) {
        this.Cliente_Tipo_Documento = Cliente_Tipo_Documento;
    }

    /**
     * @return the Cliente_Numero_Documento
     */
    public String getCliente_Numero_Documento() {
        return Cliente_Numero_Documento;
    }

    /**
     * @param Cliente_Numero_Documento the Cliente_Numero_Documento to set
     */
    public void setCliente_Numero_Documento(String Cliente_Numero_Documento) {
        this.Cliente_Numero_Documento = Cliente_Numero_Documento;
    }

    /**
     * @return the Cliente_Denominacion
     */
    public String getCliente_Denominacion() {
        return Cliente_Denominacion;
    }

    /**
     * @param Cliente_Denominacion the Cliente_Denominacion to set
     */
    public void setCliente_Denominacion(String Cliente_Denominacion) {
        this.Cliente_Denominacion = Cliente_Denominacion;
    }

    /**
     * @return the Cliente_Direccion
     */
    public String getCliente_Direccion() {
        return Cliente_Direccion;
    }

    /**
     * @param Cliente_Direccion the Cliente_Direccion to set
     */
    public void setCliente_Direccion(String Cliente_Direccion) {
        this.Cliente_Direccion = Cliente_Direccion;
    }

    /**
     * @return the Cliente_Email
     */
    public String getCliente_Email() {
        return Cliente_Email;
    }

    /**
     * @param Cliente_Email the Cliente_Email to set
     */
    public void setCliente_Email(String Cliente_Email) {
        this.Cliente_Email = Cliente_Email;
    }

    /**
     * @return the Cliente_Email1
     */
    public String getCliente_Email1() {
        return Cliente_Email1;
    }

    /**
     * @param Cliente_Email1 the Cliente_Email1 to set
     */
    public void setCliente_Email1(String Cliente_Email1) {
        this.Cliente_Email1 = Cliente_Email1;
    }

    /**
     * @return the Cliente_Email2
     */
    public String getCliente_Email2() {
        return Cliente_Email2;
    }

    /**
     * @param Cliente_Email2 the Cliente_Email2 to set
     */
    public void setCliente_Email2(String Cliente_Email2) {
        this.Cliente_Email2 = Cliente_Email2;
    }

    /**
     * @return the Fecha_Emision
     */
    public LocalDate getFecha_Emision() {
        return Fecha_Emision;
    }

    /**
     * @param Fecha_Emision the Fecha_Emision to set
     */
    public void setFecha_Emision(LocalDate Fecha_Emision) {
        this.Fecha_Emision = Fecha_Emision;
    }

    /**
     * @return the Fecha_Vencimiento
     */
    public LocalDate getFecha_Vencimiento() {
        return Fecha_Vencimiento;
    }

    /**
     * @param Fecha_Vencimiento the Fecha_Vencimiento to set
     */
    public void setFecha_Vencimiento(LocalDate Fecha_Vencimiento) {
        this.Fecha_Vencimiento = Fecha_Vencimiento;
    }

    /**
     * @return the Tipo_Modena
     */
    public int getTipo_Modena() {
        return Tipo_Modena;
    }

    /**
     * @param Tipo_Modena the Tipo_Modena to set
     */
    public void setTipo_Modena(int Tipo_Modena) {
        this.Tipo_Modena = Tipo_Modena;
    }

    /**
     * @return the Tipo_Cambio
     */
    public double getTipo_Cambio() {
        return Tipo_Cambio;
    }

    /**
     * @param Tipo_Cambio the Tipo_Cambio to set
     */
    public void setTipo_Cambio(double Tipo_Cambio) {
        this.Tipo_Cambio = Tipo_Cambio;
    }

    /**
     * @return the Porcentaje_Igv
     */
    public double getPorcentaje_Igv() {
        return Porcentaje_Igv;
    }

    /**
     * @param Porcentaje_Igv the Porcentaje_Igv to set
     */
    public void setPorcentaje_Igv(double Porcentaje_Igv) {
        this.Porcentaje_Igv = Porcentaje_Igv;
    }

    /**
     * @return the Descuento_Global
     */
    public double getDescuento_Global() {
        return Descuento_Global;
    }

    /**
     * @param Descuento_Global the Descuento_Global to set
     */
    public void setDescuento_Global(double Descuento_Global) {
        this.Descuento_Global = Descuento_Global;
    }

    /**
     * @return the Total_Descuento
     */
    public double getTotal_Descuento() {
        return Total_Descuento;
    }

    /**
     * @param Total_Descuento the Total_Descuento to set
     */
    public void setTotal_Descuento(double Total_Descuento) {
        this.Total_Descuento = Total_Descuento;
    }

    /**
     * @return the Total_Anticipo
     */
    public double getTotal_Anticipo() {
        return Total_Anticipo;
    }

    /**
     * @param Total_Anticipo the Total_Anticipo to set
     */
    public void setTotal_Anticipo(double Total_Anticipo) {
        this.Total_Anticipo = Total_Anticipo;
    }

    /**
     * @return the Total_Gravada
     */
    public double getTotal_Gravada() {
        return Total_Gravada;
    }

    /**
     * @param Total_Gravada the Total_Gravada to set
     */
    public void setTotal_Gravada(double Total_Gravada) {
        this.Total_Gravada = Total_Gravada;
    }

    /**
     * @return the Total_inafecta
     */
    public double getTotal_inafecta() {
        return Total_inafecta;
    }

    /**
     * @param Total_inafecta the Total_inafecta to set
     */
    public void setTotal_inafecta(double Total_inafecta) {
        this.Total_inafecta = Total_inafecta;
    }

    /**
     * @return the Total_exonerada
     */
    public double getTotal_exonerada() {
        return Total_exonerada;
    }

    /**
     * @param Total_exonerada the Total_exonerada to set
     */
    public void setTotal_exonerada(double Total_exonerada) {
        this.Total_exonerada = Total_exonerada;
    }

    /**
     * @return the Total_Igv
     */
    public double getTotal_Igv() {
        return Total_Igv;
    }

    /**
     * @param Total_Igv the Total_Igv to set
     */
    public void setTotal_Igv(double Total_Igv) {
        this.Total_Igv = Total_Igv;
    }

    /**
     * @return the Total_Gratuita
     */
    public double getTotal_Gratuita() {
        return Total_Gratuita;
    }

    /**
     * @param Total_Gratuita the Total_Gratuita to set
     */
    public void setTotal_Gratuita(double Total_Gratuita) {
        this.Total_Gratuita = Total_Gratuita;
    }

    /**
     * @return the Total_Otros_Cargos
     */
    public double getTotal_Otros_Cargos() {
        return Total_Otros_Cargos;
    }

    /**
     * @param Total_Otros_Cargos the Total_Otros_Cargos to set
     */
    public void setTotal_Otros_Cargos(double Total_Otros_Cargos) {
        this.Total_Otros_Cargos = Total_Otros_Cargos;
    }

    /**
     * @return the Total_Isc
     */
    public double getTotal_Isc() {
        return Total_Isc;
    }

    /**
     * @param Total_Isc the Total_Isc to set
     */
    public void setTotal_Isc(double Total_Isc) {
        this.Total_Isc = Total_Isc;
    }

    /**
     * @return the Total
     */
    public double getTotal() {
        return Total;
    }

    /**
     * @param Total the Total to set
     */
    public void setTotal(double Total) {
        this.Total = Total;
    }

    /**
     * @return the Tipo_Percepcion
     */
    public int getTipo_Percepcion() {
        return Tipo_Percepcion;
    }

    /**
     * @param Tipo_Percepcion the Tipo_Percepcion to set
     */
    public void setTipo_Percepcion(int Tipo_Percepcion) {
        this.Tipo_Percepcion = Tipo_Percepcion;
    }

    /**
     * @return the Percepcion_base_Imponible
     */
    public double getPercepcion_base_Imponible() {
        return Percepcion_base_Imponible;
    }

    /**
     * @param Percepcion_base_Imponible the Percepcion_base_Imponible to set
     */
    public void setPercepcion_base_Imponible(double Percepcion_base_Imponible) {
        this.Percepcion_base_Imponible = Percepcion_base_Imponible;
    }

    /**
     * @return the Total_Percepcion
     */
    public double getTotal_Percepcion() {
        return Total_Percepcion;
    }

    /**
     * @param Total_Percepcion the Total_Percepcion to set
     */
    public void setTotal_Percepcion(double Total_Percepcion) {
        this.Total_Percepcion = Total_Percepcion;
    }

    /**
     * @return the Total_Incluido_Percepcion
     */
    public double getTotal_Incluido_Percepcion() {
        return Total_Incluido_Percepcion;
    }

    /**
     * @param Total_Incluido_Percepcion the Total_Incluido_Percepcion to set
     */
    public void setTotal_Incluido_Percepcion(double Total_Incluido_Percepcion) {
        this.Total_Incluido_Percepcion = Total_Incluido_Percepcion;
    }

    /**
     * @return the Tipo_Retencion
     */
    public int getTipo_Retencion() {
        return Tipo_Retencion;
    }

    /**
     * @param Tipo_Retencion the Tipo_Retencion to set
     */
    public void setTipo_Retencion(int Tipo_Retencion) {
        this.Tipo_Retencion = Tipo_Retencion;
    }

    /**
     * @return the Retencion_Base_Imponible
     */
    public double getRetencion_Base_Imponible() {
        return Retencion_Base_Imponible;
    }

    /**
     * @param Retencion_Base_Imponible the Retencion_Base_Imponible to set
     */
    public void setRetencion_Base_Imponible(double Retencion_Base_Imponible) {
        this.Retencion_Base_Imponible = Retencion_Base_Imponible;
    }

    /**
     * @return the Total_Retencion
     */
    public double getTotal_Retencion() {
        return Total_Retencion;
    }

    /**
     * @param Total_Retencion the Total_Retencion to set
     */
    public void setTotal_Retencion(double Total_Retencion) {
        this.Total_Retencion = Total_Retencion;
    }

    /**
     * @return the Total_Impuestos_Bolsas
     */
    public double getTotal_Impuestos_Bolsas() {
        return Total_Impuestos_Bolsas;
    }

    /**
     * @param Total_Impuestos_Bolsas the Total_Impuestos_Bolsas to set
     */
    public void setTotal_Impuestos_Bolsas(double Total_Impuestos_Bolsas) {
        this.Total_Impuestos_Bolsas = Total_Impuestos_Bolsas;
    }

    /**
     * @return the Observaciones
     */
    public String getObservaciones() {
        return Observaciones;
    }

    /**
     * @param Observaciones the Observaciones to set
     */
    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }

    /**
     * @return the Tipo_Documento_Que_Se_Modifica
     */
    public int getTipo_Documento_Que_Se_Modifica() {
        return Tipo_Documento_Que_Se_Modifica;
    }

    /**
     * @param Tipo_Documento_Que_Se_Modifica the Tipo_Documento_Que_Se_Modifica
     * to set
     */
    public void setTipo_Documento_Que_Se_Modifica(int Tipo_Documento_Que_Se_Modifica) {
        this.Tipo_Documento_Que_Se_Modifica = Tipo_Documento_Que_Se_Modifica;
    }

    /**
     * @return the SeriePrefijo_Documento_Que_Se_Modifica
     */
    public String getSeriePrefijo_Documento_Que_Se_Modifica() {
        return SeriePrefijo_Documento_Que_Se_Modifica;
    }

    /**
     * @param SeriePrefijo_Documento_Que_Se_Modifica the
     * SeriePrefijo_Documento_Que_Se_Modifica to set
     */
    public void setSeriePrefijo_Documento_Que_Se_Modifica(String SeriePrefijo_Documento_Que_Se_Modifica) {
        this.SeriePrefijo_Documento_Que_Se_Modifica = SeriePrefijo_Documento_Que_Se_Modifica;
    }

    /**
     * @return the NumeroCorrelativo_Documento_Que_Se_Modifica
     */
    public int getNumeroCorrelativo_Documento_Que_Se_Modifica() {
        return NumeroCorrelativo_Documento_Que_Se_Modifica;
    }

    /**
     * @param NumeroCorrelativo_Documento_Que_Se_Modifica the
     * NumeroCorrelativo_Documento_Que_Se_Modifica to set
     */
    public void setNumeroCorrelativo_Documento_Que_Se_Modifica(int NumeroCorrelativo_Documento_Que_Se_Modifica) {
        this.NumeroCorrelativo_Documento_Que_Se_Modifica = NumeroCorrelativo_Documento_Que_Se_Modifica;
    }

    /**
     * @return the Tipo_Nota_Credito
     */
    public int getTipo_Nota_Credito() {
        return Tipo_Nota_Credito;
    }

    /**
     * @param Tipo_Nota_Credito the Tipo_Nota_Credito to set
     */
    public void setTipo_Nota_Credito(int Tipo_Nota_Credito) {
        this.Tipo_Nota_Credito = Tipo_Nota_Credito;
    }

    /**
     * @return the Tipo_Nota_Debito
     */
    public int getTipo_Nota_Debito() {
        return Tipo_Nota_Debito;
    }

    /**
     * @param Tipo_Nota_Debito the Tipo_Nota_Debito to set
     */
    public void setTipo_Nota_Debito(int Tipo_Nota_Debito) {
        this.Tipo_Nota_Debito = Tipo_Nota_Debito;
    }

    /**
     * @return the Enviar_Sunat
     */
    public boolean isEnviar_Sunat() {
        return Enviar_Sunat;
    }

    /**
     * @param Enviar_Sunat the Enviar_Sunat to set
     */
    public void setEnviar_Sunat(boolean Enviar_Sunat) {
        this.Enviar_Sunat = Enviar_Sunat;
    }

    /**
     * @return the Envia_cliente
     */
    public boolean isEnvia_cliente() {
        return Envia_cliente;
    }

    /**
     * @param Envia_cliente the Envia_cliente to set
     */
    public void setEnvia_cliente(boolean Envia_cliente) {
        this.Envia_cliente = Envia_cliente;
    }

    /**
     * @return the Codigo_Unico
     */
    public String getCodigo_Unico() {
        return Codigo_Unico;
    }

    /**
     * @param Codigo_Unico the Codigo_Unico to set
     */
    public void setCodigo_Unico(String Codigo_Unico) {
        this.Codigo_Unico = Codigo_Unico;
    }

    /**
     * @return the Condiciones_Pago
     */
    public String getCondiciones_Pago() {
        return Condiciones_Pago;
    }

    /**
     * @param Condiciones_Pago the Condiciones_Pago to set
     */
    public void setCondiciones_Pago(String Condiciones_Pago) {
        this.Condiciones_Pago = Condiciones_Pago;
    }

    /**
     * @return the Medio_Pago
     */
    public String getMedio_Pago() {
        return Medio_Pago;
    }

    /**
     * @param Medio_Pago the Medio_Pago to set
     */
    public void setMedio_Pago(String Medio_Pago) {
        this.Medio_Pago = Medio_Pago;
    }

    /**
     * @return the Placa_vehiculo
     */
    public String getPlaca_vehiculo() {
        return Placa_vehiculo;
    }

    /**
     * @param Placa_vehiculo the Placa_vehiculo to set
     */
    public void setPlaca_vehiculo(String Placa_vehiculo) {
        this.Placa_vehiculo = Placa_vehiculo;
    }

    /**
     * @return the Orden_Compra_Servicio
     */
    public String getOrden_Compra_Servicio() {
        return Orden_Compra_Servicio;
    }

    /**
     * @param Orden_Compra_Servicio the Orden_Compra_Servicio to set
     */
    public void setOrden_Compra_Servicio(String Orden_Compra_Servicio) {
        this.Orden_Compra_Servicio = Orden_Compra_Servicio;
    }

    /**
     * @return the Detraccion
     */
    public boolean isDetraccion() {
        return Detraccion;
    }

    /**
     * @param Detraccion the Detraccion to set
     */
    public void setDetraccion(boolean Detraccion) {
        this.Detraccion = Detraccion;
    }

    /**
     * @return the Detraccion_Tipo
     */
    public int getDetraccion_Tipo() {
        return Detraccion_Tipo;
    }

    /**
     * @param Detraccion_Tipo the Detraccion_Tipo to set
     */
    public void setDetraccion_Tipo(int Detraccion_Tipo) {
        this.Detraccion_Tipo = Detraccion_Tipo;
    }

    /**
     * @return the Detraccion_Total
     */
    public double getDetraccion_Total() {
        return Detraccion_Total;
    }

    /**
     * @param Detraccion_Total the Detraccion_Total to set
     */
    public void setDetraccion_Total(double Detraccion_Total) {
        this.Detraccion_Total = Detraccion_Total;
    }

    /**
     * @return the Detraccion_Porcentaje
     */
    public double getDetraccion_Porcentaje() {
        return Detraccion_Porcentaje;
    }

    /**
     * @param Detraccion_Porcentaje the Detraccion_Porcentaje to set
     */
    public void setDetraccion_Porcentaje(double Detraccion_Porcentaje) {
        this.Detraccion_Porcentaje = Detraccion_Porcentaje;
    }

    /**
     * @return the Medio_Pago_Detraccion
     */
    public int getMedio_Pago_Detraccion() {
        return Medio_Pago_Detraccion;
    }

    /**
     * @param Medio_Pago_Detraccion the Medio_Pago_Detraccion to set
     */
    public void setMedio_Pago_Detraccion(int Medio_Pago_Detraccion) {
        this.Medio_Pago_Detraccion = Medio_Pago_Detraccion;
    }

    /**
     * @return the Ubigeo_Origen
     */
    public int getUbigeo_Origen() {
        return Ubigeo_Origen;
    }

    /**
     * @param Ubigeo_Origen the Ubigeo_Origen to set
     */
    public void setUbigeo_Origen(int Ubigeo_Origen) {
        this.Ubigeo_Origen = Ubigeo_Origen;
    }

    /**
     * @return the Direccion_Origen
     */
    public String getDireccion_Origen() {
        return Direccion_Origen;
    }

    /**
     * @param Direccion_Origen the Direccion_Origen to set
     */
    public void setDireccion_Origen(String Direccion_Origen) {
        this.Direccion_Origen = Direccion_Origen;
    }

    /**
     * @return the Ubigeo_Destino
     */
    public int getUbigeo_Destino() {
        return Ubigeo_Destino;
    }

    /**
     * @param Ubigeo_Destino the Ubigeo_Destino to set
     */
    public void setUbigeo_Destino(int Ubigeo_Destino) {
        this.Ubigeo_Destino = Ubigeo_Destino;
    }

    /**
     * @return the Direccion_Destino
     */
    public String getDireccion_Destino() {
        return Direccion_Destino;
    }

    /**
     * @param Direccion_Destino the Direccion_Destino to set
     */
    public void setDireccion_Destino(String Direccion_Destino) {
        this.Direccion_Destino = Direccion_Destino;
    }

    /**
     * @return the Detalle_Viaje
     */
    public String getDetalle_Viaje() {
        return Detalle_Viaje;
    }

    /**
     * @param Detalle_Viaje the Detalle_Viaje to set
     */
    public void setDetalle_Viaje(String Detalle_Viaje) {
        this.Detalle_Viaje = Detalle_Viaje;
    }

    /**
     * @return the Val_Ref_Serv_trans
     */
    public double getVal_Ref_Serv_trans() {
        return Val_Ref_Serv_trans;
    }

    /**
     * @param Val_Ref_Serv_trans the Val_Ref_Serv_trans to set
     */
    public void setVal_Ref_Serv_trans(double Val_Ref_Serv_trans) {
        this.Val_Ref_Serv_trans = Val_Ref_Serv_trans;
    }

    /**
     * @return the Val_Ref_Carga_Efec
     */
    public double getVal_Ref_Carga_Efec() {
        return Val_Ref_Carga_Efec;
    }

    /**
     * @param Val_Ref_Carga_Efec the Val_Ref_Carga_Efec to set
     */
    public void setVal_Ref_Carga_Efec(double Val_Ref_Carga_Efec) {
        this.Val_Ref_Carga_Efec = Val_Ref_Carga_Efec;
    }

    /**
     * @return the Val_Ref_Carga_util
     */
    public double getVal_Ref_Carga_util() {
        return Val_Ref_Carga_util;
    }

    /**
     * @param Val_Ref_Carga_util the Val_Ref_Carga_util to set
     */
    public void setVal_Ref_Carga_util(double Val_Ref_Carga_util) {
        this.Val_Ref_Carga_util = Val_Ref_Carga_util;
    }

    /**
     * @return the Detalle_Tramo_Vehiculo
     */
    public String getDetalle_Tramo_Vehiculo() {
        return Detalle_Tramo_Vehiculo;
    }

    /**
     * @param Detalle_Tramo_Vehiculo the Detalle_Tramo_Vehiculo to set
     */
    public void setDetalle_Tramo_Vehiculo(String Detalle_Tramo_Vehiculo) {
        this.Detalle_Tramo_Vehiculo = Detalle_Tramo_Vehiculo;
    }

    /**
     * @return the Punto_Origen_Viaje
     */
    public int getPunto_Origen_Viaje() {
        return Punto_Origen_Viaje;
    }

    /**
     * @param Punto_Origen_Viaje the Punto_Origen_Viaje to set
     */
    public void setPunto_Origen_Viaje(int Punto_Origen_Viaje) {
        this.Punto_Origen_Viaje = Punto_Origen_Viaje;
    }

    /**
     * @return the Punto_Destino_Viaje
     */
    public int getPunto_Destino_Viaje() {
        return Punto_Destino_Viaje;
    }

    /**
     * @param Punto_Destino_Viaje the Punto_Destino_Viaje to set
     */
    public void setPunto_Destino_Viaje(int Punto_Destino_Viaje) {
        this.Punto_Destino_Viaje = Punto_Destino_Viaje;
    }

    /**
     * @return the Descripcion_Tramo
     */
    public String getDescripcion_Tramo() {
        return Descripcion_Tramo;
    }

    /**
     * @param Descripcion_Tramo the Descripcion_Tramo to set
     */
    public void setDescripcion_Tramo(String Descripcion_Tramo) {
        this.Descripcion_Tramo = Descripcion_Tramo;
    }

    /**
     * @return the val_ref_carga_efec_tramo_virtual
     */
    public double getVal_ref_carga_efec_tramo_virtual() {
        return val_ref_carga_efec_tramo_virtual;
    }

    /**
     * @param val_ref_carga_efec_tramo_virtual the
     * val_ref_carga_efec_tramo_virtual to set
     */
    public void setVal_ref_carga_efec_tramo_virtual(double val_ref_carga_efec_tramo_virtual) {
        this.val_ref_carga_efec_tramo_virtual = val_ref_carga_efec_tramo_virtual;
    }

    /**
     * @return the Configuracion_Vehicular
     */
    public String getConfiguracion_Vehicular() {
        return Configuracion_Vehicular;
    }

    /**
     * @param Configuracion_Vehicular the Configuracion_Vehicular to set
     */
    public void setConfiguracion_Vehicular(String Configuracion_Vehicular) {
        this.Configuracion_Vehicular = Configuracion_Vehicular;
    }

    /**
     * @return the carga_util_tonel_metricas
     */
    public double getCarga_util_tonel_metricas() {
        return carga_util_tonel_metricas;
    }

    /**
     * @param carga_util_tonel_metricas the carga_util_tonel_metricas to set
     */
    public void setCarga_util_tonel_metricas(double carga_util_tonel_metricas) {
        this.carga_util_tonel_metricas = carga_util_tonel_metricas;
    }

    /**
     * @return the carga_efec_tonel_metricas
     */
    public double getCarga_efec_tonel_metricas() {
        return carga_efec_tonel_metricas;
    }

    /**
     * @param carga_efec_tonel_metricas the carga_efec_tonel_metricas to set
     */
    public void setCarga_efec_tonel_metricas(double carga_efec_tonel_metricas) {
        this.carga_efec_tonel_metricas = carga_efec_tonel_metricas;
    }

    /**
     * @return the val_ref_tonel_metrica
     */
    public double getVal_ref_tonel_metrica() {
        return val_ref_tonel_metrica;
    }

    /**
     * @param val_ref_tonel_metrica the val_ref_tonel_metrica to set
     */
    public void setVal_ref_tonel_metrica(double val_ref_tonel_metrica) {
        this.val_ref_tonel_metrica = val_ref_tonel_metrica;
    }

    /**
     * @return the val_pre_ref_carga_util_nominal
     */
    public double getVal_pre_ref_carga_util_nominal() {
        return val_pre_ref_carga_util_nominal;
    }

    /**
     * @param val_pre_ref_carga_util_nominal the val_pre_ref_carga_util_nominal
     * to set
     */
    public void setVal_pre_ref_carga_util_nominal(double val_pre_ref_carga_util_nominal) {
        this.val_pre_ref_carga_util_nominal = val_pre_ref_carga_util_nominal;
    }

    /**
     * @return the indicador_aplicacion_retorno_vacio
     */
    public boolean isIndicador_aplicacion_retorno_vacio() {
        return indicador_aplicacion_retorno_vacio;
    }

    /**
     * @param indicador_aplicacion_retorno_vacio the
     * indicador_aplicacion_retorno_vacio to set
     */
    public void setIndicador_aplicacion_retorno_vacio(boolean indicador_aplicacion_retorno_vacio) {
        this.indicador_aplicacion_retorno_vacio = indicador_aplicacion_retorno_vacio;
    }

    /**
     * @return the matricula_emb_pesquera
     */
    public String getMatricula_emb_pesquera() {
        return matricula_emb_pesquera;
    }

    /**
     * @param matricula_emb_pesquera the matricula_emb_pesquera to set
     */
    public void setMatricula_emb_pesquera(String matricula_emb_pesquera) {
        this.matricula_emb_pesquera = matricula_emb_pesquera;
    }

    /**
     * @return the nombre_emb_pesquera
     */
    public String getNombre_emb_pesquera() {
        return nombre_emb_pesquera;
    }

    /**
     * @param nombre_emb_pesquera the nombre_emb_pesquera to set
     */
    public void setNombre_emb_pesquera(String nombre_emb_pesquera) {
        this.nombre_emb_pesquera = nombre_emb_pesquera;
    }

    /**
     * @return the descripcion_tipo_especie_vendida
     */
    public String getDescripcion_tipo_especie_vendida() {
        return descripcion_tipo_especie_vendida;
    }

    /**
     * @param descripcion_tipo_especie_vendida the
     * descripcion_tipo_especie_vendida to set
     */
    public void setDescripcion_tipo_especie_vendida(String descripcion_tipo_especie_vendida) {
        this.descripcion_tipo_especie_vendida = descripcion_tipo_especie_vendida;
    }

    /**
     * @return the lugar_de_descarga
     */
    public String getLugar_de_descarga() {
        return lugar_de_descarga;
    }

    /**
     * @param lugar_de_descarga the lugar_de_descarga to set
     */
    public void setLugar_de_descarga(String lugar_de_descarga) {
        this.lugar_de_descarga = lugar_de_descarga;
    }

    /**
     * @return the cantidad_especie_vendida
     */
    public double getCantidad_especie_vendida() {
        return cantidad_especie_vendida;
    }

    /**
     * @param cantidad_especie_vendida the cantidad_especie_vendida to set
     */
    public void setCantidad_especie_vendida(double cantidad_especie_vendida) {
        this.cantidad_especie_vendida = cantidad_especie_vendida;
    }

    /**
     * @return the fecha_de_descarga
     */
    public LocalDate getFecha_de_descarga() {
        return fecha_de_descarga;
    }

    /**
     * @param fecha_de_descarga the fecha_de_descarga to set
     */
    public void setFecha_de_descarga(LocalDate fecha_de_descarga) {
        this.fecha_de_descarga = fecha_de_descarga;
    }

    /**
     * @return the Formato_Pdf
     */
    public String getFormato_Pdf() {
        return Formato_Pdf;
    }

    /**
     * @param Formato_Pdf the Formato_Pdf to set
     */
    public void setFormato_Pdf(String Formato_Pdf) {
        this.Formato_Pdf = Formato_Pdf;
    }

    /**
     * @return the generado_por_contingencia
     */
    public boolean isGenerado_por_contingencia() {
        return generado_por_contingencia;
    }

    /**
     * @param generado_por_contingencia the generado_por_contingencia to set
     */
    public void setGenerado_por_contingencia(boolean generado_por_contingencia) {
        this.generado_por_contingencia = generado_por_contingencia;
    }

    /**
     * @return the bienes_region_selva
     */
    public boolean isBienes_region_selva() {
        return bienes_region_selva;
    }

    /**
     * @param bienes_region_selva the bienes_region_selva to set
     */
    public void setBienes_region_selva(boolean bienes_region_selva) {
        this.bienes_region_selva = bienes_region_selva;
    }

    /**
     * @return the servicios_region_selva
     */
    public boolean isServicios_region_selva() {
        return servicios_region_selva;
    }

    /**
     * @param servicios_region_selva the servicios_region_selva to set
     */
    public void setServicios_region_selva(boolean servicios_region_selva) {
        this.servicios_region_selva = servicios_region_selva;
    }

    /**
     * @return the nubecont_tipo_de_venta_codigo
     */
    public String getNubecont_tipo_de_venta_codigo() {
        return nubecont_tipo_de_venta_codigo;
    }

    /**
     * @param nubecont_tipo_de_venta_codigo the nubecont_tipo_de_venta_codigo to
     * set
     */
    public void setNubecont_tipo_de_venta_codigo(String nubecont_tipo_de_venta_codigo) {
        this.nubecont_tipo_de_venta_codigo = nubecont_tipo_de_venta_codigo;
    }

    /**
     * @return the items
     */
    public JSONArray getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(JSONArray items) {
        this.items = items;
    }

    /**
     * @return the guias
     */
    public JSONArray getGuias() {
        return guias;
    }

    /**
     * @param guias the guias to set
     */
    public void setGuias(JSONArray guias) {
        this.guias = guias;
    }

    /**
     * @return the venta_al_credito
     */
    public JSONArray getVenta_al_credito() {
        return venta_al_credito;
    }

    /**
     * @param venta_al_credito the venta_al_credito to set
     */
    public void setVenta_al_credito(JSONArray venta_al_credito) {
        this.venta_al_credito = venta_al_credito;
    }

    /**
     * @return the Tabla_Personalizada_Codigo
     */
    public String getTabla_Personalizada_Codigo() {
        return Tabla_Personalizada_Codigo;
    }

    /**
     * @param Tabla_Personalizada_Codigo the Tabla_Personalizada_Codigo to set
     */
    public void setTabla_Personalizada_Codigo(String Tabla_Personalizada_Codigo) {
        this.Tabla_Personalizada_Codigo = Tabla_Personalizada_Codigo;
    }

    public String getMotivo() {
        return Motivo;
    }

    public void setMotivo(String Motivo) {
        this.Motivo = Motivo;
    }

    /**
     * @return the Cabecera
     */
    public JSONObject getCabecera() {
        return Cabecera;
    }

    /**
     * @param Cabecera the Cabecera to set
     */
    public void setCabecera(JSONObject Cabecera) {
        this.Cabecera = Cabecera;
    }

    public JSONObject getRespuesta() {
        return Cabecera;
    }

    public void setRespuesta(JSONObject Respuesta) {
        this.Respuesta = Respuesta;
    }

    public boolean getEjecucionNormal() {
        return ejecucionNormal;
    }

    public void setEjecucionNormal(boolean ejecucion) {
        this.ejecucionNormal = ejecucion;
    }

    /**
     * @param RUTA the RUTA to set
     */
    public void setRUTA(String RUTA) {
        this.RUTA = RUTA;
    }

    /**
     * @param TOKEN the TOKEN to set
     */
    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }
}
