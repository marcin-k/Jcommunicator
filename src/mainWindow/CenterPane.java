package mainWindow;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import logInWindow.Login_Controller;
import mainWindow.model.Contact;

/**
 * Created by marcin on 11/06/2016.
 */
public class CenterPane {
    public Node getCenterPane(){

        //ObservableList<Contact> contactList = Login_Controller.getInstance().getList();
        ListView<Object> contactView = new ListView(Login_Controller.getInstance().getList());
        MultipleSelectionModel<Object> myListSelMod = contactView.getSelectionModel();
        myListSelMod.setSelectionMode(SelectionMode.SINGLE);

        contactView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Object>() {
                    public void changed(ObservableValue<? extends Object> ov,
                                        Object old_val, Object new_val) {
                        //call to overloaded setCenter method to change what is displayed in center screen
                        //open new conversation window


                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Conversation Window");
                        alert.setContentText("This will be replace with conversation window"+(Contact)new_val);
                        alert.showAndWait();
                    }
                });
        return contactView;

    }
}
