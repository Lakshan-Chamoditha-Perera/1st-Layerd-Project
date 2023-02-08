package lk.ijse.studentsmanagement.dao.util;

import lk.ijse.studentsmanagement.db.DBconnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static <T> T execute(String sql, Object... args) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DBconnection.getInstance().getConnection().prepareStatement(sql);
        for (int i = 0; i < args.length; i++)
            preparedStatement.setObject(i + 1, args[i]);
        return (sql.startsWith("SELECT") | sql.startsWith("select"))
                ? (T) preparedStatement.executeQuery()
                : (T) (Boolean) (preparedStatement.executeUpdate() > 0);
    }
}
