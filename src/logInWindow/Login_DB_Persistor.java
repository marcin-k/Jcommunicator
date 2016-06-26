package logInWindow;

/**
 * Created by marcin on 08/06/2016.
 */


import mainWindow.model.Contact;

import java.sql.*;
import java.util.ArrayList;

public class Login_DB_Persistor {
    //This is the JDBC Connection object.
    private Connection dbConnection;

    public Login_DB_Persistor(){
        try {
            //instance of db driver
            Class.forName("com.mysql.jdbc.Driver");
//TODO: Change the database connection details to servers
            this.dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JComm?user=root&password=");
            //this.dbConnection = DriverManager.getConnection("jdbc:mysql://mysql1.host.ie:3306/krma50_db?user=krma50_user&password=abc12345");
            if(this.dbConnection != null)
            {
                System.out.println("CONNECTED TO DATABASE!! : "+this.dbConnection);
            }
            else
            {
                System.out.println("CONNECTION FAILED!!");
            }
        }
        catch(ClassNotFoundException c)
        {
            System.out.println(c.getMessage());
        }
        catch(SQLException s)
        {
            System.out.println(s.getMessage());

        }
    }
//TODO: change the return type to string to indicate the type of problem
    // Checks login details
    public boolean check(String login, String password) {

        boolean toReturn = false;
        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User where login='"+login+"'");
            if(rs.next()) {
                String pass = rs.getString("password");
                if (rs.getString("password").equals(password)) {
                    toReturn = true;
                    //get address and set in C_controller
                }
                System.out.println("----PASSWORD IS: X" + pass + "X");
                }
            else{
                //msg user not in database
                System.out.println("wrong username");
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return toReturn;
    }
    // Pulls list of contacts from a database
    public ArrayList<Contact> loadContacts(String username){

        //Create an empty ListOfContacts
        ArrayList<Contact> contacts = new ArrayList();
        try{
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Contacts where username='"+username+"'");

            //loops through all rows and inserts them into "contacts" (contact list)
            while(rs.next())
            {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                int address = rs.getInt("address");

                Contact recreatedContact = new Contact(address, firstName, lastName);
                contacts.add(recreatedContact);
            }
            rs.close();
            stmt.close();
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return contacts;
    }
//---------------------------Retrieves logged in user address------------------
public int getLoggedInUserAddress(String username) {
    int address = 0;
    try {
        Statement stmt = dbConnection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM User where login='"+username+"'");
        if(rs.next()) {
            address = rs.getInt("address");
        }
        rs.close();
        stmt.close();
    }
    catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return address;
}
    public String getLoggedInUserFirstName(String username){
        String firstName="";
        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User where login='"+username+"'");
            if(rs.next()) {
                firstName = rs.getString("firstName");
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return firstName;
    }
    public String getLoggedInUserLastName(String username){
        String lastName="";
        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User where login='"+username+"'");
            if(rs.next()) {
                lastName = rs.getString("lastName");
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return lastName;
    }


}
