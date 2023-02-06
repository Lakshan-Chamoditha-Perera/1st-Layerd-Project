package lk.ijse.studentsmanagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.dto.BatchDTO;
import lk.ijse.studentsmanagement.dto.ExamDTO;
import lk.ijse.studentsmanagement.dto.RegistrationExamResultDTO;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.BatchService;
import lk.ijse.studentsmanagement.service.custom.ExamService;
import lk.ijse.studentsmanagement.service.custom.RegistrationExamResultsService;
import lk.ijse.studentsmanagement.tblModels.RegistrationExamResultTM;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AcademicViewResultsFormController implements Initializable {

    public AnchorPane pane;
    public Label lblSelectExam;
    public Label lblSelectBatch;
    public TableColumn colExamId;
    public TableColumn colMarks;
    public TableColumn colStdID;
    public TableColumn colResults;
    RegistrationExamResultsService registrationExamResultsService;
    ExamService examService;
    BatchService batchService;
    @FXML
    private TableView<RegistrationExamResultTM> tblResults;
    @FXML
    private ComboBox<String> cmbBatch;
    @FXML
    private ComboBox<String> cmbExam;
    @FXML
    private Label lblSubjectId;
    @FXML
    private Label lblExamName;
    @FXML
    private Label lblExamDate;
    @FXML
    private Label lblExamLab;
    @FXML
    private Label lblStdCount;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.EXAMS, pane);
    }

    @FXML
    void btnViewOnAction(ActionEvent event) {
        try {
            loadRegistrationEaxmResults(cmbExam.getSelectionModel().getSelectedItem());
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
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

    public void cmbExamOnMouseClicked(MouseEvent mouseEvent) {
        lblSelectBatch.setVisible(false);

    }

    public void cmbBatchOnMouseClicked(MouseEvent mouseEvent) {
        lblSelectBatch.setVisible(false);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblSelectBatch.setVisible(false);
        lblSelectExam.setVisible(false);

        colResults.setCellValueFactory(new PropertyValueFactory<>("result"));
        colMarks.setCellValueFactory(new PropertyValueFactory<>("mark"));
        colExamId.setCellValueFactory(new PropertyValueFactory<>("examId"));
        colStdID.setCellValueFactory(new PropertyValueFactory<>("registrationId"));

        try {
            registrationExamResultsService = ServiceFactory.getInstance().getService(ServiceTypes.REGISTRATION_EXAM_RESULTS);
            examService = ServiceFactory.getInstance().getService(ServiceTypes.EXAM);
            batchService = ServiceFactory.getInstance().getService(ServiceTypes.BATCH);
            loadBatchIDS(cmbBatch);
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadBatchIDS(ComboBox<String> comboBox) throws SQLException, ClassNotFoundException, RuntimeException {
        List<BatchDTO> allBatchID = batchService.getAllBatchID();
        //  ArrayList<Batch> batches = BatchModel.getBatches();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (BatchDTO ele : allBatchID) {
            observableList.add(ele.getId());
        }
        comboBox.setItems(observableList);
    }

    public void cmbBatchOnAction(ActionEvent actionEvent) {
        try {
            if (cmbBatch != null) {
                String selectedBatch = cmbBatch.getSelectionModel().getSelectedItem();
                loadExamId(selectedBatch, cmbExam);
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }

    public void loadExamId(String value, ComboBox<String> cmbExam) throws SQLException, ClassNotFoundException {
        List<ExamDTO> exam = examService.getExam(new ExamDTO(value));
        // ArrayList<Exam> exams = ExamModel.getExam(new Exam(value));
        ObservableList<String> observableArrayList = FXCollections.observableArrayList();
        for (ExamDTO ele : exam) {
            observableArrayList.add(ele.getExamId());
        }
        cmbExam.setItems(observableArrayList);
    }

    public void cmbExamOnAction(ActionEvent actionEvent) {
        try {
            if (cmbExam.getSelectionModel().getSelectedItem() != null) {
                ExamDTO view = examService.view(new ExamDTO(cmbExam.getSelectionModel().getSelectedItem(), cmbBatch.getSelectionModel().getSelectedItem()));
                //  exam = ExamModel.getExamDetails(new Exam(cmbExam.getSelectionModel().getSelectedItem(), cmbBatch.getSelectionModel().getSelectedItem()));
                if (view != null) {
                    lblExamDate.setText(String.valueOf(view.getExamDate()));
                    lblExamLab.setText(view.getLab());
                    lblExamName.setText(view.getDescription());
                    lblSubjectId.setText(examService.getSubjectName(new ExamDTO(cmbExam.getSelectionModel().getSelectedItem(), cmbBatch.getSelectionModel().getSelectedItem())));
                    // lblSubjectId.setText(ExamModel.getSubjectName(new Exam(cmbExam.getSelectionModel().getSelectedItem(), cmbBatch.getSelectionModel().getSelectedItem())));
                }
            }
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
