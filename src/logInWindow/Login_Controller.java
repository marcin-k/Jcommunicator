package logInWindow;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import mainWindow.MainWindow_Controller;

/**
 * Created by marcin on 08/06/2016.
 */
public class Login_Controller {
    private static Login_Controller ourInstance = null;
    private static Label myLabel;
    private static Login_DB_Persistor persistor;
    private Login_Controller() {}

    public static Login_Controller getInstance() {
        if(ourInstance == null){
            ourInstance = new Login_Controller();
            myLabel = new Label("");
            persistor = new Login_DB_Persistor();
        }
        return ourInstance;
    }
//------------ NON-SINGLETON PART OF CLASS ------------------------------------------------
    public boolean login(String login, String password) {
        boolean isItConnected = false;
        if (!persistor.check(login, password)) {
            myLabel.setTextFill(Color.RED);
            myLabel.setText("Login Failed");
        } else {
            myLabel.setText("Login Successful");
            myLabel.setTextFill(Color.GREEN);
            MainWindow_Controller.getInstance().setConnectionConnected();
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
}
