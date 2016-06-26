package mainWindow;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by marcin on 24/06/2016.
 */
public class ContactListCell extends ListCell<Object> {
    protected void updateItem(Object item, boolean empty){
        super.updateItem(item, empty);
        setGraphic(null);
        setText(null);
        if(item!=null){
            String fileName = "file:Java1.png";
            ImageView searchIV = new ImageView(new Image(fileName));
            searchIV.setFitWidth(25);
            searchIV.setFitHeight(25);
            setGraphic(searchIV);
            setText(item.toString()+"\n"+"< Status Info >");

        }
    }

}