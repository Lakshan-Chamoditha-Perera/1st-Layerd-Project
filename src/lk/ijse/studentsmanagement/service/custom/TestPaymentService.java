package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.TestPaymentDTO;
import lk.ijse.studentsmanagement.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface TestPaymentService extends SuperService <TestPaymentDTO> {
    String getLastPaymentID() throws SQLException, ClassNotFoundException;

    TestPaymentDTO addTestPaymentTransaction(TestPaymentDTO testPayment) throws SQLException, ClassNotFoundException,RuntimeException;
    double getPaymentsSum() throws SQLException, ClassNotFoundException;

    List<TestPaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException;
}
