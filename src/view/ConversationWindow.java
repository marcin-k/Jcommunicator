package view;

import Controllers.Login_Controller;
import Controllers.Main_Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.Serializable;

/**
 * Created by marcin on 17/06/2016.
 *
 * Conversation window Class
 */
public class ConversationWindow implements Serializable {

    TextArea conversation;
    //Senders and recipients addresses
    int sender;
    int recipient;
    String sendersFirstName="";
    String sendersLastName="";

    //Constructor
    public ConversationWindow(int sender, int recipient){
        this.sender = sender;
        this.recipient = recipient;
        this.sendersFirstName = Login_Controller.getInstance().getLoggedInUserFirstName();
        this.sendersLastName = Login_Controller.getInstance().getLoggedInUserLastName();
        conversation = Main_Controller.getInstance().getConversationTextArea(recipient);
    }

    //Returns a conversation window
    public Node getNode(){

        BorderPane rootNode = new BorderPane();

        rootNode.setTop(new Label(" Conversation preview: "));

        VBox mainPartOfWindow = new VBox();
        TextArea conversationPreview = conversation;
        conversationPreview.setEditable(false);
        conversationPreview.setPrefRowCount(100);
        conversationPreview.setFocusTraversable(false);
        conversationPreview.setWrapText(true);

        TextArea inputArea = new TextArea();
        inputArea.setPrefRowCount(4);
        inputArea.setWrapText(true);

        mainPartOfWindow.setSpacing(10);
        mainPartOfWindow.getChildren().addAll(conversationPreview, inputArea);
        rootNode.setCenter(mainPartOfWindow);

        Button myButton = new Button("Send");
        myButton.setOnAction(e -> {
            sendMsg(inputArea);
            inputArea.clear();
        });

        inputArea.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                sendMsg(inputArea);
                inputArea.setText("");
                ke.consume(); // necessary to prevent event handlers for this event
            }
        });


        HBox myBottomBox = new HBox();
        myBottomBox.setAlignment(Pos.CENTER_RIGHT);
        myBottomBox.getChildren().addAll(myButton);
        myBottomBox.setPadding(new Insets(10, 0, 0, 0));
        rootNode.setBottom(myBottomBox);

        rootNode.setPadding(new Insets(10, 10, 10, 10));

        return rootNode;
    }
    //Sends message to a server
    private void sendMsg(TextArea inputArea){
        Main_Controller.getInstance().sendMsg(sender, recipient, inputArea.getText(), 0, sendersFirstName, sendersLastName);
        conversation.appendText(inputArea.getText() + System.getProperty("line.separator"));
    }

}
