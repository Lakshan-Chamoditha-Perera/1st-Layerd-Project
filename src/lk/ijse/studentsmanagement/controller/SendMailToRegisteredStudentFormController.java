package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.comboLoad.ComboLoader;
import lk.ijse.studentsmanagement.model.RegistrationModel;
import lk.ijse.studentsmanagement.smtp.Mail;
import lk.ijse.studentsmanagement.util.RegExPatterns;

import javax.mail.MessagingException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SendMailToRegisteredStudentFormController implements Initializable {

    public Label lblSendTo;
    public Label txtSelectBatch;
    public JFXTextField txtID;
    public Label lblEnterEmail;
    @FXML
    private AnchorPane mainPain;

    @FXML
    private TextArea txtMsg;

    @FXML
    private JFXRadioButton rBtnStd;

    @FXML
    private ToggleGroup group;

    @FXML
    private ComboBox<String> cmbBatch;

    @FXML
    private JFXButton btnSend;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtSubject;

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnSendOnAction(ActionEvent event){
        try {
            if (rBtnStd.isSelected()) {
                sendToStudent();
            } else {
                sendToGroup();
            }
        } catch (SQLException | MessagingException | ClassNotFoundException e) {
           new Alert(Alert.AlertType.ERROR,String.valueOf(e)).show();
        }
    }

    private void sendToGroup() throws SQLException, ClassNotFoundException, MessagingException {
        if (cmbBatch.getValue() != null) {
            ArrayList<String> emailList = RegistrationModel.getRegistrationEmailList(cmbBatch.getValue());
            if (txtSubject.getText() != null) {
                if (txtMsg.getText() != null) {
                    Mail.outMail(txtMsg.getText(), emailList, txtSubject.getText());
                } else {
                    new Alert(Alert.AlertType.ERROR, "enter msg").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "enter subject").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "select group").show();
        }
    }

    private void sendToStudent() throws SQLException, ClassNotFoundException, MessagingException {
        if (RegExPatterns.getRegistrationIdPattern().matcher(txtID.getText()).matches()) {
            String registrationEmail = RegistrationModel.getRegistrationEmail(txtID.getText());
            if (registrationEmail != null) {
                txtEmail.setText(registrationEmail);
                if (txtSubject.getText() != null) {
                    if (txtMsg.getText() != null) {
                        Mail.outMail(txtMsg.getText(), txtEmail.getText(), txtSubject.getText());
                    } else {
                        new Alert(Alert.AlertType.ERROR, "enter msg").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "enter subject").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Student does not exists").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Enter Std ID").show();
        }
    }

    @FXML
    void cmbSelectBatchOnAction(ActionEvent event) {
        lblEnterEmail.setVisible(true);
        lblEnterEmail.setText(cmbBatch.getValue().toLowerCase());
    }

    @FXML
    void rBtnBatchOnMouseClicked(MouseEvent event) {
        txtEmail.setVisible(false);
        lblEnterEmail.setVisible(false);
        cmbBatch.setVisible(true);
        txtSelectBatch.setVisible(true);

        if (cmbBatch.getValue() != null) {
//            lblEnterEmail.setVisible(true);
//            lblEnterEmail.setText(cmbBatch.getValue().toLowerCase());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Choose Batch ID").show();
        }
        txtID.setEditable(false);
    }

    @FXML
    void rBtnStdOnMouseClicked(MouseEvent event) {
        txtEmail.setEditable(true);
        txtID.setVisible(true);
        txtEmail.setVisible(true);
        lblEnterEmail.setVisible(true);
        cmbBatch.setVisible(false);
        txtSelectBatch.setVisible(false);
        lblEnterEmail.setText("Enter Email");
        txtID.setEditable(true);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbBatch.setVisible(false);
        txtSelectBatch.setVisible(false);
        try {
            boolean loadBatchIDS = ComboLoader.loadBatchIDS(cmbBatch);
            if(!loadBatchIDS){
                new Alert(Alert.AlertType.INFORMATION,"No any batcehs added").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtIDOnAction(ActionEvent actionEvent) {
    }
}
