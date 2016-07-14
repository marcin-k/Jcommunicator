package view;

import controllers.Main_Controller;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by marcin on 24/05/2016.
 *
 * Main Application Class
 * Calls other classes to compose the main window
 */
public class MainWindow extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane rootNode = new BorderPane();
        stage.setTitle("JCommunicator");
        rootNode.setTop(new MainWindow_TopLogo().getTopLogo(rootNode));
        rootNode.setCenter(new MainWindow_CenterPane_Contacts().getCenterPane());
        rootNode.setBottom(new MainWindow_BottomPane().getBottomPane());
        Scene myScene = new Scene(rootNode, 296, 600);
        stage.setScene(myScene);

        //gets a screen boundaries
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        //set Stage boundaries to top right part of the screen
        stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth()-320);
        stage.setY(primaryScreenBounds.getMinY() + 45);

        stage.show();

    }
    public void stop(){
        Main_Controller.getInstance().closeThread();
    }

    //Application starter
    public static void main(String[] args){
        launch(args);
    }

}
