package mainWindow;

import conversationWindow.All_In_One_ConversationWindow;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
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
                (ov, old_val, new_val) -> {
                    //call to overloaded setCenter method to change what is displayed in center screen
                    //open new conversation window
                    Stage conversationStage = new Stage();
                    Contact recipient = (Contact)new_val;
                    All_In_One_ConversationWindow conversationWindow = new All_In_One_ConversationWindow(
                            Login_Controller.getInstance().getLoggedInUserAddress(),recipient.getAddress());

                    Scene myScene = new Scene((Parent) conversationWindow.getNode(), 300, 275);
                    conversationStage.setTitle(recipient.getName());
                    conversationStage.setScene(myScene);
                    conversationStage.setResizable(false);
                    conversationStage.show();
                });
        return contactView;

    }
}
