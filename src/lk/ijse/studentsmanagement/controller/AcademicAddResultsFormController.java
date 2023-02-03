package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import lk.ijse.studentsmanagement.comboLoad.ComboLoader;
import lk.ijse.studentsmanagement.comboLoad.TableLoader;
import lk.ijse.studentsmanagement.model.RegistrationExamResultModel;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.tblModels.RegistrationExamResultTM;
import lk.ijse.studentsmanagement.tblModels.RegistrationTM;
import lk.ijse.studentsmanagement.entity.RegistrationExamResult;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static lk.ijse.studentsmanagement.comboLoad.ComboLoader.*;

public class AcademicAddResultsFormController implements Initializable {

    public JFXButton btnDelete;
    public Label lblNameId;
    public Label txtSelectBatch;
    public Label txtSelectExam;
    public Label txtInvalidMarks;
    public AnchorPane marksPane;
    public Label lblGrade;
    public AnchorPane pane;
    @FXML
    private JFXComboBox<String> cmbBatch;

    @FXML
    private TableView<RegistrationTM> tblStudents;

    @FXML
    private TableColumn<?, ?> regIdCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> batchCol;

    @FXML
    private JFXComboBox<String> cmbExams;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXTextField txtMarks;

    @FXML
    private JFXTextField txtGrade;

    @FXML
    private Label txtName;

    @FXML
    private TableView<RegistrationExamResultTM> tblResults;

    @FXML
    private TableColumn<?, ?> colRegistrationId;

    @FXML
    private TableColumn<?, ?> colExamID;

    @FXML
    private TableColumn<?, ?> colMark;

    @FXML
    private TableColumn<?, ?> colResult;

    @FXML
    void btnBackOnAction() throws IOException {
        Navigation.navigate(Routes.EXAMS,pane);
    }

