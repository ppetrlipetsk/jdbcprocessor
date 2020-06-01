package examples;

import com.ppsdevelopment.jdbcprocessor.DataBaseConnector;
import com.ppsdevelopment.jdbcprocessor.DataBaseProcessor;
import com.ppsdevelopment.jdbcprocessor.QueryPreparer;
import java.net.ConnectException;
import java.sql.*;


public class DataBaseProcessorUses {

    static{
        String instanceName = "localhost\\MSSQLSERVER";
        String databaseName = "dogc";
        String userName = "sa";
        String password = "win";
        String connectionUrl = "jdbc:sqlserver://%1$s;databaseName=%2$s;integratedSecurity=true";
        try {
            DataBaseConnector.connectDataBaseIntSecurity(connectionUrl,instanceName,databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public static boolean exec() {
        boolean val = false;
        DataBaseProcessor dbProcessor=new DataBaseProcessor(DataBaseConnector.getConnection());
        String q = "INSERT INTO [dbo].[extable]" +
                "           ([fname],[fcount])" +
                "     VALUES ('123',123)";
        try {
            val = dbProcessor.exec(q);
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }


    // Метод execResultedQuery() выполняет запрос в БД, типа exec, и возвращает результат в экземпляре типа Statement.
    public static boolean execResulted() {
        boolean val = false;
        Statement queryResult=null;
        DataBaseProcessor dbProcessor=new DataBaseProcessor(DataBaseConnector.getConnection());
        String q = "INSERT INTO [dbo].[extable]" +
                "           ([fname],[fcount])" +
                "     VALUES ('123',123)";
        try {
            queryResult = dbProcessor.execResulted(q);
            if (queryResult!=null) {
                // Делаем тут то, что нужно с queryResult
                val=true;
            }
        } catch (ConnectException | SQLException e) {
            e.printStackTrace();
        }

        return val;
    }

    // Работа с результатом выполнения запроса
    public static boolean testExecResultedQuery() {
        Statement statement = null;
        DataBaseProcessor h=new DataBaseProcessor(DataBaseConnector.getConnection());
        String q = "SELECT * FROM [dbo].[extable]";
        try {
            statement = h.execResulted(q);
            if (statement.getResultSet() != null) System.out.println("The query result have a ResultSet");
            else
                System.out.println("The query result have not ResultSet");
            statement.close();
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement != null;
    }



    public static boolean query() {
        boolean result = false;
        DataBaseProcessor h = new DataBaseProcessor(DataBaseConnector.getConnection());
        String q = "SELECT * FROM [dbo].[extable]";
        try {
            ResultSet r = h.query(q);
            if (r != null) result = true;
            else
                System.out.println("The query result have not ResultSet");
            h.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    // Демонстрация того, как можно использовать метод public ResultSet query(String expression, Statement statement) throws SQLException
    // для того, чтобы задавать параметры при создании экземпляра Statement
    // В даннои примере, делается запрос для получения набора данных, с возможностью двунаправленного перемещения курсора.
    public static boolean scrolledResultQuery() {
        boolean result = false;
        DataBaseProcessor h = new DataBaseProcessor(DataBaseConnector.getConnection());
        String q = "SELECT * FROM [dbo].[extable]";
        try {
            Statement statement = h.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet r = h.query(q,statement);
            // Пробуем, можем ли перемещать курсор в обоих направлениях
            r.last();
            r.beforeFirst();
            h.close();
            result=true;
        } catch (Exception e) { //SQLException обработается этим перехватом
            e.printStackTrace();
        }

        return result;
    }

    // Демонстрация того, как можно использовать метод:
    // public  void query(String expression, ResultSetCallBack callback) throws SQLException

    public static boolean queryResultSetCallBack() {
        boolean result = false;
        DataBaseProcessor h = new DataBaseProcessor(DataBaseConnector.getConnection());
        String q = "SELECT * FROM [dbo].[extable]";

        DataBaseProcessor.ResultSetCallBack c = new DataBaseProcessor.ResultSetCallBack() {
            @Override
            public void call(ResultSet resultSet) throws SQLException {
                if ((resultSet != null)) {
                    while (resultSet.next()) {
                        String s = resultSet.getString("fname");
                        System.out.println("fname=" + s);
                    }
                }
            }
        };

        try {
            h.query(q, c);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Демонстрация того, как можно использовать метод:
    // public  void query(String expr, ResultSetCallBack callback, Statement statement) throws SQLException {

    public static boolean query_String_ResultSetCallBack_Statement() {
        boolean result = false;
        DataBaseProcessor h = new DataBaseProcessor(DataBaseConnector.getConnection());
        String q = "SELECT * FROM [dbo].[extable]";

        DataBaseProcessor.ResultSetCallBack c = new DataBaseProcessor.ResultSetCallBack() {
            @Override
            public void call(ResultSet resultSet) throws SQLException {
                if ((resultSet != null)) {
                    while (resultSet.next()) {
                        String s = resultSet.getString("fname");
                        System.out.println("fname=" + s);
                    }
                }
                //Перемещаем курсок в начало...
                resultSet.beforeFirst();
            }
        };

        try {
            Statement statement=h.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            h.query(q, c, statement);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Пример, демонстрирует перебор записей набора данных с помощью метода
    // public  void iterateQuery(ResultSet resultSet, ResultSetCallBack callback) throws SQLException {
    public static boolean iterateQuery_ResultSet_ResultSetCallBack() {
        boolean result = false;
        DataBaseProcessor h = new DataBaseProcessor(DataBaseConnector.getConnection());
        String q = "SELECT * FROM [dbo].[extable]";

        DataBaseProcessor.ResultSetCallBack c = new DataBaseProcessor.ResultSetCallBack() {
            @Override
            public void call(ResultSet resultSet) throws SQLException {
                if ((resultSet != null)) {
                        String s = resultSet.getString("fname");
                        System.out.println("fname=" + s);
                    }
            }
        };

        try {
            ResultSet r=h.query(q);
            h.iterateQuery(r,c);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Пример, демонстрирует перебор записей набора данных с помощью метода
    // public  void iterateQuery(String query, ResultSetCallBack callback) throws SQLException {
    public static boolean iterateQuery_String_ResultSetCallBack() {
        boolean result = false;
        DataBaseProcessor h = new DataBaseProcessor(DataBaseConnector.getConnection());
        String q = "SELECT * FROM [dbo].[extable]";

        DataBaseProcessor.ResultSetCallBack c = new DataBaseProcessor.ResultSetCallBack() {
            @Override
            public void call(ResultSet resultSet) throws SQLException {
                if ((resultSet != null)) {
                    String s = resultSet.getString("fname");
                    System.out.println("fname=" + s);
                }
            }
        };

        try {
            h.iterateQuery(q,c);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }




        public static boolean getRowCount() {
        boolean result = false;
        DataBaseProcessor h = new DataBaseProcessor(DataBaseConnector.getConnection());
        String q = "SELECT * FROM [dbo].[extable]";
        try {
            ResultSet r = h.query(q,h.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            if (r != null) {
                long c = h.getRowCount(r);
                if (c > -1) {
                    result = true;
                    System.out.println("RowCount=" + c);
                }
            } else
                System.out.println("The query result have not ResultSet");
            h.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public static boolean getRowCountNotScrollable() {
        // После того, как курсор окажется на последней строке, его уже нельзя переместить на другую позицию.
        // Поэтому, этот набор данных, становится нечитаемым.
        // Чтобы
        boolean result = false;
        DataBaseProcessor h = new DataBaseProcessor(DataBaseConnector.getConnection());

        String q = "SELECT * FROM [dbo].[extable]";
        try {
            ResultSet r = h.query(q);
            if (r != null) {
                long c = h.getRowCountNoScrollable(r);
                if (c > -1) {
                    result = true;
                    System.out.println("RowCount=" + c);
                }
            } else
                System.out.println("The query result have not ResultSet");
            h.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean insertQuery_String_String() {
        boolean result = false;
        String q = "INSERT INTO [dbo].[extable] ([fname],[fcount]) VALUES ('123',123)";

        try (DataBaseProcessor h = new DataBaseProcessor(DataBaseConnector.getConnection())) {
            long indx = -1;
            indx = h.insertQuery(q, "id");
            if (indx > -1) {
                System.out.println("RowInserted index=" + indx);
                result=true;
            } else
                System.out.println("The query result have not ResultSet");

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("insertQuery pass:" + String.valueOf(result));
        return result;
    }


    public static boolean insertPreparedQueryQuery() {
        System.out.println("insertPreparedQueryQuery test:");
        boolean result = false;
        DataBaseProcessor h = new DataBaseProcessor(DataBaseConnector.getConnection());
        String q = "INSERT INTO [dbo].[extable] ([fname],[fcount]) VALUES (?,?)";

        QueryPreparer qp= new QueryPreparer() {
            @Override
            public boolean prepareStatement(PreparedStatement statement) throws SQLException {
                String fname="123";
                int fcount=123;
                statement.setString(1, fname);
                statement.setInt(2, fcount);
                return true;
            }
        };

        try {
            long indx = -1;
            indx = h.insertPreparedQuery(q, qp,"id");
            if (indx > -1) {
                System.out.println("RowInserted index=" + indx);
                result=true;
            } else
                System.out.println("The query result have not ResultSet");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("insertQuery pass:" + String.valueOf(result));
        return result;
    }

    public static boolean close() {
        boolean result=true;
        DataBaseProcessor h = new DataBaseProcessor(DataBaseConnector.getConnection());
        String q = "SELECT * FROM [dbo].[extable]";
        try {
            ResultSet r = h.query(q);
            h.close();
        } catch (SQLException e) {
            e.printStackTrace();
            result=false;
        } catch (Exception e) {
            e.printStackTrace();
            result=false;
        }

        return result;
    }

}

