package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import org.json.JSONArray; 
import org.json.JSONObject; 

public class Conversor extends javax.swing.JFrame {
     
    JLabel lblMensaje = new JLabel("");
    
    public Conversor() {
        initComponents();
        txtFecha = new JTextField();
        txtMoneda = new JTextField();
        txtValor = new JTextField();
        btnObtener = new JButton("Obtener Tipo de Cambio");
        
        btnObtener.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                // Llama al método para obtener los datos cuando se presiona el botón
                obtenerDatosTipoCambio();
            }
        });
    }
    
    private void obtenerDatosTipoCambio() {
        // Muestra un mensaje de carga o de estado
        if (lblMensaje != null) {
            lblMensaje.setText("Obteniendo datos...");
        }

        // SwingWorker permite ejecutar tareas largas en segundo plano y actualizar la UI después.
        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                // URL del API que creaste con Express.js
                String apiUrl = "http://localhost:3000/";
                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET"); // Método HTTP GET
                conn.setRequestProperty("Accept", "application/json"); // Esperamos una respuesta JSON

                // Verifica si la conexión fue exitosa (código de respuesta HTTP 200 OK)
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Fallo al conectar con el API. Código HTTP: " + conn.getResponseCode());
                }

                // Lee la respuesta del servidor
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    output.append(line);
                }
                conn.disconnect(); // Cierra la conexión
                return output.toString(); // Retorna la respuesta JSON como String
            }

            @Override
            protected void done() {
                // Este método se ejecuta en el Event Dispatch Thread (EDT)
                // después de que doInBackground() ha terminado.
                try {
                    String jsonResponse = get(); // Obtiene el resultado de doInBackground()
                    procesarJsonYActualizarUI(jsonResponse); // Procesa el JSON y actualiza la UI
                    if (lblMensaje != null) {
                        lblMensaje.setText("Datos obtenidos con éxito.");
                    }
                } catch (Exception ex) {
                    // Manejo de errores: muestra el mensaje de error en la UI y la consola
                    if (lblMensaje != null) {
                        lblMensaje.setText("Error: " + ex.getMessage());
                    }
                    ex.printStackTrace(); // Imprime el stack trace para depuración
                }
            }
        };
        worker.execute(); // Inicia el SwingWorker en su propio hilo
    }
    
    /**
     * Procesa la cadena JSON recibida y actualiza los JTextFields en la interfaz de usuario.
     * @param jsonString La cadena JSON que contiene los datos del tipo de cambio.
     */
    private void procesarJsonYActualizarUI(String jsonString) {
        // Asegurarse de que las actualizaciones de la UI se hagan en el Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            try {
                // Crea un objeto JSON principal a partir de la cadena
                JSONObject jsonObject = new JSONObject(jsonString);
                // Obtiene el array JSON bajo la clave "tipo_cambio"
                JSONArray tipoCambioArray = jsonObject.getJSONArray("tipo_cambio");

                // Verifica si el array no está vacío
                if (tipoCambioArray.length() > 0) {
                    // Obtiene el primer objeto dentro del array (asumiendo que solo hay uno)
                    JSONObject primerObjetoTipoCambio = tipoCambioArray.getJSONObject(0);

                    // Extrae los valores por sus claves
                    String fecha = primerObjetoTipoCambio.getString("fecha");
                    String moneda = primerObjetoTipoCambio.getString("moneda");
                    double valor = primerObjetoTipoCambio.getDouble("valor");

                    // Actualiza los JTextFields con los datos obtenidos
                    txtFecha.setText(fecha);
                    txtMoneda.setText(moneda);
                    txtValor.setText(String.format("%.2f", valor)); // Formatea el valor a 2 decimales
                } else {
                    if (lblMensaje != null) {
                        lblMensaje.setText("Error: El array 'tipo_cambio' está vacío.");
                    }
                }
            } catch (Exception e) {
                // Manejo de errores de parseo JSON
                if (lblMensaje != null) {
                    lblMensaje.setText("Error al parsear JSON: " + e.getMessage());
                }
                e.printStackTrace();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        txtValor = new javax.swing.JTextField();
        txtMoneda = new javax.swing.JTextField();
        btnObtener = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(355, 300));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("TIPO DE CAMBIO APP");

        jLabel2.setText("FECHA:");

        jLabel3.setText("MONEDA:");

        jLabel4.setText("VALOR:");

        btnObtener.setText("OBTENER DATOS");
        btnObtener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObtenerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(txtMoneda)
                            .addComponent(txtValor)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(btnObtener, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnObtener, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnObtenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObtenerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnObtenerActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Conversor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Conversor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Conversor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Conversor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Conversor().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnObtener;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtMoneda;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}