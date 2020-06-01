package examples;

import com.ppsdevelopment.jdbcprocessor.DataBaseConnector;

import java.sql.Connection;
import java.sql.SQLException;


public class DataBaseConnectorUsage {

    public static Connection connectDataBase() {
        // Можно без установки драйвера. Т.к. этот драйвер установлен по умолчанию.
        // Если будет драйвер другой, то следует воспользоваться этим методом.
        // setDriverName("\"com.microsoft.sqlserver.jdbc.SQLServerDriver\"");

        String instanceName = "localhost\\MSSQLSERVER";
        String databaseName = "dogc";
        String userName = "sa";
        String password = "win";
        String connectionUrl = "jdbc:sqlserver://%1$s;databaseName=%2$s;integratedSecurity=true";
        Connection c = null;
        try {
            c = DataBaseConnector.connectDataBase(connectionUrl, userName, password, instanceName, databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }

    public static Connection connectDataBaseIntSecurity() {

            String instanceName = "localhost\\MSSQLSERVER";
            String databaseName = "dogc";
            String connectionUrl = "jdbc:sqlserver://%1$s;databaseName=%2$s;integratedSecurity=true";
            Connection c = null;
            try {
                c = DataBaseConnector.connectDataBaseIntSecurity(connectionUrl, instanceName, databaseName);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return c;
        }

    public static Connection connectDataBaseWithDrv() {
        //Этот драйвер стоит по умолчанию, поэтому
        DataBaseConnector.setDriverName("\"com.microsoft.sqlserver.jdbc.SQLServerDriver\"");

        String instanceName = "localhost\\MSSQLSERVER";
        String databaseName = "dogc";
        String userName = "sa";
        String password = "win";
        String connectionUrl = "jdbc:sqlserver://%1$s;databaseName=%2$s;integratedSecurity=true";
        Connection c = null;
        try {
            c = DataBaseConnector.connectDataBase(connectionUrl, userName, password, instanceName, databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }


    }

