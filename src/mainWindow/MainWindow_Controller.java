package mainWindow;

import javafx.scene.control.Label;

/**
 * Created by marcin on 09/06/2016.
 */
public class MainWindow_Controller {
    private static MainWindow_Controller ourInstance = null;
    private static Label connectionStatus = new Label("Not Connected");


    private MainWindow_Controller() {}

    public static MainWindow_Controller getInstance() {
        if(ourInstance==null){
            ourInstance = new MainWindow_Controller();
            connectionStatus.setStyle("-fx-background-color: red; -fx-alignment: center; -fx-font-family: \"Bradley Hand\";");
            connectionStatus.setPrefWidth(296);
        }
        return ourInstance;
    }
//--------------------------- NON-SINGLETON PART OF A CLASS -------------------------------------
    public void setConnectionConnected(){
        connectionStatus.setText("Connected");
        connectionStatus.setStyle("-fx-background-color: greenyellow; -fx-alignment: center; -fx-font-family: \"Bradley Hand\";");
    }
    public void setConnectionNotConnected(){
        connectionStatus.setText("Not Connected");
        connectionStatus.setStyle("-fx-background-color: red; -fx-alignment: center; -fx-font-family: \"Bradley Hand\";");
    }
    public Label getConnectionStatus(){
        return connectionStatus;
    }
}
