package App.controller;

import java.io.IOException;

import App.dataModel.SuggestionData;
import App.database.SuggestionDatabase;
import App.function.FileReader;
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

public class AddSuggestion {

    @FXML
    private TextArea solutionDescribeTextArea;

    @FXML
    private TextArea problemDescribeTextArea;

    @FXML
    private ComboBox<String> shipTypeChoiceBox;

    @FXML
    private TextField suggestionIDTextFiled;

    @FXML
    private ComboBox<String> shipCompanyChoiceBox;

    @FXML
    private TextField outfittingTextField;

    @FXML
    private Hyperlink sugFilePathHyperLink;

    @FXML
    private TextArea sugFileContentTextArea;

    private SuggestionData suggestionData = new SuggestionData();
    private String editSuggestionId;

    private ObservableList<String> shipTypeList = FXCollections.observableArrayList(null, "油轮", "散货船", "集装箱船", "平台", "豪华游轮");
    private ObservableList<String> classificationSocietyList = FXCollections.observableArrayList(null, "CCS", "BV", "ABS", "DNV-GL", "LR");

    @FXML
    void initialize() {
        shipTypeChoiceBox.setItems(shipTypeList);
        shipCompanyChoiceBox.setItems(classificationSocietyList);
    }

    //将选择的船东意见信息添加到修改界面中
    private void setSuggestionData(SuggestionData suggestionData) {
        this.editSuggestionId = suggestionData.getSugId();

        if (editSuggestionId != null) {
            suggestionIDTextFiled.setText(suggestionData.getSugId());
            outfittingTextField.setText(suggestionData.getSugOutfittingRegion());
            problemDescribeTextArea.setText(suggestionData.getSugProblemDescribe());
            solutionDescribeTextArea.setText(suggestionData.getSugSolutionDescribe());
            shipTypeChoiceBox.setValue(suggestionData.getSugShipType());
            shipCompanyChoiceBox.setValue(suggestionData.getSugShipCompany());
            sugFilePathHyperLink.setText(suggestionData.getSugFilePath());
            sugFileContentTextArea.setText(suggestionData.getSugContent());
        }
    }

    @FXML
    public void handleOk(ActionEvent event) throws IOException {

        suggestionData.setSugId(suggestionIDTextFiled.getText());
        suggestionData.setSugShipCompany(shipCompanyChoiceBox.getValue());
        suggestionData.setSugShipType(shipTypeChoiceBox.getValue());
        suggestionData.setSugOutfittingRegion(outfittingTextField.getText());
        suggestionData.setSugProblemDescribe(problemDescribeTextArea.getText());
        suggestionData.setSugSolutionDescribe(solutionDescribeTextArea.getText());
        suggestionData.setSugFilePath(sugFilePathHyperLink.getText());
        suggestionData.setSugContent(sugFileContentTextArea.getText());

        if (editSuggestionId != null) {
            SuggestionDatabase.update(suggestionData, editSuggestionId);
            closeAddSuggestion(event);
        } else {
            SuggestionDatabase.insert(suggestionData);
            closeAddSuggestion(event);
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        closeAddSuggestion(event);
    }

    @FXML
    void handleDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        if (dragboard.hasFiles()) {
            System.out.println(dragboard.getFiles());
            String[] temp = dragboard.getFiles().toString().split("\\[");
            sugFilePathHyperLink.setText(temp[temp.length - 1].split("]")[0]);
            sugFileContentTextArea.setText(FileReader.readFileContent(temp[temp.length - 1].split("]")[0]));
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    void handleDragOver(DragEvent event) {
        if (event.getGestureSource() != sugFileContentTextArea && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    //显示意见添加页面
    void showAddSuggestion(SuggestionData suggestionData) throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/AddSuggestion.fxml");
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setTitle("Add/Edit SuggestionData");
        stage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL表示一次只能打开一个窗口进行操作
        stage.setScene(scene);

        AddSuggestion addSuggestion = loader.getController();
        addSuggestion.setSuggestionData(suggestionData);

        stage.showAndWait();
    }

    //关闭界面
    private void closeAddSuggestion(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
