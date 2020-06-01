package com.ppsdevelopment.jdbcprocessor;

import java.net.ConnectException;
import java.sql.*;

/**
 * This class provide methods to DataBase operations.
 *  @author PetrPaliy
 *  @since 1.0
 */
public class DataBaseProcessor implements AutoCloseable {
    private Statement statement;
    private Connection connection;

    public DataBaseProcessor(Statement statement, Connection connection) {
        this.statement = statement;
        this.connection = connection;
    }

    public DataBaseProcessor(Connection connection) {
        this.connection = connection;
    }

    public DataBaseProcessor() {
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Returns static instance of the class Statement for the executed request.
     * @return
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * Sets static instance of the class Statement for the request.
     * @param statement
     */
    public void setStatement(Statement statement) {
        this.statement = statement;
    }


    /**
     * Execute query for database and returns a query result as dataset.
     * @param expression - sql expression.
     * @return true if success, else false.
     * @throws SQLException - if sql error is except.
     */
    public boolean exec(String expression) throws SQLException, ConnectException {
        Statement statement=null;
        if (connection != null) {
            statement = connection.createStatement();
            statement.execute(expression);
            statement.close();
        } else
            throw new ConnectException();
        return statement!=null;
    }

    /**
     * Execute query for database and returns a query result as dataset.
     * Need to call close() method after after it will not be needed.
     * @param expression - sql expression.
     * @return Statement instance for a query result.
     * @throws SQLException - if sql error is except.
     */
    public Statement execResulted(String expression) throws SQLException, ConnectException {
        if (connection != null) {
            statement = connection.createStatement();
            statement.execute(expression);
        } else
            throw new ConnectException();
        return statement;
    }

    /**
     * Выполняет запрос в БД и возвращает результирующий набор записей
     * Оставляет открытым экземпляр класса Statement. После обработки результатов, необходимо вызвать метод close().
     * @param expression - выражение, например: select * from tablename
     * @return
     * @throws SQLException
     */
    public ResultSet query(String expression) throws SQLException {
        ResultSet resultSet = null;
        statement = connection.createStatement();
        resultSet= query(expression,statement);
        return resultSet;
    }
//
//    /**
//     * Выполняет запрос в БД и возвращает результирующий набор записей, с возможностью двунаправленного просмотра (перемещения курсора).
//     * Оставляет открытым экземпляр класса Statement. После обработки результатов, необходимо вызвать метод close().
//     * @param expression - выражение, например: select * from tablename
//     * @return
//     * @throws SQLException
//     */
//    public ResultSet scrolledResultQuery(String expression) throws SQLException {
//        ResultSet resultSet = null;
//        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//                ResultSet.CONCUR_READ_ONLY);
//        resultSet = statement.executeQuery(expression);
//        return resultSet;
//    }

    /**
     * Выполняет запрос в БД и возвращает результирующий набор записей, с возможностью указать экземпляр Statement, если требуется его конструктор, отличный от
     * Statement(). Например, нужно создать Statement двунаправленного просмотра (перемещения курсора). Тогда следует передать:
     * connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
     * Оставляет открытым экземпляр класса Statement. После обработки результатов, необходимо вызвать метод close().
     * @param expression - выражение, например: select * from tablename
     * @return
     * @throws SQLException
     */
    public ResultSet query(String expression, Statement statement) throws SQLException {
        ResultSet resultSet = null;
        if (statement==null)
            statement = connection.createStatement();
        this.statement=statement;
        resultSet = this.statement.executeQuery(expression);
        return resultSet;
    }


    /**
     * Выполняет запрос в БД и возвращает результирующий набор записей
     * Для обработки результата выполнения запроса, используется обратный вызов метода класса call(ResultSet resultSet), реализующего интерфейс ResultSetCallBack;
     * @param expression - выражение, например: select * from tablename
     * @param callback- метод обратного вызова для обработки результатов.
     * @return набор записей
     * @throws SQLException
     */
    public  void query(String expression, ResultSetCallBack callback) throws SQLException {
        Statement statement = connection.createStatement();
        query(expression,callback,statement);
    }

    /**
     * Выполняет запрос в БД и возвращает результирующий набор записей, с возможностью указать экземпляр Statement, если требуется его конструктор, отличный от
     * Statement(). Например, нужно создать Statement двунаправленного просмотра (перемещения курсора). Тогда следует передать:
     * connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
     * Для обработки результата выполнения запроса, используется обратный вызов метода класса call(ResultSet resultSet), реализующего интерфейс ResultSetCallBack;
     * Отличие от iterateQuery с использованием ResultSetCallBack в том, что в данном методе происходит передача методу call интерфейса ResultSetCallBack,
     * не отдельной записи набора данных, а всего набора данных.
     * @param expr - выражение, например: select * from tablename
     * @param callback- метод обратного вызова для обработки результатов.
     * @return набор записей
     * @throws SQLException
     */
    public  void query(String expr, ResultSetCallBack callback, Statement statement) throws SQLException {
        if (statement==null)
            statement = connection.createStatement();
        this.statement=statement;
        ResultSet resultSet = statement.executeQuery(expr);
        if ((callback != null) && (resultSet!=null)){
            callback.call(resultSet);
        }
        resultSet.close();
        statement.close();
    }

    /**
     * Выполняет перебор записей в наборе данных
     * Для обработки результата выполнения запроса, используется обратный вызов метода класса call(ResultSet resultSet), реализующего интерфейс ResultSetCallBack;
     * @param callback- метод обратного вызова для обработки результатов.
     * @return набор записей
     * @throws SQLException
     */
    public  void iterateQuery(ResultSet resultSet, ResultSetCallBack callback) throws SQLException {
        // Перебирает всех записей набора данных и вызывает метод обработки записи
        if ((resultSet!=null)){
            while (resultSet.next()) {
                callback.call(resultSet);
            }
        }
    }

    /**
     * Выполняет запрос в БД и делает перебор всех записей набора данных
     * Для обработки каждой записи, вызывается метод call интерфеса ResultSetCallBack.
     * Отличие от query с использованием ResultSetCallBack в том, что в данном методе происходит последовательный перебор записей внутри этого метода и вызов
     * callback для каждой записи
     * @param callback- метод обратного вызова для обработки результатов.
     * @return набор записей
     * @throws SQLException
     */
    public  void iterateQuery(String query, ResultSetCallBack callback) throws SQLException {
        ResultSet resultSet= query(query);
        iterateQuery(resultSet,callback);
        close();
    }


    /**
     * Returns rows count for the Scrollable ResultSet instance.
     * @param resultSet - ResultSet instance
     * @return rows count
     * @throws SQLException
     */
    public long getRowCount(ResultSet resultSet) throws SQLException {
            resultSet.last();
            int rowCount =resultSet.getRow();
            resultSet.beforeFirst();
            return rowCount;
    }

    /**
     * Returns rows count for not Scrollable ResultSet instance.
     * This method scroll cursor to the last element.
     * @param resultSet - ResultSet instance
     * @return rows count
     * @throws SQLException
     */
    public  long getRowCountNoScrollable(ResultSet resultSet) throws SQLException {
        long rowCount=0;
        while (resultSet.next()) {
            ++rowCount;
        }
        return rowCount;
    }

    /**
     * Execute insert query and, if success, returns id of the inserted row.
     * @param statement PreparedStatement instance.
     * @param key table key.
     * @return id of the inserted row
     * @throws SQLException
     */
    private  long insertQuery(PreparedStatement statement, String key) throws SQLException {
        long id=-1;
        int affectedRows = statement.executeUpdate();
        if (affectedRows ==0){
            throw new SQLException("Row insertion failed, no rows affected.");
        }
        try (ResultSet rs = statement.getGeneratedKeys()) {
            if (rs.next()) {
                id=rs.getInt(1);
            }
        }
        statement.close();
        return id;
    }

    /**
     * Execute insert query and, if success, returns id of the inserted row.
     * @param query- sql insert query.
     * @return id of the inserted row
     * @throws SQLException
     */
    public  long insertQuery(String query, String key) throws SQLException {
        String[] returnId={"id"};
        PreparedStatement statement = connection.prepareStatement(query, returnId);
        return insertQuery(statement,key);
    }

    /**
     * Execute prepared insert query and, if success, returns id of the inserted row.
     * @param query- sql insert query.
     * @param preparer - QueryPreparer instance.
     * @return id of the inserted row
     * @throws SQLException
     */
    public  long insertPreparedQuery(String query, QueryPreparer preparer, String key) throws SQLException {
        String[] returnId={key};
        PreparedStatement statement = connection.prepareStatement(query, returnId);
        preparer.prepareStatement(statement);
        return insertQuery(statement,key);
    }



    @Override
    public void close() throws SQLException {
        if (statement!=null) {
                if ((statement.getResultSet()!=null)&&(!statement.getResultSet().isClosed())) statement.getResultSet().close();
                statement.close();
        }
    }


    /**
     *  A class that implements the {@code ppsdevelopment.ResultSetCallBack} interface defines the method {@code void call​​(ResultSet resultSet)}
     *  which implements the data set processing mechanism in the parameter resultSet.     *
      * After returning to the calling method, this object will be used to start the execution of the request.
      * @author PPS
      * @since 1.0
      */
    public interface ResultSetCallBack {
        void call(ResultSet resultSet) throws SQLException;
    }

    /**
     *  A class that implements the {@code ppsdevelopment.ResultSetCallBack} interface defines the method {@code void call​​(ResultSet resultSet)}
     *  which implements the data set processing mechanism in the parameter resultSet.     *
      * After returning to the calling method, this object will be used to start the execution of the request.
      * @author PPS
      * @since 1.0
      */
    public interface RowProcess {
        void call(ResultSet resultSet) throws SQLException;
    }

    // TODO getScalarValueFromQuery(String expression);
    // TODO getScalarValueFromExec(String expression);

}