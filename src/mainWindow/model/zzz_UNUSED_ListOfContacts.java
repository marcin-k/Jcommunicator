package mainWindow.model;

import java.util.ArrayList;
/*
*
 * Created by marcin on 11/06/2016.
*/
public class zzz_UNUSED_ListOfContacts {
    private ArrayList<Contact> contacts;


    // Constructor initiate empty arraylist
    public zzz_UNUSED_ListOfContacts(){
        this.contacts = new ArrayList<Contact>();
    }
    //------------------------------------Returns all Politicians in Dail-------------------------------------------------
    public ArrayList<Contact> getContacts(){
        return contacts;
    }

    //------------------------------------------------------------------------------------------
    public void addContact(Contact c){
        this.contacts.add(c);
    }
}
