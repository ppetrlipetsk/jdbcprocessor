package com.ppsdevelopment.jdbcprocessor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
*  A class that implements the {@code ppsdevelopment.QueryPreparer} interface defines the method {@code boolean setValues ​​(PreparedStatement statement)}
*  which implements the request preparation mechanism required for a particular class.
*  In each case, the request has a different number and type of parameters than other requests, which require preparation and initialization with values.
*  In order to unify the method of calling a request having parameters, a mechanism is provided for filling in the request parameters from the calling class,
*  owning data on values ​​and types, filled parameters.
*  For this, the calling class must implement the QueryPreparer interface by defining the prepareStatement (PreparedStatement statement) throws SQLException method.
*  For instance:
     * public boolean prepareStatement (PreparedStatement statement) throws SQLException {
     * statement.setLong (1, tableId);
     * statement.setString (2, values.getAlias ​​());
     * statement.setString (3, values.getHeader ());
     * statement.setString (4, values.getFieldType (). toString ());
     * return true;
     *}
 * This method is called from the ppsdevelopment.MSDataBaseHelper library, passing a reference to the PreparedStatement class object through the {@code statement} parameter to the method.
 * After returning to the calling method, this object will be used to start the execution of the request.
 * @author PPS
 * @since 1.0
 */

 public interface QueryPreparer {
    boolean prepareStatement(PreparedStatement statement) throws SQLException;
 }
