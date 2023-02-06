package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import lk.ijse.studentsmanagement.dto.InquiryDTO;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.InquiryService;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateInquaryFormController implements Initializable {
    public AnchorPane pane;
    public JFXTextField txtName;
    public JFXTextField txtMobile;
    public JFXTextField txtEmail;
    public JFXTextField txtCity;
    public JFXButton btnUpdate;
    public ToggleGroup gender;
    public JFXTextField txtID;
    public JFXButton btnSearch;
    public Label lblInvalidID;
    public JFXButton l;
    public JFXTextField txtGender;
    public Label lblInvalidName;
    public Label lblInvalidEmail;
    public Label lblInvalidCity;
    public Label lblInvalidMobile;
    public RadioButton rBtnMale;
    public RadioButton rBtnFemale;
    public JFXButton btnCancel;

    private InquiryService inquiryService;

    public void btnCancelOnAction(ActionEvent actionEvent) {
        clearAll();
        rBtnMale.setVisible(false);
        rBtnFemale.setVisible(false);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        int i = 0;
        if (RegExPatterns.getNamePattern().matcher(txtName.getText()).matches()) {
            System.out.println(i++);
            if (RegExPatterns.getEmailPattern().matcher(txtEmail.getText()).matches()) {
                System.out.println(i++);
                if (RegExPatterns.getCityPattern().matcher(txtCity.getText()).matches()) {
                    System.out.println(i++);
                    if (RegExPatterns.getMobilePattern().matcher(txtMobile.getText()).matches()) {
                        System.out.println(i++);
                        try {
                            System.out.println(i++);
                            updateInquiry();
                            System.out.println(i++);
                        } catch (SQLException | ClassNotFoundException e) {
                            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                        }
                    } else {
                        txtMobile.setFocusColor(Color.RED);
                        lblInvalidMobile.setVisible(true);
                    }
                } else {
                    txtCity.setFocusColor(Color.RED);
                    lblInvalidCity.setVisible(true);
                }
            } else {
                txtEmail.setFocusColor(Color.RED);
                lblInvalidName.setVisible(true);
            }
        } else {
            txtName.setFocusColor(Color.RED);
            lblInvalidName.setVisible(true);
        }
    }

    private void updateInquiry() throws SQLException, ClassNotFoundException {
        InquiryDTO inquiryDTO = inquiryService.updateInquiryDetails(
                new InquiryDTO(
                        txtID.getText(),
                        txtName.getText(),
                        txtCity.getText(),
                        txtEmail.getText(),
                        txtMobile.getText(),
                        (rBtnMale.isSelected()) ? "Male" : "Female")
        );
        new Alert(Alert.AlertType.INFORMATION, "Inquiry Updated").show();
        clearAll();
    }

    public void backClickOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.INQUIRIES, pane);
    }

    public void txtIDOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnSearchOnAction(actionEvent);
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        if (txtID.getText() != null) {
            if (RegExPatterns.getIdPattern().matcher(txtID.getText()).matches()) {
                viewInquiry();
            } else {
                lblInvalidID.setVisible(true);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Enter ID number First").show();
        }

    }

    private void viewInquiry() {
        try {
            InquiryDTO inquiry = inquiryService.view(new InquiryDTO(txtID.getText()));
          //  System.out.println(inquiry);
            if (inquiry != null) {
                btnUpdate.setDisable(false);
                rBtnMale.setVisible(true);
                rBtnFemale.setVisible(true);
                btnUpdate.setDisable(false);
                txtName.setText(inquiry.getName());
                txtCity.setText(inquiry.getCity());
                txtEmail.setText(inquiry.getEmail());
                txtGender.setText(inquiry.getGender());
                txtMobile.setText(inquiry.getMobile());
            }
        } catch (SQLException | ClassNotFoundException | RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
            clearAll();
        }
    }

    private void clearAll() {
        txtID.clear();
        txtCity.clear();
        txtEmail.clear();
        txtMobile.clear();
        txtName.clear();
        txtGender.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            inquiryService = ServiceFactory.getInstance().getService(ServiceTypes.INQUIRY);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        btnUpdate.setDisable(true);
        lblInvalidID.setVisible(false);
        lblInvalidCity.setVisible(false);
        lblInvalidEmail.setVisible(false);
        lblInvalidMobile.setVisible(false);
        lblInvalidName.setVisible(false);
        rBtnMale.setVisible(false);
        rBtnFemale.setVisible(false);
    }

    public void txtIDOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidID.setVisible(false);
    }

    public void txtCityOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidCity.setVisible(false);
    }

    public void txtEmailOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidEmail.setVisible(false);
    }

    public void txtMobileNumberOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidMobile.setVisible(false);
    }

    public void txtNameOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidName.setVisible(false);
    }
}
