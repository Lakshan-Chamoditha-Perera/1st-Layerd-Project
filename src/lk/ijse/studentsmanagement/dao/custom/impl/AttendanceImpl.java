package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.AttendanceDAO;
import lk.ijse.studentsmanagement.entity.Attendance;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceImpl implements AttendanceDAO {

    private final Connection connection;

    public AttendanceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Attendance save(Attendance entity) throws SQLException, ClassNotFoundException {
        if( CrudUtil.execute("INSERT INTO attendance VALUES(?,?,?,?)", entity.getRegistrationId(), entity.getDate(), entity.getStatus(), entity.getBatchId())) return entity;
        return null;
    }

    @Override
    public Attendance update(Attendance entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }

    @Override
    public Attendance delete(Attendance entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }

    @Override
    public Attendance view(Attendance entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }

    @Override
    public List<Attendance> loadDayAttendance(Date day) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM attendance WHERE date = ?", day.toString());
        ArrayList<Attendance> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new Attendance(resultSet.getString(1), Date.valueOf(resultSet.getString(2)), resultSet.getString(3), resultSet.getString(4)));
        }
        return list;
    }
}
