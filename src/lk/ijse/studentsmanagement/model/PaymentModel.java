package lk.ijse.studentsmanagement.model;

import lk.ijse.studentsmanagement.entity.Payment;
import lk.ijse.studentsmanagement.entity.Registration;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {
    public static String getLastPaymentID() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT paymentId FROM payments ORDER BY paymentId DESC LIMIT 1");
        if (result.next()) {
            return result.getString(1);
        }
        return null;
    }

    public static ArrayList<Payment> getPayments(Registration registration) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM payments WHERE registration_id = ?", registration.getRegistrationId());
        if (resultSet != null) {
            ArrayList<Payment> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(
                        new Payment(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                Double.parseDouble(resultSet.getString(5)),
                                Date.valueOf(resultSet.getString(6))
                        )
                );
            }
            return list;
        }
        return null;
    }

    public static double getPaymentsSum() throws SQLException, ClassNotFoundException {
        ResultSet execute = CrudUtil.execute("SELECT SUM(amount) FROM payments");
        execute.next();
        if(execute.getString(1) != null) return Double.parseDouble(execute.getString(1));
        return 0;
    }

    public static ArrayList<Payment> getAllPayments() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM payments");
        if (resultSet != null) {
            ArrayList<Payment> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(
                        new Payment(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                Double.parseDouble(resultSet.getString(5)),
                                Date.valueOf(resultSet.getString(6))
                        )
                );
            }
            return list;
        }
        return null;
    }
}
