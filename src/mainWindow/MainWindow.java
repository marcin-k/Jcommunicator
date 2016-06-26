package mainWindow;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by marcin on 24/05/2016.
 */
public class MainWindow extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane rootNode = new BorderPane();
        stage.setTitle("JCommunicator");
        rootNode.setTop(new TopLogo().getTopLogo(rootNode));
        rootNode.setCenter(new CenterPane().getCenterPane());
        rootNode.setBottom(new BottomPane().getBottomPane());
        Scene myScene = new Scene(rootNode, 296, 600);
        stage.setScene(myScene);
        //
        //hides top bar close/minimise/maximize
        //stage.initStyle(StageStyle.UNDECORATED);
        //
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
