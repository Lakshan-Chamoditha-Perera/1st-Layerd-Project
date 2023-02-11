package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.RegistrationExamResultsDAO;
import lk.ijse.studentsmanagement.dao.exception.NotImplementedException;
import lk.ijse.studentsmanagement.dao.util.CrudUtil;
import lk.ijse.studentsmanagement.entity.RegistrationExamResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationExamResultsImpl implements RegistrationExamResultsDAO {
    private final Connection connection;

    public RegistrationExamResultsImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public RegistrationExamResult save(RegistrationExamResult entity) throws SQLException, ClassNotFoundException,RuntimeException {
        if (CrudUtil.execute("INSERT INTO registration_exam_results VALUES(?,?,?,?)",
                entity.getExamId(),
                entity.getRegistrationId(),
                entity.getMark(),
                entity.getResult()
        )) return entity;
        throw new RuntimeException("not added");
    }

    @Override
    public RegistrationExamResult update(RegistrationExamResult entity) throws SQLException, ClassNotFoundException, RuntimeException {
        if (CrudUtil.execute("UPDATE registration_exam_results SET marks =?, result=? WHERE exam_id=? AND registration_id =?",
                entity.getMark(),
                entity.getResult(),
                entity.getExamId(),
                entity.getRegistrationId()
        )) return entity;
        throw new RuntimeException();
    }

    @Override
    public RegistrationExamResult delete(RegistrationExamResult entity) throws SQLException, ClassNotFoundException, RuntimeException {
        if (CrudUtil.execute("DELETE FROM registration_exam_results WHERE exam_id=? AND registration_id =?",
                entity.getExamId(),
                entity.getRegistrationId()
        )) return entity;
        throw new RuntimeException();
    }

    @Override
    public RegistrationExamResult view(RegistrationExamResult entity) throws SQLException, ClassNotFoundException, RuntimeException {
        throw new NotImplementedException("view function in dao is not implemented");
    }

    @Override
    public List<RegistrationExamResult> getAllResults(String examId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM registration_exam_results WHERE exam_id = ?", examId);
        List<RegistrationExamResult> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new RegistrationExamResult(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            Integer.parseInt(resultSet.getString(3)),
                            resultSet.getString(4)
                    )
            );
        }
        return list;
    }
}
