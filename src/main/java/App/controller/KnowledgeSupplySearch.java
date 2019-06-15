package App.controller;

import App.dataModel.*;
import App.database.CriterionDatabase;
import App.database.ExperienceDatabase;
import App.database.SuggestionDatabase;
import App.database.TabooDatabase;
import App.utile.HyperlinkTableCell;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class KnowledgeSupplySearch {

    @FXML
    private TableColumn<?, ?> c35;
    @FXML private TableColumn<?, ?> sugContentTC;
    @FXML private TableColumn<SuggestionData, String> sugFilePathTC;
    @FXML private TextArea sugContentTA;

    @FXML
    private TableColumn<?, ?> suggestionOutfittingRegionColumn;

    @FXML
    private TextField experienceOutfittingTypeTextField1;

    @FXML
    private TableColumn<?, ?> tabooOutfittingRegionColumn;

    @FXML
    private TextField tabooSearchTextField;

    @FXML
    private ComboBox<String> criterionShipTypeCB;

    @FXML
    private TextField suggestionSearchTextField;

    @FXML
    private TextField guifanOutfittingTypeTextField;

    @FXML
    private ComboBox<String> tabooLevelCB;

    @FXML
    private TextArea suggestionProblemTextArea;

    @FXML
    private TableView<SuggestionData> suggestionTable;

    @FXML
    private TableColumn<?, ?> suggestionIdColumn;

    @FXML
    private TableColumn<?, ?> tabooIdColumn;

    @FXML
    private ComboBox<String> suggestionChuandongCompanyCB;

    @FXML
    private TableColumn<?, ?> tabooNameColumn;

    @FXML
    private TableColumn<?, ?> c46;

    @FXML
    private ComboBox<String> criterionChuanjisheCB;

    @FXML
    private TableColumn<?, ?> criterionShipTypeColumn;

    @FXML
    private TableColumn<?, ?> c48;

    @FXML
    private TableColumn<?, ?> c47;

    @FXML
    private TableColumn<?, ?> c49;

    @FXML
    private TextArea tabooContentTextArea;

    @FXML
    private TableColumn<?, ?> experienceNameColumn;

    @FXML
    private ComboBox<String> suggestionShipTypeCB;

    @FXML
    private TextArea suggestionSolutionTextArea;

    @FXML
    private TextField criterionSearchTextField;

    @FXML
    private TextArea experienceContentTextArea;

    @FXML
    private TableColumn<?, ?> experienceContentColumn;

    @FXML
    private TableColumn<?, ?> experienceShipTypeColumn;
    @FXML private TableColumn<ExperienceData, String> expFilePathTC;

    @FXML
    private ComboBox<String> tabooShipTypeCB;

    @FXML
    private TableColumn<?, ?> problemColumn;

    @FXML
    private TableColumn<?, ?> experienceIdColumn;

    @FXML
    private TableColumn<?, ?> tabooShipTypeColumn;

    @FXML
    private TextField jinjiNameTextField;

    @FXML
    private TableColumn<?, ?> criterionOutfittingRegionColumn;

    @FXML
    private TableColumn<?, ?> tabooContentColumn;

    @FXML
    private TextField jinjioutfittingTypeTextField;

    @FXML
    private TextField experienceNameTextField1;

    @FXML
    private TextField guifanNameTextField;

    @FXML
    private TableColumn<?, ?> criterionIdColumn;

    @FXML
    private TableColumn<?, ?> suggestionShipTypeColumn;

    @FXML
    private TableColumn<?, ?> solutionColumn;

    @FXML
    private ComboBox<String> experienceShipTypeCB;

    @FXML
    private TableView<ExperienceData> experienceTable;

    @FXML
    private TableColumn<?, ?> criterionNameColumn;

    @FXML
    private TableColumn<CriterionData, String> criterionUrlTC;

    @FXML
    private TableColumn<?, ?> criterionClassificationSocietyTC;

    @FXML
    private TableView<TabooData> tabooTable;

    @FXML
    private TableView<CriterionData> criterionTable;

    @FXML
    private TextField soutfittingTypeTextField;

    @FXML
    private TextArea criterionContentTextArea;

    @FXML
    private TextField experienceSearchTextField;

    @FXML
    private TableColumn<?, ?> criterionContentColumn;

    @FXML
    private TableColumn<?, ?> experienceOutfittingRegionColumn;

    @FXML
    void handleCriterionSearch(ActionEvent event) {
        try {
            criterionTable.setItems(CriterionDatabase.query(
                    criterionSearchTextField.getText(),
                    criterionShipTypeCB.getValue(),
                    criterionChuanjisheCB.getValue(),
                    guifanNameTextField.getText(),
                    guifanOutfittingTypeTextField.getText()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleResetCriterion(ActionEvent event) {
        criterionSearchTextField.setText(null);
        criterionChuanjisheCB.setValue(null);
        criterionShipTypeCB.setValue(null);
        guifanNameTextField.setText(null);
        guifanOutfittingTypeTextField.setText(null);
        criterionTable.setItems(CriterionDatabase.getCriterionDataList());
    }

    @FXML
    void handleSuggestionSearch(ActionEvent event) {
        try {
            suggestionTable.setItems(SuggestionDatabase.query(
                    suggestionChuandongCompanyCB.getValue(),
                    suggestionShipTypeCB.getValue(),
                    soutfittingTypeTextField.getText(),
                    suggestionSearchTextField.getText()
            ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleResetSuggestion(ActionEvent event) {
        suggestionChuandongCompanyCB.setValue(null);
        suggestionShipTypeCB.setValue(null);
        soutfittingTypeTextField.setText(null);
        suggestionSearchTextField.setText(null);
        suggestionTable.setItems(SuggestionDatabase.getSugDataList());
    }

    @FXML
    void handleTabooSearch(ActionEvent event) {
        String keyword = tabooShipTypeCB.getValue() +
                tabooLevelCB.getValue() +
                jinjiNameTextField.getText() +
                jinjioutfittingTypeTextField.getText() +
                tabooSearchTextField.getText();
        try {
            tabooTable.setItems(TabooDatabase.query(keyword));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleResetTaboo(ActionEvent event) {
        tabooTable.setItems(TabooDatabase.getTabooDataList());
    }

    @FXML
    void handleExperienceSearch(ActionEvent event) {
        try {
            experienceTable.setItems(ExperienceDatabase.query(
                    experienceShipTypeCB.getValue(),
                    experienceOutfittingTypeTextField1.getText(),
                    experienceNameTextField1.getText(),
                    experienceSearchTextField.getText()
            ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleResetExperience(ActionEvent event) {
        experienceShipTypeCB.setValue(null);
        experienceOutfittingTypeTextField1.setText(null);
        experienceNameTextField1.setText(null);
        experienceSearchTextField.setText(null);
        experienceTable.setItems(ExperienceDatabase.getExpDataList());
    }

    private ObservableList<String> jinjiLevelList = FXCollections.observableArrayList(null, "公司级", "部门级", "船级社级");
    private ObservableList<String> shipTypeList = FXCollections.observableArrayList(null, "油轮", "散货船", "集装箱船", "平台", "豪华游轮");
    private ObservableList<String> classificationSocietyList = FXCollections.observableArrayList(null, "CCS", "BV", "ABS", "DNV-GL", "LR");

    private String criFile;
    private String sugFile;
    private String expFile;

    @FXML
    void initialize() {
        final ComboBox<String> comboBox = new ComboBox<String>() {
            protected javafx.scene.control.Skin<?> createDefaultSkin() {
                return new ComboBoxListViewSkin<String>(this) {
                    @Override
                    protected boolean isHideOnClickEnabled() {
                        return false;
                    }
                };
            }
        };
        //criterionTable绑定
        criterionShipTypeCB.setItems(shipTypeList);
        criterionShipTypeCB.setVisibleRowCount(5);
        criterionChuanjisheCB.setItems(classificationSocietyList);
        criterionChuanjisheCB.setVisibleRowCount(5);
        criterionIdColumn.setCellValueFactory(new PropertyValueFactory<>("criId"));
        criterionShipTypeColumn.setCellValueFactory(new PropertyValueFactory<>("criShipType"));
        criterionClassificationSocietyTC.setCellValueFactory(new PropertyValueFactory<>("criShipCompany"));
        criterionOutfittingRegionColumn.setCellValueFactory(new PropertyValueFactory<>("criOutfittingRegion"));
        criterionNameColumn.setCellValueFactory(new PropertyValueFactory<>("criName"));
        criterionContentColumn.setCellValueFactory(new PropertyValueFactory<>("criContent"));
        criterionUrlTC.setCellValueFactory(new PropertyValueFactory<>("criFilePath"));
        criterionUrlTC.setCellFactory(param -> new HyperlinkTableCell<>());

        criterionTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CriterionData>() {
            @Override
            public void changed(ObservableValue<? extends CriterionData> observable, CriterionData oldValue, CriterionData newValue) {
                if (newValue != null) {
                    showCriterionDetail(newValue);
                    criFile = newValue.getCriFilePath();
                }
            }
        });

        MenuItem criItem1 = new MenuItem("打开文件");
        criItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(criFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        MenuItem criItem2 = new MenuItem("打开文件夹");
        criItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(new File(criFile).getParent()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ContextMenu criMenu = new ContextMenu();
        criMenu.getItems().addAll(criItem1, criItem2);
        criterionTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                criMenu.show(criterionTable, event.getScreenX(), event.getScreenY());
            }
        });
        criterionTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                criMenu.hide();
            }
        });

        //suggestionTable绑定
        suggestionShipTypeCB.setItems(shipTypeList);
        suggestionChuandongCompanyCB.setItems(classificationSocietyList);
        suggestionIdColumn.setCellValueFactory(new PropertyValueFactory<>("sugId"));
        suggestionShipTypeColumn.setCellValueFactory(new PropertyValueFactory<>("sugShipType"));
        c35.setCellValueFactory(new PropertyValueFactory<>("sugShipCompany"));
        suggestionOutfittingRegionColumn.setCellValueFactory(new PropertyValueFactory<>("sugOutfittingRegion"));
        problemColumn.setCellValueFactory(new PropertyValueFactory<>("sugProblemDescribe"));
        solutionColumn.setCellValueFactory(new PropertyValueFactory<>("sugSolutionDescribe"));
        sugContentTC.setCellValueFactory(new PropertyValueFactory<>("sugContent"));
        sugFilePathTC.setCellValueFactory(new PropertyValueFactory<>("sugFilePath"));
        sugFilePathTC.setCellFactory(param -> new HyperlinkTableCell<>());

        suggestionTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SuggestionData>() {
            @Override
            public void changed(ObservableValue<? extends SuggestionData> observable, SuggestionData oldValue, SuggestionData newValue) {
                if (newValue != null) {
                    showSuggestionDetail(newValue);
                    sugFile = newValue.getSugFilePath();
                }
            }
        });

        MenuItem sugItem1 = new MenuItem("打开文件");
        sugItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(sugFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        MenuItem sugItem2 = new MenuItem("打开文件夹");
        sugItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(new File(sugFile).getParent()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ContextMenu sugMenu = new ContextMenu();
        sugMenu.getItems().addAll(sugItem1, sugItem2);
        suggestionTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                sugMenu.show(suggestionTable, event.getScreenX(), event.getScreenY());
            }
        });
        suggestionTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sugMenu.hide();
            }
        });

        //tabooTable绑定
        tabooShipTypeCB.setItems(shipTypeList);
        tabooLevelCB.setItems(jinjiLevelList);
        tabooIdColumn.setCellValueFactory(new PropertyValueFactory<>("jinjiID"));
        tabooShipTypeColumn.setCellValueFactory(new PropertyValueFactory<>("shipType"));
        tabooOutfittingRegionColumn.setCellValueFactory(new PropertyValueFactory<>("outfittingType"));
        tabooNameColumn.setCellValueFactory(new PropertyValueFactory<>("jinjiName"));
        tabooContentColumn.setCellValueFactory(new PropertyValueFactory<>("jinjiContent"));
        tabooTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showTabooDetail(newValue));

        //experienceTable绑定
        experienceShipTypeCB.setItems(shipTypeList);
        experienceIdColumn.setCellValueFactory(new PropertyValueFactory<>("expId"));
        experienceShipTypeColumn.setCellValueFactory(new PropertyValueFactory<>("expShipType"));
        experienceOutfittingRegionColumn.setCellValueFactory(new PropertyValueFactory<>("expOutfittingRegion"));
        experienceNameColumn.setCellValueFactory(new PropertyValueFactory<>("expName"));
        experienceContentColumn.setCellValueFactory(new PropertyValueFactory<>("expContent"));
        expFilePathTC.setCellValueFactory(new PropertyValueFactory<>("expFilePath"));
        expFilePathTC.setCellFactory(param -> new HyperlinkTableCell<>());

        experienceTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ExperienceData>() {
            @Override
            public void changed(ObservableValue<? extends ExperienceData> observable, ExperienceData oldValue, ExperienceData newValue) {
                if (newValue != null) {
                    showExperienceDetail(newValue);
                    expFile = newValue.getExpFilePath();
                }
            }
        });

        MenuItem expItem1 = new MenuItem("打开文件");
        expItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(expFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        MenuItem expItem2 = new MenuItem("打开文件夹");
        expItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(new File(expFile).getParent()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ContextMenu expMenu = new ContextMenu();
        expMenu.getItems().addAll(expItem1, expItem2);
        experienceTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                expMenu.show(experienceTable, event.getScreenX(), event.getScreenY());
            }
        });
        experienceTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                expMenu.hide();
            }
        });
    }

    void refreshAllTable() {
        criterionTable.setItems(CriterionDatabase.getCriterionDataList());
        suggestionTable.setItems(SuggestionDatabase.getSugDataList());
        tabooTable.setItems(TabooDatabase.getTabooDataList());
        experienceTable.setItems(ExperienceDatabase.getExpDataList());
    }

    private void showCriterionDetail(CriterionData criterionData) {
        if (criterionData != null) {
            criterionContentTextArea.setText(criterionData.getCriContent());
        } else {
            criterionContentTextArea.setText(null);
        }
    }

    private void showSuggestionDetail(SuggestionData suggestionData) {
        if (suggestionData != null) {
            suggestionProblemTextArea.setText(suggestionData.getSugProblemDescribe());
            suggestionSolutionTextArea.setText(suggestionData.getSugSolutionDescribe());
            sugContentTA.setText(suggestionData.getSugContent());
        } else {
            suggestionProblemTextArea.setText("");
            suggestionSolutionTextArea.setText("");
            sugContentTA.setText("");
        }
    }

    private void showTabooDetail(TabooData tabooData) {
        if (tabooData != null) {
            tabooContentTextArea.setText(tabooData.getJinjiContent());
        } else {
            tabooContentTextArea.setText("");
        }
    }

    private void showExperienceDetail(ExperienceData experienceData) {
        if (experienceData != null) {
            experienceContentTextArea.setText(experienceData.getExpContent());
        } else {
            experienceContentTextArea.setText("");
        }
    }

}
