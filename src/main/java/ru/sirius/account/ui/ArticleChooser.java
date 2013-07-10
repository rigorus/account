package ru.sirius.account.ui;

import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import ru.sirius.account.model.nomenclature.NmTreeTableModel;
import ru.sirius.account.model.nomenclature.NmNode;
import ru.sirius.account.model.nomenclature.NmTreeBuilder;
import ru.sirius.account.service.NomenclatureService;

public class ArticleChooser extends JDialog {

    private TreeModel groupModel;
    private TreeModel selectionModel;
    private NmTreeTableModel complexModel;

    private final NmTreeBuilder builder;
    
    /**
     * Creates new form ArticleChooser
     */
    public ArticleChooser(java.awt.Frame parent, boolean modal) {
        
        super(parent, modal);                
        builder = new NmTreeBuilder(NomenclatureService.getInstance().getPrototype());
        
        groupModel = new DefaultTreeModel(builder.buildGroupTree());        
        complexModel = new NmTreeTableModel(builder.buidComplexTree());
        selectionModel = new DefaultTreeModel(builder.buidSelectionTree());
                
        initComponents();
        classifierTreeTable.setTreeTableModel(complexModel);
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setLeafIcon(new ImageIcon(getClass().getResource("resource/folder_close_16.png")));
        renderer.setOpenIcon(new ImageIcon(getClass().getResource("/ru/sirius/distributor/ui/resource/folder_open_16.png")));
        renderer.setClosedIcon(new ImageIcon(getClass().getResource("/ru/sirius/distributor/ui/resource/folder_close_16.png")));
        groupTree.setCellRenderer(renderer);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chooserSplitPane = new javax.swing.JSplitPane();
        classifierSplitPane = new javax.swing.JSplitPane();
        groupScrollPane = new javax.swing.JScrollPane();
        groupTree = new javax.swing.JTree();
        classifierTreeTableScrollPane = new javax.swing.JScrollPane();
        classifierTreeTable = new org.jdesktop.swingx.JXTreeTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        selectionTree = new javax.swing.JTree();
        classifierToggleButton = new javax.swing.JToggleButton();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        chooserSplitPane.setDividerLocation(500);

        classifierSplitPane.setToolTipText("");

        groupTree.setModel(groupModel);
        groupTree.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        groupTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                groupTreeValueChanged(evt);
            }
        });
        groupScrollPane.setViewportView(groupTree);

        classifierSplitPane.setLeftComponent(groupScrollPane);

        classifierTreeTable.setDragEnabled(true);
        classifierTreeTable.setEditable(false);
        classifierTreeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                classifierTreeTableMouseClicked(evt);
            }
        });
        classifierTreeTableScrollPane.setViewportView(classifierTreeTable);

        classifierSplitPane.setRightComponent(classifierTreeTableScrollPane);

        chooserSplitPane.setLeftComponent(classifierSplitPane);

        selectionTree.setModel(selectionModel);
        jScrollPane2.setViewportView(selectionTree);

        chooserSplitPane.setRightComponent(jScrollPane2);

        classifierToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/sirius/distributor/ui/resource/tree.png"))); // NOI18N
        classifierToggleButton.setSelected(true);
        classifierToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classifierToggleButtonActionPerformed(evt);
            }
        });

        okButton.setLabel("Выбрать");

        cancelButton.setLabel("Отменить");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chooserSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(classifierToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(okButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chooserSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(classifierToggleButton)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private int classifierDividerLocation = 0;
    private int classifierDividerSize = 0;

    private void classifierToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classifierToggleButtonActionPerformed

        if (!classifierToggleButton.isSelected()) {
            classifierDividerLocation = classifierSplitPane.getDividerLocation();
            classifierDividerSize = classifierSplitPane.getDividerSize();
            classifierSplitPane.setDividerSize(0);
        } else {
            classifierSplitPane.setDividerLocation(classifierDividerLocation);
            classifierSplitPane.setDividerSize(classifierDividerSize);
        }

        groupScrollPane.setVisible(classifierToggleButton.isSelected());
        classifierSplitPane.updateUI();
        chooserSplitPane.updateUI();
    }//GEN-LAST:event_classifierToggleButtonActionPerformed

    private void groupTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_groupTreeValueChanged
        
        NmNode node = (NmNode) evt.getNewLeadSelectionPath().getLastPathComponent();
        complexModel.setRoot(node);
        classifierTreeTable.setTreeTableModel(complexModel);
        classifierTreeTable.updateUI();
    }//GEN-LAST:event_groupTreeValueChanged

    private void classifierTreeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_classifierTreeTableMouseClicked
        int clickCount = evt.getClickCount();
        int button = evt.getButton();     
        if (clickCount == 2 && button == MouseEvent.BUTTON1 && classifierTreeTable.getSelectedRow() != -1) {
            int rowIndex = classifierTreeTable.getSelectedRow();
            TreePath path = classifierTreeTable.getPathForRow(rowIndex);
            NmNode node = (NmNode)path.getLastPathComponent();  
            builder.mergeSelectionTree((NmNode)selectionModel.getRoot(),node);
            selectionTree.updateUI();
        }
    }//GEN-LAST:event_classifierTreeTableMouseClicked
    
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
            java.util.logging.Logger.getLogger(ArticleChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ArticleChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ArticleChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ArticleChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ArticleChooser dialog = new ArticleChooser(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JSplitPane chooserSplitPane;
    private javax.swing.JSplitPane classifierSplitPane;
    private javax.swing.JToggleButton classifierToggleButton;
    private org.jdesktop.swingx.JXTreeTable classifierTreeTable;
    private javax.swing.JScrollPane classifierTreeTableScrollPane;
    private javax.swing.JScrollPane groupScrollPane;
    private javax.swing.JTree groupTree;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton okButton;
    private javax.swing.JTree selectionTree;
    // End of variables declaration//GEN-END:variables
}
