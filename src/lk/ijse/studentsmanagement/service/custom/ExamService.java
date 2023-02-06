package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.ExamDTO;
import lk.ijse.studentsmanagement.entity.Exam;
import lk.ijse.studentsmanagement.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface ExamService extends SuperService<ExamDTO> {
    List<ExamDTO> getAllExams() throws SQLException, ClassNotFoundException,RuntimeException;

    ExamDTO getLastExamID() throws SQLException, ClassNotFoundException;

    List<ExamDTO> getExams(String batchId, String subjectId) throws SQLException, ClassNotFoundException;

    List<ExamDTO> getExam(ExamDTO examDTO) throws SQLException, ClassNotFoundException;

    Exam delete(ExamDTO examDTO) throws SQLException, ClassNotFoundException;

    Exam update(ExamDTO examDTO) throws SQLException, ClassNotFoundException;

    ExamDTO view(ExamDTO examDTO) throws SQLException, ClassNotFoundException;

    String getSubjectName(ExamDTO examDTO) throws SQLException, ClassNotFoundException;

}
