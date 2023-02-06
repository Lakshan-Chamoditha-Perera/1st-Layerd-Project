package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.AttendanceDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.AttendanceDTO;
import lk.ijse.studentsmanagement.entity.Attendance;
import lk.ijse.studentsmanagement.service.custom.AttendanceService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;
import lk.ijse.studentsmanagement.service.util.Converter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceDAO attendanceDAO;
    private final Connection connection;
    Converter converter;


    public AttendanceServiceImpl() throws RuntimeException {
        try {
            converter = new Converter();
            connection = DBconnection.getInstance().getConnection();
            attendanceDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.ATTENDANCE);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AttendanceDTO save(AttendanceDTO dto) throws SQLException, ClassNotFoundException, DuplicateException {
        return null;
    }

    @Override
    public List<AttendanceDTO> loadDayAttendance(Date day) throws SQLException, ClassNotFoundException {
        List<Attendance> attendances = attendanceDAO.loadDayAttendance(day);
        if (attendances.size() > 0) {
            return attendances.stream().map(attendance -> converter.toAttendanceDTO(attendance)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public boolean addAttendance(AttendanceDTO present) throws SQLException, ClassNotFoundException {
        Attendance save = attendanceDAO.save(converter.toAttendanceEntity(present));
        return save!=null;
    }

}
