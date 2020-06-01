package com.ppsdevelopment.jdbcprocessor;

import examples.DataBaseConnectorUsage;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class DataBaseConnectorTest {

    @Test
    public void connectDataBase() {
        Connection c= DataBaseConnectorUsage.connectDataBase();
        assertEquals((c!=null),true);

    }

    @Test
    public void connectDataBaseIntSecurity() {
        Connection c= DataBaseConnectorUsage.connectDataBaseIntSecurity();
        assertEquals((c!=null),true);
    }
}