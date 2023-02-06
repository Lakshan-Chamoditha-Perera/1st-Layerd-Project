package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.AttendanceDTO;
import lk.ijse.studentsmanagement.service.SuperService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface AttendanceService extends SuperService <AttendanceDTO>{
    List<AttendanceDTO> loadDayAttendance(Date day) throws SQLException, ClassNotFoundException;

    boolean addAttendance(AttendanceDTO present) throws SQLException, ClassNotFoundException;
}
