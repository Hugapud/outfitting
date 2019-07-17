package App.controller;

import App.dataModel.ExperienceData;
import App.database.ExperienceDb;
import App.function.FileReader;
import App.utile.Constant;
import App.utile.FxmlUtile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddExperience {

    @FXML
    private TextArea experienceTextArea;

    @FXML
    private ComboBox<String> chuanboTypeChoiceBox;

    @FXML
    private TextField experienceNameTextField;

    @FXML
    private TextField outfittingTextField;

    @FXML
    private Hyperlink pathNameHyperlink;
    private ExperienceData experienceData = new ExperienceData();
    private String editExperienceId;

    public static AddExperience getInstance() {
        return AddExperienceInstance.Instance;
    }

    @FXML
    void initialize() {
        chuanboTypeChoiceBox.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
    }

    private void setExperience(ExperienceData experience) {
        this.editExperienceId = experience.getExpId();

        if (editExperienceId != null) {
            outfittingTextField.setText(experience.getExpOutfittingRegion());
            experienceNameTextField.setText(experience.getExpName());
            chuanboTypeChoiceBox.setValue(experience.getExpShipType());
            experienceTextArea.setText(experience.getExpContent());
            pathNameHyperlink.setText(experience.getExpFilePath());
        }
    }

    @FXML
    public void handleOk(ActionEvent event) throws IOException {
        experienceData.setExpName(experienceNameTextField.getText());
        experienceData.setExpShipType(chuanboTypeChoiceBox.getValue());
        experienceData.setExpOutfittingRegion(outfittingTextField.getText());
        experienceData.setExpContent(experienceTextArea.getText());
        experienceData.setExpFilePath(pathNameHyperlink.getText());

        if (editExperienceId != null) {
            ExperienceDb.update(experienceData, editExperienceId);
            closeAddExperience(event);
        } else {
            ExperienceDb.insert(experienceData);
            closeAddExperience(event);
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        closeAddExperience(event);
    }

    @FXML
    void handleDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        if (dragboard.hasFiles()) {
            System.out.println(dragboard.getFiles());
            String[] temp = dragboard.getFiles().toString().split("\\[");
            pathNameHyperlink.setText(temp[temp.length - 1].split("]")[0]);
            experienceTextArea.setText(FileReader.readFileContent(temp[temp.length - 1].split("]")[0]));
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    void handleDragOver(DragEvent event) {
        if (event.getGestureSource() != experienceTextArea && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    //显示经验知识添加页面
    void showAddExperience(ExperienceData experienceData) throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/AddExperience.fxml");
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setTitle("Add/Edit ExperienceData");
        stage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL表示一次只能打开一个窗口进行操作
        stage.setScene(scene);

        AddExperience addExperienceController = loader.getController();
        addExperienceController.setExperience(experienceData);

        stage.showAndWait();
    }

    //关闭界面
    private void closeAddExperience(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    private static class AddExperienceInstance {
        private static final AddExperience Instance = new AddExperience();
    }

}
