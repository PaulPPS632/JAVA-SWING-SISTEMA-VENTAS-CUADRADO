/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Frames;

import Utilitaries.Funcionalidades;
import Utilitaries.MetodoClientesProveedor;
import java.awt.event.KeyEvent;
import javax.swing.JInternalFrame;

/**
 *
 * @author Paul
 */
public class BuscarClienteProveedor extends javax.swing.JInternalFrame {

    /**
     * Creates new form BuscarClienteProveedor
     */
    private int frmAIndexar;
    private int id_pseudonimo = 0;
    public BuscarClienteProveedor() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        chkNombre = new javax.swing.JCheckBox();
        chkDocumento = new javax.swing.JCheckBox();
        chkPseudonimo = new javax.swing.JCheckBox();
        btnRegresar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnbus = new javax.swing.JButton();

        setBackground(new java.awt.Color(220, 220, 220));
        setClosable(true);

        jPanel1.setBackground(new java.awt.Color(220, 220, 220));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BUSCAR MARCA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(33, 97, 140))); // NOI18N

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Marca"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        chkNombre.setBackground(new java.awt.Color(220, 220, 220));
        chkNombre.setForeground(new java.awt.Color(0, 0, 0));
        chkNombre.setText("Por Nombre");
        chkNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkNombreActionPerformed(evt);
            }
        });

        chkDocumento.setBackground(new java.awt.Color(220, 220, 220));
        chkDocumento.setForeground(new java.awt.Color(0, 0, 0));
        chkDocumento.setSelected(true);
        chkDocumento.setText("Por Documento");
        chkDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDocumentoActionPerformed(evt);
            }
        });

        chkPseudonimo.setBackground(new java.awt.Color(220, 220, 220));
        chkPseudonimo.setForeground(new java.awt.Color(0, 0, 0));
        chkPseudonimo.setText("Por Pseudonimo");
        chkPseudonimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPseudonimoActionPerformed(evt);
            }
        });

        btnRegresar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnRegresar.setText("REGRESAR");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        btnAgregar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnAgregar.setText("NUEVO");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnbus.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnbus.setText("TODO");
        btnbus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(chkDocumento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkPseudonimo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnbus)
                        .addGap(0, 7, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addGap(30, 30, 30)
                        .addComponent(btnAgregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegresar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkNombre)
                    .addComponent(chkPseudonimo)
                    .addComponent(chkDocumento)
                    .addComponent(btnbus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void settxtBuscar(String Buscado){
        txtBuscar.setText(Buscado);
        BUSCAR();
    }
    private void insertaconsulta(){
        String txt = txtBuscar.getText();
        jTable1.setModel(MetodoClientesProveedor.BuscarPorDocumento(txt));
    }
    public void MarcarFiltros(int i){
        
        if(i == 1){
            chkDocumento.setSelected(true);
            chkNombre.setSelected(false);
            chkPseudonimo.setSelected(false);
        }else if(i == 2){
            chkDocumento.setSelected(false);
            chkNombre.setSelected(true);
            chkPseudonimo.setSelected(false);
        }else if(i == 3){
            chkDocumento.setSelected(false);
            chkNombre.setSelected(false);
            chkPseudonimo.setSelected(true);
        }
        txtBuscar.setText(null);
        txtBuscar.requestFocus();
        BUSCAR();
    }
    public void BUSCAR(){
        String buscado = txtBuscar.getText();
        if(chkDocumento.isSelected()){
            jTable1.setModel(MetodoClientesProveedor.BuscarPorDocumento(buscado));
        }
        if(chkNombre.isSelected()){
            jTable1.setModel(MetodoClientesProveedor.BuscarPorNombre(buscado));
        }
        if(chkPseudonimo.isSelected()){
            jTable1.setModel(MetodoClientesProveedor.clientes_asociados_a_pseudonimo(id_pseudonimo));
        }
    }
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void chkDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDocumentoActionPerformed
        // TODO add your handling code here:
        MarcarFiltros(1);
    }//GEN-LAST:event_chkDocumentoActionPerformed

    private void chkNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkNombreActionPerformed
        // TODO add your handling code here:
        MarcarFiltros(2);
    }//GEN-LAST:event_chkNombreActionPerformed

    private void chkPseudonimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPseudonimoActionPerformed
        // TODO add your handling code here:
        MarcarFiltros(3);
    }//GEN-LAST:event_chkPseudonimoActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            BUSCAR();
        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            int row = jTable1.getSelectedRow();
            if(row != -1){
                InsertarDatos(row);
                dispose();
                
            }
            
            
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        RegistroClientes nuevo = new RegistroClientes();
        Funcionalidades.CentrarVentana(frmPrincipal.tbn_escritorio, nuevo);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        RegistroProductos.txtMarca.setText("");
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnbusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbusActionPerformed
        // TODO add your handling code here:
        txtBuscar.setText("");
        insertaconsulta();
    }//GEN-LAST:event_btnbusActionPerformed
    private void InsertarDatos(int row){
        
        String direccion = "", email = "";
        
        if(jTable1.getValueAt(row, 5) != null ){
                direccion = jTable1.getValueAt(row, 5).toString();
            }
            if(jTable1.getValueAt(row, 6) != null ){
                email = jTable1.getValueAt(row, 6).toString();
        }
        if(frmAIndexar == 1){
            frmVentas.setID_ClienteProveedor(Integer.parseInt(jTable1.getValueAt(row, 0).toString()));
            frmVentas.settxtIdentidadClienteProveedor(jTable1.getValueAt(row, 2).toString());
            frmVentas.settxtNombreClienteProveedor(jTable1.getValueAt(row, 3).toString());
            switch (jTable1.getValueAt(row, 1).toString()) {
                case "DNI":
                    frmVentas.setTipoDocumento(1);
                    break;
                case "RUC":
                    frmVentas.setTipoDocumento(6);
                    break;
                case "CARNET EXTRANJERIA":
                    frmVentas.setTipoDocumento(4);
                    break;
                case "PASAPORTE":
                    frmVentas.setTipoDocumento(7);
                    break;
            }
            
            frmVentas.setDireccionCliente(direccion);
            frmVentas.setEmailCliente(email);
            if(id_pseudonimo != 0) frmVentas.setId_pseudonimo(id_pseudonimo);
            
        }else if(frmAIndexar == 2){
            frmIngreso.setID_ClienteProveedor(Integer.parseInt(jTable1.getValueAt(row, 0).toString()));
            frmIngreso.settxtIdentidadClienteProveedor(jTable1.getValueAt(row, 2).toString());
            frmIngreso.settxtNombreClienteProveedor(jTable1.getValueAt(row, 3).toString());
            switch (jTable1.getValueAt(row, 1).toString()) {
                case "DNI":
                    frmIngreso.setTipoDocumento(1);
                    break;
                case "RUC":
                    frmIngreso.setTipoDocumento(6);
                    break;
                case "CARNET EXTRANJERIA":
                    frmIngreso.setTipoDocumento(4);
                    break;
                case "PASAPORTE":
                    frmIngreso.setTipoDocumento(7);
                    break;
            }
            frmIngreso.setDireccionCliente(direccion);
            frmIngreso.setEmailCliente(email);

        }
        //RegistroProductos.txtMarca.setText(jTable1.getValueAt(row, 1).toString());
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnbus;
    public javax.swing.JCheckBox chkDocumento;
    public javax.swing.JCheckBox chkNombre;
    public javax.swing.JCheckBox chkPseudonimo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private static javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the frmAIndexar
     */
    public int getFrmAIndexar() {
        return frmAIndexar;
    }

    /**
     * @param frmAIndexar the frmAIndexar to set
     */
    public void setFrmAIndexar(int frmAIndexar) {
        this.frmAIndexar = frmAIndexar;
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
