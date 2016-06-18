package Changed_Unused;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by marcin on 29/05/2016.
 */
public class C_GUI{
    public Node getNode(int sender, int recipient){


            C_Controller controller = new C_Controller();
            BorderPane rootNode = new BorderPane();

            rootNode.setTop(new Label(" will be replaced with chatter info "));

            VBox mainPartOfWindow = new VBox();
            TextArea conversationPreview = controller.getConversationPreview();
            conversationPreview.setEditable(false);
            conversationPreview.setPrefRowCount(100);
            conversationPreview.setFocusTraversable(false);

            TextArea inputArea = new TextArea();
            inputArea.setPrefRowCount(4);

            // Focus request to inputArea, unused since focus disabled in line 34 for conversationPreview
            //Platform.runLater(new Runnable() {
            //    @Override
            //    public void run() {
            //        inputArea.requestFocus();
            //    }
            //});

            mainPartOfWindow.setSpacing(10);
            mainPartOfWindow.getChildren().addAll(conversationPreview, inputArea);
            rootNode.setCenter(mainPartOfWindow);

            Button myButton = new Button("Send");
            myButton.setOnAction(e -> {
                controller.sendMessage(sender, recipient, inputArea.getText(), 0);
                controller.addToConversationPreview(inputArea.getText() + System.getProperty("line.separator"));
                inputArea.clear();
            });
            //
            //inputArea.setOnKeyPressed(ke -> {
            //if (ke.getCode().equals(KeyCode.ENTER))
            //{
            //C_Controller.getInstance().sendMessage(inputArea.getText());
            //conversationPreview.appendText(inputArea.getText()+System.getProperty("line.separator"));
            //inputArea.clear();
            //}
            //});
            //Event filter uses the same EventHandler interface. The difference is only that it is called before any event handler.
            //If an event filter consumes the event, no event handlers are fired for that event.
            //
            inputArea.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    controller.sendMessage(sender, recipient, inputArea.getText(), 0);
                    controller.addToConversationPreview(inputArea.getText() + System.getProperty("line.separator"));
                    inputArea.setText("");
                    ke.consume(); // necessary to prevent event handlers for this event
                }
            });


            HBox myBottomBox = new HBox();
            myBottomBox.setAlignment(Pos.CENTER_RIGHT);
            myBottomBox.getChildren().addAll(myButton);
            myBottomBox.setPadding(new Insets(10, 0, 0, 0));
            rootNode.setBottom(myBottomBox);

            rootNode.setPadding(new Insets(10, 10, 10, 10));

        return rootNode;
        }
}
