package logInWindow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by marcin on 08/06/2016.
 */
public class Login_GUI {
    public Login_GUI(){
        Login_Controller.getInstance().resetLabel();
    }

    public Node getNode() throws Exception {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField login = new TextField();
        grid.add(login, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwd = new PasswordField();
        grid.add(pwd, 1, 2);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        btn.setOnAction(event -> {
            if(login.getText().equals(""))
                System.out.println("empty login");
            else if(pwd.getText().equals(""))
                System.out.println("empty password");
            else
                if(Login_Controller.getInstance().login(login.getText(), pwd.getText())){
                    try {
                        Thread.sleep(2000);
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        });

        Label loginInfo = Login_Controller.getInstance().getStatus();
        grid.add(loginInfo,1,5);
        return grid;
    }
}
