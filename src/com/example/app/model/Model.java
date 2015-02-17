package com.example.app.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; // this enables use of the list class
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {
    
    // our model constructor called instance is set to private static so 
    // that it cannot be created outside of this class
    // it is initialized to null
    private static Model instance = null;
    
    // this public method is used to access the instance Model object
    public static synchronized Model getInstance() {
        if (instance == null) {     // if the instance has been created it will just skip this if statement and return instance
                                    // this makes sure we only have one instance of this model object
            instance = new Model(); // creates new instance when the instance is set to null and sets instance to a new Model object
        }
        return instance; // returns the new model object
    }
    
    // a list of type property objects 
    List<Property> properties;
    
    // this is an instance of the PropertyTableGateway class
    PropertyTableGateway gateway;
    
    // a private constructor that connects to the database
    private Model() {
        try { 
            // new connection object conn is passed the function getInstance which 
            // will create and return a connection to the database
            Connection conn = DBConnection.getInstance(); 
            
            // we pass this conn connection object to the PropertyTableGateway 
            // class through the gateway object
            this.gateway = new PropertyTableGateway(conn);
            
            // the properties array list is initialized
            // it will retrieve the property objects from the database via
            // the property table gateway and store in the properties array list locally
            this.properties = this.gateway.getProperties();
        } catch (ClassNotFoundException ex) { // these catch claues will catch an errors thrown from inside the DBConnection class
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex); // error message is logged into log file
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex); // error message is logged into log file
        }
    }
    
    // this returns the array list of all the property objects that are stored locally
    public List<Property> getProperties() {
        return this.properties;
    }
    
    // add property to properties array list
    public boolean addProperty(Property p) {
        boolean result = false; // result is set to false as it has not beena added to the array list in this model class
        try {
            int id = this.gateway.insertProperty(p.getAddress(), p.getDescription(), p.getRent(), p.getBedrooms());
            if (id != -1) { // if the id is not equal to one it has been inserted into the SQL database
                p.setId(id); // set the ID which has been retrieved from SQL database
                this.properties.add(p); // this property is added to properties array list in this model class
                result = true; // set the result to true
            }
        } catch (SQLException ex) { // catch any SQL errors
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result; // return the result
    }
    
    // attempts to delete the property from the SQL database via gateway class
    // and if successful removes it from the properties array list
    public boolean removeProperty(Property p) {
        boolean removed = false;
        
        try {
            // using the gateway connection object we delete the object on the database
            // it gets the ID of the property object being passed into the constructor
            removed = this.gateway.deleteProperty(p.getId()); 
            if (removed) { // if it was returns true 
                removed = this.properties.remove(p); // removed is set to remove the the specified property object
            }
        } catch (SQLException ex) { // catches the error
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // returns removed
        return removed;
    }
    
    // an int representing the ID will be passed into this
    // this function will search the properties array list
    public Property findPropertiesById(int id) {
        // a property object is set to null
        // it will be used as a placeholder for the loop 
        Property p = null;
        int i = 0;
        boolean found = false; // found is initialized to false
        // runs through array list 
        // while i in smaller than the length of the array 
        // and the specified property object hasn't been found
        while (i < this.properties.size() && !found) { 
            // this get method returns the next property in our array list and passes it the local property object
            p = this.properties.get(i); 
            // checks to see if the ID of the property that has been returned 
            // on this iteration of the loop is equal to the ID entered into the function
            if (p.getId() == id) {       
                found = true; // if the IDs match set found to true
            } else {
                i++; // if the IDs don't match increment i plus one
            }
        }
        // if no property objects in the properties array list match IDs it will 
        // set the p to null
        if (!found) {
            p = null;
        }
        return p; // return the property object 
    }
    
    // the update property function will pass in the 
    public boolean updateProperty(Property p) {
        boolean updated = false; // update boolean set to false
        
        try { 
             // passes in the property object from the array list to gateway class to update on the SQL database
            updated = this.gateway.updateProperty(p);
        } catch (SQLException ex) { // catches any errors
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex); // puts the error into the log
        } 
        return updated; // return updated
    }
    
    /* 
        // couldn't figure out how to get the string to be passed into the parameters 
    
    public Property findPropertiesByAddress(String address) {
        Property p = null;
        int i = 0;
        boolean found = false;
        while (i < this.properties.size() && !found) {
            p = this.properties.get(i);
            if (p.getAddress() == address) {
                found = true;
            } else {
                i++;
            }
            
            }
        if (!found) {
            p = null;
        }
        return p;
    } 
    */ 
}
