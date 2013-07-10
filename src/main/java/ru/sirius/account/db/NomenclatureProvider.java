/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.sirius.account.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import ru.sirius.account.model.Article;
import ru.sirius.account.model.Group;

// TODO продумать систему оповещения об ошибках + поведение при сбое !!!
public class NomenclatureProvider {
    
    private static final Logger LOGGER = Logger.getLogger(NomenclatureProvider.class);
    
    public static List<Article> getArticles(){
        
        Connection connection = DbUtils.getConnection();

        List<Article> articles = new ArrayList<>();
        
        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM nomenclature.article")) {

            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("article_id"));
                article.setClassificationId(rs.getInt("classification_id"));
                article.setFullName(rs.getString("full_name"));
                article.setShortName(rs.getString("short_name"));
                article.setDescription(rs.getString("description"));
                article.setComment(rs.getString("comment"));
                articles.add(article);
            }
            
            return articles;

        } catch (SQLException ex) {
            LOGGER.error(ex);
        }
        return null;
    }

    public static List<Group> getGroups(){
        
        Connection connection = DbUtils.getConnection();
        
        List<Group> groups = new ArrayList<>();                
        
        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM nomenclature.classification")) {

            while (rs.next()) {
                Group group = new Group();
                int classificationId = rs.getInt("classification_id");
                group.setId(classificationId);
                group.setParentId(rs.getInt("parent_id"));
                group.setName(rs.getString("classification_name"));
                group.setComment(rs.getString("comment"));
                groups.add(group);
            }
            
            return groups;
            
        } catch (SQLException ex) {
            LOGGER.error(ex);
        }
        return null;
    }
}
