package lk.ijse.studentsmanagement.model;

import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.entity.TestPayment;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestPaymentModel {
    public static String getLastPaymentID() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT id FROM test_Payment ORDER BY id DESC LIMIT 1");
        if (result.next()) {
            return result.getString(1);
        }
        return null;
    }

    public static double getPaymentsSum() throws SQLException, ClassNotFoundException {
        ResultSet execute = CrudUtil.execute("SELECT SUM(amount) FROM test_Payment");
        if (execute.next()) {
            if (execute.getString(1) != null) {
                return Double.parseDouble(execute.getString(1));
            }
        }
        return 0;
    }

    public static ArrayList<TestPayment> getAllPayments() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM test_Payment");
        if (resultSet != null) {
            ArrayList<TestPayment> list = new ArrayList<>();
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
        return null;
    }
}