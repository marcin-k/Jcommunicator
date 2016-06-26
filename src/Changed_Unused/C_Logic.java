package Changed_Unused;

import msg.FloatingMsg;

import java.io.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

/**
 * Created by marcin on 30/05/2016.
 */
public class C_Logic implements Runnable, Serializable{
    Socket clientSocket;
    Thread t;
    String server = "localhost";
    int port = 1777;
    static C_conversationTextArea conversationTextArea = new C_conversationTextArea();

    public C_Logic(){
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
                conversationTextArea.add("Server says " + msg.getMessage()+System.getProperty("line.separator"));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch(IOException e) {
                conversationTextArea.add("Server has close the connection: "+ e +System.getProperty("line.separator"));
                break;
            }
            catch (NullPointerException e){
                conversationTextArea.add("You are not connected to a server" +System.getProperty("line.separator"));
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendMsg(int sender, int recipient, String msg, int specialInfo){

        //sends msg
        OutputStream outToServer = null;
        try {
            outToServer = clientSocket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outToServer);
            //out.writeObject(new FloatingMsg(sender, recipient, msg, 0 ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void close(){
        //close thread
    }
    public static javafx.scene.control.TextArea getConvesationArea(){
        return conversationTextArea.get();
    }
    public void addToConversationPreview(String text){
        conversationTextArea.add(text);
    }
}
