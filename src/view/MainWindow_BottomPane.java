package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import view.listCells.StatusListCell;

/**
 * Created by marcin on 25/05/2016.
 */
public class MainWindow_BottomPane {
    public Node getBottomPane(){
        GridPane gridPane = new GridPane();

        Label bottomLabel = new Label("Developed by Marcin Krzeminski");
        bottomLabel.setPrefWidth(296);
        bottomLabel.setStyle("-fx-background-color: #95c6f2; -fx-alignment: center; -fx-font-family: \"Bradley Hand\";");

//TODO: Fix the preview for status icon
        
        HBox bottomBox = new HBox();

        String notOnLine = "file:Java1.png";
        String onLine = "file:Java2.png";
        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll(notOnLine, onLine);
        final ComboBox<String> comboBox = new ComboBox(options);
        comboBox.setCellFactory(c -> new StatusListCell());
        comboBox.setButtonCell(new StatusListCell());

        bottomBox.getChildren().addAll(comboBox);

        gridPane.add(bottomBox, 0, 0);
        gridPane.add(bottomLabel, 0 , 1);
        return gridPane;
    }
}
