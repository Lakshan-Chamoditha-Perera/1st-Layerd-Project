package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.AttendanceDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.AttendanceDTO;
import lk.ijse.studentsmanagement.service.custom.AttendanceService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;

import java.sql.Connection;
import java.sql.SQLException;

public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceDAO attendanceDAO;
    private final Connection connection;

    public AttendanceServiceImpl() throws RuntimeException{
        try {
            connection = DBconnection.getInstance().getConnection();
            attendanceDAO = DAOFactory.getInstance().getDAO(connection,DaoTypes.ATTENDANCE);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public AttendanceDTO save(AttendanceDTO dto) throws SQLException, ClassNotFoundException, DuplicateException {
        return null;
    }
}
