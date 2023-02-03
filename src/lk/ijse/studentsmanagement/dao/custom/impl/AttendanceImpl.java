package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.AttendanceDAO;
import lk.ijse.studentsmanagement.entity.Attendance;

import java.sql.Connection;
import java.sql.SQLException;

public class AttendanceImpl implements AttendanceDAO {

    private final Connection connection;

    public AttendanceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Attendance save(Attendance entity) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Attendance update(Attendance entity) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Attendance delete(Attendance entity) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Attendance view(Attendance entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }
}
