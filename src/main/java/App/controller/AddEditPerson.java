package App.controller;

import App.dataModel.UserData;
import App.database.UserDb;
import App.utile.Constant;
import App.utile.FxmlUtile;
import App.utile.MyDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddEditPerson {

    @FXML
    private TextField jobText;

    @FXML
    private ComboBox<String> positionComboBox;

    @FXML
    private TextField nameText;

    @FXML
    private TextField telText;

    @FXML
    private ComboBox<String> roleComboBox;
    private ObservableList<String> positionList = FXCollections.observableArrayList("设计人员", "主管", "主任", "部长");
    private ObservableList<String> roleList = FXCollections.observableArrayList("用户", "管理员", "超级管理员");
    private UserData userData = new UserData();
    private String editId;
    FxmlUtile fxmlUtile = FxmlUtile.getInstance();

    @FXML
    private void initialize() {
        positionComboBox.setItems(FXCollections.observableArrayList(Constant.getPositionList()));
        roleComboBox.setItems(FXCollections.observableArrayList(Constant.roleList));
    }

    @FXML
    void handleOk(ActionEvent event) {
        userData.setName(nameText.getText());
        userData.setTel(telText.getText());
        userData.setJobNum(jobText.getText());
        userData.setPosition(positionComboBox.getValue());
        userData.setRole(roleComboBox.getValue());

        if (nameText.getText().length() < 1 ||
                jobText.getText().length() < 1 ||
                positionComboBox.getValue() == null ||
                positionComboBox.getValue().length() < 1 ||
                roleComboBox.getValue() == null ||
                roleComboBox.getValue().length() < 1) {
            MyDialog.warning("警告", "有必填项未填写");
            return;
        }

        if (editId != null) {
            UserDb.update(userData, Integer.valueOf(editId));
            closeAddEditPerson(event);
            MyDialog.information(null, userData.getName() + "的信息已修改!");
        } else {
//            UserDb.insert(userData);
//            closeAddEditPerson(event);
//            MyDialog.information(null, "已添加用户" + userData.getName());
        }
    }

    @FXML
    void handleCancel(ActionEvent event) {
        closeAddEditPerson(event);
    }

    //将选择的人员信息添加到修改界面中
    private void setUserData(UserData editUserData) {
        this.editId = editUserData.getId();
        if (editId != null) {
            this.userData = editUserData;
            nameText.setText(userData.getName());
            telText.setText(userData.getTel());
            jobText.setText(userData.getJobNum());
            positionComboBox.setValue(userData.getPosition());
            roleComboBox.setValue(userData.getRole());
        }
    }

    //显示添加、修改人员界面
    void showAddEditPerson(UserData userData) throws IOException {
        fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/AddEditPerson.fxml");
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("修改");
        stage.setScene(scene);
        AddEditPerson addEditPerson = loader.getController();
        addEditPerson.setUserData(userData);
        stage.showAndWait();
    }

    //关闭添加、修改人员界面
    private void closeAddEditPerson(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
