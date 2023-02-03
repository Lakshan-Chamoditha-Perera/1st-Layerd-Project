package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;

public class ViewStudentEnrollmentFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXTextField txtStdAddress;

    @FXML
    private JFXTextField txtStdCity;

    @FXML
    private JFXTextField txtStdMobileNumber;

    @FXML
    private JFXTextField txtStdEmail;

    @FXML
    private JFXTextField txtSchool;

    @FXML
    private JFXTextField txtUniversity;

    @FXML
    private JFXDatePicker calDob;

    @FXML
    private JFXTextField txtStdName;

    @FXML
    private Label txtBatch;

    @FXML
    private JFXTextField txtUniversity1;

    @FXML
    private JFXTextField txtParentAddress;

    @FXML
    private JFXTextField txtParentMobile;

    @FXML
    private JFXTextField txtParentName;

    @FXML
    private JFXTextField txtParentEmail;

    @FXML
    private JFXTextField txtParentDesignation;

    @FXML
    private JFXTextField txtParentWorkPlace;

    @FXML
    private JFXTextField txtParentMobile1;

    @FXML
    void backClickOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.ENROLLMENTS,pane);
    }

    public void btnUpdateClickOnAction(ActionEvent actionEvent) {
    }
}
