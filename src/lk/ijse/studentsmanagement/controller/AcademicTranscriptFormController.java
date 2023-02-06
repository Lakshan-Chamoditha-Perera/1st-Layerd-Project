package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import lk.ijse.studentsmanagement.dto.CourseDTO;
import lk.ijse.studentsmanagement.dto.RegistrationDTO;
import lk.ijse.studentsmanagement.dto.RegistrationExamResultDTO;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.CourseService;
import lk.ijse.studentsmanagement.service.custom.RegistrationService;
import lk.ijse.studentsmanagement.tblModels.RegistrationExamResultTM;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AcademicTranscriptFormController implements Initializable {

    public TableView tblResults;
    public TableColumn colSubName;
    public TableColumn colMarks;
    public TableColumn colGrade;
    public JFXButton btnSendEmail;
    public Label lblStdNic1;
    CourseService courseService;
    RegistrationService registrationService;
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
                RegistrationDTO registrationDTO = registrationService.view(new RegistrationDTO(txtId.getText()));
                //  Registration registrationDTO = RegistrationModel.getRegistrationDetails(txtId.getText());
                if (registrationDTO != null) {
                    lblStdName.setText(registrationDTO.getName());
                    lblStdNic.setText(registrationDTO.getNic());
                    lblBatch.setText(registrationDTO.getBatchId());
                    CourseDTO courseDTO = courseService.view(new CourseDTO(registrationDTO.getCourseId()));
                    lblCourseId.setText(courseDTO.getId());
                    lblCourseName.setText(courseDTO.getName());
                    lblCourseDuration.setText(courseDTO.getDuration());
                    loadResults();
                } else {
                    lblStdName.setText("");
                    lblStdNic.setText("");
                    lblCourseId.setText("");
                    lblCourseName.setText("");
                    lblCourseDuration.setText("");
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Registration ID! Enter Correct ID").show();
                txtId.setFocusColor(Color.RED);
            }
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadResults() throws SQLException, ClassNotFoundException {
        loadTableTranscript(new RegistrationDTO(txtId.getText()), tblResults);
    }

    public void loadTableTranscript(RegistrationDTO registration, TableView tblResults) throws SQLException, ClassNotFoundException, RuntimeException {
        List<RegistrationExamResultDTO> registrationExamResultDTOList = registrationService.getTranscript(registration);
        // ArrayList<RegistrationExamResult> transcript = RegistrationExamResultModel.getTranscript(registration);
        ObservableList<RegistrationExamResultTM> registrationExamResultTMS = FXCollections.observableArrayList();
        for (RegistrationExamResultDTO ele : registrationExamResultDTOList) {
            registrationExamResultTMS.add(new RegistrationExamResultTM(ele.getMark(), ele.getResult(), ele.getSubject()));
        }
        tblResults.setItems(registrationExamResultTMS);
        new Alert(Alert.AlertType.INFORMATION, "table loaded").show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            courseService = ServiceFactory.getInstance().getService(ServiceTypes.COURSE);
            registrationService = ServiceFactory.getInstance().getService(ServiceTypes.REGISTRATION);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        colGrade.setCellValueFactory(new PropertyValueFactory<>("result"));
        colMarks.setCellValueFactory(new PropertyValueFactory<>("mark"));
        colSubName.setCellValueFactory(new PropertyValueFactory<>("sub"));
    }
}
