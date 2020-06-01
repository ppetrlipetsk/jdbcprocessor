package com.ppsdevelopment.jdbcprocessor;

import examples.DataBaseProcessorUses;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataBaseProcessorTest {

//    private static Connection connection;

    private void outMethodStr(String s){
        System.out.print("Test the method "+s+":");
    }
    private void outResult(String s){
        System.out.println(s);
    }
    private void outLineMethodStr(String s) {
        System.out.println("Test the method"+s);
    }

    @Test
    public void exec() {
        outMethodStr("boolean exec(String expression) throws SQLException, ConnectException");
        boolean actual=true;
        boolean val= DataBaseProcessorUses.exec();
        assertEquals(val,actual);
        System.out.println("OK");
    }

    @Test
    public void execResulted() {
        outMethodStr("public Statement execResulted(String expression) throws SQLException, ConnectException");
        boolean actual=true;
        boolean val= DataBaseProcessorUses.execResulted();
        assertEquals(val,actual);
        System.out.println("OK");
    }

    @Test
    public void query() {
        System.out.print("Test the method: public ResultSet queryResult(String expression) throws SQLException: ");
        outMethodStr("public ResultSet query(String expression) throws SQLException ");
        boolean actual=true;
        boolean val= DataBaseProcessorUses.query();
        assertEquals(val,actual);
        System.out.print("OK");
    }

    @Test
    public void query_String_Statement() {
        outMethodStr("public ResultSet query(String expression, Statement statement) throws SQLException:");
        boolean expected= DataBaseProcessorUses.scrolledResultQuery();
        boolean actual=true;
        assertEquals(expected,actual);
        System.out.print("OK");
    }

    @Test
    public void query_String_ResultSetCallBack() {
        outLineMethodStr("public  void query(String expression, DataBaseProcessor.ResultSetCallBack callback) throws SQLException");
        boolean expected= DataBaseProcessorUses.queryResultSetCallBack();
        boolean actual=true;
        assertEquals(expected,actual);
        System.out.print("OK");
    }

    @Test
    public void query_String_ResultSetCallBack_Statement() {
        outLineMethodStr("public  void query(String expr, ResultSetCallBack callback, Statement statement) throws SQLException");
        boolean expected=DataBaseProcessorUses.query_String_ResultSetCallBack_Statement();
        boolean actual=true;
        assertEquals(expected,actual);
        System.out.print("OK");
    }

    @Test
    public void iterateQuery_ResultSet_ResultSetCallBack() {
        outLineMethodStr("public  void iterateQuery(ResultSet resultSet, ResultSetCallBack callback) throws SQLException");
        boolean expected=DataBaseProcessorUses.iterateQuery_ResultSet_ResultSetCallBack();
        boolean actual=true;
        assertEquals(expected,actual);
        System.out.print("OK");
    }

    @Test
    public void iterateQuery_String_ResultSetCallBack() {
        outLineMethodStr("public  void iterateQuery(String query, ResultSetCallBack callback) throws SQLException");
        boolean expected=DataBaseProcessorUses.iterateQuery_String_ResultSetCallBack();
        boolean actual=true;
        assertEquals(expected,actual);
        System.out.print("OK");

    }

    @Test
    public void getRowCount() {
        System.out.println("Test getRowCount:");
        boolean actual=true;
        boolean val= DataBaseProcessorUses.getRowCount();
        assertEquals(val,actual);
    }

    @Test
    public void getRowCountNoScrollable() {
        System.out.println("Test getRowCountNoScrollable:");
        boolean actual=true;
        boolean val= DataBaseProcessorUses.getRowCountNotScrollable();
        assertEquals(val,actual);
    }

    @Test
    public void insertQuery_String_String() {
        outMethodStr("private  long insertQuery(String query, String key) throws SQLException");
        boolean actual=true;
        boolean val= DataBaseProcessorUses.insertQuery_String_String();
        assertEquals(val,actual);
    }


    @Test
    public void insertPreparedQuery() {
        System.out.println("Test insertPreparedQuery:");
        boolean actual=true;
        boolean val= DataBaseProcessorUses.insertPreparedQueryQuery();
        assertEquals(val,actual);
    }

    @Test
    public void close() {
        System.out.println("Test close:");
        boolean actual=true;
        boolean val= DataBaseProcessorUses.close();
        assertEquals(val,actual);
    }


    @Test
    public void testExecResultedQuery() {
        System.out.println("Test testExecResultedQueryShort:");
        boolean expected= DataBaseProcessorUses.testExecResultedQuery();
        boolean actual=true;
        assertEquals(expected,actual);
    }

}