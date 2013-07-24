package ru.sirius.account.ui.goods;

import java.awt.Color;
import java.sql.SQLException;
import ru.sirius.account.db.GoodsProvider;
import ru.sirius.account.model.entity.Article;
import ru.sirius.account.model.entity.Category;
import ru.sirius.account.ui.utils.multispan.AttributiveCellTableModel;
import ru.sirius.account.ui.utils.multispan.DefaultCellAttribute;



public class GoodsModelBuilder {
    
    private AttributiveCellTableModel model;
    
    public GoodsModelBuilder(AttributiveCellTableModel model){
        this.model = model;
        model.addColumn("");
        model.addColumn("Наименование");
        model.addColumn("Количество");
    }
    
    public void build() throws SQLException{
       for(Category category : GoodsProvider.readCategories() ){
           //рисуем категорию
           model.addRow(new Object[]{"", category.getName(),""});
           DefaultCellAttribute attribute = (DefaultCellAttribute) model.getCellAttribute();
           attribute.combine(new int[]{model.getRowCount() - 1}, new int[]{1,2});
           attribute.setBackground(Color.YELLOW, new int[]{model.getRowCount() - 1}, new int[]{0,1});
           attribute.setForeground(Color.BLUE, new int[]{model.getRowCount() - 1}, new int[]{0, 1});
           for( Article article : GoodsProvider.readCategoryArticles(category.getId())){
               model.addRow(new Object[]{article.getId(), article.getName(), "100"});
           }
       }       
    }    
}
