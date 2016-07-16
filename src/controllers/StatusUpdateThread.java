package Controllers;

import javafx.application.Platform;
import javafx.scene.layout.BorderPane;

import static java.lang.Thread.sleep;

/**
 * Created by marcin on 02/07/2016.
 *
 * Thread used to refresh every 20 seconds the list of contacts, their status and availability
 *
 */
public class StatusUpdateThread implements Runnable {
    BorderPane rootNode = null;
    public StatusUpdateThread(BorderPane rootNode){
        this.rootNode = rootNode;
    }
    @Override
    public void run() {
        while (true){
            try {
                sleep(20000);
                Login_Controller.getInstance().checkMyContactsStatus();
                if(rootNode==null){
                    System.out.println("rootnode is null");
                }
                Platform.runLater(() -> Login_Controller.getInstance().setContactWindow(rootNode));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
