package view;

import controllers.Login_Controller;
import controllers.Main_Controller;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Contact;
import model.SearchResultsArrayList;

/**
 * Created by marcin on 24/06/2016.
 *
 * Class provides the contact search window
 */
public class MainWindow_CenterPane_Search {
    public Node getNode(){
        GridPane gridPane = new GridPane();
//--------------------Top Text Field--------------------------------------------
        SearchResultsArrayList searchResultsArrayList = new SearchResultsArrayList();
        ListView searchResults = new ListView();
        searchResults.setItems(FXCollections.observableArrayList(searchResultsArrayList.getSearchResults()));

        TextField searchTextField = new TextField();
        searchTextField.setPrefWidth(296);
        Platform.runLater(() -> searchTextField.requestFocus());

        //on enter pressed limit the results to only those that meets the search criteria (contains letters entered)
        searchTextField.setOnAction(e-> searchResults.setItems(FXCollections.observableArrayList(searchResultsArrayList.getSearchResults(searchTextField.getText()))));
//--------------------Search Results---------------------------------------------
        //Right mouse button functions (add to contacts /send IM)
        ContextMenu menu = new ContextMenu();
        MenuItem add = new MenuItem("add to contacts");
        add.setOnAction(e-> Login_Controller.getInstance().addUserToMyContacts((Contact) searchResults.getSelectionModel().getSelectedItem()));

        MenuItem sendIm = new MenuItem("send IM");
        sendIm.setOnAction(e-> Main_Controller.getInstance().createConversationWindow((Contact) searchResults.getSelectionModel().getSelectedItem()));
        menu.getItems().addAll(add, sendIm);
        searchResults.setContextMenu(menu);

        gridPane.add(searchTextField, 0, 0);
        gridPane.add(searchResults, 0, 1);
        return gridPane;
    }
}
