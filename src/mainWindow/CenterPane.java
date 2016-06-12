package mainWindow;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import logInWindow.Login_Controller;

/**
 * Created by marcin on 11/06/2016.
 */
public class CenterPane {
    public Node getCenterPane(){

        //ObservableList<Contact> contactList = Login_Controller.getInstance().getList();
        //ListView<Object> contactView = new ListView(Login_Controller.getInstance().getList());
        return new ListView(Login_Controller.getInstance().getList());
    }
}
