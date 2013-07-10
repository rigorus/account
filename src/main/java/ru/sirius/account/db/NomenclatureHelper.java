package ru.sirius.account.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.sirius.account.model.Article;
import ru.sirius.account.model.Group;

public class NomenclatureHelper {

    private static final Map<Integer, Article> ARTICLES;
    private static final Map<Integer, Group> GROUPS;

    static {

        ARTICLES = new HashMap<>();
        GROUPS = new HashMap<>();

        Connection connection = DbUtils.getConnection();
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
                ARTICLES.put(article.getId(), article);
            }

        } catch (SQLException ex) {
            Logger.getLogger(NomenclatureHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM nomenclature.classification")) {

            while (rs.next()) {
                Group classification = new Group();
                int classificationId = rs.getInt("classification_id");
                classification.setId(classificationId);
                classification.setParentId(rs.getInt("parent_id"));
                classification.setName(rs.getString("classification_name"));
                classification.setComment(rs.getString("comment"));
                List<Integer> articles = new ArrayList<>();
                for (Article article : ARTICLES.values()) {
                    if (article.getClassificationId() == classificationId) {
                        articles.add(article.getId());
                    }
                }
             //   classification.setArticles(articles);
                GROUPS.put(classificationId, classification);
            }

        } catch (SQLException ex) {
            Logger.getLogger(NomenclatureHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Map<Integer, Article> getARTICLES() {
        return ARTICLES;
    }

    public static Map<Integer, Group> getGROUPS() {
        return GROUPS;
    }
 
    public static void main(String[] args) throws SQLException {

        fillArticleClassification(1, 40, 2);
        fillArticleClassification(41, 44, 3);
        fillArticleClassification(45, 48, 4);
        fillArticleClassification(49, 52, 5);
        fillArticleClassification(53, 83, 7);

        DbUtils.releaseConnection();
    }

    public static void fillArticleClassification(int articleFirst, int articleLast, int classification) throws SQLException {

        final String SQL = "INSERT INTO nomenclature.acticle_classification(article_id, classification_id) VALUES(?,?)";

        Connection connection = DbUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL);

        for (int i = articleFirst; i <= articleLast; ++i) {
            statement.setInt(1, i);
            statement.setInt(2, classification);
            statement.addBatch();
        }

        statement.executeBatch();

    }
}
