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
    
    public static final int CATEGORY_STEP = 100000;

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
    
    public static ArrayList<Article> readDeletedArticles() throws SQLException {
        Connection connection = DbUtils.getConnection();

        ArrayList<Article> articles = new ArrayList<>();

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

    public static void replaceCategory(Category a, Category b) throws SQLException {
        
        Connection connection = DbUtils.getConnection();
        
        String cSql = "UPDATE category SET weight = %1$d WHERE category_id = %2$d";
        String aSql = "UPDATE article SET weight = weight + (%1$d - %2$d) WHERE category_id = %3$d";
        
        try( Statement statement = connection.createStatement()){

            statement.executeUpdate(String.format(cSql, b.getWeight(), a.getId()));
            statement.executeUpdate(String.format(aSql, b.getWeight(), a.getWeight(), a.getId()));
            
            statement.executeUpdate(String.format(cSql, a.getWeight(), b.getId()));
            statement.executeUpdate(String.format(aSql, a.getWeight(), b.getWeight(), b.getId()));
            
            connection.commit();
            
        }catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }              
    }

    public static void replaceArticle(Article a, Article b) throws SQLException {

        Connection connection = DbUtils.getConnection();
        
        String sql = "UPDATE article SET weight = weight + (%1$d - %2$d) WHERE article_id = %3$d";
        
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(String.format(sql, b.getWeight(), a.getWeight(), a.getId()));

            statement.executeUpdate(String.format(sql, a.getWeight(), b.getWeight(), b.getId()));

            connection.commit();

        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }
    
    public static void deleteCategory(Category category) throws SQLException{
        
        Connection connection = DbUtils.getConnection();
        String sql;

        try(Statement statement = connection.createStatement()){
            
            sql = String.format("UPDATE article SET category_id = null, weight = %1$d WHERE category_id = %2$d", 
                    Integer.MAX_VALUE, category.getId());
            statement.executeUpdate(sql);
            
            sql = String.format("DELETE category WHERE category_id = %1$d", category.getId());
            statement.executeUpdate(sql);

            sql = String.format("UPDATE category SET weight = weight - %1$d WHERE weight > %2$d", 
                    CATEGORY_STEP, category.getWeight());
            statement.executeUpdate(sql);

            sql = String.format("UPDATE article SET weight = weight - %1$d WHERE weight > %2$d AND category_id IS NOT NULL",
                    CATEGORY_STEP, category.getWeight());
            statement.executeUpdate(sql);
            
            connection.commit();
            
        }catch(SQLException e){
            connection.rollback();
            throw e;
        }        
    }
    
    public static void deleteArticule(Article article) throws SQLException {
        
        Connection connection = DbUtils.getConnection();
        String sql;
        
        try (Statement statement = connection.createStatement()) {

            sql = String.format("UPDATE article SET category_id = null, weight = %1$d WHERE article_id = %2$d",
                    Integer.MAX_VALUE, article.getId());
            statement.executeUpdate(sql);
            
            sql = String.format("UPDATE article SET weight = weight - %1$d WHERE weight > %2$d AND category_id = %3$d",
                    1, article.getWeight(), article.getCategoryId());
            statement.executeUpdate(sql);
            
            connection.commit();
            
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public static void updateArticle(Article article) throws SQLException{
        Connection connection = DbUtils.getConnection();
        
        String sql = "UPDATE article SET category_id = ?, full_name = ?, short_name = ?, price = ?, description = ? WHERE article_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, article.getCategoryId());
            statement.setString(2, article.getName());
            statement.setString(3, article.getShortName());
            statement.setBigDecimal(4, article.getPrice());
            statement.setString(5, article.getDescription());
            statement.setInt(6, article.getId());
            statement.executeUpdate();
            
            connection.commit();

        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }
    
    public static void restoreArticle(Article article) throws SQLException{
        Connection connection = DbUtils.getConnection();

        String sql = "UPDATE article SET category_id = ?, weight = ? WHERE article_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, article.getCategoryId());
            statement.setInt(2, article.getWeight());
            statement.setInt(3, article.getId());
            statement.executeUpdate();

            connection.commit();

        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public static void updateCategory(Category category) throws SQLException{
        Connection connection = DbUtils.getConnection();

        String sql = "UPDATE category SET category_name = ? WHERE category_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, category.getName());
            statement.setInt(2, category.getId());
            statement.executeUpdate();
            
            connection.commit();

        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

        
    public static int getNextValue() throws SQLException {
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
