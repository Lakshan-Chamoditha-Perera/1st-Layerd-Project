package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.dto.BatchDTO;
import lk.ijse.studentsmanagement.dto.RegistrationDTO;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.BatchService;
import lk.ijse.studentsmanagement.service.custom.RegistrationService;
import lk.ijse.studentsmanagement.util.RegExPatterns;

import javax.mail.MessagingException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SendMailToRegisteredStudentFormController implements Initializable {

    public Label lblSendTo;
    public Label txtSelectBatch;
    public JFXTextField txtID;
    public Label lblEnterEmail;
    RegistrationService registrationService;
    BatchService batchService;
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
    void btnSendOnAction(ActionEvent event) {
        try {
            if (rBtnStd.isSelected()) {
                sendToStudent();
            } else {
                sendToGroup();
            }
        } catch (SQLException | MessagingException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void sendToGroup() throws SQLException, ClassNotFoundException, MessagingException, RuntimeException {
        if (cmbBatch.getValue() != null) {
            List<String> emailList = registrationService.getRegistrationEmailList(cmbBatch.getValue());
            if (txtSubject.getText() != null) {
                if (txtMsg.getText() != null) {
                    //    Mail.outMail(txtMsg.getText(), emailList, txtSubject.getText());
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

    private void sendToStudent() throws SQLException, ClassNotFoundException, MessagingException, RuntimeException {
        if (RegExPatterns.getRegistrationIdPattern().matcher(txtID.getText()).matches()) {
            registrationService.view(new RegistrationDTO(txtID.getText()));
            String registrationEmail = registrationService.getRegistrationEmail(txtID.getText());
            txtEmail.setText(registrationEmail);
            if (txtSubject.getText() != null) {
                if (txtMsg.getText() != null) {
                    //  Mail.outMail(txtMsg.getText(), txtEmail.getText(), txtSubject.getText());
                } else {
                    new Alert(Alert.AlertType.ERROR, "enter msg").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "enter subject").show();
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
            registrationService = ServiceFactory.getInstance().getService(ServiceTypes.REGISTRATION);
            batchService = ServiceFactory.getInstance().getService(ServiceTypes.BATCH);
            loadBatchIDS();
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadBatchIDS() throws SQLException, ClassNotFoundException, RuntimeException {
        List<BatchDTO> batchList = batchService.getAllBatchID();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (BatchDTO ele : batchList) {
            observableList.add(ele.getId());
        }
        cmbBatch.setItems(observableList);
    }


    public void txtIDOnAction(ActionEvent actionEvent) {
    }
}
