package App.controller;

import App.Main;
import App.database.UserDb;
import App.utile.FxmlUtile;
import App.utile.MyDialog;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindows {

    @FXML
    private Button forgetPwdBtn;

    @FXML
    private PasswordField password;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField userName;

    @FXML
    private Button signInButton;

    @FXML
    void goHome(ActionEvent event) throws IOException {

        if (userName.getText().trim().length() < 1 || password.getText().trim().length() < 1) {
            MyDialog.information("登录失败", "用户名或密码未输入");
            return;
        }

        String userNameText = userName.getText().trim();
        String userPassword = password.getText().trim();

        if (!UserDb.getUserNameList().contains(userNameText)) {
            MyDialog.information("该用户不存在", "请先注册");
            return;
        }

        if (!userPassword.matches("^[A-Za-z0-9]+$")) {
            MyDialog.error("密码格式错误", "密码只能包含字母和数字");
            return;
        }

        if (!UserDb.getPasswordOfUser(userNameText).equals(userPassword)) {
            MyDialog.error("登录失败", "密码输入有误");
            return;
        }

        Main main = new Main();
        main.closeLoginWindows(event);
        Home hc = new Home();
        hc.showHomeWindows();
    }

    FxmlUtile fxmlUtile = FxmlUtile.getInstance();

    @FXML
    void signUpAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/SignUp.fxml");
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("注册");
        stage.getIcons().add(new Image("App/appView/images/used/注册.png"));
        stage.showAndWait();
    }

    @FXML
    void forgetPwdAction(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/SignUp.fxml");
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("重置密码");
        stage.getIcons().add(new Image("App/appView/images/used/重置密码.png"));
        stage.showAndWait();
    }

}
