package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MailsCounselorFormControler implements Initializable {

    public JFXButton btnInq;
    @FXML
    private AnchorPane mainPain;

    @FXML
    void sendToRegisteredStudentOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.SEND_MAIL_TO_REGISTERED,mainPain);
    }

    public void sendToInquiryOnAction(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnInq.setDisable(true);
        btnInq.setVisible(false);
    }
}
