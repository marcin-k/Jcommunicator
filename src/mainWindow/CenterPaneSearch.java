package mainWindow;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Created by marcin on 24/06/2016.
 */
public class CenterPaneSearch {
    public Node getNode(){
        GridPane gridPane = new GridPane();
//--------------------Top Text Field--------------------------------------------
        TextField searchTextField = new TextField();
        searchTextField.setPrefWidth(296);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                searchTextField.requestFocus();
            }
        });
//--------------------Search Results---------------------------------------------
 //TODO: Add the method to get all people in a database in a list, add "add" button for each person so
 //TODO: they can by add to contact list

        ListView searchResults = new ListView(FXCollections.observableArrayList());


        gridPane.add(searchTextField, 0, 0);
        gridPane.add(searchResults, 0, 1);
        return gridPane;
    }
}
