package lk.ijse.studentsmanagement.model;

import lk.ijse.studentsmanagement.entity.SystemUser;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemUserModel {
    public static SystemUser search(SystemUser user) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * from loginCredential where userName=?", user.getUserName());
        if (resultSet.next()) {
            return (new SystemUser(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3))
            );
        }
        return null;
    }
}