    @FXML
    void cmbBatchOnAction() {
        txtSelectBatch.setVisible(false);
        try {
            if (cmbBatch.getValue() != null) {
                cmbExams.setItems(FXCollections.observableArrayList());
                tblStudents.setItems(FXCollections.observableArrayList());

                boolean loadExamId = loadExamId(cmbBatch.getValue(), cmbExams);
                if (!loadExamId) {
                    new Alert(Alert.AlertType.INFORMATION, "No any exam added").show();
                }
            } else {
                txtSelectBatch.setVisible(true);
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }

    @FXML
    void cmbExamIDOnAction() {
        try {
          //  clearAll();
            if (cmbBatch.getValue() != null) {
                if (cmbExams.getValue() != null) {
                    boolean loadStudents = TableLoader.loadBatchRegistrations(tblStudents, cmbBatch.getValue());
                    if (!loadStudents) {
                        new Alert(Alert.AlertType.INFORMATION, "No any student in Batch !").show();
                    } else {
                        boolean isLoaded = loadRegistrationResultTable();
                        if (!isLoaded) {
                            new Alert(Alert.AlertType.INFORMATION, "No any results added").show();

                        } else {
                            new Alert(Alert.AlertType.INFORMATION, "Results Loaded").show();
                        }
                    }
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Select a Exam First").show();
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Select a Batch First").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }

    private boolean loadRegistrationResultTable() throws SQLException, ClassNotFoundException {
        return TableLoader.loadRegistrationEaxmResults(tblResults, cmbExams.getValue());
    }

    @FXML
    void searchBtnOnAction() {
        cmbExamIDOnAction();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // cmbBatch.setDisable(true);
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        txtInvalidMarks.setVisible(false);
        txtSelectExam.setVisible(false);
        txtSelectBatch.setVisible(false);

        txtMarks.setDisable(true);

        try {
            boolean loadBatchIDS = ComboLoader.loadBatchIDS(cmbBatch);
            if (!loadBatchIDS) {
                new Alert(Alert.AlertType.INFORMATION, "No any batches").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.INFORMATION, String.valueOf(e)).show();
        }
        batchCol.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        regIdCol.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        colMark.setCellValueFactory(new PropertyValueFactory<>("mark"));
        colResult.setCellValueFactory(new PropertyValueFactory<>("result"));
        colRegistrationId.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        colExamID.setCellValueFactory(new PropertyValueFactory<>("examId"));
    }

    public void btnAddOnAction() {
        try {
            RegistrationTM selectedItem = tblStudents.getSelectionModel().getSelectedItem();
            if (RegExPatterns.getIntPattern().matcher(txtMarks.getText()).matches()) {
                boolean isAdded = addMarks(selectedItem);
                if (isAdded) {
                    loadRegistrationResultTable();
                    btnAdd.setDisable(true);
                    txtMarks.setText("");
                    lblGrade.setText("");
                    txtName.setText("");
                    new Alert(Alert.AlertType.INFORMATION, "Done!").show();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Not Added!").show();
                }
            } else {
                txtMarks.setFocusColor(Color.RED);
                txtInvalidMarks.setVisible(true);
                new Alert(Alert.AlertType.ERROR, "Invalid Input ").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }

    public void btnUpdateOnAction() {
        try {

            RegistrationExamResultTM selectedItem = tblResults.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                boolean isUpdated = RegistrationExamResultModel.updateResult(
                        new RegistrationExamResult(
                                selectedItem.getExamId(),
                                selectedItem.getRegistrationId(),
                                Integer.parseInt(txtMarks.getText()),
                                setGrade(Integer.parseInt(txtMarks.getText()))
                        ));
                if (!isUpdated) {
                    new Alert(Alert.AlertType.ERROR, "not updated").show();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Updated").show();
                    loadRegistrationResultTable();
                    btnUpdate.setDisable(false);
                    btnDelete.setDisable(false);
                    txtName.setText("");
                    txtMarks.setText("");
                    lblGrade.setText("");
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Select Student First!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    public void tblStudentsOnMouseClicked(MouseEvent mouseEvent) {
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        lblNameId.setText("Student Name");
        btnAdd.setDisable(false);
        lblGrade.setText("");
        txtMarks.setText("");
        RegistrationTM selectedItem = tblStudents.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
        //    btnAdd.setDisable(false);
            txtMarks.setDisable(false);
            txtName.setText(selectedItem.getName());
        }
    }

    private boolean addMarks(RegistrationTM selectedItem) throws SQLException, ClassNotFoundException {
        RegistrationExamResult registrationExamResult = new RegistrationExamResult(
                cmbExams.getValue().toString(),
                selectedItem.getRegistrationId(),
                Integer.parseInt(txtMarks.getText()),
                setGrade(Integer.parseInt(txtMarks.getText()))
        );
        return RegistrationExamResultModel.addResult(registrationExamResult);
    }
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            RegistrationExamResultTM selectedItem = tblResults.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                boolean isDeleted = RegistrationExamResultModel.deleteResult(
                        new RegistrationExamResult(
                                selectedItem.getExamId(),
                                selectedItem.getRegistrationId()
                        ));
                if (!isDeleted) {
                    new Alert(Alert.AlertType.ERROR, "not deleted").show();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
                    txtName.setText("");
                    txtMarks.setText("");
                    lblGrade.setText("");
                    loadRegistrationResultTable();
                    btnUpdate.setDisable(false);
                    btnDelete.setDisable(false);
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Select Student First!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    public void tblResultsOnMouseClicked(MouseEvent mouseEvent) {
        RegistrationExamResultTM selectedItem = tblResults.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnAdd.setDisable(true);
            lblNameId.setText("Student ID");
            txtMarks.setText(String.valueOf(selectedItem.getMark()));
            txtName.setText(selectedItem.getRegistrationId());
            lblGrade.setText(selectedItem.getResult());
        }
    }

    void clearAll() {
        txtName.setText("");
        txtMarks.setText("");
        lblNameId.setText("Student Name");
        tblResults.setItems(null);
    }

    String setGrade(int mark) {
        if (mark >= 90) {
            return "A+";
        } else if (mark >= 85) {
            return "A";
        } else if (mark >= 80) {
            return ("A-");
        } else if (mark >= 75) {
            return ("B+");
        } else if (mark >= 70) {
            return ("B-");
        } else if (mark >= 65) {
            return ("C+");
        } else if (mark >= 60) {
            return ("C");
        } else if (mark >= 55) {
            return ("C-");
        }
        return ("F");
    }

    public void cmbBatchOnMouseClicked(MouseEvent mouseEvent) {
        txtSelectBatch.setVisible(false);
    }

    public void cmbExamIDOnMouseClicked(MouseEvent mouseEvent) {
        txtSelectExam.setVisible(false);
    }

    public void txtMarksclickOnAction(MouseEvent mouseEvent) {
        txtInvalidMarks.setVisible(false);
    }

    public void txtMarksOnKeyReleased(KeyEvent keyEvent) {
        if (RegExPatterns.getIntPattern().matcher(txtMarks.getText()).matches()) {
            lblGrade.setText(setGrade(Integer.parseInt(txtMarks.getText())));
        } else {
            lblGrade.setText("");
        }
    }
}
