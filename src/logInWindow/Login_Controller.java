package logInWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import mainWindow.MainWindow_Controller;
import mainWindow.model.Contact;

import java.util.ArrayList;

/**
 * Created by marcin on 08/06/2016.
 */
public class Login_Controller {
    private static Login_Controller ourInstance = null;
    private static Label myLabel;
    private static Login_DB_Persistor persistor;
    private static ObservableList<Contact> contacts;

    //constructor
    private Login_Controller() {
        myLabel = new Label("");
        persistor = new Login_DB_Persistor();
        contacts = FXCollections.observableArrayList();
    }

    public static Login_Controller getInstance() {
        if(ourInstance == null){
            ourInstance = new Login_Controller();
        }
        return ourInstance;
    }
//------------ NON-SINGLETON PART OF CLASS ------------------------------------------------
    public boolean login(String login, String password, BorderPane rootNode) {
        boolean isItConnected = false;
        if (!persistor.check(login, password)) {
            myLabel.setTextFill(Color.RED);
            myLabel.setText("Login Failed");
        } else {
            myLabel.setText("Login Successful");
            myLabel.setTextFill(Color.GREEN);
            MainWindow_Controller.getInstance().setConnectionConnected();
            ArrayList<Contact> arry = persistor.loadContacts(login);
            contacts = FXCollections.observableArrayList(arry);
            //rootNode.setCenter(new CenterPane().getCenterPane());
            rootNode.setCenter(new ListView(getList()));
            //pull contact list from server
            //close login window and prevent from reopen it
            isItConnected = true;

        }
        return isItConnected;
    }
    public Label getStatus(){
        return myLabel;
    }
    public void resetLabel(){
        myLabel.setText("");
    }
    public ObservableList<Contact> getList(){
        return contacts;
    }

}
