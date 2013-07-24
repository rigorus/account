package ru.sirius.account.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
