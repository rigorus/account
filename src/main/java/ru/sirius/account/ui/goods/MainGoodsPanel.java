package ru.sirius.account.ui.goods;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.netbeans.validation.api.ui.swing.ValidationPanel;
import org.openide.util.Exceptions;
import ru.sirius.account.model.entity.Article;
import ru.sirius.account.ui.utils.multispan.AttributiveCellTableModel;
import ru.sirius.account.ui.utils.multispan.MultiSpanCellTable;


public class MainGoodsPanel extends javax.swing.JPanel {

    private GoodsModelBuilder builder;

    
    public MainGoodsPanel() {
        initComponents();
        addCategoryButton.setIcon(new ImageIcon(this.getClass().getResource("add_category.png")));
        addArticleButton.setIcon(new ImageIcon(this.getClass().getResource("add_article.png")));
        editButton.setIcon(new ImageIcon(this.getClass().getResource("edit.png")));
        upButton.setIcon(new ImageIcon(this.getClass().getResource("up.png")));
        downButton.setIcon(new ImageIcon(this.getClass().getResource("down.png")));
        deleteButton.setIcon(new ImageIcon(this.getClass().getResource("delete.png")));
        hiddenButton.setIcon(new ImageIcon(this.getClass().getResource("hidden.png")));        
        restoreButton.setIcon(new ImageIcon(this.getClass().getResource("restore.png")));
        
        try {
            builder = new GoodsModelBuilder((AttributiveCellTableModel) goodsTable.getModel());
            builder.build();
        } catch (SQLException ex) {
            Logger.getLogger(MainGoodsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        goodsTable = new MultiSpanCellTable();
        addCategoryButton = new javax.swing.JButton();
        addArticleButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        upButton = new javax.swing.JButton();
        downButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        hiddenButton = new javax.swing.JToggleButton();
        restoreButton = new javax.swing.JButton();

        goodsTable.setModel(new AttributiveCellTableModel());
        goodsTable.setDoubleBuffered(true);
        goodsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        goodsTable.setUpdateSelectionOnSort(false);
        goodsTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane3.setViewportView(goodsTable);

        addCategoryButton.setToolTipText("Создать категорию");
        addCategoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCategoryButtonActionPerformed(evt);
            }
        });

        addArticleButton.setToolTipText("Создать артикул");
        addArticleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addArticleButtonActionPerformed(evt);
            }
        });

        editButton.setToolTipText("Редактировать");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        upButton.setToolTipText("Вверх");
        upButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upButtonActionPerformed(evt);
            }
        });

        downButton.setToolTipText("Вниз");
        downButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downButtonActionPerformed(evt);
            }
        });

        deleteButton.setToolTipText("Удалить");
        deleteButton.setRolloverEnabled(false);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        hiddenButton.setToolTipText("Показать удалённые");
        hiddenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hiddenButtonActionPerformed(evt);
            }
        });

        restoreButton.setToolTipText("Восстановить");
        restoreButton.setEnabled(false);
        restoreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restoreButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(addCategoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addArticleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(upButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(downButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hiddenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(restoreButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addCategoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(addArticleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(upButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(downButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(hiddenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(restoreButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addCategoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCategoryButtonActionPerformed
        CreateCategoryPanel panel = new CreateCategoryPanel(null);
        ValidationPanel validationPanel = new ValidationPanel(panel.getValidationGroup());
        validationPanel.setInnerComponent(panel);
        if (validationPanel.showOkCancelDialog("Создание категории")) {
            try {
                builder.createCategory(panel.getCategory());
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }//GEN-LAST:event_addCategoryButtonActionPerformed

    private void addArticleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addArticleButtonActionPerformed
        try {
            CreateArticlePanel panel = new CreateArticlePanel(null);
            ValidationPanel validationPanel = new ValidationPanel(panel.getValidationGroup());
            validationPanel.setInnerComponent(panel);
            if (validationPanel.showOkCancelDialog("Создание артикула")) {
                    builder.createArticle(panel.getArticle());
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_addArticleButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed

        GoodsModelBuilder.Row row = builder.getRow(goodsTable.getSelectedRow());
        if (row == null) {
            return;
        }
        
        if( row.rowtype == GoodsModelBuilder.ROWTYPE.CATEGORY){
            CreateCategoryPanel panel = new CreateCategoryPanel(row.category);
            ValidationPanel validationPanel = new ValidationPanel(panel.getValidationGroup());
            validationPanel.setInnerComponent(panel);
            if (validationPanel.showOkCancelDialog("Редактирование категории")) {
                try {
                    row.category = panel.getCategory();
                    builder.updateRow(row);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } 
        }else if( row.article != null){
            try {
                CreateArticlePanel panel = new CreateArticlePanel(row.article);
                ValidationPanel validationPanel = new ValidationPanel(panel.getValidationGroup());
                validationPanel.setInnerComponent(panel);
                if (validationPanel.showOkCancelDialog("Редактирование артикула")) {
                    row.article = panel.getArticle();
                    builder.updateRow(row);
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        
    }//GEN-LAST:event_editButtonActionPerformed

    private void upButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upButtonActionPerformed
        try {

            GoodsModelBuilder.Row row = builder.getRow(goodsTable.getSelectedRow());
            if (row == null) {
                return;
            }

            builder.moveRowUp(row);        
            
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_upButtonActionPerformed

    private void downButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downButtonActionPerformed
        try {
            GoodsModelBuilder.Row row = builder.getRow(goodsTable.getSelectedRow());
            if (row == null) {
                return;
            }
            builder.moveRowDown(row);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_downButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        try {

            GoodsModelBuilder.Row row = builder.getRow(goodsTable.getSelectedRow());
            if (row == null) {
                return;
            }

            if (row.rowtype == GoodsModelBuilder.ROWTYPE.CATEGORY &&  JOptionPane.YES_OPTION == 
                    JOptionPane.showConfirmDialog(this, "Удалить директорию и все артикулы, входящие в неё?", 
                    "Удаление", JOptionPane.YES_NO_OPTION)) {
                builder.removeRow(row, hiddenButton.isSelected());
                
            } else if (row.rowtype == GoodsModelBuilder.ROWTYPE.ARTICLE && JOptionPane.YES_OPTION == 
                    JOptionPane.showConfirmDialog(this, "Удалить артикул?", "Удаление", JOptionPane.YES_NO_OPTION)) {
                builder.removeRow(row, hiddenButton.isSelected());
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void hiddenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hiddenButtonActionPerformed
       
        boolean show = hiddenButton.isSelected();
        restoreButton.setEnabled(show);
        builder.showDeleted(show);
        
    }//GEN-LAST:event_hiddenButtonActionPerformed

    private void restoreButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restoreButtonActionPerformed
        
        int position = goodsTable.getSelectedRow();
        if (position == -1) {
            return;
        }
        
        Article article = builder.getDeletedArticle(position);
        
        if( article != null){
            try {
                CreateArticlePanel panel = new CreateArticlePanel(article);
                ValidationPanel validationPanel = new ValidationPanel(panel.getValidationGroup());
                validationPanel.setInnerComponent(panel);
                if (validationPanel.showOkCancelDialog("Восстановление артикула")) {
                    builder.restoreArticle(panel.getArticle());
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            } 
        }        
    }//GEN-LAST:event_restoreButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addArticleButton;
    private javax.swing.JButton addCategoryButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton downButton;
    private javax.swing.JButton editButton;
    private javax.swing.JTable goodsTable;
    private javax.swing.JToggleButton hiddenButton;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton restoreButton;
    private javax.swing.JButton upButton;
    // End of variables declaration//GEN-END:variables
}
