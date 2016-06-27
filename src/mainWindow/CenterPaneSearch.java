package mainWindow;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import mainWindow.model.SearchResultsArrayList;

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

        SearchResultsArrayList searchResultsArrayList = new SearchResultsArrayList();
        ListView searchResults = new ListView(FXCollections.observableArrayList(searchResultsArrayList.getSearchResults()));
        //Right mouse button click handler
        //TODO: add contentMenu
        final ContextMenu fruitChoices = new ContextMenu();
        fruitChoices.getItems().addAll(new MenuItem("Apples"), new MenuItem("Oranges"), new MenuItem("Pears"));
        for (final MenuItem menuItem : fruitChoices.getItems()) {
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {

                    System.out.println("You like " + menuItem.getText());
                }
            });
        }

        searchResults.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                System.out.println(mouseEvent);
                System.out.println("Secondary button down: " + mouseEvent.isSecondaryButtonDown());
                if (MouseButton.SECONDARY.equals(mouseEvent.getButton())) {
                    System.out.println("Choosing fruit");
                    Scene myScene = new Scene(fruitChoices, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                }
            }
        });



        gridPane.add(searchTextField, 0, 0);
        gridPane.add(searchResults, 0, 1);
        return gridPane;
    }
}
