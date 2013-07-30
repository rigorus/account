package ru.sirius.account.ui.goods;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import ru.sirius.account.db.GoodsService;
import ru.sirius.account.model.entity.Article;
import ru.sirius.account.model.entity.Category;
import ru.sirius.account.ui.utils.multispan.AttributiveCellTableModel;
import ru.sirius.account.ui.utils.multispan.DefaultCellAttribute;

public class RBGoodsController {

    public enum ROWTYPE {CATEGORY, ARTICLE};
    
    private AttributiveCellTableModel model;
    private DefaultCellAttribute attribute; 

    private ArrayList<Row> rows = new ArrayList<>();
    private ArrayList<Article> deleted; 

    public RBGoodsController(AttributiveCellTableModel model) throws SQLException{
        
        this.model = model;
        
        this.model.addColumn("Наименование");
        this.model.addColumn("Отпускная цена");
        this.model.addColumn("");
        
        this.attribute = (DefaultCellAttribute) model.getCellAttribute();
        
    }    
       
    public void initialize() throws SQLException{
        for (Category category : GoodsService.readCategories()) {
            insertRow(rows.size(), new Row(ROWTYPE.CATEGORY, category, null));
            for (Article article : GoodsService.readCategoryArticles(category.getId())) {
                insertRow(rows.size(), new Row(ROWTYPE.ARTICLE, category, article));
            }
        }
        deleted = GoodsService.readDeletedArticles();   
    }    
    
