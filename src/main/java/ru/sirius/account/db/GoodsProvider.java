package ru.sirius.account.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
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
        connection.commit();
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
        connection.commit();
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

    public static void replace(Category a, Category b) throws SQLException {
        Connection connection = DbUtils.getConnection();

        String updateCategory = "UPDATE category SET weight = ? WHERE category_id = ?";
        String updateArticles = "UPDER article SET weight = ? WHERE category_id = ?";
        try (PreparedStatement articles = connection.prepareStatement(updateArticles);
                PreparedStatement category = connection.prepareStatement(updateCategory)) {                
            
            int weight = a.getWeight();
            a.setWeight(b.getWeight());
            b.setWeight(weight);
                       
            category.setInt(1,a.getWeight()); 
            category.setInt(2,a.getId());            
            category.executeUpdate();
            articles.setInt(a.getId(), a.getWeight());
            articles.executeUpdate();
            
            category.setInt(1, b.getWeight());
            category.setInt(2, b.getId());
            category.executeUpdate();
            connection.commit();
            articles.setInt(b.getId(), b.getWeight());
            articles.executeUpdate();

            
        }catch(SQLException ex){
            connection.rollback();
            throw ex;
        }
        
    }

    public static void replace(Article a, Article b) throws SQLException {
        Connection connection = DbUtils.getConnection();

        String sql = "UPDATE article SET weight = ? WHERE article_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            int weight = a.getWeight();
            a.setWeight(b.getWeight());
            b.setWeight(weight);

            statement.setInt(1, a.getWeight());
            statement.setInt(2, a.getId());
            statement.executeUpdate();

            statement.setInt(1, b.getWeight());
            statement.setInt(2, b.getId());
            statement.executeUpdate();
            connection.commit();

        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }
    
    public static void deleteCategory(Category category) throws SQLException{
        Connection connection = DbUtils.getConnection();

        String sqlDelete = "DELETE category WHERE category_id = ?";
        String sqlUpdate = "UPDATE article SET category_id = null, weight = ? WHERE category_id = ?";
        try (PreparedStatement update = connection.prepareStatement(sqlUpdate);
                PreparedStatement delete = connection.prepareStatement(sqlDelete)) {

            update.setInt(1, Integer.MAX_VALUE);
            update.setInt(2, category.getId());
            update.executeUpdate();
            delete.setInt(1, category.getId());
            delete.executeUpdate();
            connection.commit();            
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } 
    }
    
    public static void deleteArticule(Article article) throws SQLException {
        Connection connection = DbUtils.getConnection();

        String sql = "UPDATE article SET category_id = null, weight = ? WHERE article_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            article.setWeight(Integer.MAX_VALUE);
            
            statement.setInt(1, article.getWeight());
            statement.setInt(2, article.getId());
            statement.executeUpdate();
            connection.commit();

        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public static TreeSet<Article> readDeletedArticles() throws SQLException{
        Connection connection = DbUtils.getConnection();

        TreeSet<Article> articles = new TreeSet<>(new Comparator<Article>(){
            @Override
            public int compare(Article o1, Article o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(String.format(
                "SELECT * FROM article WHERE category_id is null ORDER BY full_name"))) {

            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("article_id"));
                article.setName(rs.getString("full_name"));
                article.setShortName(rs.getString("short_name"));
                article.setDescription(rs.getString("description"));
                article.setPrice(rs.getBigDecimal("price"));
                articles.add(article);
            }

            return articles;
        }
    }

    public static void updateArticle(Article article) throws SQLException{
        Connection connection = DbUtils.getConnection();
        
        String sql = "UPDATE article SET category_id = ?, full_name = ?, short_name = ?, price = ?, description = ?, weight = ? WHERE article_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, article.getCategoryId());
            statement.setString(2, article.getName());
            statement.setString(3, article.getShortName());
            statement.setBigDecimal(4, article.getPrice());
            statement.setString(5, article.getDescription());
            statement.setInt(6, article.getWeight());
            statement.setInt(7, article.getId());
            statement.executeUpdate();
        }
        connection.commit();
    }

    public static void updateCategory(Category category) throws SQLException{
        Connection connection = DbUtils.getConnection();

        String sql = "UPDATE category SET category_name = ?, weight = ? WHERE category_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, category.getName());
            statement.setInt(2, category.getWeight());
            statement.setInt(3, category.getId());
            statement.executeUpdate();
        }
        connection.commit();
    }

    public static void downWeight(int weight) throws SQLException {
        Connection connection = DbUtils.getConnection();

        String categories = "UPDATE category SET weight = weight - 1000 WHERE weight > ?";
        String articles = "UPDATE article SET weight = weight - 1000 WHERE weight > ? AND category_id IS NOT NULL";
        try (PreparedStatement updateCategories = connection.prepareStatement(articles);
                PreparedStatement updateArticles = connection.prepareStatement(categories)) {

            updateCategories.setInt(1, weight);
            updateCategories.executeUpdate();
            updateArticles.setInt(1, weight);
            updateArticles.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }
    
}
