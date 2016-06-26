package logInWindow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
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

    public Node getNode(BorderPane rootNode) throws Exception {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("JCommunicator");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        sceneTitle.setFill(Color.WHITE);
        grid.setStyle("-fx-background-color: #565A5C;-fx-border-color: beige");
        grid.add(sceneTitle, 1, 0, 2, 1);

        Image javaLogo = new Image("file:Java3.png");
        ImageView javaLogoIV = new ImageView(javaLogo);
        javaLogoIV.setFitHeight(50);
        javaLogoIV.setFitWidth(50);
        grid.add(javaLogoIV, 0,0);

        Label userName = new Label("User Name:");
        userName.setTextFill(Color.WHITE);
        grid.add(userName, 0, 1);

        TextField login = new TextField();
        grid.add(login, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);
        pw.setTextFill(Color.WHITE);
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
                if(Login_Controller.getInstance().login(login.getText(), pwd.getText(), rootNode)){
                    try {
                        Thread.sleep(500);
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        });
        pwd.setOnAction(e->{
            if(login.getText().equals(""))
                System.out.println("empty login");
            else if(pwd.getText().equals(""))
                System.out.println("empty password");
            else
            if(Login_Controller.getInstance().login(login.getText(), pwd.getText(), rootNode)){
                try {
                    Thread.sleep(500);
                    ((Node)(e.getSource())).getScene().getWindow().hide();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        Label loginInfo = Login_Controller.getInstance().getStatus();
        grid.add(loginInfo,1,5);
        return grid;
    }
}
