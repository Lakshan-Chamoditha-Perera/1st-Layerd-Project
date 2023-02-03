package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import lk.ijse.studentsmanagement.comboLoad.TableLoader;
import lk.ijse.studentsmanagement.model.CourseModel;
import lk.ijse.studentsmanagement.model.RegistrationModel;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.entity.Course;
import lk.ijse.studentsmanagement.entity.Registration;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AcademicTranscriptFormController implements Initializable {

    public TableView tblResults;
    public TableColumn colSubName;
    public TableColumn colMarks;
    public TableColumn colGrade;
    public JFXButton btnSendEmail;
    public Label lblStdNic1;
    @FXML
    private AnchorPane pane;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private Label lblStdName;

    @FXML
    private Label lblStdNic;

    @FXML
    private Label lblRegDate;

    @FXML
    private Label lblCourseId;

    @FXML
    private Label lblBatch;

    @FXML
    private Label lblCourseDuration;

    @FXML
    private Label lblCourseName;

    @FXML
    private JFXButton btnPrint;

    @FXML
    private JFXButton btnCancel;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.ACADEMIC_STUDENTFORM, pane);
    }

    public void txtIdOnAction(ActionEvent actionEvent) {
        try {
            if (RegExPatterns.getRegistrationIdPattern().matcher(txtId.getText()).matches()) {
                Registration registrationDetails = RegistrationModel.getRegistrationDetails(txtId.getText());
                if (registrationDetails != null) {
                    lblStdName.setText(registrationDetails.getName());
                    lblStdNic.setText(registrationDetails.getNic());
                    lblBatch.setText(registrationDetails.getBatchId());
                    Course course = CourseModel.getCourseDetail(new Course(registrationDetails.getCourseId()));
                    lblCourseId.setText(course.getId());
                    lblCourseName.setText(course.getName());
                    lblCourseDuration.setText(course.getDuration());
                    loadResults();

                } else {
                    lblStdName.setText("");
                    lblStdNic.setText("");
                    lblCourseId.setText("");
                    lblCourseName.setText("");
                    lblCourseDuration.setText("");
                    new Alert(Alert.AlertType.ERROR, "Student Does not exists!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Registration ID! Enter Correct ID").show();
                txtId.setFocusColor(Color.RED);
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }

    private void loadResults() throws SQLException, ClassNotFoundException {
        boolean isLoaded = TableLoader.loadTableTranscript(new Registration(txtId.getText()), tblResults);
        if(!isLoaded){
            new Alert(Alert.AlertType.INFORMATION,"No any exams ").show();
        }else{
            new Alert(Alert.AlertType.INFORMATION,"table loaded").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            colGrade.setCellValueFactory(new PropertyValueFactory<>("result"));
            colMarks.setCellValueFactory(new PropertyValueFactory<>("mark"));
            colSubName.setCellValueFactory(new PropertyValueFactory<>("sub"));
    }
}
