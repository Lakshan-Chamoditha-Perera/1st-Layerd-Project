package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

public class ViewStudentDetailsFormController implements Initializable {

    public JFXTextField txtMobile;
    public JFXTextField txtGender;
    public JFXTextField txtDate;
    public JFXButton btnSearch;
    public JFXTextField txtID;
    public Label lblInvalidID;
    InquiryService inquiryService;
    @FXML
    private AnchorPane pane;
    @FXML
    private JFXTextField txtProgram;
    @FXML
    private JFXTextField txtRegistraion;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtCity;

    @FXML
    void backClickOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.INQUIRIES, pane);
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        if (txtID.getText() != null) {
            if (RegExPatterns.getIdPattern().matcher(txtID.getText()).matches()) {
                try {
                    InquiryDTO inquiryDTO = inquiryService.view(new InquiryDTO(txtID.getText()));
                    if (inquiryDTO != null) {
                        txtId.setText(inquiryDTO.getStudentID());
                        txtName.setText(inquiryDTO.getName());
                        txtDate.setText(inquiryDTO.getDate());
                        txtCity.setText(inquiryDTO.getCity());
                        txtEmail.setText(inquiryDTO.getEmail());
                        txtGender.setText(inquiryDTO.getGender());
                        txtMobile.setText(inquiryDTO.getMobile());
                    }
                } catch (SQLException | ClassNotFoundException | RuntimeException exception) {
                    new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
                    clearAll();
                }
            } else {
                lblInvalidID.setVisible(true);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Enter ID number First").show();
        }
    }

    public void txtIDClickOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        lblInvalidID.setVisible(false);
        btnSearchOnAction(new ActionEvent());
    }

    public void txtIDonMouseClicked(MouseEvent mouseEvent) {
        lblInvalidID.setVisible(false);
        clearAll();
    }

    private void clearAll() {
        txtId.clear();
        txtDate.clear();
        txtCity.clear();
        txtEmail.clear();
        txtGender.clear();
        txtMobile.clear();
        txtName.clear();
        txtID.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            inquiryService = ServiceFactory.getInstance().getService(ServiceTypes.INQUIRY);
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        lblInvalidID.setVisible(false);
    }


}
