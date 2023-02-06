package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.RegistrationDTO;
import lk.ijse.studentsmanagement.dto.RegistrationExamResultDTO;
import lk.ijse.studentsmanagement.entity.RegistrationExamResult;
import lk.ijse.studentsmanagement.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface RegistrationExamResultsService extends SuperService  <RegistrationExamResultDTO>{


    List<RegistrationExamResultDTO> getResults(String examId) throws SQLException, ClassNotFoundException;

    boolean delete(RegistrationExamResultDTO registrationExamResultDTO) throws SQLException, ClassNotFoundException;

    boolean update(RegistrationExamResultDTO registrationExamResultDTO) throws SQLException, ClassNotFoundException;
}
