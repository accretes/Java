package com.example.app.model;

public class Tenant {
//  fN, lN, a, g, e, p, pId, lId
    
    private int id;
    private String fName;  
    private String lName; 
    private int age;
    private String gender;
    private String email; 
    private int phone;  
    private int propertyid;
    private int leaseid;
    
    public Tenant(int id, String fN, String lN, int a, String g, String e, int p, int pId, int lId) {
        this.id = id; 
        this.fName = fN;
        this.lName = lN;
        this.age = a;
        this.gender = g;
        this.email = e;
        this.phone = p;
        this.propertyid = pId;
        this.leaseid = lId;
//    fName 
//    lName
//    age
//    gender
//    email
//    phone 
//    propertyid
//    leaseid
//    fName,lName, age, gender, email, phone, propertyid, leaseid
//    fN, lN, a, g, e, p, pId, lId
    }
    
    public Tenant(String fN, String lN, int a, String g, String e, int p, int pId, int lId) {
        this(-1, fN, lN, a, g, e, p, pId, lId);
    }
    
    // get and set methods for the columns in our database
    // they return and pass the private variables stored locally inside a property object
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return fName;
    }
    
    public void setFirstName(String fN) {
        this.fName = fN;
    }
    
    public String getLastName() {
        return lName;
    }
    
    public void setLastName(String lN) {
        this.lName = lN;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int a) {
        this.age = age;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String g) {
        this.gender = g;
    }
            
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String e) {
        this.email = e;
    }
    
    public int getPhone() {
        return phone;
    }
    
    public void setPhone(int p) {
        this.phone = p;
    }
    
    public int getPropertyID() {
        return propertyid;
    }
    
    public void setPropertyID(int pId) {
        this.propertyid = pId;
    }
    
    public int getLeaseID() {
        return leaseid;
    }
    
    public void setLeaseID(int lId) {
        this.leaseid = lId;
    }
}
