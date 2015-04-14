package com.example.app.model;

import static java.lang.System.console; // utilises the console for interactivity 
import java.util.List; // 
import java.util.Scanner; 


public class MainApp {

    // the main method of this program
    public static void main(String [] args) {
        // scanner object for input
        Scanner keyboard = new Scanner(System.in);
        
        // a new instance of the Model class is created
        Model model;
        
        // an int variable for the "option" number to be passed into
        int opt = 0;
        
        // the condition is while the opt variable is NOT 5
        do { 
            try {
                model = Model.getInstance();
                System.out.println("1. Create new Property");
                System.out.println("2. Delete existing Property");
                System.out.println("3. Edit existing Property");
                System.out.println("4. View all Properties");
                System.out.println("5. View single Property");
                System.out.println();
                System.out.println("7. Create new Tenant");
                System.out.println("8. Delete existing Tenant");
                System.out.println("9. Edit existing Tenant");
                System.out.println("10. View all Tenants");
                System.out.println("10. View single Tenant");
                System.out.println();
                System.out.println("12. Exit");
                System.out.println();

                // print out a new line for user viewability 
                System.out.println("");
                //read in the option and pass it to a string type variable
                String line = keyboard.nextLine();
                // parse the string into an integer using parseInt() and passing it to the opt variable
                opt = Integer.parseInt(line);

                // display the option that has been chosen
                System.out.println("You chose option " + opt);

                switch (opt) {
                    // case 1: used to CREATE a NEW property entry
                    case 1: { 
                        // print out a status message
                        System.out.println("Creating Property");
                        // call the createProperty method 
                        // it is passed in a new Scanner object called keyboard 
                        // and an instance of the Model object called model
                        createProperty(keyboard, model);     
                        // break statement transfers control out of the body after 
                        // the function above and it's subsequent functions are returned.
                        // when the control is transferred it will leave the do loop to check 
                        // opt is not equal to 5 by the while loop
                        break;
                    }
                    // case 2: used to DELETE a property entry
                    case 2: {
                        // print out a status message
                        System.out.println("Deleting Property");
                        // calls the delete property method with the respective parameters
                        deleteProperty(keyboard, model);
                        break;
                    }
                    // case 3: used to EDIT an EXISTING property entry
                    case 3: {
                        // print out a status message
                        System.out.println("Editing property");
                        // calls the edit property method 
                        editProperty(keyboard, model);
                        break;
                    }
                    // case 4: used to VIEW all existing property entries
                    case 4: {
                        // print out a status message
                        System.out.println("Viewing Properties");
                        // calls the view properties method 
                        // it is only passed a model parameter as it has no user input
                        viewProperties(model);
                        break;
                    }
                    case 5: {
                        System.out.println("Viewing Single Property");
                        viewProperty(keyboard, model);
                        break;
                    }
                    case 6: { 
                        System.out.println("Creating Tenant");
                        createTenant(keyboard, model);     
                        break;
                    }
                    case 7: {
                        System.out.println("Deleting Tenant");
                        deleteTenant(keyboard, model);
                        break;
                    }
                    case 8: {
                        System.out.println("Editing Tenant");
                        editTenant(keyboard, model);
                        break;
                    }
                    case 9: {
                        System.out.println("Viewing Tenants");
                        viewTenants(model);
                        break;
                    }
                    case 10: {
                        System.out.println("Viewing Single Tenant");
                        viewTenant(keyboard, model);
                        break;
                    }
                }
                
            }
            catch (DataAccessException e) {
                System.out.println();
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
        // while opt is not equal to 5 the do loop will continue to run 
        while (opt != 5);
        // when the user passes the value of 5 into the opt variable 
        // it will print out this message and the program will effectively end
        System.out.println("Goodbye");
    }
    
    // contains calls to the methods to create a property entry
    private static void createProperty(Scanner keyboard, Model model) throws DataAccessException {
        // a new property object is created and it's input is handled through the readProperty function
        Property p = readProperty(keyboard);
        
        if (model.addProperty(p)) {// if the model p has been added to the model class's array list properties
            System.out.println("Programmer added to database"); // print out that it has been added to the arraylist/SQL database
        } else { // if the property object has not been added to the database
            System.out.print("Programmer not added to database"); // print out that it has not been added to the arraylist/SQL database
        }
        System.out.println(); // blank line
    }
    
    // contains calls to the methods to delete a property entry
    private static void deleteProperty(Scanner keyboard, Model model) throws DataAccessException {
        String input; // user input
        int idNum; // the ID of the property the user wants to delete
        String prompt = ("Enter the property's ID to be deleted: "); // prompt the user to enter in ID
        input = getString(keyboard, prompt); // calls the get string method and returns the ID that is entered
        idNum = Integer.parseInt(input); // parse the input string into an int so it can passed onto the findPropertiesByID function
        Property p; // Property object
        
        // passes the now parsed ID into the find properties by ID function via the model class
        // this function returns a property object 
        p = model.findPropertiesById(idNum); 
        if (p != null) { // if a p object is returned it will be not null
            if (model.removeProperty(p)) { // then if the removeProperty function  in the model class returns true pass the found property object
                System.out.println("Property deleted"); // prompt to say the property was deleted
            } else { // then if the property is not removed 
                System.out.println("Property not deleted"); // prompt to say the property was not delted
            }
        }
        else { // if the property is not found it will be null 
            System.out.println("Property not found"); // prompt to say the property has not been found
        }
    }
    
    //
    private static void editProperty(Scanner keyboard, Model model) throws DataAccessException {
        String input; // input string for our in
        int idNum;    // used to store the id number that is passed into our find properties by id function
        String prompt = ("Enter the ID of the property to edit: "); // prompt to enter in the ID for the property to edit
        input = getString(keyboard, prompt); // use the get string method to take in the user input
        idNum = Integer.parseInt(input);     // parse the string into an int and store it in idNum
        Property p; // property object
        
        // the property object is set to the find properties by id function which will 
        // return a property object from the array list if it find it with a the user inputted ID
        p = model.findPropertiesById(idNum); 
        if (p != null) { // if the property object was returned 
            editPropertyDetails(keyboard, p); // call the edit property details function and pass in the property object to be edited
            if (model.updateProperty(p)) {    // the edit property details returns the edited property details and passes it to the update property function in the model class  
                System.out.println("Property updated");  // prompt to say the property was updated
            } else { // if the property was not updated
                System.out.println("Property not updated"); // prompt to say the property was not updated
            }
        }
        else { // couldn't find the property
            System.out.println("Property not found");
        }
        
    }
    
    // the property object to be edited is passed into this function 
    // and it's new details are set using the setX methods in the property object class
    private static void editPropertyDetails(Scanner keyboard, Property p) throws DataAccessException {
        // variables to pass into the user input
        String address, description;
        double rent;
        int bedrooms;
        String line1, line2;
        
        // gets the user input from the keyboard object  and the and passes it to the corresponding 
        address = getString(keyboard, "Enter address [" + p.getAddress() + "]: ");
        description = getString(keyboard, "Enter description [" + p.getDescription() + "]: ");
        line1 = getString(keyboard, "Enter rent [" + p.getRent() + "]: ");
        line2 = getString(keyboard, "Enter bedrooms [" + p.getBedrooms() + "]: ");
        
        // these if statements check to see if the length of the input is not set to 0 ie is it blank
        if (address.length() != 0) { 
            p.setAddress(address); // set the input to the corresponding local variable inside property object 
        }
        if (description.length() != 0) {
            p.setDescription(description);
        }
        if (line1.length() != 0) {
            rent = Double.parseDouble(line1);
            p.setRent(rent);
        }
        if (line2.length() != 0) {
            bedrooms = Integer.parseInt(line2);
            p.setBedrooms(bedrooms);
        } 
    }
    
    // is called from case 4 in the switch statement
    private static void viewProperties(Model model) {
        // accesses the model object and calls the getProperties function which 
        // will return the property objects and store them in a local array list
        List<Property> properties = model.getProperties();
        // print out blank line
        System.out.println();
        // checks to see if the properties array list is empty ie no property objects contained within it
        if (properties.isEmpty()) { 
            // prints out the out a message to say the array list is empty
            System.out.println("There are no properties in the database."); 
        } else { // if there are properties in thed database this else statement will run
            // print out a table
            System.out.printf(
                    "%5s %20s %20s %15s %12s\n", // spacing
                    "id", "address", "description", "rent", "bedrooms" // column names
                );
                for (Property pr : properties) { 
                    // for each property object in properties array list 
                    // get the corresponding data and print to the row  
                    System.out.printf("%5s %20s %20s %15s %12s\n",
                    pr.getId(),
                    pr.getAddress(),
                    pr.getDescription(),
                    pr.getRent(),
                    pr.getBedrooms());
            }
        }
        System.out.println(); // blank line
    }
    
    private static void viewProperty(Scanner keyboard, Model model) throws DataAccessException {
    int id = getInt(keyboard, "Enter the id of the property to view:", -1);
    Property p;

    p = model.findPropertiesById(id);
    System.out.println();
    if (p != null) {
        System.out.println("Name         : " + p.getId());
        System.out.println("Email        : " + p.getAddress());
        System.out.println("Mobile       : " + p.getDescription());
        System.out.println("Staff Number : " + p.getRent());
        System.out.println("Skills       : " + p.getBedrooms());
    }
    else {
        System.out.println("Property not found");
    }
    System.out.println();
}
    
    
    private static Property readProperty(Scanner keyb) {
        String address, description;
        double rent;
        int bedrooms;
        String line;
   
        // prompts the user to enter in 
        address = getString(keyb, "Enter address: ");
        description= getString(keyb, "Enter description: ");
        line = getString(keyb, "Enter rent: ");
        rent = Double.parseDouble(line);
        line  = getString(keyb, "Enter bedrooms: ");
        bedrooms = Integer.parseInt(line);
        
        Property p = new Property(address, description, rent, bedrooms);
        
        return p;
    }
    
    
    // a function to return a string from the keyboard scanner object
    // it takes a prompt as an input also
    private static String getString(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine(); // uses scanner object to return the next line
    }
    
    private static int getInt(Scanner keyb, String prompt, int defaultValue) {
        int opt = defaultValue;
        boolean finished = false;

        do {
            try {
                System.out.print(prompt);
                String line = keyb.nextLine();
                if (line.length() > 0) {
                    opt = Integer.parseInt(line);
                }
                finished = true;
            }
            catch (NumberFormatException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
        while (!finished);

        return opt;
    }
    
    
    private static void createTenant(Scanner keyboard, Model model) throws DataAccessException {

        Tenant t = readTenant(keyboard);

        if (model.addTenant(t)) {
            System.out.println("Tenant added to database"); 
        } else { 
            System.out.print("Tenant not added to database"); 
        }
        System.out.println(); 
    }


    private static void deleteTenant(Scanner keyboard, Model model) throws DataAccessException {
        String input; 
        int idNum; 
        String prompt = ("Enter the Tenant's ID to be deleted: "); 
        input = getString(keyboard, prompt); 
        idNum = Integer.parseInt(input); 
        Tenant t; 



        t = model.findTenantsById(idNum); 
        if (t != null) { 
            if (model.removeTenant(t)) { 
                System.out.println("Tenant deleted"); 
            } else { 
                System.out.println("Tenant not deleted"); 
            }
        }
        else { 
            System.out.println("Tenant not found"); 
        }
    }


    private static void editTenant(Scanner keyboard, Model model) throws DataAccessException {
        String input; 
        int idNum;    
        String prompt = ("Enter the ID of the Tenant to edit: "); 
        input = getString(keyboard, prompt); 
        idNum = Integer.parseInt(input);     
        Tenant t; 



        t = model.findTenantsById(idNum); 
        if (t != null) { 
            editTenantDetails(keyboard, t); 
            if (model.updateTenant(t)) {    
                System.out.println("Tenant updated");  
            } else { 
                System.out.println("Tenant not updated"); 
            }
        }
        else { 
            System.out.println("Tenant not found");
        }

    }



    private static void editTenantDetails(Scanner keyboard, Tenant t) throws DataAccessException {

        int age, phone, propertyid, leaseid;
        String line1, line2, line3, line4, fName, lName, gender, email;


        fName = getString(keyboard, "Enter first name [" + t.getFirstName() + "]: ");
        lName = getString(keyboard, "Enter last name [" + t.getLastName() + "]: ");
        line1 = getString(keyboard, "Enter age [" + t.getAge() + "]: ");
        age = Integer.parseInt(line1);
        gender = getString(keyboard, "Enter gender [" + t.getGender() + "]: ");
        email = getString(keyboard, "Enter email [" + t.getEmail() + "]: ");
        line2 = getString(keyboard, "Enter phone [" + t.getPhone() + "]: ");
        phone = Integer.parseInt(line2);
        line3 = getString(keyboard, "Enter propertyid [" + t.getPropertyID() + "]: ");
        propertyid = Integer.parseInt(line3);
        line4 = getString(keyboard, "Enter leaseid [" + t.getLeaseID() + "]: ");
        leaseid = Integer.parseInt(line4);


        if (fName.length() != 0) { 
            t.setFirstName(fName); 
        }
        if (lName.length() != 0) { 
            t.setLastName(lName); 
        }
        if (line1.length() != 0) {
            t.setAge(age);
        }
        if (gender.length() != 0) {
            t.setGender(gender);
        }
        if (email.length() != 0) {
            t.setEmail(email);
        }
        if (line2.length() != 0) {
            t.setPhone(phone);
        }
        if (line3.length() != 0) {
            t.setPropertyID(propertyid);
        }
        if (line4.length() != 0) {
            t.setLeaseID(leaseid);
        }

    }


    private static void viewTenants(Model model) {


        List<Tenant> tenants = model.getTenants();

        System.out.println();

        if (tenants.isEmpty()) { 

            System.out.println("There are no tenants in the database."); 
        } else { 

            System.out.printf(
                    "%5s %20s %20s %15s %12s %5s %20s %20s %15s %12s\n", 
                    "id", "first name", "last name", "age", "gender", "email", "phone", "property id", "lease id"  
                );
                for (Tenant t : tenants) { 

                    System.out.printf("%5s %20s %20s %15s %12s %5s %20s %20s %15s %12s\n",
                    t.getId(),
                    t.getFirstName(),
                    t.getLastName(),
                    t.getAge(),
                    t.getGender(),
                    t.getEmail(),
                    t.getPhone(),
                    t.getPropertyID(),
                    t.getLeaseID());
            }
        }
        System.out.println(); 
    }
    
    private static void viewTenant(Scanner keyboard, Model model) throws DataAccessException {
    int id = getInt(keyboard, "Enter the id of the tenant to view:", -1);
    Tenant t;
    Property p = null;

    t = model.findTenantsById(id);
    System.out.println();
    if (t != null) {
        p = model.findPropertiesById(p.getId());
        System.out.println("First Name          : " + t.getFirstName());
        System.out.println("Last Name           : " + t.getLastName());
        System.out.println("Age                 : " + t.getAge());
        System.out.println("Gender              : " + t.getGender());
        System.out.println("Email               : " + t.getEmail());
        System.out.println("Phone               : " + t.getPhone());
        System.out.println("Property            : " + ((p != null) ? p.getAddress() : ""));
        System.out.println("Lease               : " + t.getLeaseID());
    }
    else {
        System.out.println("Tenant not found");
    }
    System.out.println();
}


    private static Tenant readTenant(Scanner keyb) {
        int age, phone, propertyid, leaseid;
        String line, fName, lName, gender, email;


        fName = getString(keyb, "Enter first name: ");
        lName = getString(keyb, "Enter last name: ");
        line = getString(keyb, "Enter age: ");
        age = Integer.parseInt(line);
        gender = getString(keyb, "Enter gender: ");
        email = getString(keyb, "Enter email: ");
        line = getString(keyb, "Enter phone: ");
        phone = Integer.parseInt(line);
        line = getString(keyb, "Enter propertyid: ");
        propertyid = Integer.parseInt(line);
        line = getString(keyb, "Enter leaseid: ");
        leaseid = Integer.parseInt(line);

        Tenant t = new Tenant(fName, lName, age, gender, email,  phone, propertyid, leaseid);

        return t;
    }
}
