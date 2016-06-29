package view;

import controllers.Main_Controller;
import javafx.scene.Node;
import javafx.scene.control.*;
import controllers.Login_Controller;
import view.listCells.ContactListCell;
import model.Contact;

/**
 * Created by marcin on 11/06/2016.
 */
public class CenterPane {
    public Node getCenterPane(){

        //ObservableList<Contact> contactList = Login_Controller.getInstance().getList();
        ListView<Contact> contactView = new ListView(Login_Controller.getInstance().getList());
        contactView.setCellFactory(c -> new ContactListCell());
        MultipleSelectionModel<Contact> myListSelMod = contactView.getSelectionModel();
        myListSelMod.setSelectionMode(SelectionMode.SINGLE);


        ContextMenu menu = new ContextMenu();
        MenuItem remove = new MenuItem("remove from contacts");
        remove.setOnAction(e-> {
            System.out.println("removing item");
            Login_Controller.getInstance().removeContact((Contact) contactView.getSelectionModel().getSelectedItem());
        });
        MenuItem sendMsg = new MenuItem("send im");
        sendMsg.setOnAction(e->{
            Contact recipient = (Contact) contactView.getSelectionModel().getSelectedItem();
            Main_Controller.getInstance().createConversationWindow(recipient);
        });
        menu.getItems().addAll(remove, sendMsg);
        contactView.setContextMenu(menu);

        contactView.setOnMouseClicked(e-> {
            if(e.getClickCount()==2){
                Contact recipient = (Contact) contactView.getSelectionModel().getSelectedItem();
                Main_Controller.getInstance().createConversationWindow(recipient);
            }

        });
        /*
        contactView.getSelectionModel().selectedItemProperty().addListener(
                (ov, old_val, new_val) -> {
                    //call to overloaded setCenter method to change what is displayed in center screen
                    //open new conversation window

                    Contact recipient = (Contact)new_val;
                    Main_Controller.getInstance().createConversationWindow(recipient);

                    System.out.println("clearing selection model");
                    //int index = contactView.getEditingIndex();
                    //contactView.getSelectionModel().clearSelection();


                });
          */
        return contactView;
    }

}
