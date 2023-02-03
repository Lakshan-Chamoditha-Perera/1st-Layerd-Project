package lk.ijse.studentsmanagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.comboLoad.ComboLoader;
import lk.ijse.studentsmanagement.comboLoad.TableLoader;
import lk.ijse.studentsmanagement.model.ExamModel;
import lk.ijse.studentsmanagement.tblModels.RegistrationExamResultTM;
import lk.ijse.studentsmanagement.entity.Exam;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AcademicViewResultsFormController implements Initializable {

    public AnchorPane pane;
    public Label lblSelectExam;
    public Label lblSelectBatch;
    public TableColumn colExamId;
    public TableColumn colMarks;
    public TableColumn colStdID;
    public TableColumn colResults;

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
            boolean isLoaded = TableLoader.loadRegistrationEaxmResults(tblResults, cmbExam.getSelectionModel().getSelectedItem());

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
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
            boolean isBatchesLoaded = ComboLoader.loadBatchIDS(cmbBatch);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }

    public void cmbBatchOnAction(ActionEvent actionEvent) {
        try {
            if (cmbBatch != null) {
                String selectedBatch = cmbBatch.getSelectionModel().getSelectedItem();
                boolean isExamIdLoaded = ComboLoader.loadExamId(selectedBatch, cmbExam);
                if (!isExamIdLoaded) {
                    new Alert(Alert.AlertType.INFORMATION, "No any exams added yet!").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }

    public void cmbExamOnAction(ActionEvent actionEvent) {
        try {
            if (cmbExam.getSelectionModel().getSelectedItem() != null) {
                Exam exam = null;
                exam = ExamModel.getExamDetails(new Exam(
                        cmbExam.getSelectionModel().getSelectedItem(),
                        cmbBatch.getSelectionModel().getSelectedItem()
                ));


                lblExamDate.setText(String.valueOf(exam.getExamDate()));
                lblExamLab.setText(exam.getLab());
                lblExamName.setText(exam.getDescription());
                lblSubjectId.setText(
                        ExamModel.getSubjectName(new Exam(
                                cmbExam.getSelectionModel().getSelectedItem(),
                                cmbBatch.getSelectionModel().getSelectedItem()
                        )));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }
}
