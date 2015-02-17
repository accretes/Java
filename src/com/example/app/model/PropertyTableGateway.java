package com.example.app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PropertyTableGateway {
    // these strings correspond to the names of the columns on our SQL database
    // thsese static variables are constant variables they are tied to this class only
    // they are got and set through the Property class get and set methods
    private static final String TABLE_NAME = "properties";
    private static final String COLUMN_ID = "Property_ID";
    private static final String COLUMN_ADDRESS = "Property_Address";
    private static final String COLUMN_DESCRIPTION = "Property_Description";
    private static final String COLUMN_RENT = "Property_Rent";
    private static final String COLUMN_BEDROOMS = "Property_NoOfRooms";
    
    // private connection object for this class
    private Connection mConnection;
    
    // this constructor will be passed the connection from the model class
    // through this parameters as the model class handles the connection 
    public PropertyTableGateway(Connection connection) {
        // the passed in connection from the model class is set to the 
        // private connection object mConnection
        mConnection = connection;
    }
    
    
    public List<Property> getProperties() throws SQLException {
        // the query string is used to store the SQL query fot the database
        String query;
        // the statement object is used to execute the query
        Statement stmt;
        // the result set will be returned with the data from the query and stored in rs
        ResultSet rs;
        // this list contains the property objects it will be passed the result set
        List<Property> properties;
        
        // variables for the columns
        int id;
        String address, description;
        double rent;
        int bedrooms;
        
        // a property object will be created from the result of the query
        Property p;
        
        // the query is set
        query = "SELECT * FROM " + TABLE_NAME;
        // the statement is prepared and ready for the query to be executed. 
        stmt = this.mConnection.createStatement();
        // the the query is a passed into the statement and executed and the results are stored in the result set
        rs = stmt.executeQuery(query);
        
        
        properties = new ArrayList<Property>();
        // this iterates through the result set
        while (rs.next()) { // .next() will run through each row of the data until there are no lines left
            id = rs.getInt(COLUMN_ID); // each row will be passed to it's corresponding variable
            address = rs.getString(COLUMN_ADDRESS);
            description = rs.getString(COLUMN_DESCRIPTION);
            rent = rs.getDouble(COLUMN_RENT);
            bedrooms = rs.getInt(COLUMN_BEDROOMS);
           
            // these variables are stored in a new property object
            p = new Property(id, address, description, rent, bedrooms);
            // this new property object is passed into the array list properties
            properties.add(p);
        }
        
        // it then returns the list of property objects
        return properties;
    }
   
    // the insertProperty function will take in variables for address, description, rent and bedrooms
    // it is an int as it returns ID as ID is an int variable
    public int insertProperty(String a, String d, double r, int b) throws SQLException { 
        String query;           // the query we will execute
        PreparedStatement stmt; // a prepared statement can stored place holders before execution which will 
        int numRowsAffected;    // the JDBC driver's will store the number of rows of data in this variable
        int id = -1;            // this will show the database has set it's auto incremented
        
        query = "INSERT INTO " + TABLE_NAME + " (" + // SQL query statement
                COLUMN_ADDRESS + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_RENT + ", " +
                COLUMN_BEDROOMS +
                ") VALUES (?, ?, ?, ?)"; // the corresponding placeholder values
                
        // a prepared statement is used to execute the query above
        // mConnection represents the active connection 
        // prepareStatement takes the query and 
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); // the keys are made retrievable through RETURN_GENERATED_KEYS
        stmt.setString(1, a); // the values are set into the variables
        stmt.setString(2, d); // the method used must be compatible with the SQL type 
        stmt.setDouble(3, r);
        stmt.setInt(4, b);
        
        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected == 1) { // as only one row is being added to the database only one row should be affected
            ResultSet keys = stmt.getGeneratedKeys(); // the results of the statement will be passed to a ResultSet object called keys
            keys.next(); // the ResultSet will go through each line until all lines are complete
            
            id = keys.getInt(1); 
        }
        
        // id is returned
        return id;
    }
    
    // the ID of the row we want to delete is passed in from the input
    public boolean deleteProperty(int id) throws SQLException { 
        String query;           // the delete string will be held here
        PreparedStatement stmt; 
        int numRowsAffected;
        
        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?"; // SQL to delete specified row
        
        // the statement object is prepared through the active connection
        stmt = mConnection.prepareStatement(query); 
        // id for the row we want to delete is passed to the statement
        stmt.setInt(1, id);
        
        // execute update is used when a delete, insert or update SQL query is used
        // the statement is executed and passed to the number of rows affected integer
        numRowsAffected = stmt.executeUpdate(); 
        
        // number of rows affected is set to one when the row is deleted and is returned true
        return(numRowsAffected == 1);
    }

    // updates the property on the server
    boolean updateProperty(Property p) throws SQLException {
        String query; // SQL query for updated will be passed to this string variable
        PreparedStatement stmt;
        int numRowsAffected;
        
        query = "UPDATE " + TABLE_NAME + " SET " +  // the query reads as: update the table name and set the columns to the place holder values 
                COLUMN_ADDRESS     + " = ?, " +
                COLUMN_DESCRIPTION + " = ?, " +
                COLUMN_RENT        + " = ?, " +
                COLUMN_BEDROOMS    + " = ? " +
                " WHERE " + COLUMN_ID + " = ?";
                
        
        stmt = mConnection.prepareStatement(query); // prepare the statment with the SQL query
        
        stmt.setString(1, p.getAddress()); // set the statement types to the their number and get the corresponding types from property object
        stmt.setString(2, p.getDescription());
        stmt.setDouble(3, p.getRent());
        stmt.setInt(4, p.getBedrooms());
        stmt.setInt(5, p.getId());
        
        numRowsAffected = stmt.executeUpdate(); // execute the statement
        
        return (numRowsAffected == 1); // set the numRowAffected to true and return it
    }
}