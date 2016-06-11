package msg;

import java.io.Serializable;

/**
 * Created by marcin on 05/06/2016.
 */
public class FloatingMsg implements Serializable {
    private int sender;
    private int recipient;
    private String message;

    public FloatingMsg(int sender, int recipient, String message){
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
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
}
