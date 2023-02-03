package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.PaymentDTO;
import lk.ijse.studentsmanagement.dto.RegistrationDTO;
import lk.ijse.studentsmanagement.entity.Payment;
import lk.ijse.studentsmanagement.entity.Registration;
import lk.ijse.studentsmanagement.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface PaymentService extends SuperService <PaymentDTO> {


    String getLastPaymentID() throws SQLException, ClassNotFoundException;
    List<PaymentDTO> getPayments(RegistrationDTO registrationDTO) throws SQLException, ClassNotFoundException;

    double getPaymentsSum() throws SQLException, ClassNotFoundException;
}
