package ru.sirius.account.ui.goods;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
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
    private TreeSet<Article> deleted; 
    private int rows;
    
    public GoodsModelBuilder(AttributiveCellTableModel model) throws SQLException{
        this.model = model;
        this.model.addColumn("Наименование");
        this.model.addColumn("Отпускная цена");
        this.model.addColumn("");
        this.attribute = (DefaultCellAttribute) model.getCellAttribute();
        categories = GoodsProvider.readCategories();
        articles = new HashMap<>();
        for (Category category : categories){
            articles.put(category.getId(), GoodsProvider.readCategoryArticles(category.getId()));
        }        
        deleted = GoodsProvider.readDeletedArticles();      
    }
    
    public void build() throws SQLException{
       for(Category category : categories ){
           addCategoryRow(category);
           for( Article article : articles.get(category.getId())){
               addArticleRow(article);
           }
       }       
    }    
    
    public void showHidden(boolean show){
        if( show ){
            model.addRow(new Object[]{"Удалённые артикулы", "", ""});
            attribute.combine(new int[]{model.getRowCount() - 1}, new int[]{0, 1, 2});
            attribute.setBackground(Color.GRAY, new int[]{model.getRowCount() - 1}, new int[]{0});
            attribute.setForeground(Color.BLACK, new int[]{model.getRowCount() - 1}, new int[]{0});
            Font font = new Font(Font.SANS_SERIF, Font.ITALIC, 12);
            attribute.setFont(font, model.getRowCount() - 1, 0);
            for( Article article : deleted){
                model.addRow(new Object[]{article.getName(), article.getPrice(), "100"});
                attribute.setBackground(Color.LIGHT_GRAY, new int[]{model.getRowCount() - 1}, new int[]{0,1,2});                
            }        
        }else{
            for(int i=0; i < deleted.size() + 1; ++i){
                model.removeRow(rows);
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
    
    public void move(int position, boolean up) throws SQLException{
  
        SearchResult result = new SearchResult(position);
        int cIndex = result.cIndex;
        int aIndex = result.aIndex;
        Category category = result.category;
        Article article = result.article;
        
        if( category != null && up && cIndex > 0 ) {
        
            GoodsProvider.replace(category, categories.get(cIndex - 1));           
            int end = position + articles.get(category.getId()).size();
            int to = position - articles.get(categories.get(cIndex - 1).getId()).size() -1;
            categories.set(cIndex, categories.get(cIndex - 1));
            categories.set(cIndex - 1, category);
            model.moveRow(position, end, to);
            
        
        }else if ( category != null && !up && cIndex < categories.size() - 1){

            GoodsProvider.replace(category, categories.get(cIndex + 1));
            int start = position + articles.get(category.getId()).size() + 1;
            int end = start + articles.get(categories.get(cIndex + 1).getId()).size();
            categories.set(cIndex, categories.get(cIndex + 1));
            categories.set(cIndex + 1, category);
            model.moveRow(start, end, position);
        
        } else if (article != null && up && aIndex > 0 ){
        
            GoodsProvider.replace(article, articles.get(categories.get(cIndex).getId()).get(aIndex - 1));
            articles.get(categories.get(cIndex).getId()).set(aIndex, articles.get(categories.get(cIndex).getId()).get(aIndex - 1));
            articles.get(categories.get(cIndex).getId()).set(aIndex - 1, article);
            model.moveRow(position, position, position - 1);
            
        }else if (article != null && !up && aIndex < articles.get(categories.get(cIndex).getId()).size() - 1){
            
            GoodsProvider.replace(article, articles.get(categories.get(cIndex).getId()).get(aIndex + 1));
            articles.get(categories.get(cIndex).getId()).set(aIndex, articles.get(categories.get(cIndex).getId()).get(aIndex + 1));
            articles.get(categories.get(cIndex).getId()).set(aIndex + 1, article);
            model.moveRow(position, position, position + 1);
        
        }                           
    }            
        
    private void addCategoryRow(Category category){
        ++rows;
        model.addRow(new Object[]{category.getName(), "",""});
        attribute.combine(new int[]{model.getRowCount() - 1}, new int[]{0,1, 2});
        attribute.setBackground(Color.YELLOW, new int[]{model.getRowCount() - 1}, new int[]{0});
        attribute.setForeground(Color.BLUE, new int[]{model.getRowCount() - 1}, new int[]{0});          
        Font font = new Font(Font.SANS_SERIF, Font.ITALIC, 12);
        attribute.setFont(font, model.getRowCount() - 1, 0);
    }
    
    private void insertCategoryRow(int position, Category category) {                
        ++rows;
        model.insertRow(position, new Object[]{category.getName(), "",""});
        attribute.combine(new int[]{position}, new int[]{0,1, 2});
        attribute.setBackground(Color.YELLOW, new int[]{position}, new int[]{0});
        attribute.setForeground(Color.BLUE, new int[]{position}, new int[]{0});
        Font font = new Font(Font.SANS_SERIF, Font.ITALIC, 12);
        attribute.setFont(font, position, 0);
    }
    
    private void addArticleRow(Article article) {
        ++rows;
        model.addRow(new Object[]{article.getName(), article.getPrice(), "100"});
    }
    
    private void insertArticleRow(int position, Article article) {
        ++rows;
        model.insertRow(position, new Object[]{article.getName(), article.getPrice(), "100"});
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

    void removeCategory(SearchResult result, boolean showHidden) throws SQLException{
        int id = result.category.getId();
        GoodsProvider.deleteCategory(result.category);
        --rows;
        int index = categories.indexOf(result.category);
        categories.remove(result.category);
        GoodsProvider.downWeight(result.category.getWeight());
        
        model.removeRow(result.position);
        for( Article article : articles.get(id)){
            --rows;
            model.removeRow(result.position);
            deleted.add(article);           
        }
        articles.remove(id);       
        if( showHidden ){
            showHidden(false);
            showHidden(true);
        }
    }

    void removeArticle(SearchResult result, boolean showHidden) throws SQLException{
        --rows;
        GoodsProvider.deleteArticule(result.article);
        // если категория ид будет обнуляться то будет еррор
        articles.get(result.article.getCategoryId()).remove(result.article);
        model.removeRow(result.position);
        deleted.add(result.article);
        if (showHidden) {
            showHidden(false);
            showHidden(true);
        }
    }

    void updateCategory(SearchResult result) throws SQLException{
        GoodsProvider.updateCategory(result.category);
        model.setValueAt(result.category.getName(), result.position, 0);
    }

    void updateArticle(SearchResult result) throws SQLException{
        GoodsProvider.updateArticle(result.article);
        model.setValueAt(result.article.getName(), result.position, 0);
        
    }

    void restoreArticle(SearchResult result) throws SQLException {
                
        for(Category category : categories){
            if( category.getId() == result.article.getCategoryId()){
                result.article.setWeight(category.getWeight() + articles.get(category.getId()).size() + 1);                                
                break;
            }            
        }
        
        GoodsProvider.updateArticle(result.article);
        deleted.remove(result.article);
       
        model.removeRow(result.position);
        showHidden(false);
        showHidden(true);        

        
        int position = getPosition(result.article.getCategoryId())
                + articles.get(result.article.getCategoryId()).size() + 1;

        articles.get(result.article.getCategoryId()).add(result.article);
        insertArticleRow(position, result.article);
    }
    
    public class SearchResult{
        public Category category;
        public Article article;
        public int cIndex;
        public int aIndex;
        public int position;
        
        public SearchResult(int position){
            
            this.position = position;
            
            int index = 0;
            if( position < rows){

                category:            
                for (Category c : categories) {
                    aIndex = 0;
                    if (index++ == position) {
                        category = c;
                        break;
                    }
                    for (Article a : articles.get(c.getId())) {
                        if (index++ == position) {
                            article = a;
                            break category;
                        }
                        ++aIndex;
                    }
                    ++cIndex;
                }
            }else{
                cIndex = 0;
                aIndex = 0;
                if( position > rows){
                    index = 1;
                    Iterator<Article> iterator = deleted.iterator();
                    while(iterator.hasNext()){                                                
                        if( position == rows + index++){
                            article = iterator.next();
                            break;
                        }else{
                            iterator.next();
                        }                        
                    }
                }
            }
        }
    }
}
