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

}
