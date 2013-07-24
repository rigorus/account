package ru.sirius.account.utils;

import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;


//TODO значения по умолчанию, для рабочего режима
public class Config {
    
    private static final Logger logger = Logger.getLogger(Config.class);

    private static final Properties module = new Properties();
    static{
        try {
            module.load(Config.class.getResourceAsStream("/module.properties"));
        } catch (IOException ex) {
            logger.fatal("Загрузка конфигурации", ex);
        }
    }
    
    public static String getDbDriverName(){
        return module.getProperty("db.driver.name");
    }
    
    public static String getDbUrl(){
        return module.getProperty("db.url") + module.getProperty("home.dir") + "/" + 
        module.getProperty("data.dir.name") + "/" +  module.getProperty("db.name");
    }
    
    public static String getDbUserLogin(){
        return module.getProperty("db.user.login");
    }
    
    public static String getDbUserPassword(){
        return module.getProperty("db.user.password");
    }
    
}
