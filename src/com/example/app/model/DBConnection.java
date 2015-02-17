package com.example.app.model;

import java.sql.Connection; // these methods are added through the MySQL JDBC library that is connected to our project
                            // the connection object we will use to connect to our is defined in this class
import java.sql.DriverManager; // manages the the drivers for the Java Database Connectivity
import java.sql.SQLException; // is used to catch exceptions for any errors in the SQL code 

public class DBConnection {
    
    // private static type of this Connection object is used 
    // so only ONE instance of the connection can exist.
    private static Connection sConnection;
    
    // returns a connection object
    // can be called by just using the class name
    public static Connection getInstance() throws ClassNotFoundException, SQLException { 
        // declaring the host, database, username and password Strings 
        // for signing into the server that stores the database
        String host, db, user, password;
        
        // initialization the Strings to the respective connection and login data
        host = "daneel";
        db = "n00121148";
        user = "N00121148";
        password= "N00121148";
        
        // checks if is null OR returns if the connection has been closed. 
        // this creates a connection if one does not exist.
        // the conditions are set so that once a connection is established 
        // (in this case through this if statment) another connection cannot be created
        if (sConnection == null || sConnection.isClosed()) { // isClosed() method is true if the Connection is closed
            
            // this url String will contain the URL that will access our database 
            String url = "jdbc:mysql://" + host + "/" + db; 
            
            // this loads the driver class from the java database connectivity class. 
            // if an error occurs it will throw an ClassNotFound Exception
            Class.forName("com.mysql.jdbc.Driver"); 
            
            // this method attempts to establish a connection through the specified URL 
            // with a specified username and password. it passes this connection to the static connection variable
            // if succeeded it will give back connection
            sConnection = DriverManager.getConnection(url, user, password);
        }
        
        // the connection object is returned with JDBC connection to our database
        // if it has already been created it will skip the if statement as it is false.
        return sConnection;
    }
    
}
