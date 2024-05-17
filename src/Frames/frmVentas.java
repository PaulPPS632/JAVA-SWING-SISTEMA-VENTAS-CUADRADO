/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Frames;

import ConexionMysql.queries;
import Utilitaries.ConsumeAPI;
import Utilitaries.Funcionalidades;
import Utilitaries.MetodoTipos;
import Utilitaries.Producto;
import Utilitaries.Venta;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Paul
 */
public class frmVentas extends javax.swing.JInternalFrame {
    
    private static ArrayList<Producto> lista;
    private static int id_venta;
    private static int ID_Vendedor;
    private static int id_pseudonimo;
    private static String[] PrefijosComprobante;
    private static int ID_ClienteProveedor;
    private static int TipoDocumento;
    private static String DireccionCliente;
    private static String EmailCliente;
    private static LocalDate fecha = LocalDate.now();
    public frmVentas(Venta vnt) {
        initComponents();
        PreCarga();
        id_venta = vnt.getId_venta();
        fecha = vnt.getFecha();
        cmbTipoCondicion.setSelectedIndex(vnt.getId_tipo_condicion() - 1);
        cmbTipoPago.setSelectedIndex(vnt.getId_tipo_pago() - 1);
        cmbTipoComprobante.setSelectedIndex(vnt.getId_tipo_comprobante() - 1);
        cmbEstadoVenta.setSelectedIndex(vnt.getEstadoVenta() - 1);
        setID_ClienteProveedor(vnt.getId_clienteproveedor());
        setId_pseudonimo(vnt.getId_pseudonimo());
        txtCorrelativo.setText(vnt.getCorrelativo() + "");
        txaNota.setText(vnt.getNota());
        setLista(vnt.getLista());
        setID_ClienteProveedor(vnt.getId_clienteproveedor());
        String[] DatosCliente = queries.querieReturnDatosFila("select * from clientesyproveedor where id_clienteproveedor = ?", ID_ClienteProveedor);
        txtIdentidadClienteProveedor.setText(DatosCliente[2]);
        txtNombreCLienteProveedor.setText(DatosCliente[3]);
        Render();
    }

    public frmVentas() {
        initComponents();
        PreCarga();
        Limpiar();
    }

    public static void setEmailCliente(String emailcliente) {
        EmailCliente = emailcliente;
    }

    public static String getEmailCliente() {
        return EmailCliente;
    }

    public static void setDireccionCliente(String direccioncliente) {
        DireccionCliente = direccioncliente;
    }

    public static String getDireccionCliente() {
        return DireccionCliente;
    }

    public static void setTipoDocumento(int tipodocumento) {
        TipoDocumento = tipodocumento;
    }

    public static int getTipoDocumento() {
        return TipoDocumento;
    }

    public void setlblVendedor(String Vendedor) {
        lblVendedor.setText(Vendedor);

    }

    public void setID_vendedor(int ID) {
        this.ID_Vendedor = ID;
    }

    public int getID_vendedor() {
        return ID_Vendedor;
    }

    public String getlblVendedor() {
        return lblVendedor.getText();
    }

    public static void setID_ClienteProveedor(int ID) {
        ID_ClienteProveedor = ID;
    }

    public static void settxtIdentidadClienteProveedor(String Identidad) {
        txtIdentidadClienteProveedor.setText(Identidad);
    }

    public static void settxtNombreClienteProveedor(String Nombre) {
        txtNombreCLienteProveedor.setText(Nombre);
    }

    private void Limpiar() {
        cmbAlmacen.setSelectedIndex(0);
        cmbTipoCondicion.setSelectedIndex(0);
        cmbTipoPago.setSelectedIndex(0);
        cmbTipoMoneda.setSelectedIndex(0);
        txtTipoCambio.setEditable(false);
        txaFormaPago.setText(null);
        txaFormaPago.setEditable(false);
        cmbEstadoVenta.setSelectedIndex(0);
        lista.clear();
        txtGravada.setText(null);
        txtImpuestoTotal.setText(null);
        txtImporteTotal.setText(null);
        txaNota.setText(null);
        cmbTipoComprobante.setSelectedIndex(0);
        txtCorrelativo.setText("1");
        txtBuscarClienteProveedor.setText(null);
        txtIdentidadClienteProveedor.setText(null);
        txtNombreCLienteProveedor.setText(null);
        cmbTipoGeneracion.setSelectedIndex(0);
        lista.clear();
        Render();
    }

