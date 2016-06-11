package mainWindow;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by marcin on 24/05/2016.
 */
public class MainWindow extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane rootNode = new BorderPane();
        stage.setTitle("JCommunicator");
        rootNode.setTop(new TopLogo().getTopLogo());
        rootNode.setBottom(new BottomPane().getBottomPane());
        Scene myScene = new Scene(rootNode, 296, 600);
        stage.setScene(myScene);
        //
        //hides top bar close/minimise/maximize
        //stage.initStyle(StageStyle.UNDECORATED);
        //
        stage.show();

    }

    //Application starter
    public static void main(String[] args){
        launch(args);
    }

}
