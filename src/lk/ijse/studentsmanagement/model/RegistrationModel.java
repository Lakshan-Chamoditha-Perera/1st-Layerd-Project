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
    public static String getLastRegistrationID() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT registration_id FROM registration ORDER BY registration_id DESC LIMIT 1");
        if (result.next()) {
            return result.getString(1);
        }
        return null;
    }

    public static boolean addRegistration(Registration registration) throws SQLException, ClassNotFoundException, IOException, WriterException {
        return CrudUtil.execute("INSERT INTO registration VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                registration.getRegistrationId(),
                registration.getNic(),
                registration.getBatchId(),
                registration.getCourseId(),
                registration.getGardianId(),
                registration.getName(),
                registration.getAddress(),
                registration.getCity(),
                registration.getPostalCode(),
                registration.getMobile(),
                registration.getEmail(),
                registration.getDob(),
                registration.getGender(),
                registration.getSchool(),
                registration.getHigherEDU(),
                registration.getStatus()
        );
//        if (isAdded) {
//           // boolean inquiryStatus =
//            //QRGenerator.getGenerator(registration.getRegistrationId());
//        }
       // return isAdded;
    }

//    public static boolean registrationPaymentTransaction(Registration registration) throws SQLException, ClassNotFoundException, IOException, WriterException {
//        try {
//            DBconnection.getInstance().getConnection().setAutoCommit(false);
//            if (addRegistration(registration)) {
//                if (PaymentModel.addPayment(registration.getPayment())) {
//                    if(InquiryModel.updateInquiryStatus(registration.getNic())){
//                        DBconnection.getInstance().getConnection().commit();
//                        return true;
//                    }
//                }
//            }
//            DBconnection.getInstance().getConnection().rollback();
//            return false;
//
//        } finally {
//            DBconnection.getInstance().getConnection().setAutoCommit(true);
//        }
//    }

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

    public static boolean updateRegistrationDetails(Registration registration) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE registration SET full_name = ?, address = ?, city = ?, postal_code = ?, mobile_number = ?, dob = ?, school = ? WHERE registration_id = ?",
                registration.getName(),
                registration.getAddress(),
                registration.getCity(),
                registration.getPostalCode(),
                registration.getMobile(),
                registration.getDob(),
                registration.getSchool(),
                registration.getRegistrationId()
        );
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