    private void InsertarConSN(String SN) {
        int id = Producto.IDProductoPorSerie(SN);
        int contador = 0;
        boolean encontrado = false;
        if (id != 0) {
            for (Producto item : getLista()) {
                if (id == item.getId_producto()) {
                    encontrado = true;
                    break;
                }
                contador++;
            }
            if (encontrado) {
                getLista().get(contador).InsertarSN(SN);
            } else {
                if (Producto.EnSTOCK(SN)) {
                    getLista().add(new Producto(SN));
                } else {
                    JOptionPane.showMessageDialog(null, "Productos esta fuera de STOCK");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Productos no INGRESADO");
        }
        Render();
    }

    private static void CambiarPrefijo() {
        int index = cmbTipoComprobante.getSelectedIndex();
        if (index == -1) {
            index = 0;
        }
        int idrazon = cmbRazonSocial.getSelectedIndex();
        if (idrazon == -1) {
            idrazon = 0;
        }
        txtPrefijo.setText(getPrefijosComprobante()[index]);
        switch (index) {
            case 0:
                setTipoDocumento(1);
                break;
            case 1:
                setTipoDocumento(6);
                break;
        }
        String correlativo = queries.querieReturnCell("select numero from correlativos where id_tipo_comprobante = ? and id_razonsocial= ?", "numero", new Object[]{index + 1, idrazon + 1});
        txtCorrelativo.setText(correlativo);
    }

    private void PreCarga() {
        String[] Almacenes = MetodoTipos.BuscarAlmacenes("");
        String[] NombreComprobantes = MetodoTipos.NombresComprobantes();
        setPrefijosComprobante(MetodoTipos.PrefijosComprobantes());
        String[] TipoCondicion = MetodoTipos.BuscarCondicion("");
        String[] TipoPago = MetodoTipos.NombrePagos();
        String[] EstadoVentas = MetodoTipos.NombresEstadoVenta();
        cmbAlmacen.removeAllItems();
        cmbTipoComprobante.removeAllItems();
        cmbEstadoVenta.removeAllItems();
        cmbTipoCondicion.removeAllItems();
        cmbTipoPago.removeAllItems();
        for (String item : Almacenes) {
            cmbAlmacen.addItem(item);
        }
        for (String item : NombreComprobantes) {
            cmbTipoComprobante.addItem(item);
        }
        for (String item : EstadoVentas) {
            cmbEstadoVenta.addItem(item);
        }
        for (String item : TipoCondicion) {
            cmbTipoCondicion.addItem(item);
        }
        for (String item : TipoPago) {
            cmbTipoPago.addItem(item);
        }

        setLista(new ArrayList<Producto>());
    }

    private static void AgregarFilaVacia(DefaultTableModel model) {

        Object[] emptyRow = new Object[model.getColumnCount()];
        model.addRow(emptyRow);
    }

    public static void InsertarProducto(int ID, int index) {
        if (getLista().size() > index) {
            getLista().set(index, new Producto(ID));
        } else {
            getLista().add(new Producto(ID));
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            AgregarFilaVacia(model);
        }
        Render();
    }

    public static void Render() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        model.setRowCount(getLista().size() + 1);
        double PrecioTotal = 0;
        double impuesTotal = 0;
        double PrecioBrutoTotal = 0;
        int cont = 0;
        for (Producto item : getLista()) {
            model.setValueAt(item.getDescripcion(), cont, 0);
            model.setValueAt(item.getCantidadVenta(), cont, 1);
            model.setValueAt(item.getPrecio(), cont, 2);
            model.setValueAt(item.getPrecioBruto(), cont, 3);
            model.setValueAt(item.getImpuesto(), cont, 4);
            model.setValueAt(item.getPrecioNeto(), cont, 5);
            model.setValueAt(item.getPrecioNeto(), cont, 6);
            PrecioBrutoTotal += item.getPrecioBruto();
            PrecioTotal += item.getPrecioNeto();
            impuesTotal += item.getImpuesto();
            cont++;
        }
        jTable1.setModel(model);
        txtGravada.setText(PrecioBrutoTotal + "");
        txtImpuestoTotal.setText(impuesTotal + "");
        txtImporteTotal.setText(PrecioTotal + "");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbTipoComprobante = new javax.swing.JComboBox<>();
        txtPrefijo = new javax.swing.JTextField();
        txtCorrelativo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtBuscarClienteProveedor = new javax.swing.JTextField();
        txtNombreCLienteProveedor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtIdentidadClienteProveedor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cmbAlmacen = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cmbTipoCondicion = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cmbTipoPago = new javax.swing.JComboBox<>();
        lblVendedor = new javax.swing.JLabel();
        cmbTipoMoneda = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtTipoCambio = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        JScrollPanelFormaPago = new javax.swing.JScrollPane();
        txaFormaPago = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        cmbEstadoVenta = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txtBuscarSerie = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtImporteTotal = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtImpuestoTotal = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtGravada = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaNota = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        cmbTipoGeneracion = new javax.swing.JComboBox<>();
        cmbRazonSocial = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(220, 220, 220));
        setClosable(true);
        setPreferredSize(new java.awt.Dimension(860, 720));

        jPanel2.setBackground(new java.awt.Color(33, 97, 140));
        jPanel2.setForeground(new java.awt.Color(0, 0, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("GENERADOR VENTAS/SALIDAS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 316, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 315, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 6, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 7, Short.MAX_VALUE)))
        );

        jPanel1.setBackground(new java.awt.Color(220, 220, 220));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Documento:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 11), new java.awt.Color(33, 97, 140))); // NOI18N
        jPanel1.setMaximumSize(new java.awt.Dimension(442, 160));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("RUC o DNI u otro:");

