package com.example.app.model;
// Tenant_fName, Tenant_lName, Tenant_Age, Tenant_Gender, Tenant_Email, Tenant_Phone, Property_ID, Lease_ID

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
// COLUMN_ID COLUMN_FIRSTNAME COLUMN_LASTNAME COLUMN_AGE COLUMN_EMAIL COLUMN_GENDER COLUMN_PHONE COLUMN_PROPERTY COLUMN_LEASE
public class TenantTableGateway {
    private static final String TABLE_NAME = "tenants";
    private static final String COLUMN_ID = "Tenant_ID";
    private static final String COLUMN_FIRSTNAME = "Tenant_fName";
    private static final String COLUMN_LASTNAME = "Tenant_lName";
    private static final String COLUMN_AGE = "Tenant_Age";
    private static final String COLUMN_GENDER = "Tenant_Gender";
    private static final String COLUMN_EMAIL = "Tenant_Email";
    private static final String COLUMN_PHONE = "Tenant_Phone";
    private static final String COLUMN_PROPERTY = "Property_ID";
    private static final String COLUMN_LEASE = "Lease_ID";
    
    private Connection mConnection;
    
    public TenantTableGateway(Connection connection) {
        mConnection = connection;
    }
    
    public List<Tenant> getTenants() throws SQLException {
        // the query string is used to store the SQL query fot the database
        String query;
        // the statement object is used to execute the query
        Statement stmt;
        // the result set will be returned with the data from the query and stored in rs
        ResultSet rs;
        // this list contains the tenant objects it will be passed the result set
        List<Tenant> tenants;
        
        // variables for the columns
        int id, age, phone, propertyid, leaseid;
        String fName, lName, gender, email;
        
        // a tenant object will be created from the result of the query
        Tenant t;
        
        // the query is set
        query = "SELECT * FROM " + TABLE_NAME;
        // the statement is prepared and ready for the query to be executed. 
        stmt = this.mConnection.createStatement();
        // the the query is a passed into the statement and executed and the results are stored in the result set
        rs = stmt.executeQuery(query);
        
        
        tenants = new ArrayList<Tenant>();
        // this iterates through the result set
        while (rs.next()) { // .next() will run through each row of the data until there are no lines left
            id = rs.getInt(COLUMN_ID); // each row will be passed to it's corresponding variable
            fName = rs.getString(COLUMN_FIRSTNAME);
            lName = rs.getString(COLUMN_LASTNAME);
            age = rs.getInt(COLUMN_AGE);
            gender = rs.getString(COLUMN_GENDER);
            email = rs.getString(COLUMN_EMAIL);
            phone = rs.getInt(COLUMN_PHONE);
            propertyid = rs.getInt(COLUMN_PROPERTY);
            leaseid = rs.getInt(COLUMN_LEASE);

            // these variables are stored in a new tenant object
            t = new Tenant(id, fName,lName, age, gender, email, phone, propertyid, leaseid);
            // this new tenant object is passed into the array list tenants
            tenants.add(t);
        }
        
        // it then returns the list of tenant objects
        return tenants;
    }
   
    // the insertTenant function
    // it is an int as it returns ID as ID is an int variable
    public int insertTenant(String fN, String lN, int a, String g, String e, int p, int pId, int lId) throws SQLException { 
        String query;           // the query we will execute
        PreparedStatement stmt; // a prepared statement can stored place holders before execution which will 
        int numRowsAffected;    // the JDBC driver's will store the number of rows of data in this variable
        int id = -1;            // this will show the database has set it's auto incremented
        
        query = "INSERT INTO " + TABLE_NAME + " (" + // SQL query statement
                COLUMN_FIRSTNAME + ", " +
                COLUMN_LASTNAME + ", " +
                COLUMN_AGE + ", " +
                COLUMN_GENDER + ", " +
                COLUMN_EMAIL + ", " +
                COLUMN_PHONE + ", " +
                COLUMN_PROPERTY + ", " +
                COLUMN_LEASE + 
                ") VALUES (?, ?, ?, ?, ?, ?, ?)"; // the corresponding placeholder values
                
        // a prepared statement is used to execute the query above
        // mConnection represents the active connection 
        // prepareStatement takes the query and 
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); // the keys are made retrievable through RETURN_GENERATED_KEYS
        stmt.setString(1, fN); // the values are set into the variables
        stmt.setString(2, lN); // the method used must be compatible with the SQL type 
        stmt.setInt(3, a);
        stmt.setString(4, g);
        stmt.setString(5, e);
        stmt.setInt(6, p);
        stmt.setInt(7, pId);
        stmt.setInt(8, lId);

        
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
    public boolean deleteTenant(int id) throws SQLException { 
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

    // updates the tenant on the server
    boolean updateTenant(Tenant t) throws SQLException {
        String query; // SQL query for updated will be passed to this string variable
        PreparedStatement stmt;
        int numRowsAffected;
        
        query = "UPDATE " + TABLE_NAME + " SET " +  // the query reads as: update the table name and set the columns to the place holder values 
                COLUMN_FIRSTNAME + " = ?, " +
                COLUMN_LASTNAME + " = ?, " +
                COLUMN_AGE + " = ?, " +
                COLUMN_GENDER + " = ?, " +
                COLUMN_EMAIL + " = ?, " +
                COLUMN_PHONE + " = ?, " +
                COLUMN_PROPERTY + " = ?, " +
                COLUMN_LEASE + " = ? " +
                " WHERE " + COLUMN_ID + " = ?";
                
        
        stmt = mConnection.prepareStatement(query); // prepare the statment with the SQL query
        
        stmt.setString(1, t.getFirstName()); // set the statement types to the their number and get the corresponding types from tenant object
        stmt.setString(2, t.getLastName());
        stmt.setInt(3, t.getAge());
        stmt.setString(4, t.getEmail());
        stmt.setString(5, t.getGender());
        stmt.setInt(6, t.getPhone());
        stmt.setInt(7, t.getPropertyID());
        stmt.setInt(8, t.getLeaseID());
        stmt.setInt(9, t.getId());
        
        numRowsAffected = stmt.executeUpdate(); // execute the statement
        
        return (numRowsAffected == 1); // set the numRowAffected to true and return it
    }
}
