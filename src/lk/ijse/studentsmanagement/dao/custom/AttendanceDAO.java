package lk.ijse.studentsmanagement.dao.custom;

import lk.ijse.studentsmanagement.dao.CrudDAO;
import lk.ijse.studentsmanagement.entity.Attendance;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface AttendanceDAO extends CrudDAO <Attendance,String>{
    List<Attendance> loadDayAttendance(Date day) throws SQLException, ClassNotFoundException;
}
