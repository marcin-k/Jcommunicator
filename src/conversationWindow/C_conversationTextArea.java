package conversationWindow;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * Created by Marcin on 6/5/2016.
 */
public class C_conversationTextArea {
    private static TextArea conversation;
    C_conversationTextArea(){
        conversation = new TextArea();
    }
    public void add(String text){
        //Platform.runLater(() -> appLog.add(text));
        //anonymous class version (older java)
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                conversation.appendText(text);
            }
        });
    }
    public static TextArea get(){
        return conversation;
    }
}
