package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import model.Contact;
import persistors.DBPersistor;
import view.MainWindow_CenterPane_Contacts;

import java.util.ArrayList;

/**
 * Created by marcin on 08/06/2016.
 */
public class Login_Controller {
    private static Login_Controller ourInstance = null;
    private static Label myLabel; //label used to display display login status(successful/fail)
    private static DBPersistor persistor; //db connection class instance
    private static ObservableList<Contact> contacts; //list of contacts
    private static String loggedInUser=""; //maintains the info about logged in user
    //array used to determine how to display contact status (available/not available), each contact is stored
    //at address position in the array
    private boolean[] contactsAvailability;
    //indicate max number of contacts in the list
    private final int MAX_NUMBER_OF_CONTACTS=30;

    //Constructor
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
//------------------------ NON-SINGLETON PART OF CLASS ------------------------------------------------
    //log-in method (validates password, loads contact list,
    // opens a new thread that refreshes the contact list)
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
            setContactWindow(rootNode);
            Main_Controller.getInstance().loginToServer();
            //recipient is set to -1 as this message is only for server to know
            //that client is on and what port to forward the traffic
            Main_Controller.getInstance().sendMsg(getLoggedInUserAddress(), (-1), "LogMeIn", 1, "", "");
            initializeContactsAvailable();
            checkMyContactsStatus();
            new Thread(new StatusUpdateThread(rootNode)).start();
            isItConnected = true;
        }
        return isItConnected;
    }
    //prepares new center window to display contact list
    public void setContactWindow(BorderPane rootNode){
        rootNode.setCenter(new MainWindow_CenterPane_Contacts().getCenterPane());
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
    //sets contact at specific position as not available(assigns value of false)
    public void setContactAsNotAvailable(int position){
        contactsAvailability[position]=false;
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
    //sets status for a contact of a given address
    public void setStatusForContact(int address, String status){
        for(Contact c: contacts){
            if(c.getAddress()==address){
                c.setStatus(status);
                return;
            }
        }
    }
//---------------------------------------------------------------------------------------------

    //returns the status label
    public Label getStatus(){
        return myLabel;
    }

    //resets the label to no value (the initial state)
    public void resetLabel(){
        myLabel.setText("");
    }

    //returns list of contacts
    public ObservableList<Contact> getList(){
       return contacts;
    }

    //returns logged user address (db query only on first call)
    public int getLoggedInUserAddress(){
        return persistor.getLoggedInUserAddress(loggedInUser);
    }

    //returns logged user first name (db query only on first call)
    public String getLoggedInUserFirstName(){
        return persistor.getLoggedInUserFirstName(loggedInUser);
    }

    //returns logged user last name (db query only on first call)
    public String getLoggedInUserLastName(){
        return persistor.getLoggedInUserLastName(loggedInUser);
    }

    //loads list of people from db
    public ArrayList<Contact> getSearchResults(){
        return persistor.loadSearchResults();
    }

    //adds user to a contacts (in db)
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
