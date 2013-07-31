package ru.sirius.account.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import ru.sirius.account.utils.Config;


public class DbUtils {

    private static final Logger logger = Logger.getLogger(DbUtils.class);
    private static Connection connection;
    
    static{
        try {       
            Class.forName(Config.getDbDriverName());
            connection = DriverManager.getConnection(
                    Config.getDbUrl(),  
                    Config.getDbUserLogin(), 
                    Config.getDbUserPassword());
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException ex) {
            logger.fatal(ex);           
        }
    }
       
    public static Connection getConnection(){
        return connection;
    }
    
    public static void releaseConnection(){
        if( connection != null){
            try {
                connection.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
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
