package mainWindow.model;

import javafx.scene.control.Button;

/**
 * Created by marcin on 11/06/2016.
 */
public class Contact {
    private int address;
    private String firstName;
    private String lastName;

    public Contact(int address, String firstName, String lastName){
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getAddress() {
        return address;
    }
    public String getName(){
        return firstName+" "+lastName;
    }

    public String toString(){
        return firstName+ " "+lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }
}
