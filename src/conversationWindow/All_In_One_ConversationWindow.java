package conversationWindow;

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
import mainWindow.Main_Controller;
import msg.FloatingMsg;

import java.io.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

/**
 * Created by marcin on 17/06/2016.
 */
public class All_In_One_ConversationWindow implements Serializable {
    Socket clientSocket;
    TextArea conversation;
    int sender;
    int recipient;

    public All_In_One_ConversationWindow(int sender, int recipient){
        this.sender = sender;
        this.recipient = recipient;

        conversation = Main_Controller.getInstance().getConversationTextArea(recipient);

/*
        Thread t;
        String server = "localhost";
        int port = 1777;

        try {
            t = new Thread(this, "Client Thread");
            t.start();
            clientSocket = new Socket(server, port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        */
        //creates entry in servers rooting table
        //sendMsg(sender, recipient, "", 1);
    }

    public Node getNode(){

        BorderPane rootNode = new BorderPane();

        rootNode.setTop(new Label(" will be replaced with chatter info "));

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
            Main_Controller.getInstance().sendMsg(sender, recipient, inputArea.getText(), 0);
            conversation.appendText(inputArea.getText() + System.getProperty("line.separator"));
            inputArea.clear();
        });

        inputArea.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                Main_Controller.getInstance().sendMsg(sender, recipient, inputArea.getText(), 0);
                conversation.appendText(inputArea.getText() + System.getProperty("line.separator"));
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
/*
    @Override
    public void run() {
        while(true) {
            try {
                sleep(100);
                InputStream inFromServer = clientSocket.getInputStream();
                ObjectInputStream in = new ObjectInputStream(inFromServer);
                FloatingMsg msg = (FloatingMsg)in.readObject();
                conversation.appendText("Server says " + msg.getMessage()+System.getProperty("line.separator"));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch(IOException e) {
                conversation.appendText("Server has close the connection: "+ e +System.getProperty("line.separator"));
                break;
            }
            catch (NullPointerException e){
                conversation.appendText("You are not connected to a server" +System.getProperty("line.separator"));
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMsg(int sender, int recipient, String msg, int specialInfo){
        OutputStream outToServer = null;
        try {
            outToServer = clientSocket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outToServer);
            out.writeObject(new FloatingMsg(sender, recipient, msg, specialInfo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
