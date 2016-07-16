package view;

import Controllers.Main_Controller;
import javafx.scene.Node;
import javafx.scene.control.*;
import Controllers.Login_Controller;
import view.listCells.ContactListCell;
import model.Contact;

/**
 * Created by marcin on 11/06/2016.
 *
 * This class displays the main window contact list
 */
public class MainWindow_CenterPane_Contacts {
    public Node getCenterPane(){
        //List of Contact Objects
        ListView<Contact> contactView = new ListView(Login_Controller.getInstance().getList());
        //Sets the display of he list
        contactView.setCellFactory(c -> new ContactListCell());
        MultipleSelectionModel<Contact> myListSelMod = contactView.getSelectionModel();
        myListSelMod.setSelectionMode(SelectionMode.SINGLE);

        //Right mouse button functions (removing contact from list / send IM)
        ContextMenu menu = new ContextMenu();
        MenuItem remove = new MenuItem("remove from contacts");
        remove.setOnAction(e-> {
            System.out.println("removing item");
            Login_Controller.getInstance().removeContact(contactView.getSelectionModel().getSelectedItem());
        });
        MenuItem sendMsg = new MenuItem("send im");
        sendMsg.setOnAction(e->{
            Contact recipient = contactView.getSelectionModel().getSelectedItem();
            Main_Controller.getInstance().createConversationWindow(recipient);
        });
        menu.getItems().addAll(remove, sendMsg);
        contactView.setContextMenu(menu);

        //On double mouse click send IM
        contactView.setOnMouseClicked(e-> {
            if(e.getClickCount()==2){
                Contact recipient = contactView.getSelectionModel().getSelectedItem();
                Main_Controller.getInstance().createConversationWindow(recipient);
            }

        });
        return contactView;
    }

}
