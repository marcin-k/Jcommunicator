package view.listCells;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import controllers.Login_Controller;
import model.Contact;

/**
 * Created by marcin on 24/06/2016.
 *
 * Class used to define the display of the contact list
 */
public class ContactListCell extends ListCell<Contact> {
    protected void updateItem(Contact item, boolean empty){
        super.updateItem(item, empty);
        setGraphic(null);
        setText(null);
        if(item!=null){
            String fileName = null;
            if(Login_Controller.getInstance().isContactAvailable(item.getAddress())){
                fileName = "file:Java2.png";
            }
            else {
                fileName = "file:Java1.png";
            }
            ImageView searchIV = new ImageView(new Image(fileName));
            searchIV.setFitWidth(25);
            searchIV.setFitHeight(25);
            setGraphic(searchIV);
            setText(item.toString()+"\n"+item.getStatus());

        }

    }

}