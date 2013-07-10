package ru.sirius.account.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;




public class DbUtils {

    private static final Logger logger = Logger.getLogger(DbUtils.class);
    private static Connection connection;
    
    static{
        try {       
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:data/db0001", "dbadmin", "dbadmin");
            int i = 0;
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
        
}
