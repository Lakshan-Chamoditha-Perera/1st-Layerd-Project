package lk.ijse.studentsmanagement.util;

import lk.ijse.studentsmanagement.db.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class CrudUtil {
    public static <T>T execute(String sql, Object... args) throws SQLException, ClassNotFoundException {
            PreparedStatement pstm = DBconnection.getInstance().getConnection().prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                pstm.setObject((i+1), args[i]);
            }
            System.out.println(sql);
            if(sql.startsWith("SELECT") || sql.startsWith("select")) {
                return (T)pstm.executeQuery();
            }
            return (T)((Boolean)(pstm.executeUpdate() > 0));   // convert boolean to Boolean(Boxing type)
    }
}