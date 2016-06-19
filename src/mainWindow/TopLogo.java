package mainWindow;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logInWindow.Login_GUI;

/**
 * Created by marcin on 24/05/2016.
 */
public class TopLogo {
    public Node getTopLogo(BorderPane rootNode){

//---------------------------Image View ------------------------------------------
        //Creates imageView node to return
        ImageView iv = new ImageView();
        //Imports an logo file
        Image image = new Image("file:logo.png");
        //Sets the logo as the image view and returns it
        iv.setImage(image);

//---------------------------Menu Bar ---------------------------------------------
        MenuBar mb = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem settings = new MenuItem("Settings");
        MenuItem login = new MenuItem("Log in");
        MenuItem exit = new MenuItem("Exit");
        fileMenu.getItems().addAll(login, settings, exit);
        mb.getMenus().add(fileMenu);

//---------------------------Connections Status Indicator -------------------------
        Label connectionStatus = Main_Controller.getInstance().getConnectionStatus();


//---------------------------Menu Event handler -----------------------------------
        //One event handler will handle all menu events
        //Lambda Expression
        EventHandler<ActionEvent> MEHandler = event -> {
            String name = ((MenuItem) event.getTarget()).getText();
            if (name.equals("Exit")) {
                Platform.exit();
            }
            if (name.equals("Settings")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Settings");
                alert.setContentText("This will be replace with settings window");
                alert.showAndWait();
            }

            if (name.equals("Log in")) {
                System.out.println("login window should appear now");
                Stage loginWindow = new Stage();
                Login_GUI node  = new Login_GUI();
                try {
                    Scene myScene = new Scene((Parent) node.getNode(rootNode), 300, 275);
                    loginWindow.setScene(myScene);
                    loginWindow.setResizable(false);
                    loginWindow.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        };
        //Event Handler Version - JAVA 7 and below
        /*
                EventHandler<ActionEvent> MEHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = ((MenuItem)event.getTarget()).getText();
                if(name.equals("Exit")){
                    Platform.exit();
                }
                if(name.equals("Settings")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Settings");
                    alert.setContentText("This will be replace with settings window");
                    alert.showAndWait();
                }
            }
        };
         */
        settings.setOnAction(MEHandler);
        exit.setOnAction(MEHandler);
        login.setOnAction(MEHandler);

//---------Creates parent Node that will store image view and menu bar and connection status------------
        GridPane gp = new GridPane();
        gp.add(iv,0,0);
        gp.add(connectionStatus,0,1); //currently connection status indicates only successful connection to db
        gp.add(mb,0,2);
        
        return gp;
    }
}
