package view;

import controllers.Main_Controller;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by marcin on 24/05/2016.
 */
public class TopLogo {
    public Node getTopLogo(BorderPane rootNode){

//---------------------------Image View ------------------------------------------
        //Creates imageView node to return
        ImageView imageView = new ImageView();
        //Imports an logo file
        Image image = new Image("file:logo.png");
        //Sets the logo as the image view and returns it
        imageView.setImage(image);

//---------------------------Menu Bar ---------------------------------------------
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem settings = new MenuItem("Settings");
        MenuItem login = new MenuItem("Log in");
        MenuItem exit = new MenuItem("Exit");
        fileMenu.getItems().addAll(login, settings, exit);
        Menu aboutMenu = new Menu("About");
        MenuItem aboutItem = new MenuItem("About");
        aboutMenu.getItems().addAll(aboutItem);
        menuBar.getMenus().addAll(fileMenu, aboutMenu);
        menuBar.setStyle("-fx-font-size: 12px;-fx-font-weight:bold;"); //original color: #95c6f2

//---------------------------Contacts, Search Icons --------------------------------
        HBox iconsPanel = new HBox();
        ImageView contactIV = new ImageView(new Image("file:userM.png"));
        ImageView searchIV = new ImageView(new Image("file:loop.png"));

        searchIV.setFitWidth(40);
        searchIV.setFitHeight(40);
        contactIV.setFitHeight(40);
        contactIV.setFitWidth(40);

        Button search = new Button("", searchIV);
        search.setOnAction(e-> {
           rootNode.setCenter(new CenterPaneSearch().getNode());
        });
        //search.setStyle("-fx-background-color: #95c6f2;-fx-border-color: #166bb6;");


        Button contacts = new Button("", contactIV);
        contacts.setOnAction(e-> {
            rootNode.setCenter(new CenterPane().getCenterPane());
        });
        //contacts.setStyle("-fx-background-color: #95c6f2;-fx-border-color: #166bb6");


        //iconsPanel.setStyle("-fx-background-color: #95c6f2;");
        iconsPanel.getChildren().addAll(contacts, search);
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
                    Scene myScene = new Scene((Parent) node.getNode(rootNode), 320, 250);
                    loginWindow.setScene(myScene);
                    loginWindow.setResizable(false);
                    loginWindow.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if (name.equals("About")) {
                System.out.println("About window would be here");
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
        aboutItem.setOnAction(MEHandler);
        settings.setOnAction(MEHandler);
        exit.setOnAction(MEHandler);
        login.setOnAction(MEHandler);

//---------Creates parent Node that will store image view and menu bar and connection status------------
        GridPane gridPane = new GridPane();
        gridPane.add(menuBar,0,0);
       // gridPane.add(imageView,0,1); //currently connection status indicates only successful connection to db
        gridPane.add(iconsPanel,0,2);
        gridPane.add(connectionStatus,0,3);
        
        return gridPane;
    }
}
