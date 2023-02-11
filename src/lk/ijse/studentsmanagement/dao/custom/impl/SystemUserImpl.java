package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.SystemUserDAO;
import lk.ijse.studentsmanagement.dao.exception.NotImplementedException;
import lk.ijse.studentsmanagement.dao.util.CrudUtil;
import lk.ijse.studentsmanagement.entity.SystemUser;

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
        throw new NotImplementedException("save function in dao is not implemented");
    }

    @Override
    public SystemUser update(SystemUser entity) throws SQLException, ClassNotFoundException, RuntimeException {
        throw new NotImplementedException("update function in dao is not implemented");
    }

    @Override
    public SystemUser delete(SystemUser entity) throws SQLException, ClassNotFoundException, RuntimeException {
        throw new NotImplementedException("delete function in dao is not implemented");
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
