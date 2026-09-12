package hr.algebra.dal.sql;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import hr.algebra.utilities.DataAccessException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

public class DataSourceSingleton {

    private static final String SERVER_NAME = "SERVER_NAME";
    private static final String DATABASE_NAME = "DATABASE_NAME";
    private static final String USER = "USER";
    private static final String PASSWORD = "PASSWORD";

    private static DataSource instance;
    private Connection connection;

    private static final Properties PROPERTIES = new Properties();
    private static final String PATH = "/config/db.properties";
    static{
        try(InputStream is = DataSourceSingleton.class.getResourceAsStream(PATH)){
            PROPERTIES.load(is);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        System.out.print(PROPERTIES);
    }

    public static DataSource getInstance() throws DataAccessException {
        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }
    
    private static DataSource createInstance(){
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setServerName(PROPERTIES.getProperty(SERVER_NAME));
        dataSource.setDatabaseName(PROPERTIES.getProperty(DATABASE_NAME));
        dataSource.setUser(PROPERTIES.getProperty(USER));
        dataSource.setPassword(PROPERTIES.getProperty(PASSWORD));
        dataSource.setEncrypt(true);
        dataSource.setTrustServerCertificate(true);
        return dataSource;
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
