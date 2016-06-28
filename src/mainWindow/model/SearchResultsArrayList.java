package mainWindow.model;

import logInWindow.Login_Controller;

import java.util.ArrayList;

/**
 * Created by marcin on 26/06/2016.
 */
public class SearchResultsArrayList {
    ArrayList<Contact> searchResults;
    //Constructor - loads all the contacts in the DB, contacts are filtered down once text is entered in the textfield
    public SearchResultsArrayList(){
        searchResults = Login_Controller.getInstance().getSearchResults();
    }
    //method returns all unfiltered results
    public ArrayList<Contact> getSearchResults(){
        return searchResults;
    }
    //overloaded method returns filtered results
    public ArrayList<Contact> getSearchResults(String filter){
        ArrayList<Contact> filteredResults = new ArrayList<>();
        for(Contact e:searchResults){
            if(e.getName().toLowerCase().contains(filter.toLowerCase())){
                filteredResults.add(e);
            }
        }
        return filteredResults;
    }


}