        cmbTipoComprobante.setBackground(new java.awt.Color(240, 240, 240));
        cmbTipoComprobante.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cmbTipoComprobante.setForeground(new java.awt.Color(0, 0, 0));
        cmbTipoComprobante.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FACTURA", "BOLETA" }));
        cmbTipoComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoComprobanteActionPerformed(evt);
            }
        });

        txtPrefijo.setBackground(new java.awt.Color(240, 240, 240));
        txtPrefijo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtPrefijo.setForeground(new java.awt.Color(0, 0, 0));
        txtPrefijo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrefijo.setText("0000");
        txtPrefijo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtCorrelativo.setBackground(new java.awt.Color(240, 240, 240));
        txtCorrelativo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtCorrelativo.setForeground(new java.awt.Color(0, 0, 0));
        txtCorrelativo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCorrelativo.setText("1");

        jLabel2.setBackground(new java.awt.Color(220, 220, 220));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("-");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("DOCUMENTO:");

        txtBuscarClienteProveedor.setBackground(new java.awt.Color(240, 240, 240));
        txtBuscarClienteProveedor.setForeground(new java.awt.Color(0, 0, 0));
        txtBuscarClienteProveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtBuscarClienteProveedor.setMaximumSize(new java.awt.Dimension(24, 301));
        txtBuscarClienteProveedor.setPreferredSize(new java.awt.Dimension(24, 301));
        txtBuscarClienteProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarClienteProveedorKeyPressed(evt);
            }
        });

        txtNombreCLienteProveedor.setEditable(false);
        txtNombreCLienteProveedor.setBackground(new java.awt.Color(240, 240, 240));
        txtNombreCLienteProveedor.setForeground(new java.awt.Color(0, 0, 0));
        txtNombreCLienteProveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNombreCLienteProveedor.setMaximumSize(new java.awt.Dimension(24, 301));
        txtNombreCLienteProveedor.setName(""); // NOI18N
        txtNombreCLienteProveedor.setPreferredSize(new java.awt.Dimension(24, 301));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("CLIENTE:");

        txtIdentidadClienteProveedor.setEditable(false);
        txtIdentidadClienteProveedor.setBackground(new java.awt.Color(240, 240, 240));
        txtIdentidadClienteProveedor.setForeground(new java.awt.Color(0, 0, 0));
        txtIdentidadClienteProveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtIdentidadClienteProveedor.setMaximumSize(new java.awt.Dimension(24, 301));
        txtIdentidadClienteProveedor.setPreferredSize(new java.awt.Dimension(24, 301));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Doc.Ident:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIdentidadClienteProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrefijo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel2)
                        .addGap(3, 3, 3)
                        .addComponent(txtCorrelativo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtBuscarClienteProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNombreCLienteProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtCorrelativo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtPrefijo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscarClienteProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreCLienteProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdentidadClienteProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(220, 220, 220));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Internos:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 11), new java.awt.Color(33, 97, 140))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(0, 0, 0));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("VENDEDOR:");

        cmbAlmacen.setBackground(new java.awt.Color(240, 240, 240));
        cmbAlmacen.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cmbAlmacen.setForeground(new java.awt.Color(0, 0, 0));
        cmbAlmacen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FACTURA", "BOLETA" }));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("ALMACEN:");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("CONDICION:");

        cmbTipoCondicion.setBackground(new java.awt.Color(240, 240, 240));
        cmbTipoCondicion.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cmbTipoCondicion.setForeground(new java.awt.Color(0, 0, 0));
        cmbTipoCondicion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FACTURA", "BOLETA" }));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("TIPO DE PAGO:");

        cmbTipoPago.setBackground(new java.awt.Color(240, 240, 240));
        cmbTipoPago.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cmbTipoPago.setForeground(new java.awt.Color(0, 0, 0));
        cmbTipoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FACTURA", "BOLETA" }));

        lblVendedor.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblVendedor.setForeground(new java.awt.Color(0, 0, 0));
        lblVendedor.setText("NULL");

        cmbTipoMoneda.setBackground(new java.awt.Color(240, 240, 240));
        cmbTipoMoneda.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cmbTipoMoneda.setForeground(new java.awt.Color(0, 0, 0));
        cmbTipoMoneda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SOLES", "DOLARES", "EUROS", "LIBRA ESTERLINA" }));
        cmbTipoMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoMonedaActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("TIPO MONEDA:");

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("TIPO CAMBIO:");

        txtTipoCambio.setEditable(false);
        txtTipoCambio.setBackground(new java.awt.Color(240, 240, 240));
        txtTipoCambio.setForeground(new java.awt.Color(0, 0, 0));
        txtTipoCambio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Forma de Pago:");

        txaFormaPago.setEditable(false);
        txaFormaPago.setColumns(20);
        txaFormaPago.setRows(5);
        JScrollPanelFormaPago.setViewportView(txaFormaPago);

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Estado Venta:");

        cmbEstadoVenta.setBackground(new java.awt.Color(240, 240, 240));
        cmbEstadoVenta.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cmbEstadoVenta.setForeground(new java.awt.Color(0, 0, 0));
        cmbEstadoVenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8))
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbAlmacen, 0, 169, Short.MAX_VALUE)
                    .addComponent(lblVendedor)
                    .addComponent(cmbTipoCondicion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel17)
                        .addComponent(jLabel16)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cmbTipoPago, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbTipoMoneda, 0, 169, Short.MAX_VALUE))
                    .addComponent(txtTipoCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JScrollPanelFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(cmbEstadoVenta, 0, 131, Short.MAX_VALUE))
                .addGap(176, 176, 176))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(cmbTipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbTipoMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel16))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblVendedor)
                                .addComponent(jLabel7))))
                    .addComponent(JScrollPanelFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(txtTipoCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19)
                        .addComponent(cmbEstadoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbTipoCondicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(225, 225, 225));

        jTable1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Descripcion", "Cant", "Precio und", "Precio bruto", "Impuesto", "Precio Neto", "Total"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setAutoscrolls(false);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTable1KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(420);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(65);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(65);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(65);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(65);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(70);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 817, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 0, 0));
        jLabel11.setText("Busqueda x Codigo de Barras (S/N):");

        txtBuscarSerie.setBackground(new java.awt.Color(240, 240, 240));
        txtBuscarSerie.setForeground(new java.awt.Color(0, 0, 0));
        txtBuscarSerie.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtBuscarSerie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarSerieKeyPressed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(220, 220, 220));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/VBus.png"))); // NOI18N

        jPanel5.setBackground(new java.awt.Color(33, 97, 140));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("IMPORTE TOTAL");

        txtImporteTotal.setEditable(false);
        txtImporteTotal.setBackground(new java.awt.Color(240, 240, 240));
        txtImporteTotal.setForeground(new java.awt.Color(0, 0, 0));
        txtImporteTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel12))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtImporteTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtImporteTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(33, 97, 140));

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("I.G.V. (18.00%)");

        txtImpuestoTotal.setEditable(false);
        txtImpuestoTotal.setBackground(new java.awt.Color(240, 240, 240));
        txtImpuestoTotal.setForeground(new java.awt.Color(0, 0, 0));
        txtImpuestoTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtImpuestoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel13)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtImpuestoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(33, 97, 140));

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("GRAVADA");

        txtGravada.setEditable(false);
        txtGravada.setBackground(new java.awt.Color(240, 240, 240));
        txtGravada.setForeground(new java.awt.Color(0, 0, 0));
        txtGravada.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtGravada, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(32, 32, 32))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGravada, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton2.setBackground(new java.awt.Color(210, 210, 210));
        jButton2.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/comp1.png"))); // NOI18N
        jButton2.setText("Borra Item");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(210, 210, 210));
        jButton3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 0, 0));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/add.png"))); // NOI18N
        jButton3.setText("GENERAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txaNota.setBackground(new java.awt.Color(210, 210, 210));
        txaNota.setColumns(20);
        txaNota.setRows(5);
        jScrollPane2.setViewportView(txaNota);

        jLabel15.setBackground(new java.awt.Color(220, 220, 220));
        jLabel15.setForeground(new java.awt.Color(33, 97, 140));
        jLabel15.setText("Notas / Observaciones:");

        cmbTipoGeneracion.setBackground(new java.awt.Color(240, 240, 240));
        cmbTipoGeneracion.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cmbTipoGeneracion.setForeground(new java.awt.Color(0, 0, 0));
        cmbTipoGeneracion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "REGISTRAR", "REG-FACT", " " }));
        cmbTipoGeneracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoGeneracionActionPerformed(evt);
            }
        });

        cmbRazonSocial.setBackground(new java.awt.Color(240, 240, 240));
        cmbRazonSocial.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cmbRazonSocial.setForeground(new java.awt.Color(0, 0, 0));
        cmbRazonSocial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CUADRADO", "SUPER" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(259, 274, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbTipoGeneracion, 0, 171, Short.MAX_VALUE)
                                    .addComponent(cmbRazonSocial, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3))
                            .addComponent(jScrollPane2)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 826, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtBuscarSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton1))
                                .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txtBuscarSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbTipoGeneracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton3)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        //insertarproducto();

    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        int row = jTable1.getSelectedRow();
        int column = jTable1.getSelectedColumn();
        int cantidad = 0;
        double nuevoPrecio = 0;
        if (column != 0 && jTable1.getValueAt(row, column) != null) {
            cantidad = Integer.parseInt(jTable1.getValueAt(row, 1).toString());
            nuevoPrecio = Double.parseDouble(jTable1.getValueAt(row, 2).toString());
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && column == 0 && jTable1.getValueAt(row, column) != null) {
            BuscarProducto nuevo = new BuscarProducto();
            String buscado = jTable1.getValueAt(row, column).toString();
            nuevo.setBuscado(buscado);
            nuevo.setTxtBuscado(buscado);
            nuevo.setIndexInsertar(row);//se envia la fila ah modificar
            nuevo.setFrmAIndexar(1);
            Funcionalidades.CentrarVentana(frmPrincipal.tbn_escritorio, nuevo);
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER && column == 1 && jTable1.getValueAt(row, 1) != null) {

            //RecarculoPrecios(Integer.parseInt(jTable1.getValueAt(row, 1).toString()),Double.parseDouble(jTable1.getValueAt(row, 2).toString()), row);
            //RecarculoPrecios((int) ,(double) , row);
            getLista().get(row).setCantidadVenta(cantidad);
            getLista().get(row).setPrecio(nuevoPrecio);
            Render();

            InsertarSN nuevo = new InsertarSN();
            nuevo.setId_producto(getLista().get(row).getId_producto());
            nuevo.setId_estado_producto(1);
            nuevo.setIndex_lista_insertar(row);
            nuevo.setCantidadSeleccionados(cantidad);
            nuevo.setLista(lista);
            nuevo.setListaSeriesSeleccionadas(lista.get(row).getSeries());
            nuevo.CargarSN();
            int cantStock = nuevo.getCantidadStock();
            if (cantStock < cantidad){
                JOptionPane.showMessageDialog(null, "STOCK INSUFICIENTE");
                int valor = JOptionPane.showConfirmDialog(
                        null,
                        "DESEA REVISAR LAS SERIES INGRESADAS",
                        "ADVERTENCIA",
                        JOptionPane.YES_NO_OPTION
                    );
                if(valor == JOptionPane.YES_OPTION){
                    Funcionalidades.CentrarVentana(frmPrincipal.tbn_escritorio, nuevo);
                }
            } else {
                Funcionalidades.CentrarVentana(frmPrincipal.tbn_escritorio, nuevo);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER && column == 2 && jTable1.getValueAt(row, 2) != null) {
            //RecarculoPrecios(Integer.parseInt(jTable1.getValueAt(row, 1).toString()),Double.parseDouble(jTable1.getValueAt(row, 2).toString()), row);
            getLista().get(row).setCantidadVenta(cantidad);
            getLista().get(row).setPrecio(nuevoPrecio);
            Render();
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_jTable1KeyTyped

    private void txtBuscarSerieKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarSerieKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println(txtBuscarSerie.getText());
            InsertarConSN(txtBuscarSerie.getText());
            txtBuscarSerie.setText(null);
            txtBuscarSerie.requestFocus();
        }
    }//GEN-LAST:event_txtBuscarSerieKeyPressed

    private void cmbTipoComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoComprobanteActionPerformed
        // TODO add your handling code here:
        CambiarPrefijo();
    }//GEN-LAST:event_cmbTipoComprobanteActionPerformed

    private void txtBuscarClienteProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarClienteProveedorKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            
            /*
            BuscarClienteProveedor nuevo = new BuscarClienteProveedor();
            nuevo.settxtBuscar(txtBuscarClienteProveedor.getText());
            nuevo.setFrmAIndexar(1);
            Funcionalidades.CentrarVentana(frmPrincipal.tbn_escritorio, nuevo);
            */
            frmEscogePseudonimo nueva = new frmEscogePseudonimo();
            Funcionalidades.CentrarVentana(frmPrincipal.tbn_escritorio, nueva);
            //InsertarConSN(txtBuscarSerie.getText());
        }
    }//GEN-LAST:event_txtBuscarClienteProveedorKeyPressed
    /*
    private static void GenerarFacturacionAPI(){
        ConsumeAPI generar = new ConsumeAPI();
        generar.setTipo_comprobante(cmbTipoComprobante.getSelectedIndex() + 1);
        generar.setSeriePrefijo(txtPrefijo.getText());
        generar.setNumeroCorrelativo(Integer.parseInt(txtCorrelativo.getText()));
        generar.setCliente_Tipo_Documento("" + TipoDocumento);
        generar.setCliente_Numero_Documento(txtIdentidadClienteProveedor.getText());
        generar.setCliente_Denominacion(txtNombreCLienteProveedor.getText());
        generar.setCliente_Direccion(DireccionCliente);
        generar.setCliente_Email(EmailCliente);
        generar.setFecha_Emision(LocalDate.now());
        generar.setTipo_Modena(cmbTipoMoneda.getSelectedIndex() + 1);
        if(cmbTipoMoneda.getSelectedIndex() != 0) generar.setTipo_Cambio(Double.parseDouble(txtTipoCambio.getText()));
        
        generar.setTotal_Gravada(Double.parseDouble(txtGravada.getText()));
        generar.setTotal_Igv(Double.parseDouble(txtImpuestoTotal.getText()));
        generar.setTotal(Double.parseDouble(txtImporteTotal.getText()));
        
        generar.setObservaciones(txaNota.getText());
        generar.setMedio_Pago(cmbTipoPago.getSelectedItem().toString());
        
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
            //RegistrarDATABASE(false, txaFormaPago.getText(), rpta[14].toString());
            
            Funcionalidades.goToURL(rpta[14].toString());
        }
    }*/
    private static void RegistrarDATABASE(boolean marcafecha, String FormaPago, String PDF, boolean API) {
        Venta registro = new Venta();
        registro.setId_venta(id_venta);
        registro.setFecha(fecha);
        registro.setId_tipo_comprobante(cmbTipoComprobante.getSelectedIndex() + 1);
        registro.setCorrelativo(Integer.parseInt(txtCorrelativo.getText()));
        registro.setId_clienteproveedor(ID_ClienteProveedor);
        registro.setId_usuario(ID_Vendedor);
        registro.setId_tipo_condicion(cmbTipoCondicion.getSelectedIndex() + 1);
        registro.setId_tipo_pago(cmbTipoPago.getSelectedIndex() + 1);
        registro.setId_tipo_moneda(cmbTipoMoneda.getSelectedIndex() + 1);
        if (cmbTipoMoneda.getSelectedIndex() != 0) {
            registro.setTipo_cambio(Double.parseDouble(txtTipoCambio.getText()));
        }
        registro.setNota(txaNota.getText());
        registro.setGravada(Double.parseDouble(txtGravada.getText()));
        registro.setImpuesto(Double.parseDouble(txtImpuestoTotal.getText()));
        registro.setTotal(Double.parseDouble(txtImporteTotal.getText()));
        registro.setEstadoVenta(cmbEstadoVenta.getSelectedIndex() + 1);
        if (marcafecha) {
            registro.setFechaPago(null);
        } else {
            registro.setFechaPago(LocalDate.now());
        }
        registro.setFormaPago(FormaPago);
        registro.setUrl_pdf(PDF);
        registro.setLista(getLista());
        registro.setId_pseudonimo(id_pseudonimo);
        if(API){
            registro.setDeuda_independiente(0);
        }else{
            registro.setDeuda_independiente(registro.getTotal());
        }
        registro.Registrar();
        
        if (API) {
            registro.ingresar_deuda_seudonimo(0);
            registro.GenerarFacturacionAPI(FormaPago, cmbRazonSocial.getSelectedIndex() + 1 );

        }else{
            registro.ingresar_deuda_seudonimo(registro.getTotal());
        }
    }

    private void EJECUTAR() {
        int selec = cmbTipoComprobante.getSelectedIndex();
        boolean flag1 = (txtIdentidadClienteProveedor.getText().length() == 11 && selec == 0);
        boolean flag2 = (txtIdentidadClienteProveedor.getText().length() == 8 && selec == 1);
        if (flag1 || flag2) {
            int tipogeneracion = cmbTipoGeneracion.getSelectedIndex();
            if (tipogeneracion == 0) {
                RegistrarDATABASE(true, ".", "", false);
                JOptionPane.showMessageDialog(null, "REGISTRO EXITOSO");
                Limpiar();
            } else if (tipogeneracion == 1) {
                RegistrarDATABASE(true, ".", "", true);
                //GenerarFacturacionAPI();
                Limpiar();
            }
        } else {
            JOptionPane.showMessageDialog(null, "IDENTIDAD DEL CLIENTE INCOMPATIBLE CON EL TIPO DE DOCUMENTO");
        }
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        carga nuevo = new carga();
        Thread ejecucion = new Thread() {
            @Override
            public void run() {
                EJECUTAR();
                nuevo.dispose();
            }
        };
        Thread pantallacarga = new Thread() {
            @Override
            public void run() {
                Funcionalidades.CentrarVentana(frmPrincipal.tbn_escritorio, nuevo);
            }
        };

        pantallacarga.start();
        ejecucion.start();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void cmbTipoMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoMonedaActionPerformed
        // TODO add your handling code here:
        if (cmbTipoMoneda.getSelectedIndex() + 1 >= 2) {
            txtTipoCambio.setEditable(true);
        } else {
            txtTipoCambio.setEditable(false);
        }
    }//GEN-LAST:event_cmbTipoMonedaActionPerformed

    private void cmbTipoGeneracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoGeneracionActionPerformed
        // TODO add your handling code here:
        int tipogeneracion = cmbTipoGeneracion.getSelectedIndex();
        if (tipogeneracion == 0) {
            txaFormaPago.setEditable(false);
            cmbEstadoVenta.setSelectedIndex(0);
        } else {
            txaFormaPago.setEditable(true);
            cmbEstadoVenta.setSelectedIndex(1);
            CambiarPrefijo();
        }
    }//GEN-LAST:event_cmbTipoGeneracionActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        getLista().remove(jTable1.getSelectedRow());
        Render();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane JScrollPanelFormaPago;
    private javax.swing.JComboBox<String> cmbAlmacen;
    private static javax.swing.JComboBox<String> cmbEstadoVenta;
    private static javax.swing.JComboBox<String> cmbRazonSocial;
    private static javax.swing.JComboBox<String> cmbTipoComprobante;
    private static javax.swing.JComboBox<String> cmbTipoCondicion;
    private static javax.swing.JComboBox<String> cmbTipoGeneracion;
    private static javax.swing.JComboBox<String> cmbTipoMoneda;
    private static javax.swing.JComboBox<String> cmbTipoPago;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JTable jTable1;
    private javax.swing.JLabel lblVendedor;
    private static javax.swing.JTextArea txaFormaPago;
    private static javax.swing.JTextArea txaNota;
    private static javax.swing.JTextField txtBuscarClienteProveedor;
    private javax.swing.JTextField txtBuscarSerie;
    private static javax.swing.JTextField txtCorrelativo;
    private static javax.swing.JTextField txtGravada;
    private static javax.swing.JTextField txtIdentidadClienteProveedor;
    private static javax.swing.JTextField txtImporteTotal;
    private static javax.swing.JTextField txtImpuestoTotal;
    private static javax.swing.JTextField txtNombreCLienteProveedor;
    private static javax.swing.JTextField txtPrefijo;
    private static javax.swing.JTextField txtTipoCambio;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the lista
     */
    public static ArrayList<Producto> getLista() {
        return lista;
    }

    /**
     * @param aLista the lista to set
     */
    public static void setLista(ArrayList<Producto> aLista) {
        lista = aLista;
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
     * @return the id_pseudonimo
     */
    public static int getId_pseudonimo() {
        return id_pseudonimo;
    }

    /**
     * @param aId_pseudonimo the id_pseudonimo to set
     */
    public static void setId_pseudonimo(int aId_pseudonimo) {
        id_pseudonimo = aId_pseudonimo;
    }

    /**
     * @return the PrefijosComprobante
     */
    public static String[] getPrefijosComprobante() {
        return PrefijosComprobante;
    }

    /**
     * @param aPrefijosComprobante the PrefijosComprobante to set
     */
    public static void setPrefijosComprobante(String[] aPrefijosComprobante) {
        PrefijosComprobante = aPrefijosComprobante;
    }

    
}
