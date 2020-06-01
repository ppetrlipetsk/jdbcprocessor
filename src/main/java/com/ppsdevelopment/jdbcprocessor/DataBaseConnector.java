package com.ppsdevelopment.jdbcprocessor;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnector {
        private static final String MS_DRV_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        private static Connection connection;
        private static String driverName;

        static {
            driverName =MS_DRV_NAME;
        }

        /**
         * Returns the driverName
         * @return driverName
         */
        public static String getDriverName(){return driverName;}

        /**
         * Sets the driver name for the database connection.
         * For example: com.microsoft.sqlserver.jdbc.SQLServerDriver
         * @param driverName
         */
        public  static void setDriverName(String driverName){
            DataBaseConnector.driverName=driverName;
        }



        /**
         * Return DB connection value.
         *
         * @return Connection for DataBase.
         */
        public static Connection getConnection() {
            return DataBaseConnector.connection;
        }

        /**
         * Set connection value.
         * @param connection {@code Connection}
         */
        public static void setConnection(Connection connection) {
            DataBaseConnector.connection = connection;
        }


        /**
         * Initializes and opens a new DataBase connection.
         *
         * @param url          - database URL
         * @param username-    user name value
         * @param password     - password value
         * @param instanceName - DB instance
         * @param databaseName - database name
         * @return
         * @throws SQLException              if database connection process end with error
         * @throws ClassNotFoundException    if the class driver not found
         * @throws InvocationTargetException
         * @throws NoSuchMethodException
         * @throws InstantiationException
         * @throws IllegalAccessException
         */
        public static Connection connectDataBase(String url, String username, String password, String instanceName, String databaseName) throws SQLException, ClassNotFoundException{
            Object drv = initDriver();
            if (drv != null)
                connection = initConnection(url, username, password, instanceName, databaseName);
            else
                throw new ClassNotFoundException("Драйвер JDBC не найден!");
            return connection;
        }

        /**
         * Init DataBase connection in integrated security mode.
         *
         * @param url          - database URL
         * @param instanceName - DB instance
         * @param databaseName - database name
         * @return
         * @throws SQLException              if database connection process end with error
         * @throws ClassNotFoundException    if the class driver not found
         * @throws InvocationTargetException
         * @throws NoSuchMethodException
         * @throws InstantiationException
         * @throws IllegalAccessException
         */
        public static Connection connectDataBaseIntSecurity(String url, String instanceName, String databaseName) throws SQLException, ClassNotFoundException{
            connection = connectDataBase(url,"","",instanceName,databaseName);
            return connection;
        }

        private static Object initDriver() {
            Object res= null;
            try {
                res = Class.forName(driverName).getDeclaredConstructor().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return res;
        }

        protected static Connection initConnection(String url, String username, String password, String instanceName, String databaseName) throws SQLException {
            if ((connection!=null)&&(!connection.isClosed())) connection.close();
            String connectionString = String.format(url, instanceName, databaseName, username, password);
            connection = DriverManager.getConnection(connectionString);
            return connection;
        }

    }