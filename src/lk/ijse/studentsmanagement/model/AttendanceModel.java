package lk.ijse.studentsmanagement.model;

import lk.ijse.studentsmanagement.entity.Attendance;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceModel {
    public static ArrayList<Attendance> loadDayAttendance(Date valueOf) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM attendance WHERE date = ?", valueOf);
        if(resultSet!=null){
            ArrayList<Attendance> list = new ArrayList<>();
            while(resultSet.next()){
                list.add(
                        new Attendance(
                                resultSet.getString(1),
                                Date.valueOf(resultSet.getString(2)),
                                resultSet.getString(3),
                                resultSet.getString(4)
                        )
                );
            }
            return list;
        }
        return null;
    }

    public static boolean addAttendance(Attendance present) throws SQLException, ClassNotFoundException {
        return
                CrudUtil.execute("INSERT INTO attendance VALUES(?,?,?,?)",
                present.getRegistrationId(),
                present.getDate(),
                present.getStatus(),
                present.getBatchId()
                );
    }
}