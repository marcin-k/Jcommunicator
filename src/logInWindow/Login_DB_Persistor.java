package logInWindow;

/**
 * Created by marcin on 08/06/2016.
 */


import java.sql.*;

public class Login_DB_Persistor {
    //This is the JDBC Connection object.
    private Connection dbConnection;

    public Login_DB_Persistor(){
        try {
            //instance of db driver
            Class.forName("com.mysql.jdbc.Driver");
//TODO: Change the database connection details to servers
            this.dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JComm?user=root&password=");
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
    public boolean check(String login, String password) {
        //Create an empty PatientsList
        boolean toReturn = false;
        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM JComm.User where login='"+login+"'");
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
}
