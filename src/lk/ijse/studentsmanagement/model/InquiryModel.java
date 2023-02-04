package lk.ijse.studentsmanagement.model;

import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.entity.Inquiry;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InquiryModel {

    public static Inquiry getEmail(Inquiry inquiry) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT email FROM inquiry WHERE studentID = ?", inquiry.getStudentID());
        if (resultSet.next()) {
            return new Inquiry(inquiry.getStudentID(), resultSet.getString(1));
        }
        return null;
    }
}