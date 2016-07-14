package view.listCells;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by marcin on 24/06/2016.
 *
 * Class used to define the display of the status change panel (currently unused)
 *  ************************************************************************
 *  * Panel will be added to a bottom of the main app window allowing user *
 *  * to change their status to AFK and Offline(hidden)                    *
 *  ************************************************************************
 */
public class StatusListCell extends ListCell<String> {
    protected void updateItem(String item, boolean empty){
        super.updateItem(item, empty);
        setGraphic(null);
        setText(null);
        if(item!=null){
            ImageView imageView = new ImageView(new Image(item));
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            setGraphic(imageView);
            setText("a");
        }
    }

}