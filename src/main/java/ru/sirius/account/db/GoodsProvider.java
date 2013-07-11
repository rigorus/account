package ru.sirius.account.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import ru.sirius.account.model.entity.Goods;


public class GoodsProvider {
    
    private static final Logger LOGGER = Logger.getLogger(GoodsProvider.class);
    
    public static ArrayList<Goods> getGoods(){
        
        Connection connection = DbUtils.getConnection();

        ArrayList<Goods> goods = new ArrayList<>();
        
        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM goods ORDER BY depth_index")) {

            while (rs.next()) {
                Goods article = new Goods();
                article.setId(rs.getInt("goods_id"));
                article.setParentId(rs.getInt("parent_id"));
                article.setGroup(rs.getBoolean("is_group"));
                article.setDeleted(rs.getBoolean("is_deleted"));
                article.setName(rs.getString("full_name"));
                article.setShortName(rs.getString("short_name"));
                article.setBreadthIndex(rs.getInt("breadth_index"));
                article.setDepthIndex(rs.getInt("depth_index"));
                article.setDescription(rs.getString("description"));
                goods.add(article);
            }
            
            return goods;

        } catch (SQLException ex) {
            LOGGER.error(ex);
        }
        return null;
    }
}
