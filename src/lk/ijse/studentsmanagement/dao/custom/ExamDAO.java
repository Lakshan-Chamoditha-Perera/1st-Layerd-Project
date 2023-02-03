package lk.ijse.studentsmanagement.dao.custom;

import lk.ijse.studentsmanagement.dao.CrudDAO;
import lk.ijse.studentsmanagement.entity.Exam;

import java.sql.SQLException;
import java.util.List;

public interface ExamDAO extends CrudDAO <Exam,String>{
    Exam getExamID() throws SQLException, ClassNotFoundException;
    List<Exam> getExams(String batchId, String subjectId) throws SQLException, ClassNotFoundException;
    List<Exam> getAllExams() throws SQLException, ClassNotFoundException;
    List<Exam> getExam(Exam exam) throws SQLException, ClassNotFoundException;
    Exam getExamDetails(Exam exam) throws SQLException, ClassNotFoundException;
    String getSubjectName(Exam exam) throws SQLException, ClassNotFoundException;
}
