package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.IqTestDAO;
import lk.ijse.studentsmanagement.entity.IQTest;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IqTestImpl implements IqTestDAO {
    private final Connection connection;

    public IqTestImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public IQTest save(IQTest entity) throws SQLException, ClassNotFoundException {
        if(CrudUtil.execute("INSERT INTO iqTest VALUES(?,?,?,?,?)",
                entity.getId(),
                entity.getDate(),
                entity.getTime(),
                entity.getLab(),
                entity.getAmount()
        )) return entity;
        throw new RuntimeException();
    }

    @Override
    public IQTest update(IQTest entity) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public IQTest delete(IQTest entity) throws SQLException, ClassNotFoundException {
        if(CrudUtil.execute("DELETE FROM iqTest WHERE id = ?", entity.getId())) return entity;
        throw new RuntimeException();
    }

    @Override
    public IQTest view(IQTest entity) throws SQLException, ClassNotFoundException, RuntimeException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM iqTest WHERE entity = ?", entity);
        if(resultSet.next()){
            return new IQTest(
                    resultSet.getString(1),
                    Date.valueOf(resultSet.getString(2)),
                    Time.valueOf(resultSet.getString(3)),
                    resultSet.getString(4),
                    Double.parseDouble(resultSet.getString(5))

            );
        }
        return null;
    }

    @Override
    public List<IQTest> getList() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM iqTest");
        List<IQTest> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new IQTest(
                            resultSet.getString(1),
                            Date.valueOf(resultSet.getString(2)),
                            Time.valueOf(resultSet.getString(3)),
                            resultSet.getString(4),
                            Double.parseDouble(resultSet.getString(5)))
            );
        }
        return list;
    }

    @Override
    public IQTest getExamByDate(Date date) throws SQLException, ClassNotFoundException,RuntimeException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM iqTest WHERE date = ?", date);
        if (resultSet.next()) {
            return new IQTest(
                    resultSet.getString(1),
                    Date.valueOf(resultSet.getString(2)),
                    Time.valueOf(resultSet.getString(3)),
                    resultSet.getString(4),
                    Double.parseDouble(resultSet.getString(5))
            );
        }
        throw new RuntimeException("Exam not found");
    }

    @Override
    public IQTest getExamId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT id from iqTest ORDER BY id DESC LIMIT 1");
        if (resultSet.next()) {
            return new IQTest(resultSet.getString(1));
        }
        throw new RuntimeException();
    }

    @Override
    public IQTest getExamDetails(IQTest iqTest) throws SQLException, ClassNotFoundException,RuntimeException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM iqTest WHERE id = ?", iqTest.getId());
        if(resultSet.next()){
            return new IQTest(
                    resultSet.getString(1),
                    Date.valueOf(resultSet.getString(2)),
                    Time.valueOf(resultSet.getString(3)),
                    resultSet.getString(4),
                    Double.parseDouble(resultSet.getString(5))

            );
        }
        throw new RuntimeException("Invalid iqTest ID...");
    }
}
