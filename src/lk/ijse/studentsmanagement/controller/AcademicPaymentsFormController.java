package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.PaymentService;
import lk.ijse.studentsmanagement.service.custom.TestPaymentService;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AcademicPaymentsFormController implements Initializable {
    public AnchorPane subPanel;
    public JFXButton btnRegistration;
    public JFXButton btnInquiry;
    public Label lblTotal;
    public Label lblRegistrationPayments;
    public Label lblInquiryPayments;
    PaymentService paymentService;
    TestPaymentService testPaymentService;

    public void btnRegistrationOnAction() throws IOException {
        Navigation.navigate(Routes.ACADEMIC_REGISTRATION_PAYMENTS, subPanel);
    }

    public void btnInquiryOnAction() throws IOException {
        Navigation.navigate(Routes.ACADEMIC_INQUIRY_PAYMENTS, subPanel);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            paymentService = ServiceFactory.getInstance().getService(ServiceTypes.PAYMENTS);
            testPaymentService = ServiceFactory.getInstance().getService(ServiceTypes.TEST_PAYMENTS);
            getRegistrationSum();
            getInquirySum();
            lblTotal.setText(String.valueOf(Double.parseDouble(lblRegistrationPayments.getText()) +
                    Double.parseDouble(lblInquiryPayments.getText())
            ));
        } catch (SQLException | ClassNotFoundException |RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    void getRegistrationSum() throws SQLException, ClassNotFoundException {
        double paymentsSum = paymentService.getPaymentsSum();
        lblRegistrationPayments.setText(String.valueOf(paymentsSum));
    }

    void getInquirySum() throws SQLException, ClassNotFoundException {
        double paymentsSum = testPaymentService.getPaymentsSum();
        lblInquiryPayments.setText(String.valueOf(paymentsSum));
    }
}
