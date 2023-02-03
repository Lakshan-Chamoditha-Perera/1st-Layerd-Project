package lk.ijse.studentsmanagement.dao.custom;

import lk.ijse.studentsmanagement.dao.CrudDAO;
import lk.ijse.studentsmanagement.entity.TestPayment;

import java.sql.SQLException;
import java.util.List;

public interface TestPaymentDAO extends CrudDAO <TestPayment,String>{
    String getLastPaymentID() throws SQLException, ClassNotFoundException;
    double getPaymentsSum() throws SQLException, ClassNotFoundException;
    List<TestPayment> getAllPayments() throws SQLException, ClassNotFoundException;
}