    private void addDeleted(Article article){
        
        deleted.add(article);
        Collections.sort(deleted, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }    
    //TODO вынести создание строк артикула и категории в отдельную функцию !!!!
    public void showDeleted(boolean show){
        if( show ){
            
            model.addRow(new Object[]{"Удалённые артикулы", "", ""});
            int position = model.getRowCount() - 1;
            attribute.combineRow(position);
            attribute.attributiveRow(position, Color.GRAY, Color.BLACK, new Font(Font.SANS_SERIF, Font.ITALIC, 12));
            for( Article article : deleted){
                model.addRow(new Object[]{article.getName(), article.getPrice(), "100"});
                attribute.setBackground(Color.LIGHT_GRAY, new int[]{model.getRowCount() - 1}, new int[]{0,1,2});                
            }        
        }else{
            for(int i=0; i < deleted.size() + 1; ++i){
                model.removeRow(rows.size());
            }
        }        
    }
    
    public Article getDeletedArticle(int position){
        int index = position - rows.size() - 1;
        return deleted.get(index);
    }

    public void restoreArticle(Article article) throws SQLException {
                
        Row previous = getLastInCategory(article.getCategoryId());
        article.setWeight(previous.getWeight() + 1);
        
        GoodsService.restoreArticle(article);
        
        model.removeRow(rows.size() + 1 + deleted.indexOf(article));
        deleted.remove(article);
        
        Row row = new Row(ROWTYPE.ARTICLE, previous.category, article);
        insertRow(rows.indexOf(previous) + 1, row);        
    }
       
    public void createCategory(Category category) throws SQLException{
        category.setWeight((getCategoryCount() + 1) * GoodsService.CATEGORY_STEP);

        GoodsService.createCategory(category);
        
        Row row = new Row(ROWTYPE.CATEGORY, category, null);
        insertRow(rows.size(), row);
    }
    
    public void createArticle(Article article) throws SQLException {
        Row previous = getLastInCategory(article.getCategoryId());
        article.setWeight(previous.getWeight() + 1);

        GoodsService.createArticle(article);

        Row row = new Row(ROWTYPE.ARTICLE, previous.category, article);       
        insertRow(rows.indexOf(previous) + 1, row);
    }
    
    public void updateRow(Row row) throws SQLException{
        switch (row.rowtype) {
            case CATEGORY:
                GoodsService.updateCategory(row.category);
                model.setValueAt(row.category.getName(), rows.indexOf(row), 0);
                break;
            case ARTICLE:
                GoodsService.updateArticle(row.article);
                model.setValueAt(row.article.getName(), rows.indexOf(row), 0);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип строки");
        }
    }
    
    public void removeRow(Row row, boolean showDeleted) throws SQLException{
        
        if (showDeleted ) showDeleted(false);

        int position = rows.indexOf(row);
        
        switch (row.rowtype) {
            case CATEGORY:
                GoodsService.deleteCategory(row.category);    
                deleteCategory(row);
                rows.remove(position);
                model.removeRow(position);
                int last = rows.indexOf(getNextCategory(row));
                for( int i = position; i < last; ++i){
                    addDeleted(rows.get(position).article);                
                    rows.remove(position);
                    model.removeRow(position);
                }
                break;
            case ARTICLE:
                GoodsService.deleteArticule(row.article);
                deleteArticle(row.article);              
                for( int i = position + 1; i <= rows.indexOf(getLastInCategory(row.category.getId())); ++i) {
                    rows.get(i).decrementWeight(1);
                }
                addDeleted(row.article);
                rows.remove(position);
                model.removeRow(position);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип строки");
        }
        
        if (showDeleted) showDeleted(true);
    }
    
    public void deleteCategory(Row  row){
        int position = rows.indexOf(row) + 1;
        for (int index = position; index < rows.size(); ++index) {
            if (rows.get(index).category.getId() == row.category.getId()) {
                rows.get(index).setWeight(Integer.MAX_VALUE);
            }else{
                rows.get(index).decrementWeight(GoodsService.CATEGORY_STEP);
            }
        }
    }
    
    public void deleteArticle(Article article) {
        article.setWeight(Integer.MAX_VALUE);
        article.setCategoryId(0);
    }
        
    public void moveRowUp(Row row) throws SQLException{        
        Row previous;
        int position = rows.indexOf(row);
        switch(row.rowtype){
            case CATEGORY:
                previous = getPreviousCategory(row);
                if( previous != null){
                    GoodsService.replaceCategory(row.category, previous.category);
                    replaceCategory(row.category, previous.category);
                
                    int end = rows.indexOf(getLastInCategory(row.category.getId()));
                    int to = rows.indexOf(previous);
                    for (int i = 0; i < end - position + 1; ++i) {
                        row = rows.remove(end);
                        rows.add(to, row);
                    }
                    model.moveRow(position, end, to);
                }
                break;
            case ARTICLE:
                previous = rows.get(position - 1);
                if( previous.rowtype == ROWTYPE.ARTICLE){
                    GoodsService.replaceArticle(row.article, previous.article);
                    replaceArticle(row.article, previous.article);
                    rows.set(position - 1, row);
                    rows.set(position,  previous);
                    model.moveRow(position, position, position - 1);
                }
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип строки");
        }
    }
    
    public void moveRowDown(Row row) throws SQLException {
        Row next;
        int position = rows.indexOf(row);
        switch (row.rowtype) {
            case CATEGORY:
                next = getNextCategory(row);
                if (next != null) {
                    GoodsService.replaceCategory(row.category, next.category);
                    replaceCategory(row.category, next.category);
                    
                    int start = rows.indexOf(next);
                    int end = rows.indexOf(getLastInCategory(next.category.getId()));
                    for (int i = 0; i < end - start + 1; ++i) {
                        row = rows.remove(end);
                        rows.add(position, row);
                    }
                    model.moveRow(start, end, position);
                }
                break;
            case ARTICLE:
                if( position < rows.size() - 1 && rows.get(position + 1).rowtype == ROWTYPE.ARTICLE){
                    next = rows.get(position + 1);
                    GoodsService.replaceArticle(row.article, next.article);
                    replaceArticle(row.article, next.article);
                    rows.set(position + 1, row);
                    rows.set(position, next);
                    model.moveRow(position + 1, position + 1, position);
                }
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип строки");
        }
    }
    
    private void replaceCategory(Category a, Category b){
        int incremnt = b.getWeight() - a.getWeight();
        for (Row r : rows) {
            if (a.getId() == r.category.getId()) {
                r.incrementWeight(incremnt);
            } else if (b.getId() == r.category.getId()) {
                r.incrementWeight(-incremnt);
            }
        }
    }
    
    private void replaceArticle(Article a, Article b){
        int weight = a.getWeight();
        a.setWeight(b.getWeight());
        b.setWeight(weight);
    }
     
    public Row getRow(int position){
        if (position < 0 || position >= rows.size()) {
            return null;
        }
        return rows.get(position);  
    }
    
    private void insertRow(int position, Row row){
        switch(row.rowtype){
            case CATEGORY:                
                model.insertRow(position, new Object[]{row.category.getName(), "", ""});                
                attribute.combineRow(position);
                attribute.attributiveRow(position, Color.YELLOW, Color.BLUE, new Font(Font.SANS_SERIF, Font.ITALIC, 12));
                break;
            case ARTICLE:                
                model.insertRow(position, new Object[]{row.article.getName(), row.article.getPrice(), "100"});
                break;                
            default:
                throw new IllegalArgumentException("Неизвестный тип строки");
        }
        
        if( position == rows.size()){
            rows.add(row);
        }else{
            rows.add(position, row);
        }
    }
    
    private int getCategoryCount(){
        int count = 0;
        for(Row row : rows){
            if( row.rowtype == ROWTYPE.CATEGORY){
                ++count;
            }
        }
        return count;
    }
    
    private Row getLastInCategory(int id){
        
        for( int i = rows.size() - 1; i > -1; --i ){
            Row row = rows.get(i);
            if( row.category.getId() == id){
                return row;
            }
        }
        
        return null;
    }
    
    private Row getPreviousCategory(Row current){
        int index = rows.indexOf(current);
        for( int i = index - 1; i > -1; --i){
            Row row = rows.get(i);
            if( row.rowtype == ROWTYPE.CATEGORY){
                return row;
            }
        }        
        return null;
    }
    
    private Row getNextCategory(Row current) {
        int index = rows.indexOf(current);
        for (int i = index + 1; i < rows.size(); ++i) {
            Row row = rows.get(i);
            if (row.rowtype == ROWTYPE.CATEGORY) {
                return row;
            }
        }
        return null;
    }
           
    public class Row{        
        public ROWTYPE rowtype;
        public Category category;
        public Article article;      

        public Row(ROWTYPE rowtype, Category category, Article article) {
            this.rowtype = rowtype;
            this.category = category;
            this.article = article;
        }
        
        public int getWeight(){
            return rowtype == ROWTYPE.ARTICLE ? article.getWeight() : category.getWeight();
        }
        
        public void setWeight(int weight) {
            if( rowtype == ROWTYPE.ARTICLE ){
                article.setWeight(weight);
            }else{
               category.setWeight(weight);
            }
        }        
        
        public void decrementWeight(int decrement){
            if (rowtype == ROWTYPE.ARTICLE) {
                article.setWeight(article.getWeight() - decrement);
            } else {
                category.setWeight(category.getWeight() - decrement);
            } 
        }
        
        public void incrementWeight(int decrement) {
            if (rowtype == ROWTYPE.ARTICLE) {
                article.setWeight(article.getWeight() + decrement);
            } else {
                category.setWeight(category.getWeight() + decrement);
            }
        }
    }    
}
