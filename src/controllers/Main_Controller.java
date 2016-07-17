package controllers;

import javafx.scene.control.Alert;
import view.ConversationWindow;
import model.ConversationsTextAreas;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Contact;
import model.FloatingMsg;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import static java.lang.Thread.sleep;

/**
 * Created by marcin on 09/06/2016.
 *
 */
public class Main_Controller implements Runnable, Serializable{
    private static Main_Controller ourInstance = null;
    private static Label connectionStatus = new Label("Not Connected"); //Connection indicator
    Socket clientSocket; //socket used to communicate with server
    private ConversationsTextAreas conversations;
    Media hit; //mp3 file (incoming message sound)
    MediaPlayer mediaPlayer; //class to play incoming message sound


    //Constructor
    private Main_Controller(){
        conversations = new ConversationsTextAreas();
        connectionStatus.setStyle("-fx-background-color: red; -fx-alignment: center; -fx-font-family: \"Bradley Hand\";");
        connectionStatus.setPrefWidth(296);
        setUpOpenWindows();
        hit = new Media(new File("beep3.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
    }

    public static Main_Controller getInstance() {
        if(ourInstance==null){
            ourInstance = new Main_Controller();
        }
        return ourInstance;
    }
//--------------------------- NON-SINGLETON PART OF A CLASS -------------------------------------
    //set the label to indicated successful connection to server
    public void setConnectionConnected(){
        connectionStatus.setText("Connected");
        connectionStatus.setStyle("-fx-background-color: greenyellow; -fx-alignment: center; -fx-font-family: \"Bradley Hand\";");
    }
    //set the label to indicated disconeccion from server (currently unused)
    //public void setConnectionNotConnected(){
    //    connectionStatus.setText("Not Connected");
    //    connectionStatus.setStyle("-fx-background-color: red; -fx-alignment: center; -fx-font-family: \"Bradley Hand\";");
    //}

    //returns status connection label
    public Label getConnectionStatus(){
        return connectionStatus;
    }

    //adds a text to conversation window
    public void addTextToConversation(String text, int position){
        conversations.addTextToConversation(text, position);
    }

    //
    public TextArea getConversationTextArea(int position){
        return conversations.getConversationTextArea(position);
    }

//-------------------Creates a new Thread, that listens for a messages----------------
    public void loginToServer(){
        Thread t;
        //String server = "192.168.8.7";
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
                System.out.println("msg received"+msg.getMessage());
                String serverSays = msg.getSendersFirstName()+" says: ";

                //------------update status message----------------------------------------------
                if(msg.getSpecialInfo()==8){
                    if(msg.getSendersLastName().equals("available")){
                        Login_Controller.getInstance().setContactAsAvailable(msg.getSender());
                    }
                    if(msg.getSendersLastName().equals("notAvailable")){
                        Login_Controller.getInstance().setContactAsNotAvailable(msg.getSender());
                    }
                    Login_Controller.getInstance().setStatusForContact(msg.getSender(),msg.getMessage());
                }
                else{
                    //Plays incoming message sound
                    playMessageSound();
                    addTextToConversation((serverSays + msg.getMessage() + System.getProperty("line.separator")), msg.getSender());
                    if (isWindowOpen(msg.getSender()) == 0) {
                        System.out.println("trying to open new window");
                        createConversationWindow(new Contact(msg.getSender(), msg.getSendersFirstName(), msg.getSendersLastName()));
                    }
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
        }
        catch (SocketException e){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection lost");
                alert.setContentText("Application will restart!");
                alert.showAndWait();
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateStatus(String status){
        Main_Controller.getInstance().sendMsg(Login_Controller.getInstance().getLoggedInUserAddress(), 0, status, 2, "", "");
    }
//-------------------Creates a new conversation window-------------------------------
    //Created by GUI class or incoming message if window is marked as closed
    public void createConversationWindow(Contact recipient) {
        Platform.runLater(() -> {
            if(isWindowOpen(recipient.getAddress())==0) {
                ConversationWindow conversationWindow = new ConversationWindow(
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
    public void closeThread(){
        sendMsg(Login_Controller.getInstance().getLoggedInUserAddress(),0,"",9,"","");
    }




}
