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

// TODO index пересчитываются каждый раз при удалении и перемещении !!!! 
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
        category.setWeight((categories.size() + 1) * 1000);                
        GoodsProvider.createCategory(category);
        categories.add(category);
        articles.put(category.getId(), new ArrayList<Article>());
        int position = getPosition(category.getId());
        insertCategoryRow(position, category);
    }
    
    public void addArticle(Article article) throws SQLException{
        int categoryId = article.getCategoryId();
        Category category = findCategory(categoryId);
        ArrayList<Article> list = articles.get(categoryId);
        int weight = category.getWeight() + list.size() + 1;
        article.setWeight(weight);
        
        GoodsProvider.createArticle(article);
        
        list.add(article);
        int position = getPosition(category.getId()) + list.size();
        insertArticleRow(position, article);
    }
    
    private void addCategoryRow(Category category){
        model.addRow(new Object[]{"", category.getName(), ""});
        attribute.combine(new int[]{model.getRowCount() - 1}, new int[]{1, 2});
        attribute.setBackground(Color.YELLOW, new int[]{model.getRowCount() - 1}, new int[]{0, 1});
        attribute.setForeground(Color.BLUE, new int[]{model.getRowCount() - 1}, new int[]{0, 1});          
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 12);
        attribute.setFont(font, model.getRowCount() - 1, 1);
    }
    
    private void insertCategoryRow(int position, Category category) {
                
        model.insertRow(position, new Object[]{"", category.getName(), ""});
        attribute.combine(new int[]{model.getRowCount() - 1}, new int[]{1, 2});
        attribute.setBackground(Color.YELLOW, new int[]{model.getRowCount() - 1}, new int[]{0, 1});
        attribute.setForeground(Color.BLUE, new int[]{model.getRowCount() - 1}, new int[]{0, 1});
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 12);
        attribute.setFont(font, model.getRowCount() - 1, 1);
    }
    
    private void addArticleRow(Article article) {
        model.addRow(new Object[]{article.getId(), article.getName(), "100"});
    }
    
    private void insertArticleRow(int position, Article article) {
        model.insertRow(position, new Object[]{article.getId(), article.getName(), "100"});
    }
    
    private Category findCategory(int id){
        for (Category category : categories) {
            if( category.getId() == id){
                return category;
            }
        }
        return null;
    }
    
    private int getPosition(int id){
        int position = 0;
        for (Category category : categories) {
            if (category.getId() == id) {
                return position;
            }else{
                position += 1 + articles.get(category.getId()).size();
            }
        }   
        
        return position;
    }
}
