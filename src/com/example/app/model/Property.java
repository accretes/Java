package com.example.app.model;

// property object: this contains the get and set methods which are used in the model class
public class Property {
    
    // private variables which will be used to represent our the column in our database
    // they match up with their respective names in our database
    private int id;
    private String address;
    private String description;
    private double rent;
    private int bedrooms;
    
    public Property(int id, String a, String d, double r, int b) {
        this.id = id;
        this.address = a;
        this.description = d;
        this.rent = r;
        this.bedrooms = b;
    }
    
    public Property(String a, String d, double r, int b) {
        this(-1, a, d, r, b);
    }
    
    // get and set methods for the columns in our database
    // they return and pass the private variables stored locally inside a property object
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public double getRent() {
        return rent;
    }
    
    public void setRent(double rent) {
        this.rent = rent;
    }
    
    public int getBedrooms() {
        return bedrooms ;
    }
    
    public void setBedrooms(int bedrooms) {
        this.bedrooms  = bedrooms;
    }  
}
