package ru.sirius.account.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ru.sirius.account.model.entity.Category;
import ru.sirius.account.model.entity.Article;


public class GoodsProvider {
    
//    private static final Logger LOGGER = Logger.getLogger(GoodsProvider.class);
        
    public static ArrayList<Article> readCategoryArticles(int categoryId) throws SQLException{
                
        Connection connection = DbUtils.getConnection();

        ArrayList<Article> articles = new ArrayList<>();

        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(String.format(
                        "SELECT * FROM article WHERE category_id = %1$s ORDER BY sort_number" ,categoryId))) {

            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("article_id"));
                article.setCategoryId(rs.getInt("category_id"));
                article.setDeleted(rs.getBoolean("is_deleted"));
                article.setName(rs.getString("full_name"));
                article.setShortName(rs.getString("short_name"));
                article.setDescription(rs.getString("description"));
                article.setPrice(rs.getBigDecimal("price"));
                article.setSortNumber(rs.getInt("sort_number"));
                articles.add(article);
            }

            return articles;
        }        
    }
    
    public static ArrayList<Category> readCategories() throws SQLException {

        Connection connection = DbUtils.getConnection();

        ArrayList<Category> categories = new ArrayList<>();

        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM category ORDER by sort_number")) {

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                category.setName(rs.getString("category_name"));
                category.setSortNumber(rs.getInt("sort_number"));
                categories.add(category);
            }       
            return categories;
        }
    }
    
    public static void createCategory(Category category) throws SQLException{
        
        Connection connection = DbUtils.getConnection();
        String sql = "INSERT INTO category(category_name, sort_number) VALUES(?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, category.getName());
            statement.setInt(2, category.getSortNumber());
            statement.executeUpdate();
        }
    }
    
    public static void createArticle(Article article) throws SQLException {

        Connection connection = DbUtils.getConnection();
        String sql = "INSERT INTO article(category_id, full_name, short_name, price, description, sort_number)"
                + " VALUES(?,?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, article.getCategoryId());
            statement.setString(2, article.getName());
            statement.setString(3, article.getShortName());
            statement.setBigDecimal(4, article.getPrice());
            statement.setString(5, article.getDescription());
            statement.setInt(6,article.getSortNumber());
            statement.executeUpdate();
        }
    }
    
}
