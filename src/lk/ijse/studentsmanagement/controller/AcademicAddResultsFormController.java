package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import lk.ijse.studentsmanagement.dto.BatchDTO;
import lk.ijse.studentsmanagement.dto.ExamDTO;
import lk.ijse.studentsmanagement.dto.RegistrationDTO;
import lk.ijse.studentsmanagement.dto.RegistrationExamResultDTO;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.BatchService;
import lk.ijse.studentsmanagement.service.custom.ExamService;
import lk.ijse.studentsmanagement.service.custom.RegistrationExamResultsService;
import lk.ijse.studentsmanagement.service.custom.RegistrationService;
import lk.ijse.studentsmanagement.tblModels.RegistrationExamResultTM;
import lk.ijse.studentsmanagement.tblModels.RegistrationTM;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AcademicAddResultsFormController implements Initializable {

    public JFXButton btnDelete;
    public Label lblNameId;
    public Label txtSelectBatch;
    public Label txtSelectExam;
    public Label txtInvalidMarks;
    public AnchorPane marksPane;
    public Label lblGrade;
    public AnchorPane pane;
    ExamService examService;
    BatchService batchService;
    RegistrationService registrationService;
    RegistrationExamResultsService registrationExamResultsService;
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
        Navigation.navigate(Routes.EXAMS, pane);
    }

    @FXML
    void cmbBatchOnAction() {
        txtSelectBatch.setVisible(false);
        try {
            if (cmbBatch.getValue() != null) {
                cmbExams.setItems(FXCollections.observableArrayList());
                tblStudents.setItems(FXCollections.observableArrayList());
                loadExamId(cmbBatch.getValue());
            } else {
                txtSelectBatch.setVisible(true);
            }
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadExamId(String value) throws SQLException, ClassNotFoundException, RuntimeException {
        List<ExamDTO> list = examService.getExam(new ExamDTO(value));
        // ArrayList<Exam> exams = ExamModel.getExam();
        ObservableList<String> observableArrayList = FXCollections.observableArrayList();
        for (ExamDTO ele : list) {
            observableArrayList.add(ele.getExamId());
        }
        cmbExams.setItems(observableArrayList);
    }

    public void loadBatchRegistrations(String value) throws SQLException, ClassNotFoundException, RuntimeException {
        List<RegistrationDTO> registrationDTOS = registrationService.loadBatchRegistrations(value);
//        ArrayList<Registration> list = RegistrationModel.loadBatchRegistrations(value);
        ObservableList<RegistrationTM> observableList = FXCollections.observableArrayList();
        for (RegistrationDTO ele : registrationDTOS) {
            observableList.add(new RegistrationTM(ele.getRegistrationId(), ele.getBatchId(), ele.getName(), ele.getStatus()));
        }
        tblStudents.setItems(observableList);
    }

    @FXML
    void cmbExamIDOnAction() {
        try {
            if (cmbBatch.getValue() != null) {
                if (cmbExams.getValue() != null) {
                    loadBatchRegistrations(cmbBatch.getValue());
                    loadRegistrationResultTable();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Select a Exam First").show();
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Select a Batch First").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadRegistrationResultTable() throws SQLException, ClassNotFoundException, RuntimeException {
        loadRegistrationEaxmResults(cmbExams.getValue());
    }

    public void loadRegistrationEaxmResults(String examId) throws SQLException, ClassNotFoundException {
        List<RegistrationExamResultDTO> registrationExamResultDTOS = registrationExamResultsService.getResults(examId);
        //  ArrayList<RegistrationExamResult> list = RegistrationExamResultModel.getResults(examId);
        ObservableList<RegistrationExamResultTM> registrationExamResultTMS = FXCollections.observableArrayList();
        for (RegistrationExamResultDTO ele : registrationExamResultDTOS) {
            registrationExamResultTMS.add(new RegistrationExamResultTM(ele.getExamId(), ele.getRegistrationId(), ele.getMark(), ele.getResult()));
        }
        tblResults.setItems(registrationExamResultTMS);
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
            examService = ServiceFactory.getInstance().getService(ServiceTypes.EXAM);
            batchService = ServiceFactory.getInstance().getService(ServiceTypes.BATCH);
            registrationService = ServiceFactory.getInstance().getService(ServiceTypes.REGISTRATION);
            registrationExamResultsService = ServiceFactory.getInstance().getService(ServiceTypes.REGISTRATION_EXAM_RESULTS);
            loadBatchIDS();
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
        }
        batchCol.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        regIdCol.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        colMark.setCellValueFactory(new PropertyValueFactory<>("mark"));
        colResult.setCellValueFactory(new PropertyValueFactory<>("result"));
        colRegistrationId.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        colExamID.setCellValueFactory(new PropertyValueFactory<>("examId"));
    }

    public void loadBatchIDS() throws SQLException, ClassNotFoundException, RuntimeException {
        List<BatchDTO> allBatchID = batchService.getAllBatchID();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (BatchDTO ele : allBatchID) {
            observableList.add(ele.getId());
        }
        cmbBatch.setItems(observableList);
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
                }
            } else {
                txtMarks.setFocusColor(Color.RED);
                txtInvalidMarks.setVisible(true);
                new Alert(Alert.AlertType.ERROR, "Invalid Input ").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnUpdateOnAction() {
        try {

            RegistrationExamResultTM selectedItem = tblResults.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                boolean isUpdated = registrationExamResultsService.update(new RegistrationExamResultDTO(selectedItem.getExamId(), selectedItem.getRegistrationId(), Integer.parseInt(txtMarks.getText()), setGrade(Integer.parseInt(txtMarks.getText()))));

//                boolean isUpdated = RegistrationExamResultModel.updateResult(new RegistrationExamResult(selectedItem.getExamId(), selectedItem.getRegistrationId(), Integer.parseInt(txtMarks.getText()), setGrade(Integer.parseInt(txtMarks.getText()))));
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
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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
        RegistrationExamResultDTO registrationExamResultDTO = new RegistrationExamResultDTO(cmbExams.getValue(), selectedItem.getRegistrationId(), Integer.parseInt(txtMarks.getText()), setGrade(Integer.parseInt(txtMarks.getText())));
        RegistrationExamResultDTO save = registrationExamResultsService.save(registrationExamResultDTO);
        return save != null;
        //return RegistrationExamResultModel.addResult(registrationExamResult);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            RegistrationExamResultTM selectedItem = tblResults.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                boolean isDeleted = registrationExamResultsService.delete(new RegistrationExamResultDTO(selectedItem.getExamId(), selectedItem.getRegistrationId()));
//                boolean isDeleted = RegistrationExamResultModel.deleteResult(
//                        new RegistrationExamResult(
//                                selectedItem.getExamId(),
//                                selectedItem.getRegistrationId()
//                        ));
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
                    txtName.setText("");
                    txtMarks.setText("");
                    lblGrade.setText("");
                    loadRegistrationResultTable();
                    btnUpdate.setDisable(false);
                    btnDelete.setDisable(false);
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "not - Deleted").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Select Student First!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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
