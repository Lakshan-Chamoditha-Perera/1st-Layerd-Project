package lk.ijse.studentsmanagement.model;

import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.entity.Inquiry;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InquiryModel {
    public static boolean addInquiry(Inquiry inquiry) throws SQLException, ClassNotFoundException {
        try {
            DBconnection.getInstance().getConnection().setAutoCommit(false);
            if (CrudUtil.execute(
                    "INSERT INTO inquiry VALUES (?,?,?,?,?,?,?,?)",
                    inquiry.getStudentID(),
                    inquiry.getName(),
                    inquiry.getCity(),
                    inquiry.getMobile(),
                    inquiry.getEmail(),
                    inquiry.getDate(),
                    inquiry.getGender(),
                    inquiry.getStatus()
            )) {
                if (TestPaymentModel.addTestPayment(inquiry.getTestPayment())) {
                    if (InquiryIQTestDetailModel.addInquiryTestDetail(inquiry.getTestPayment().getInquiryIQTestDetail())) {
                        DBconnection.getInstance().getConnection().commit();
                        return true;
                    }
                }
            }
            DBconnection.getInstance().getConnection().rollback();
            return false;
        } finally {
            DBconnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public static ArrayList<Inquiry> getAllInquires() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM inquiry");
        ArrayList<Inquiry> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new Inquiry(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(5),
                            resultSet.getString(4),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8)
                    )
            );
        }
        return list;
    }

    public static Inquiry searchInquiry(Inquiry inquiry) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM inquiry WHERE studentID = ?", inquiry.getStudentID());
        if (resultSet.next()) {
            return new Inquiry(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(4),
                    resultSet.getString(3),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            );
        }
        return null;
    }

    public static boolean updateInquiryDetails(Inquiry inquiry) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE inquiry SET name = ?, city = ?, email = ?, mobile = ?,gender = ?  WHERE studentID = ?",
                inquiry.getName(),
                inquiry.getCity(),
                inquiry.getEmail(),
                inquiry.getMobile(),
                inquiry.getGender(),
                inquiry.getStudentID()
        );
    }

    public static boolean updateInquiryStatus(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE inquiry SET status = 'Registered' WHERE studentID = ?", id);
    }

    public static int getRegisteredStdCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(studentID) FROM inquiry WHERE status = ?", "Registered");
        resultSet.next();
        return Integer.parseInt(resultSet.getString(1));
    }

    public static int getInquiriesCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(studentID) FROM inquiry");
        resultSet.next();
        return Integer.parseInt(resultSet.getString(1));
    }

    public static int getUnregisterdStdCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(studentID) FROM inquiry WHERE status = ?", "not-registered");
        resultSet.next();
        return Integer.parseInt(resultSet.getString(1));
    }

    public static Inquiry getEmail(Inquiry inquiry) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT email FROM inquiry WHERE studentID = ?", inquiry.getStudentID());
        if (resultSet.next()) {
            return new Inquiry(inquiry.getStudentID(), resultSet.getString(1));
        }
        return null;
    }
}