package view;

import Controllers.Main_Controller;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Created by marcin on 25/05/2016.
 *
 * Class returns the footer for the main window
 * Contains status update
 */
public class MainWindow_BottomPane {
    public Node getBottomPane(){
        GridPane gridPane = new GridPane();

        Label bottomLabel = new Label("Developed by Marcin Krzeminski");
        bottomLabel.setPrefWidth(296);
        bottomLabel.setStyle("-fx-background-color: #95c6f2; -fx-alignment: center; -fx-font-family: \"Bradley Hand\";");
        
        HBox bottomBox = new HBox();

        /*-----------------------------------------------------------------------------
        // Status Changes Option - Unimplemented
        String notOnLine = "file:Java1.png";
        String onLine = "file:Java2.png";
        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll(notOnLine, onLine);
        final ComboBox<String> comboBox = new ComboBox(options);
        comboBox.setCellFactory(c -> new StatusListCell());
        comboBox.setButtonCell(new StatusListCell());
        bottomBox.getChildren().addAll(comboBox);
        //-----------------------------------------------------------------------------*/

        //Box contains the textfield and button to enter and push the status to server
        HBox statusBox = new HBox();
        statusBox.setSpacing(5);
        statusBox.setPadding(new Insets(10,10,10,10));
        TextField statusTextField = new TextField();
        statusTextField.setPrefWidth(160);
        Button updateStatusButton = new Button("Update Status");

        updateStatusButton.setOnAction(e-> Main_Controller.getInstance().updateStatus(statusTextField.getText()));
        statusBox.getChildren().addAll(statusTextField, updateStatusButton);
        bottomBox.getChildren().addAll(statusBox);
        gridPane.add(bottomBox, 0, 0);
        gridPane.add(bottomLabel, 0 , 1);
        return gridPane;
    }
}
