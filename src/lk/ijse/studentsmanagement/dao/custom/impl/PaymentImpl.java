package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.PaymentDAO;
import lk.ijse.studentsmanagement.dao.exception.NotImplementedException;
import lk.ijse.studentsmanagement.dao.util.CrudUtil;
import lk.ijse.studentsmanagement.entity.Payment;
import lk.ijse.studentsmanagement.entity.Registration;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentImpl implements PaymentDAO {
    private final Connection connection;

    public PaymentImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Payment save(Payment entity) throws SQLException, ClassNotFoundException,RuntimeException {
        if (CrudUtil.execute("INSERT INTO payments VALUES (?,?,?,?,?,?)", entity.getId(), entity.getRegistrationId(), entity.getType(), entity.getRemark(), entity.getAmount(), entity.getDate()))
            return entity;
        throw new RuntimeException();
    }
    @Override
    public Payment update(Payment entity) throws SQLException, ClassNotFoundException, RuntimeException {
        throw new NotImplementedException("update function in dao is not implemented");
    }

    @Override
    public Payment delete(Payment entity) throws SQLException, ClassNotFoundException, RuntimeException {
        throw new NotImplementedException("delete function in dao is not implemented");
    }

    @Override
    public Payment view(Payment entity) throws SQLException, ClassNotFoundException, RuntimeException {
        throw new NotImplementedException("view function in dao is not implemented");
    }

    @Override
    public String getLastPaymentId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT paymentId FROM payments ORDER BY paymentId DESC LIMIT 1");
        if (result.next()) return result.getString(1);
        return null;
    }

    @Override
    public double getPaymentsSum() throws SQLException, ClassNotFoundException {
        ResultSet execute = CrudUtil.execute("SELECT SUM(amount) FROM payments");
        execute.next();
        if (execute.getString(1) != null) return Double.parseDouble(execute.getString(1));
        return 0;
    }

    @Override
    public List<Payment> getAllPayments() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM payments");
        List<Payment> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new Payment(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), Double.parseDouble(resultSet.getString(5)), Date.valueOf(resultSet.getString(6))));
        }
        return list;
    }

    @Override
    public List<Payment> getPayments(Registration registration) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM payments WHERE registration_id = ?", registration.getRegistrationId());
        List<Payment> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new Payment(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), Double.parseDouble(resultSet.getString(5)), Date.valueOf(resultSet.getString(6))));
        }
        return list;
    }
}
