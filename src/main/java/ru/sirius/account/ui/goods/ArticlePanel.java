package ru.sirius.account.ui.goods;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import org.netbeans.validation.api.builtin.stringvalidation.StringValidators;
import org.netbeans.validation.api.ui.swing.SwingValidationGroup;
import ru.sirius.account.db.GoodsService;
import ru.sirius.account.model.entity.Article;
import ru.sirius.account.model.entity.Category;
import ru.sirius.account.utils.Config;


public class ArticlePanel extends javax.swing.JPanel {

    private Article article;
    private final SwingValidationGroup validationGroup = SwingValidationGroup.create();

    
    /**
     * Creates new form CreateArticleFrame
     */
    public ArticlePanel(Article article) throws SQLException {

        initComponents();
        ArrayList<Category> categories = GoodsService.readCategories();
        Category[] items = new Category[categories.size() + 1];
        System.arraycopy(categories.toArray(new Category[0]), 0, items, 1, items.length - 1);
        categoryComboBox.setModel(new DefaultComboBoxModel(items));
        
        validationGroup.add(priceTextField, StringValidators.REQUIRE_VALID_NUMBER);
        validationGroup.add(fullNameTextField, StringValidators.REQUIRE_NON_EMPTY_STRING);
        validationGroup.add(categoryComboBox, StringValidators.REQUIRE_NON_EMPTY_STRING);
        
        if (article != null) {
            this.article = article;
            for( Category category : categories){
                if( category.getId() == article.getCategoryId()){
                    categoryComboBox.setSelectedItem(category);        
                    break;
                }
            }            
            fullNameTextField.setText(article.getName());
            priceTextField.setText(article.getPrice().toString());
        } else {
            this.article = new Article();
        }
    }
    


    public SwingValidationGroup getValidationGroup() {
        return validationGroup;
    }

    public Article getArticle() {
        article.setCategoryId(((Category) categoryComboBox.getSelectedItem()).getId());
        article.setName(fullNameTextField.getText());
        BigDecimal price = new BigDecimal(priceTextField.getText());
        price.setScale(Config.currencyScale, BigDecimal.ROUND_HALF_UP);        
        article.setPrice(price);       
        return article;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        priceTextField = new javax.swing.JTextField();
        fullNameTextField = new javax.swing.JTextField();
        categoryComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        priceTextField.setName("Отпускная цена"); // NOI18N

        fullNameTextField.setName("Наименование"); // NOI18N

        categoryComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        categoryComboBox.setName("Категория"); // NOI18N

        jLabel3.setText("Категория");

        jLabel4.setText("Наименование");

        jLabel6.setText("Отпускная цена");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fullNameTextField)
                    .addComponent(categoryComboBox, 0, 279, Short.MAX_VALUE)
                    .addComponent(priceTextField))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fullNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(priceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(53, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox categoryComboBox;
    private javax.swing.JTextField fullNameTextField;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField priceTextField;
    // End of variables declaration//GEN-END:variables
}
