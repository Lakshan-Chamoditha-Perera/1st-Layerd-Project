package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.SubjectDAO;
import lk.ijse.studentsmanagement.dao.util.CrudUtil;
import lk.ijse.studentsmanagement.entity.Subject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectImpl implements SubjectDAO {
    private final Connection connection;

    public SubjectImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Subject save(Subject entity) throws SQLException, ClassNotFoundException, RuntimeException {
        if (CrudUtil.execute("INSERT INTO subject VALUES(?,?,?)", entity.getId(), entity.getName(), entity.getHours()))
            return entity;
        throw new RuntimeException("Subject not added");
    }

    @Override
    public Subject update(Subject entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }

    @Override
    public Subject delete(Subject entity) throws SQLException, ClassNotFoundException, RuntimeException {
        if (CrudUtil.execute("DELETE FROM subject WHERE id = ?", entity.getId())) return entity;
        throw new RuntimeException("Subject not deleted...");
    }

    @Override
    public Subject view(Subject entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }

    @Override
    public List<Subject> getIDs() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT id FROM subject ");
        List<Subject> arrayList = new ArrayList<>();
        while (resultSet.next()) {
            arrayList.add(new Subject(resultSet.getString(1)));
        }
        return arrayList;
    }

    @Override
    public String getSubjectName(Subject subject) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT name FROM subject WHERE id = ?", subject.getId());
        resultSet.next();
        return resultSet.getString(1);
    }

    @Override
    public List<Subject> getSubjectList() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM subject");
        ArrayList<Subject> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new Subject(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
        }
        return list;

    }

    @Override
    public Subject getLastSubjectID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT id from subject ORDER BY id DESC LIMIT 1");
        if (resultSet.next()) {
            return new Subject(resultSet.getString(1));
        }
        return null;
    }

}
