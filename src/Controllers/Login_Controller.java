package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import persistors.DBPersistor;
import view.MainWindow_CenterPane_Contacts;
import model.Contact;

import java.util.ArrayList;

/**
 * Created by marcin on 08/06/2016.
 */
public class Login_Controller {
    private static Login_Controller ourInstance = null;
    private static Label myLabel;
    private static DBPersistor persistor;
    private static ObservableList<Contact> contacts;
    private static String loggedInUser="";
    private boolean[] contactsAvailability;
    private final int MAX_NUMBER_OF_CONTACTS=30;

    //constructor
    private Login_Controller() {
        myLabel = new Label("");
        persistor = new DBPersistor();
        contacts = FXCollections.observableArrayList();
        contactsAvailability = new boolean[MAX_NUMBER_OF_CONTACTS];
    }

    public static Login_Controller getInstance() {
        if(ourInstance == null){
            ourInstance = new Login_Controller();
        }
        return ourInstance;
    }
//------------ NON-SINGLETON PART OF CLASS ------------------------------------------------
    public boolean login(String login, String password, BorderPane rootNode) {
        loggedInUser = login;
        boolean isItConnected = false;
        if (!persistor.check(login, password)) {
            myLabel.setTextFill(Color.RED);
            myLabel.setText("Login Failed");
        } else {
            myLabel.setText("Login Successful");
            myLabel.setTextFill(Color.GREEN);
            Main_Controller.getInstance().setConnectionConnected();
            ArrayList<Contact> arry = persistor.loadContacts(login);
            contacts = FXCollections.observableArrayList(arry);
            rootNode.setCenter(new MainWindow_CenterPane_Contacts().getCenterPane());
            Main_Controller.getInstance().loginToServer();
            //recipient is set to -1 as this message is only for server to know
            //that client is on and what port to forward the traffic
            Main_Controller.getInstance().sendMsg(getLoggedInUserAddress(), getLoggedInUserAddress(), "LogMeIn", 1, "", "");
            initializeContactsAvailable();
            checkMyContactsStatus();


//TODO: sets logged in users address to correct value, used for conversation window
            //pull contact list from server
            //close login window and prevent from reopen it
            isItConnected = true;

        }
        return isItConnected;
    }
//----------------------Methods Deals with Contacts Availability Status------------------------
    //initializes the array as not available
    private void initializeContactsAvailable(){
        for(int i=0; i<MAX_NUMBER_OF_CONTACTS; i++){
            contactsAvailability[i]=false;
        }
    }
    //sets contact at specific position as available(assigns value of true)
    public void setContactAsAvailable(int position){
        contactsAvailability[position]=true;
    }
    //checks is contact of a given position(address) is available
    public boolean isContactAvailable(int position){
        return contactsAvailability[position];
    }
    //request to server to verify availability status for all contacts
    public void checkMyContactsStatus(){
        for(Contact e: contacts){
            Main_Controller.getInstance().sendMsg(getLoggedInUserAddress(), e.getAddress(), "TellIfContactIsOnline", 8, "", "");
        }
    }
//---------------------------------------------------------------------------------------------

    public Label getStatus(){
        return myLabel;
    }
    public void resetLabel(){
        myLabel.setText("");
    }
    public ObservableList<Contact> getList(){
        return contacts;
    }
    public int getLoggedInUserAddress(){
        return persistor.getLoggedInUserAddress(loggedInUser);
    }
    public String getLoggedInUserFirstName(){
        return persistor.getLoggedInUserFirstName(loggedInUser);
    }
    public String getLoggedInUserLastName(){
        return persistor.getLoggedInUserLastName(loggedInUser);
    }
    public ArrayList<Contact> getSearchResults(){
        return persistor.loadSearchResults();
    }

    public void addUserToMyContacts(Contact contact){
        persistor.addUserToMyContacts(loggedInUser, contact);
    }
//method adds a contact to list of users, without the need to reload a users from db each time new users is added
    public void addContact(int address, String firstName, String lastName){
        contacts.addAll(new Contact(address, firstName, lastName));
    }
//removes a contact from the menu and from database
    public void removeContact(Contact contact){
        contacts.remove(contact);
        persistor.removeContact(loggedInUser, contact);
    }

}
