package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.GuardianDAO;
import lk.ijse.studentsmanagement.entity.Guardian;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuardianImpl implements GuardianDAO {
    private final Connection connection;

    public GuardianImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Guardian save(Guardian entity) throws SQLException, ClassNotFoundException {
        if(CrudUtil.execute("INSERT INTO gardian VALUES(?,?,?,?,?,?,?)",
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getMobile(),
                entity.getEmail(),
                entity.getDesignation(),
                entity.getWorkingPlace()
        )) return entity;
        throw new RuntimeException();
    }

    @Override
    public Guardian update(Guardian entity) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Guardian delete(Guardian entity) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Guardian view(Guardian entity) throws SQLException, ClassNotFoundException, RuntimeException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM gardian where entity = ?", entity.getId());
        if (resultSet.next()) {
            return new Guardian(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
        }
        return null;
    }
}
