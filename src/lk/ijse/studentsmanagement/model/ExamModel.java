package lk.ijse.studentsmanagement.model;

import lk.ijse.studentsmanagement.entity.Exam;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class ExamModel {

    public static boolean addExam(Exam exam) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO exam VALUES (?,?,?,?,?,?,?)",
                exam.getExamId(),
                exam.getSubjectId(),
                exam.getBatchId(),
                exam.getDescription(),
                exam.getExamDate(),
                exam.getLab(),
                exam.getTime()
        );
    }

    public static Exam getExamID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT id from exam ORDER BY id DESC LIMIT 1");
        if (resultSet.next()) {
            return new Exam(resultSet.getString(1));
        }
        return null;
    }

    public static ArrayList<Exam> getExams(String batchId, String subjectId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM exam WHERE batchId = ? AND subjectID = ?", batchId, subjectId);
        ArrayList<Exam> list = new ArrayList<>();
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

    public static ArrayList<Exam> getAllExams() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM exam ");
        if (resultSet != null) {
            ArrayList<Exam> list = new ArrayList<>();
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
        return null;
    }

    public static boolean updateExamDetails(Exam exam) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE exam SET  subjectID = ?, batchId = ?, description = ?, date = ?, lab = ?, Time = ? WHERE id = ?",
                exam.getSubjectId(),
                exam.getBatchId(),
                exam.getDescription(),
                exam.getExamDate(),
                exam.getLab(),
                exam.getTime(),
                exam.getExamId()
        );
    }

    public static boolean deleteExam(Exam exam) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM exam WHERE id = ?",
                exam.getExamId()
        );
    }

    public static ArrayList<Exam> getExam(Exam exam) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM exam WHERE batchId=?", exam.getBatchId());
        if (resultSet != null) {
            ArrayList<Exam> list = new ArrayList<>();
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
        return null;
    }

    public static Exam getExamDetails(Exam exam) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM exam WHERE id = ?", exam.getExamId());
        Exam ex = null;
        if (resultSet.next()) {
            ex = new Exam(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    Date.valueOf(resultSet.getString(5)
                    )
            );
        }
        return ex;
    }

    public static String getSubjectName(Exam exam) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT name FROM subject WHERE id = (SELECT subjectID FROM exam WHERE id = ?)",exam.getExamId() );
        if(resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
