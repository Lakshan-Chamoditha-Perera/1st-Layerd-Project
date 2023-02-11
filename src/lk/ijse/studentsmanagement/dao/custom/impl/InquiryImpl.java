package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.InquiryDAO;
import lk.ijse.studentsmanagement.dao.util.CrudUtil;
import lk.ijse.studentsmanagement.entity.Inquiry;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InquiryImpl implements InquiryDAO {
    private final Connection connection;

    public InquiryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Inquiry save(Inquiry entity) throws SQLException, ClassNotFoundException {
        try {
            if (CrudUtil.execute(
                    "INSERT INTO inquiry VALUES (?,?,?,?,?,?,?,?)",
                    entity.getStudentID(),
                    entity.getName(),
                    entity.getCity(),
                    entity.getMobile(),
                    entity.getEmail(),
                    entity.getDate(),
                    entity.getGender(),
                    entity.getStatus()
            )) {
                return entity;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Inquiry update(Inquiry entity) throws SQLException, ClassNotFoundException, RuntimeException {
        try {
            if (CrudUtil.execute("UPDATE inquiry SET name = ?, city = ?, email = ?, mobile = ?,gender = ?  WHERE studentID = ?",
                    entity.getName(),
                    entity.getCity(),
                    entity.getEmail(),
                    entity.getMobile(),
                    entity.getGender(),
                    entity.getStudentID()
            )) return entity;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Inquiry delete(Inquiry entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }

    @Override
    public Inquiry view(Inquiry entity) throws SQLException, ClassNotFoundException, RuntimeException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM inquiry WHERE studentID = ?", entity.getStudentID());
        if (resultSet.next()) {
            return new Inquiry(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            );
        }
        return null;
    }

    @Override
    public List<Inquiry> getAllInquires() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM inquiry");
        List<Inquiry> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new Inquiry(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8)
                    )
            );
        }
        return list;
    }

    @Override
    public boolean updateInquiryStatus(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE inquiry SET status = 'Registered' WHERE studentID = ?", id);
    }

    @Override
    public int getRegisteredStdCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(studentID) FROM inquiry WHERE status = ?", "Registered");
        resultSet.next();
        return Integer.parseInt(resultSet.getString(1));
    }

    @Override
    public int getInquiriesCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(studentID) FROM inquiry");
        resultSet.next();
        return Integer.parseInt(resultSet.getString(1));
    }

    @Override
    public int getUnregisteredStdCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(studentID) FROM inquiry WHERE status = ?", "not-registered");
        resultSet.next();
        return Integer.parseInt(resultSet.getString(1));
    }

    @Override
    public Inquiry getEmail(Inquiry inquiry) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT email FROM inquiry WHERE studentID = ?", inquiry.getStudentID());
        if (resultSet.next()) {
            return new Inquiry(inquiry.getStudentID(), resultSet.getString(1));
        }
        throw new RuntimeException();
    }
}
