package ru.sirius.account.ui.goods;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import ru.sirius.account.db.GoodsProvider;
import ru.sirius.account.model.entity.Article;
import ru.sirius.account.model.entity.Category;
import ru.sirius.account.ui.utils.multispan.AttributiveCellTableModel;
import ru.sirius.account.ui.utils.multispan.DefaultCellAttribute;

public class GoodsModelBuilder {
    
    private AttributiveCellTableModel model;
    private DefaultCellAttribute attribute; 
    private ArrayList<Category> categories;
    private Map<Integer,ArrayList<Article>> articles;
    
    public GoodsModelBuilder(AttributiveCellTableModel model) throws SQLException{
        this.model = model;
        this.model.addColumn("");
        this.model.addColumn("Наименование");
        this.model.addColumn("Количество");
        this.attribute = (DefaultCellAttribute) model.getCellAttribute();
        categories = GoodsProvider.readCategories();
        articles = new HashMap<>();
        for (Category category : categories){
            articles.put(category.getId(), GoodsProvider.readCategoryArticles(category.getId()));
        }
        
    }
    
    public void build() throws SQLException{
       for(Category category : categories ){
           addCategoryRow(category);
           for( Article article : articles.get(category.getId())){
               addArticleRow(article);
           }
       }       
    }    
    
    public ArrayList<Category> getCategories(){
        return categories;
    }
    
    public void addCategory(Category category) throws SQLException{
        category.setSortNumber(categories.size() * 1000);        
        GoodsProvider.createCategory(category);
        categories.add(category);
        addCategoryRow(category);
    }
    
    public void addArticle(Article article) throws SQLException{
        Category category = findCategory(article.getCategoryId());
        ArrayList<Article> list = articles.get(category.getId());
        int sortNumber = category.getSortNumber() + list.size() + 1;
        article.setSortNumber(sortNumber);
        GoodsProvider.createArticle(article);
        list.add(article);
        addArticleRow(article);
    }
    
    private void addCategoryRow(Category category){
        model.addRow(new Object[]{"", category.getName(), ""});
        attribute.combine(new int[]{model.getRowCount() - 1}, new int[]{1, 2});
        attribute.setBackground(Color.YELLOW, new int[]{model.getRowCount() - 1}, new int[]{0, 1});
        attribute.setForeground(Color.BLUE, new int[]{model.getRowCount() - 1}, new int[]{0, 1});          
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 12);
        attribute.setFont(font, model.getRowCount() - 1, 1);
    }
    
    private void addArticleRow(Article article) {
//        model.insertRow(row, rowData);
        model.addRow(new Object[]{article.getId(), article.getName(), "100"});
    }
    
    private Category findCategory(int id){
        for (Category category : categories) {
            if( category.getId() == id){
                return category;
            }
        }
        return null;
    }
}
