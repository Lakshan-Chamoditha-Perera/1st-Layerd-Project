package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.SystemUserDAO;
import lk.ijse.studentsmanagement.entity.SystemUser;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemUserImpl implements SystemUserDAO {
    private final Connection connection;

    public SystemUserImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public SystemUser save(SystemUser entity) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public SystemUser update(SystemUser entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }

    @Override
    public SystemUser delete(SystemUser entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }

    @Override
    public SystemUser view(SystemUser entity) throws SQLException, ClassNotFoundException, RuntimeException {
        ResultSet resultSet = CrudUtil.execute("SELECT * from loginCredential where userName=?", entity.getUserName());
        if (resultSet.next()) {
            return new SystemUser(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3));
        }
        return null;
    }
}
