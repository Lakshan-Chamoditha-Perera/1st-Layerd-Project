package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.TestPaymentDAO;
import lk.ijse.studentsmanagement.dao.util.CrudUtil;
import lk.ijse.studentsmanagement.entity.TestPayment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestPaymentImpl implements TestPaymentDAO {
    private final Connection connection;

    public TestPaymentImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public TestPayment save(TestPayment entity) throws SQLException, ClassNotFoundException {
        if (CrudUtil.execute("INSERT INTO test_Payment VALUES(?,?,?,?,?,?)",
                entity.getId(),
                entity.getStudentID(),
                entity.getDate(),
                entity.getRemark(),
                entity.getAmount(),
                entity.getIqTestId()
        )) return entity;
        throw new RuntimeException("Test Payment not added successfully!");
    }

    @Override
    public TestPayment update(TestPayment entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }

    @Override
    public TestPayment delete(TestPayment entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }

    @Override
    public TestPayment view(TestPayment entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }

    @Override
    public String getLastPaymentID() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT id FROM test_Payment ORDER BY id DESC LIMIT 1");
        if (result.next()) {
            return result.getString(1);
        }
        return null;
    }

    @Override
    public double getPaymentsSum() throws SQLException, ClassNotFoundException {
        ResultSet execute = CrudUtil.execute("SELECT SUM(amount) FROM test_Payment");
        if (execute.next()) {
            if (execute.getString(1) != null) {
                return Double.parseDouble(execute.getString(1));
            }
        }
        return 0;
    }

    @Override
    public List<TestPayment> getAllPayments() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM test_Payment");
        List<TestPayment> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new TestPayment(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    Date.valueOf(resultSet.getString(3)),
                    resultSet.getString(4),
                    Double.parseDouble(resultSet.getString(5))
            ));
        }
        return list;
    }
}
