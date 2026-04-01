/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author gallo
 */
import java.sql.Connection;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class accounting extends javax.swing.JFrame {
    
    private JTable paymentTable;
    private JLabel totalLabel;
    private JPanel chartTicketsPanel;
    private JPanel chartRevenuePanel;


    /**
     * Creates new form accounting
     */
    public accounting() {
        initComponents();
        loadAccountingData();
        
    }
    private void loadAccountingData() {

        try {
            Connection conn = (Connection) DataSource.createConnection();

        // =======================
        // 1) REMPLIR LE TABLEAU
        // =======================

        String sql = "SELECT b.id, m.moviename AS film, b.tickets, b.total_price, b.booking_date " +
                     "FROM booking b " +
                     "JOIN movie m ON b.film_id = m.id " +
                     "WHERE payment_status = 1";

        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Film");
        model.addColumn("Tickets");
        model.addColumn("Montant (€)");
        model.addColumn("Date");

        double total = 0;

        while (rs.next()) {
            int id = rs.getInt("id");
            String film = rs.getString("film");
            int tickets = rs.getInt("tickets");
            double price = rs.getDouble("total_price");
            String date = rs.getString("booking_date");

            model.addRow(new Object[]{id, film, tickets, price, date});
            total += price;
        }

        jTable1.setModel(model);

        // AFFICHER LE TOTAL
        jLabel2.setText("Total : " + total + " €");



        // =======================
        // 2) GRAPHIQUE TICKETS PAR FILM
        // =======================

        String sqlTickets = "SELECT m.moviename, SUM(b.tickets) AS totalTickets " +
                            "FROM booking b " +
                            "JOIN movie m ON b.film_id = m.id " +
                            "WHERE payment_status = 1 " +
                            "GROUP BY m.moviename";

        PreparedStatement stmt2 = conn.prepareStatement(sqlTickets);
        ResultSet rs2 = stmt2.executeQuery();

        DefaultCategoryDataset datasetTickets = new DefaultCategoryDataset();

        while (rs2.next()) {
            datasetTickets.addValue(
                rs2.getInt("totalTickets"),
                "Tickets",
                rs2.getString("moviename")
            );
        }

        JFreeChart chartTickets = ChartFactory.createBarChart(
                "Tickets par Film",
                "Film",
                "Tickets",
                datasetTickets
        );

        ChartPanel chartPanel1 = new ChartPanel(chartTickets);
        jPanel1.setLayout(new BorderLayout());
        jPanel1.removeAll(); 
        jPanel1.add(chartPanel1, BorderLayout.CENTER);
        jPanel1.validate();



        // =======================
        // 3) GRAPHIQUE REVENUS 5 DERNIERS JOURS
        // =======================

        String sqlRevenue = "SELECT booking_date, SUM(total_price) AS total " +
                            "FROM booking " +
                            "WHERE payment_status = 1 " +
                            "AND booking_date >= DATE_SUB(CURDATE(), INTERVAL 5 DAY) " +
                            "GROUP BY booking_date";

        PreparedStatement stmt3 = conn.prepareStatement(sqlRevenue);
        ResultSet rs3 = stmt3.executeQuery();

        DefaultCategoryDataset datasetRevenue = new DefaultCategoryDataset();

        while (rs3.next()) {
            datasetRevenue.addValue(
                rs3.getDouble("total"),
                "Revenu",
                rs3.getString("booking_date")
            );
        }

        JFreeChart chartRevenue = ChartFactory.createLineChart(
                "Revenus des 5 Derniers Jours",
                "Jour",
                "Montant (€)",
                datasetRevenue
        );

        ChartPanel chartPanel2 = new ChartPanel(chartRevenue);
        jPanel2.setLayout(new BorderLayout());
        jPanel2.removeAll();
        jPanel2.add(chartPanel2, BorderLayout.CENTER);
        jPanel2.validate();


        conn.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ACCOUNTING");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel2.setText("jLabel2");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 351, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 519, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 265, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(161, 161, 161)
                            .addComponent(jLabel1))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(58, 58, 58)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(accounting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(accounting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(accounting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(accounting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new accounting().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
