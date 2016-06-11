package mainWindow;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Created by marcin on 25/05/2016.
 */
public class BottomPane {
    public Node getBottomPane(){
        Label bottomLabel = new Label("Developed by Marcin Krzeminski");
        bottomLabel.setPrefWidth(296);
        bottomLabel.setStyle("-fx-background-color: #95c6f2; -fx-alignment: center; -fx-font-family: \"Bradley Hand\";");
        return bottomLabel;
    }
}
