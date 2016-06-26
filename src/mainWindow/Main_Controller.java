package mainWindow;

import conversationWindow.All_In_One_ConversationWindow;
import conversationWindow.ConversationsTextAreas;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import logInWindow.Login_Controller;
import mainWindow.model.Contact;
import msg.FloatingMsg;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.*;
import java.net.Socket;
import static java.lang.Thread.sleep;

/**
 * Created by marcin on 09/06/2016.
 */
public class Main_Controller implements Runnable, Serializable{
    private static Main_Controller ourInstance = null;
    private static Label connectionStatus = new Label("Not Connected");
    Socket clientSocket;
    private ConversationsTextAreas conversations;
    private boolean searchForContactsWindowIsItOpen = false;
    Media hit;
    MediaPlayer mediaPlayer;

    public static Main_Controller getInstance() {
        if(ourInstance==null){
            ourInstance = new Main_Controller();
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

    public Main_Controller(){
    conversations = new ConversationsTextAreas();
    connectionStatus.setStyle("-fx-background-color: red; -fx-alignment: center; -fx-font-family: \"Bradley Hand\";");
    connectionStatus.setPrefWidth(296);
    setUpOpenWindows();
    hit = new Media(new File("beep3.mp3").toURI().toString());
    mediaPlayer = new MediaPlayer(hit);
}

    public void addTextToConversation(String text, int position){
    conversations.addTextToConversation(text, position);
}
    public TextArea getConversationTextArea(int position){
    return conversations.getConversationTextArea(position);
}

//-------------------Creates a new Thread, that listens for a messages----------------
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
//-------------------New Thread - listens for incoming messages-----------------------
    @Override
    public void run() {
        while(true) {

            try {
                sleep(100);
                InputStream inFromServer = clientSocket.getInputStream();
                ObjectInputStream in = new ObjectInputStream(inFromServer);
                FloatingMsg msg = (FloatingMsg)in.readObject();
                String serverSays = msg.getSendersFirstName()+" says: ";
    //Plays incoming message sound
                playMessageSound();

                // /serverSays.setStyle("-fx-background-color: greenyellow; -fx-font-family: \"Bradley Hand\";")
                addTextToConversation((serverSays + msg.getMessage()+System.getProperty("line.separator")), msg.getSender());
                if(isWindowOpen(msg.getSender())==0){
                    System.out.println("trying to open new window");
                    createConversationWindow(new Contact(msg.getSender(),msg.getSendersFirstName(),msg.getSendersLastName()));
                }
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
//--------Crates new instance of the message and sends it through the socket----------
    public void sendMsg(int sender, int recipient, String msg, int specialInfo, String sendersFirstName, String sendersLastName){
        OutputStream outToServer = null;
        try {
            outToServer = clientSocket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outToServer);
            out.writeObject(new FloatingMsg(sender, recipient, msg, specialInfo, sendersFirstName, sendersLastName));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//-------------------Creates a new conversation window-------------------------------
//Created by GUI class or incoming message if window is marked as closed
    public void createConversationWindow(Contact recipient) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(isWindowOpen(recipient.getAddress())==0) {
                    All_In_One_ConversationWindow conversationWindow = new All_In_One_ConversationWindow(
                            Login_Controller.getInstance().getLoggedInUserAddress(), recipient.getAddress());
                    Stage conversationStage = new Stage();
                    Scene myScene = new Scene((Parent) conversationWindow.getNode(), 300, 275);
                    conversationStage.setTitle(recipient.getName());
                    conversationStage.setScene(myScene);
                    conversationStage.setResizable(false);
                    conversationStage.show();
                    Main_Controller.getInstance().openWindow(recipient.getAddress());
                    //keeps track of closed conversation windows
                    conversationStage.setOnCloseRequest(event -> Main_Controller.getInstance().closeWindow(recipient.getAddress()));
                }
            }
        });
    }
//-------------------Track of open windows-------------------------------------------
    private int[] openWindows = new int[20];
    public void setUpOpenWindows(){
        for(int i=0; i<20; i++){
            openWindows[i]=0;
        }
    }
    public void openWindow(int address){
        openWindows[address]=1;
    }
    public void closeWindow(int address){
        openWindows[address]=0;
    }
    public int isWindowOpen(int address){
        return openWindows[address];
    }
//-------------------Plays a sound of incoming message--------------------------------
    public void playMessageSound(){
        mediaPlayer.dispose();
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }
//-------------------Opens search contact window--------------------------------------
    public void openSearchContactWindow(){
        System.out.println("opening search contacts window");
    }

    public void closeThread(){

        sendMsg(Login_Controller.getInstance().getLoggedInUserAddress(),0,"",9,"","");
    }
}
