package mainWindow;

import javafx.scene.Node;
import javafx.scene.control.ListView;

/**
 * Created by marcin on 11/06/2016.
 */
public class CenterPane {
    public Node getCenterPane(){
        ListView<Contact> contacts = new ListView();

        return contacts;
    }
}
