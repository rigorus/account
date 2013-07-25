package ru.sirius.account.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import ru.sirius.account.model.entity.Category;
import ru.sirius.account.model.entity.Article;


public class GoodsProvider {
    
//    private static final Logger LOGGER = Logger.getLogger(GoodsProvider.class);
        
    public static ArrayList<Article> readCategoryArticles(int categoryId) throws SQLException{
                
        Connection connection = DbUtils.getConnection();

        ArrayList<Article> articles = new ArrayList<>();

        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(String.format(
                        "SELECT * FROM article WHERE category_id = %1$s ORDER BY weight" ,categoryId))) {

            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("article_id"));
                article.setCategoryId(rs.getInt("category_id"));
                article.setDeleted(rs.getBoolean("is_deleted"));
                article.setName(rs.getString("full_name"));
                article.setShortName(rs.getString("short_name"));
                article.setDescription(rs.getString("description"));
                article.setPrice(rs.getBigDecimal("price"));
                article.setWeight(rs.getInt("weight"));
                articles.add(article);
            }

            return articles;
        }        
    }
    
    public static ArrayList<Category> readCategories() throws SQLException {

        Connection connection = DbUtils.getConnection();

        ArrayList<Category> categories = new ArrayList<>();

        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM category ORDER by weight")) {

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                category.setName(rs.getString("category_name"));
                category.setWeight(rs.getInt("weight"));
                categories.add(category);
            }       
            return categories;
        }
    }
    
    public static void createCategory(Category category) throws SQLException{
        
        Connection connection = DbUtils.getConnection();
        category.setId(getNextValue());
        String sql = "INSERT INTO category(category_id, category_name, weight) VALUES(?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, category.getId());
            statement.setString(2, category.getName());
            statement.setInt(3, category.getWeight());
            statement.executeUpdate();
        }
    }
    
    public static void createArticle(Article article) throws SQLException {

        Connection connection = DbUtils.getConnection();
        article.setId(getNextValue());
        String sql = "INSERT INTO article(article_id, category_id, full_name, short_name, price, description, weight)"
                + " VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,article.getId());
            statement.setInt(2, article.getCategoryId());
            statement.setString(3, article.getName());
            statement.setString(4, article.getShortName());
            statement.setBigDecimal(5, article.getPrice());
            statement.setString(6, article.getDescription());
            statement.setInt(7,article.getWeight());
            statement.executeUpdate();
        }
    }
    
    public static int getNextValue() throws SQLException{
        Connection connection = DbUtils.getConnection();

        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT NEXTVAL('seq_id')")) {

            while (rs.next()) {
                return rs.getInt(1);
            }
        }
        
        throw new SQLException("Error in next sequence value");
    }
    
}
