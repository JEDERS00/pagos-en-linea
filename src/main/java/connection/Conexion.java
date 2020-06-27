package connection;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import utils.Utils;

/**
 *
 * @author JEDERS
 */
public class Conexion {

    private boolean validClassForName = true;
    private String CLASS_FOR_NAME;
    private String DATA_BASE_NAME;
    private String DATA_BASE_ADDRESS;
    private String DATA_BASE_PORT;
    private String DATA_BASE_URL_DRIVER;
    private String DATA_BASE_USER;
    private String DATA_BASE_PASSWORD;

    public Conexion() {
        setConfigProperties();
        try {
            Class.forName(CLASS_FOR_NAME);
        } catch (ClassNotFoundException cnfe) {
            this.validClassForName = false;
            cnfe.printStackTrace();
        }
    }

    private void setConfigProperties() {
        if (this.validClassForName) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
            if (resourceBundle == null) {
                System.out.println("Se requiere archivo de configuraci\u00F3n.");
            } else {
                CLASS_FOR_NAME = resourceBundle.getString("classforName");
                DATA_BASE_NAME = resourceBundle.getString("dataBaseName");
                DATA_BASE_ADDRESS = resourceBundle.getString("dataBaseAddress");
                DATA_BASE_PORT = resourceBundle.getString("dataBasePort");
                DATA_BASE_URL_DRIVER = resourceBundle.getString("dataBaseURLDriver");
                DATA_BASE_USER = resourceBundle.getString("dataBaseUser");
                DATA_BASE_PASSWORD = resourceBundle.getString("dataBasePassword");
            }
        }
    }

    public Connection getConnection() throws RemoteException {
        if (!this.validClassForName) {
            throw new RemoteException("THERE IS NOT CONNECTION TO THE DATABASE!");
        }
        if (Utils.areEmpty(CLASS_FOR_NAME, DATA_BASE_NAME, DATA_BASE_ADDRESS,
                DATA_BASE_PORT, DATA_BASE_URL_DRIVER, DATA_BASE_USER, DATA_BASE_PASSWORD)) {
            throw new RemoteException("Se requiere informaci\u00F3n en configuraci\u00F3n de base de datos.");
        }
        try {
            StringBuilder urlConnection = new StringBuilder();
            urlConnection.append(DATA_BASE_URL_DRIVER).append("://").
                    append(DATA_BASE_ADDRESS).append(":").append(DATA_BASE_PORT)
                    .append("/").append(DATA_BASE_NAME);
            return DriverManager.getConnection(urlConnection.toString(), getPropertiesConfiguration());
        } catch (SQLException cnfe) {
            cnfe.printStackTrace();
            throw new RemoteException("Error trying to make the connection to the database.");
        }
    }

    private Properties getPropertiesConfiguration() {
        Properties properties = new Properties();
        properties.setProperty("user", DATA_BASE_USER);
        properties.setProperty("password", DATA_BASE_PASSWORD);
        properties.setProperty("ssl", "false");
        return properties;
    }

    public void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }

}
