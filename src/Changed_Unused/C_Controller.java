package Changed_Unused;

import javafx.scene.control.TextArea;

/**
 * Created by marcin on 30/05/2016.
 */
public class C_Controller {
    //private static C_Controller ourInstance = null;

    private static TextArea conversation;
    //logic is a server listener thread
    private static C_Logic logic;

    public C_Controller() {
        conversation  = new TextArea();
        logic = new C_Logic();
    }

/*
    public static C_Controller getInstance() {
        if(ourInstance == null){
            ourInstance = new C_Controller();

        }
        return ourInstance;
    }
    */
//------------ NON-SINGLETON PART OF CLASS ------------------------------------------------

    //gets the conversation window
    public TextArea getConversationPreview(){
        return logic.getConvesationArea();
    }
    public void addToConversationPreview(String text){
        logic.addToConversationPreview(text);
    }
    //method in C_Logic to send msg to server and conversation
    public void sendMessage(int sender, int recipient, String msg, int specialInfo){
        logic.sendMsg(sender, recipient, msg, specialInfo);
    }
    //Closes the listening thread
    public void close(){
        logic.close();
    }

}
