package mainWindow;

import conversationWindow.All_In_One_ConversationWindow;
import conversationWindow.ConversationsTextAreas;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import logInWindow.Login_Controller;
import mainWindow.model.Contact;
import msg.FloatingMsg;

import java.io.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

/**
 * Created by marcin on 09/06/2016.
 */
public class Main_Controller implements Runnable, Serializable{
    private static Main_Controller ourInstance = null;
    private static Label connectionStatus = new Label("Not Connected");

    public static Main_Controller getInstance() {
        if(ourInstance==null){
            ourInstance = new Main_Controller();
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
//--------------------------- Recent Additions - Moving server connection from conversation window to main window ------------------------------------------
Socket clientSocket;
private ConversationsTextAreas conversations;

public Main_Controller(){
    conversations = new ConversationsTextAreas();

}

public void addTextToConversation(String text, int position){
    conversations.addTextToConversation(text, position);
}
public TextArea getConversationTextArea(int position){
    return conversations.getConversationTextArea(position);
}


public void loginToServer(){
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
}
@Override
public void run() {
    while(true) {

        try {
            sleep(100);
            InputStream inFromServer = clientSocket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(inFromServer);
            FloatingMsg msg = (FloatingMsg)in.readObject();
            addTextToConversation(("Server says " + msg.getMessage()+System.getProperty("line.separator")), msg.getRecipient());
            System.out.println("msg to client window (new window or unhide existing conversation window)");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            //conversation.appendText("Server has close the connection: "+ e +System.getProperty("line.separator"));
            break;
        }
        catch (NullPointerException e){
            //conversation.appendText("You are not connected to a server" +System.getProperty("line.separator"));
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
    public void createConversationWindow(Contact recipient){
        All_In_One_ConversationWindow conversationWindow = new All_In_One_ConversationWindow(
                Login_Controller.getInstance().getLoggedInUserAddress(),recipient.getAddress());
        Stage conversationStage = new Stage();
        Scene myScene = new Scene((Parent) conversationWindow.getNode(), 300, 275);
        conversationStage.setTitle(recipient.getName());
        conversationStage.setScene(myScene);
        conversationStage.setResizable(false);
        conversationStage.show();
    }

}
