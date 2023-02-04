package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.ExamDAO;
import lk.ijse.studentsmanagement.entity.Exam;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamImpl implements ExamDAO {
    private final Connection connection;

    public ExamImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Exam save(Exam entity) throws SQLException, ClassNotFoundException {
        if (CrudUtil.execute("INSERT INTO exam VALUES (?,?,?,?,?,?,?)",
                entity.getExamId(),
                entity.getSubjectId(),
                entity.getBatchId(),
                entity.getDescription(),
                entity.getExamDate(),
                entity.getLab(),
                entity.getTime()
        )) return entity;
        throw new RuntimeException();
    }

    @Override
    public Exam update(Exam entity) throws SQLException, ClassNotFoundException {
        if(CrudUtil.execute("UPDATE exam SET  subjectID = ?, batchId = ?, description = ?, date = ?, lab = ?, Time = ? WHERE id = ?",
                entity.getSubjectId(),
                entity.getBatchId(),
                entity.getDescription(),
                entity.getExamDate(),
                entity.getLab(),
                entity.getTime(),
                entity.getExamId()
        )) return entity;

        throw new RuntimeException();
    }

    @Override
    public Exam delete(Exam entity) throws SQLException, ClassNotFoundException {
        if(CrudUtil.execute("DELETE FROM exam WHERE id = ?",
                entity.getExamId()
        )) return entity;
        throw new RuntimeException();
    }

    @Override
    public Exam view(Exam entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }

    @Override
    public Exam getLastExamID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT id from exam ORDER BY id DESC LIMIT 1");
        if (resultSet.next()) {
            return new Exam(resultSet.getString(1));
        }
       return null;
    }

    @Override
    public List<Exam> getExams(String batchId, String subjectId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM exam WHERE batchId = ? AND subjectID = ?", batchId, subjectId);
        List<Exam> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new Exam(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            Date.valueOf(resultSet.getString(5)),
                            resultSet.getString(6),
                            Time.valueOf(resultSet.getString(7))
                    )
            );
            return list;
        }
        throw new RuntimeException();
    }

    @Override
    public List<Exam> getAllExams() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM exam ");
        List<Exam> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new Exam(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            Date.valueOf(resultSet.getString(5)),
                            resultSet.getString(6),
                            Time.valueOf(resultSet.getString(7))
                    )
            );
        }
        return list;
    }

    @Override
    public List<Exam> getExam(Exam exam) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM exam WHERE batchId=?", exam.getBatchId());
            List<Exam> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(
                        new Exam(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                Date.valueOf(resultSet.getString(5))
                        )
                );
            }
            return list;
    }

    @Override
    public Exam getExamDetails(Exam exam) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM exam WHERE id = ?", exam.getExamId());
        if (resultSet.next()) {
            return new Exam(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    Date.valueOf(resultSet.getString(5)
                    )
            );
        }
        throw new RuntimeException();

    }

    @Override
    public String getSubjectName(Exam exam) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT name FROM subject WHERE id = (SELECT subjectID FROM exam WHERE id = ?)",exam.getExamId() );
        if(resultSet.next()){
            return resultSet.getString(1);
        }
        throw new RuntimeException();
    }
}
