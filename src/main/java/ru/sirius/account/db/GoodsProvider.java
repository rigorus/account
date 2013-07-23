package ru.sirius.account.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ru.sirius.account.model.entity.Category;
import ru.sirius.account.model.entity.Article;


public class GoodsProvider {
    
//    private static final Logger LOGGER = Logger.getLogger(GoodsProvider.class);
        
    public static List<Article> readArticles() throws SQLException{
                
        Connection connection = DbUtils.getConnection();

        ArrayList<Article> articles = new ArrayList<>();

        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM goods")) {

            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("goods_id"));
                article.setCategoryId(rs.getInt("category_id"));
                article.setDeleted(rs.getBoolean("is_deleted"));
                article.setName(rs.getString("full_name"));
                article.setShortName(rs.getString("short_name"));
                article.setDescription(rs.getString("description"));
                article.setSortNumber(rs.getInt("sort_number"));
                articles.add(article);
            }

            return articles;
        }        
    }
    
    
    public static List<Category> readCategories() throws SQLException {

        Connection connection = DbUtils.getConnection();

        ArrayList<Category> categories = new ArrayList<>();

        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM category")) {

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                category.setParentId(rs.getInt("parent_id"));
                category.setName(rs.getString("full_name"));
                category.setSortNumber(rs.getInt("sort_number"));
                categories.add(category);
            }       
            return categories;
        }

    }
    
    
}
