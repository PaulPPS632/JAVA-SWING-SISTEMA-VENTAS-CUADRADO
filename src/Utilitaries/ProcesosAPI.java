/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilitaries;

import java.time.LocalDate;

/**
 *
 * @author Paul
 */
public class ProcesosAPI {
    public ProcesosAPI(){
        
    }
    public static void GenerarComprobante(){
        ConsumeAPI nuevo = new ConsumeAPI();
        nuevo.setTipo_comprobante(2);
        nuevo.setSeriePrefijo("B001");
        nuevo.setNumeroCorrelativo(517);
        nuevo.setCliente_Tipo_Documento("1");
        nuevo.setCliente_Numero_Documento("75069452");
        nuevo.setCliente_Denominacion("PAUL YEFFERT PEREZ SANJINEZ");
        nuevo.setCliente_Direccion("Belisario Suarez");
        nuevo.setCliente_Email("");
        nuevo.setFecha_Emision(LocalDate.now());
        nuevo.setTipo_Modena(1);
        nuevo.setTotal_Gravada(200);
        nuevo.setTotal_Igv(36);
        nuevo.setTotal(236);
        nuevo.setObservaciones("ESTA ES UNA PRUEBA PARA LA INTEGRACION DE API NUBEFACT");
        nuevo.setMedio_Pago("");
        nuevo.InsertarItem("NIU", "PAD-001", "Producto ficticio Prueba 1", 1, 100, 118, 0, 100, 1, 18, 118, false);
        nuevo.InsertarItem("NIU", "PAD-002", "Producto ficticio Prueba 2", 1, 100, 118, 0, 100, 1, 18, 118, false);
        nuevo.Generar_Fact_Bole_Nota();
        nuevo.EXECUTE();
        Object[] rpta = nuevo.Respuesta_Fact_Bole_Nota();
        
        for(Object item : rpta){
            System.out.println(item.toString());
        }
        //12
    }
    public static void ConsultarComprobante(int tipo, String prefijo, int numero){
        /*
        PROCEDIMIENTO PARA USAR CLASE CONSUMEAPI
        1.- INICIALIZA
        2.- INGRESA LOS VALORES NECESARIOS CON SET
        3.- GENERA LA CABECERA JSON
        4.- EJECUTA-> significa enviar el json y recepcionar la respuesta
        5.- LEE LA RESPUESTA E INGRESALA EN UN OBJECT[]
        */
        ConsumeAPI nuevo = new ConsumeAPI();
        nuevo.setTipo_comprobante(tipo);
        nuevo.setSeriePrefijo(prefijo);
        nuevo.setNumeroCorrelativo(numero);
        nuevo.Consultas_Fact_Bole_Nota();
        nuevo.EXECUTE();
        Object[] rpta = nuevo.Respuesta_Fact_Bole_Nota();
        for(Object item : rpta){
            System.out.println(item.toString());
        }
    }
    public static boolean GenerarAnulacion(int TipoComprobante,String Prefijo,int Correlativo,String Motivo){
        ConsumeAPI nuevo = new ConsumeAPI();
        nuevo.setTipo_comprobante(TipoComprobante);
        nuevo.setSeriePrefijo(Prefijo);
        nuevo.setNumeroCorrelativo(Correlativo);
        nuevo.setMotivo(Motivo);
        nuevo.Generar_Anulacion();
        nuevo.EXECUTE();
        if(nuevo.getEjecucionNormal()){
            Object[] rpta = nuevo.Respuesta_Generar_Anulacion();
            for(Object item : rpta){
                System.out.println(item.toString());
            }
        }
        return nuevo.getEjecucionNormal();
    }
    public static void ConsultarAnulacion(int TipoComprobante,String Prefijo,int Correlativo){
        ConsumeAPI nuevo = new ConsumeAPI();
        nuevo.setTipo_comprobante(TipoComprobante);
        nuevo.setSeriePrefijo(Prefijo);
        nuevo.setNumeroCorrelativo(Correlativo);
        nuevo.Consultar_Anulacion();
        nuevo.EXECUTE();
        Object[] rpta = nuevo.Respuesta_Consultar_Anulacion();
        for(Object item : rpta){
            System.out.println(item.toString());
        }
    }
}
