package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.dto.ExamDTO;
import lk.ijse.studentsmanagement.entity.Exam;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.ExamService;
import lk.ijse.studentsmanagement.dto.tblModels.ExamTM;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AcademicManageExamsFormController implements Initializable {

    public AnchorPane pane;
    public TableColumn colExamID;
    public TableColumn colSubjectID;
    public TableColumn colBatchId;
    public TableColumn colDescription;
    public TableColumn colDate;
    public TableColumn colLab;
    public TableColumn colTime;
    public TableView tblCourse;
    public TableColumn colCourseID;
    public TableColumn colCourseName;
    public TableColumn colDuration;
    public Label lblBatchID;
    public Label lblSubjectID;
    public JFXTimePicker cmbTime;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    ExamService examService;
    @FXML
    private JFXTextField txtDescription;
    @FXML
    private JFXDatePicker cmbDate;
    @FXML
    private JFXTextField txtLab;
    @FXML
    private Label lblExamID;
    @FXML
    private TableView<ExamTM> tblExams;

    @FXML
    void backClickOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.EXAMS, pane);

    }

    @FXML
    void btnDeleteClickonAction(ActionEvent event) {
        try {
            ExamTM selectedItem = tblExams.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                deleteExam(selectedItem);
            }
        } catch (SQLException | ClassNotFoundException | IOException |RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void deleteExam(ExamTM examTM) throws SQLException, ClassNotFoundException, RuntimeException, IOException {
        Exam exam = examService.delete(new ExamDTO(examTM.getExamId(), examTM.getSubjectId(), examTM.getBatchId(), examTM.getDescription(), examTM.getExamDate()));
        new Alert(Alert.AlertType.INFORMATION, "Deleted...").showAndWait();
        Navigation.navigate(Routes.ACADEMIC_MANAGE_EXAMS, pane);
    }

    @FXML
    void btnUpdateClickOnAction(ActionEvent event) {
        try {
            if (lblExamID.getText() != null) {
                if (!cmbDate.getValue().isBefore(LocalDate.now())) {
                    if (RegExPatterns.getNamePattern().matcher(txtDescription.getText()).matches()) {
                        if (txtLab.getText() != null) {
                            updateExam();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Invalid Lab name").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Invalid Description!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Select valid date!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Select Exam First!").show();
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void updateExam() throws SQLException, ClassNotFoundException, IOException {
        examService.update(new ExamDTO(lblExamID.getText(), lblSubjectID.getText(), lblBatchID.getText(), txtDescription.getText(), Date.valueOf(cmbDate.getValue()), txtLab.getText(), Time.valueOf(cmbTime.getValue())));
        new Alert(Alert.AlertType.ERROR, "updated...").showAndWait();
        Navigation.navigate(Routes.ACADEMIC_MANAGE_EXAMS, pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colExamID.setCellValueFactory(new PropertyValueFactory<>("examId"));
        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        colLab.setCellValueFactory(new PropertyValueFactory<>("lab"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        colSubjectID.setCellValueFactory(new PropertyValueFactory<>("subjectId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        try {
            examService = ServiceFactory.getInstance().getService(ServiceTypes.EXAM);
            loadAllExams();
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadAllExams() throws SQLException, ClassNotFoundException, RuntimeException {
        List<ExamDTO> allExams = examService.getAllExams();
        //  ArrayList<Exam> list = ExamModel.getAllExams();
        ObservableList<ExamTM> observableArrayList = FXCollections.observableArrayList();
        for (ExamDTO ele : allExams) {
            observableArrayList.add(new ExamTM(ele.getExamId(), ele.getSubjectId(), ele.getBatchId(), ele.getDescription(), ele.getExamDate(), ele.getLab(), ele.getTime()));
        }
        tblExams.setItems(observableArrayList);
    }

    public void tblOnMouseClicked(MouseEvent mouseEvent) {
        ExamTM selectedItem = tblExams.getSelectionModel().getSelectedItem();
        lblExamID.setText(selectedItem.getExamId());
        lblBatchID.setText(selectedItem.getBatchId());
        lblSubjectID.setText(selectedItem.getSubjectId());
        txtDescription.setText(selectedItem.getDescription());
        txtLab.setText(selectedItem.getLab());
        cmbDate.setValue(selectedItem.getExamDate().toLocalDate());
        cmbTime.setValue(selectedItem.getTime().toLocalTime());
    }
}
