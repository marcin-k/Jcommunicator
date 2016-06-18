package msg;

import java.io.Serializable;

/**
 * Created by marcin on 05/06/2016.
 */
public class FloatingMsg implements Serializable {
    private int sender;
    private int recipient;
    private String message;
    private int specialInfo;
    /* specialInfo: 0 - ignore
                    1 - new connection
                    2 - close connection
    */
    public FloatingMsg(int sender, int recipient, String message, int specialInfo){
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.specialInfo = specialInfo;
    }


    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getRecipient() {
        return recipient;
    }

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSpecialInfo(){
        return specialInfo;
    }
}
