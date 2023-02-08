package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.dto.PaymentDTO;
import lk.ijse.studentsmanagement.dto.RegistrationDTO;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.PaymentService;
import lk.ijse.studentsmanagement.service.custom.RegistrationService;
import lk.ijse.studentsmanagement.dto.tblModels.PaymentsTM;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AcademicViewStudentsDetailsFormController implements Initializable {

    public AnchorPane pane;
    public JFXTextField txtID;
    public JFXTextField txtStdSchool;
    public JFXButton btnUpdate;
    public TableView tblPayments;
    public TableColumn colId;
    public TableColumn colRemark;
    public TableColumn colType;
    public TableColumn colAmount;
    public TableColumn colDate;
    public Label lblBatchID;
    public Label lblCourseID;
    public Label lblDate;
    public JFXTextField txtStdPostalCode;
    RegistrationService registrationService;
    PaymentService paymentService;
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
    private JFXTextField txtParentMobile1;

    @FXML
    void backClickOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.ACADEMIC_STUDENTFORM, pane);
    }

    @FXML
    void btnSearchClickOnAction(ActionEvent event) {
        try {
            if (RegExPatterns.getRegistrationIdPattern().matcher(txtID.getText()).matches()) {
                RegistrationDTO registrationDTO = registrationService.view(new RegistrationDTO(txtID.getText()));
                btnUpdate.setDisable(false);
                loadRegistrationPayments(registrationDTO);
                setRegistrationDetails(registrationDTO);
                setBatchDetails(registrationDTO);
            }
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadRegistrationPayments(RegistrationDTO registrationDTO) throws SQLException, ClassNotFoundException, RuntimeException {
        List<PaymentDTO> payments = paymentService.getPayments(registrationDTO);
        ObservableList<PaymentsTM> observableList = FXCollections.observableArrayList();
        for (PaymentDTO ele : payments) {
            observableList.add(new PaymentsTM(ele.getId(), ele.getType(), ele.getRemark(), ele.getAmount(), ele.getDate()));
        }
        tblPayments.setItems(observableList);
    }

    private void setRegistrationDetails(RegistrationDTO registrationDetailsDTO) {
        txtStdName.setText(registrationDetailsDTO.getName());
        txtStdAddress.setText(registrationDetailsDTO.getAddress());
        txtStdCity.setText(registrationDetailsDTO.getCity());
        txtStdMobileNumber.setText(registrationDetailsDTO.getMobile());
        calDob.setValue(registrationDetailsDTO.getDob().toLocalDate());
        txtStdSchool.setText(registrationDetailsDTO.getSchool());
        txtStdEmail.setText(registrationDetailsDTO.getEmail());
        txtStdPostalCode.setText(registrationDetailsDTO.getPostalCode());
    }

    private void setBatchDetails(RegistrationDTO registrationDetails) {
        lblBatchID.setText(registrationDetails.getBatchId());
        lblCourseID.setText(registrationDetails.getCourseId());
    }

    @FXML
    void btnUpdateClickOnAction(ActionEvent event) {
        try {
            RegistrationDTO registrationDTO = registrationService.update(new RegistrationDTO(txtID.getText(), txtStdName.getText(), txtStdAddress.getText(), txtStdCity.getText(), txtStdPostalCode.getText(), txtStdMobileNumber.getText(), txtStdEmail.getText(), Date.valueOf(calDob.getValue()), txtStdSchool.getText()));
            String text = (registrationDTO != null) ? "Updated Done" : "Error";
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, text).showAndWait();
            if (buttonType.get() == ButtonType.OK) Navigation.navigate(Routes.ACADEMIC_VIEW_STUDENT_DETAILS, pane);
        } catch (SQLException | ClassNotFoundException | IOException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            registrationService = ServiceFactory.getInstance().getService(ServiceTypes.REGISTRATION);
            paymentService = ServiceFactory.getInstance().getService(ServiceTypes.PAYMENTS);
            btnUpdate.setDisable(true);
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colRemark.setCellValueFactory(new PropertyValueFactory<>("remark"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
