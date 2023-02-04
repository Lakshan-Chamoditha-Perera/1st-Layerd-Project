package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.ExamDTO;
import lk.ijse.studentsmanagement.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface ExamService extends SuperService<ExamDTO> {
    List<ExamDTO> getAllExams() throws SQLException, ClassNotFoundException;

    ExamDTO getLastExamID() throws SQLException, ClassNotFoundException;

}
