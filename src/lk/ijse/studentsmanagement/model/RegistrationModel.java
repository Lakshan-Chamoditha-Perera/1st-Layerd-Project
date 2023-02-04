package lk.ijse.studentsmanagement.model;

import com.google.zxing.WriterException;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.entity.Registration;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegistrationModel {


    public static ArrayList<Registration> getCourseBatchList(String courseID, String batchID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT registration_id,full_name,mobile_number,email,status FROM registration where courseID = ? AND batchID = ?", courseID, batchID);
        ArrayList<Registration> arrayList = new ArrayList<>();
        while (resultSet.next()) {
            arrayList.add(
                    new Registration(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    )
            );
        }
        return arrayList;
    }

    public static String getRegistrationEmail(String text) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT email from registration where registration_id = ?", text);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static ArrayList<String> getRegistrationEmailList(String text) throws SQLException, ClassNotFoundException {
        ArrayList<String> emailList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT email from registration where batchID = ?", text);
        if (resultSet.next()) {
            emailList.add(resultSet.getString(1));
        }
        return emailList;
    }

    public static Registration getRegistrationDetails(String text) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * from registration WHERE registration_id = ?", text);
        if (resultSet.next()) {
            return new Registration(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    Date.valueOf(resultSet.getString(12)),
                    resultSet.getString(13),
                    resultSet.getString(14),
                    resultSet.getString(15),
                    resultSet.getString(16)

            );
        }
        return null;
    }

    public static ArrayList<Registration> loadBatchRegistrations(String value) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT registration_id, batchID, full_name, status from registration WHERE batchID = ?", value);
        if(resultSet!=null){
            ArrayList<Registration> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(
                  new Registration(
                          resultSet.getString(1),
                          resultSet.getString(2),
                          resultSet.getString(3),
                          resultSet.getString(4)
                  )
                );
            }
            return list;
        }
        return null;
    }
}
