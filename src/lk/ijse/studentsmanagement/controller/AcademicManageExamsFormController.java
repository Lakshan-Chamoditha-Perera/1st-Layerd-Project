package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
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
import lk.ijse.studentsmanagement.comboLoad.TableLoader;
import lk.ijse.studentsmanagement.model.ExamModel;
import lk.ijse.studentsmanagement.tblModels.ExamTM;
import lk.ijse.studentsmanagement.entity.Exam;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
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
            if (tblExams.getSelectionModel().getSelectedItem() != null) {
                boolean isDeleted = ExamModel.deleteExam(
                        new Exam(
                                tblExams.getSelectionModel().getSelectedItem().getExamId()
                        )
                );
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "DELETED").showAndWait();
                    Navigation.navigate(Routes.ACADEMIC_MANAGE_EXAMS, pane);
                } else {
                    new Alert(Alert.AlertType.ERROR, "ERROR").show();
                }
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateClickOnAction(ActionEvent event) {
        try {
            if (lblExamID.getText() != null) {
                if (!cmbDate.getValue().isBefore(LocalDate.now())) {
                    if (RegExPatterns.getNamePattern().matcher(txtDescription.getText()).matches()) {
                        if (txtLab.getText() != null) {
                            boolean isUpdated = ExamModel.updateExamDetails(
                                    new Exam(
                                            lblExamID.getText(),
                                            lblSubjectID.getText(),
                                            lblBatchID.getText(),
                                            txtDescription.getText(),
                                            Date.valueOf(cmbDate.getValue()),
                                            txtLab.getText(),
                                            Time.valueOf(cmbTime.getValue())
                                    )
                            );
                            if (isUpdated) {
                                new Alert(Alert.AlertType.INFORMATION, "Updated").showAndWait();
                                Navigation.navigate(Routes.ACADEMIC_MANAGE_EXAMS, pane);
                            } else {
                                new Alert(Alert.AlertType.ERROR, "ERROR").show();
                            }
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
            throw new RuntimeException(e);
        }
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
            boolean isExamLoaded = TableLoader.loadAllExams(tblExams);
            if (!isExamLoaded) {
                new Alert(Alert.AlertType.INFORMATION, "No any exam scheduled yet").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
