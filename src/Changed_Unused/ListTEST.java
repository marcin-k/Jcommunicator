package Changed_Unused;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.util.ArrayList;

/**
 * Created by marcin on 30/06/2016.
 */
public class ListTEST {
    private static ObservableList<Contact> contacts;

    public ListTEST(){
        contacts = FXCollections.observableArrayList();
    }

    public void load(ArrayList<Contact> aL){
        contacts = FXCollections.observableArrayList(aL);
    }



    public ObservableList<Contact> getList(){
            return contacts;

    }
    public void refresh(){
        ObservableList<Contact> tempList= FXCollections.observableArrayList();
        for (Contact c:contacts) {

            tempList.add(c);
        }
        contacts = tempList;
    }


}
