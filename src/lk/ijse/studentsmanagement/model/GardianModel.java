package lk.ijse.studentsmanagement.model;

import com.google.zxing.WriterException;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.entity.Guardian;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GardianModel {
    public static Guardian getGardianDetail(Guardian guardian) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM gardian where id = ?", guardian.getId());
        if (resultSet.next()) {
            return new Guardian(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
        }
        return null;
    }

//    public static boolean addGardianT(Guardian guardian) throws SQLException, ClassNotFoundException, IOException, WriterException {
//        try {
//            DBconnection.getInstance().getConnection().setAutoCommit(false);
//            boolean flag = CrudUtil.execute("INSERT INTO gardian VALUES(?,?,?,?,?,?,?)",
//                    guardian.getId(),
//                    guardian.getName(),
//                    guardian.getAddress(),
//                    guardian.getMobile(),
//                    guardian.getEmail(),
//                    guardian.getDesignation(),
//                    guardian.getWorkingPlace()
//            );
////            if (flag) {
//                if (RegistrationModel.addRegistration(guardian.getRegistration())) {
//                    if (PaymentModel.addPayment(guardian.getRegistration().getPayment())) {
//                        if (InquiryModel.updateInquiryStatus(guardian.getRegistration().getNic())) {
//                            DBconnection.getInstance().getConnection().commit();
//                            return true;
//                        }
//                    }
//                }
//            }
//            DBconnection.getInstance().getConnection().rollback();
//            return false;
//        } finally {
//            DBconnection.getInstance().getConnection().setAutoCommit(true);
//        }
//    }
}
