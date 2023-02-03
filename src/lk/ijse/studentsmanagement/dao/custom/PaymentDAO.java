package lk.ijse.studentsmanagement.dao.custom;

import lk.ijse.studentsmanagement.dao.CrudDAO;
import lk.ijse.studentsmanagement.entity.Payment;
import lk.ijse.studentsmanagement.entity.Registration;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO extends CrudDAO <Payment,String>{
    String getLastPaymentId () throws SQLException, ClassNotFoundException;
    double getPaymentsSum() throws SQLException, ClassNotFoundException;
    List<Payment> getAllPayments() throws SQLException, ClassNotFoundException;
    List<Payment> getPayments(Registration registration) throws SQLException, ClassNotFoundException;
}
