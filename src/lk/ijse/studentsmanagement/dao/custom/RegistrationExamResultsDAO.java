package lk.ijse.studentsmanagement.dao.custom;

import lk.ijse.studentsmanagement.dao.CrudDAO;
import lk.ijse.studentsmanagement.entity.Registration;
import lk.ijse.studentsmanagement.entity.RegistrationExamResult;

import java.sql.SQLException;
import java.util.List;

public interface RegistrationExamResultsDAO extends CrudDAO <RegistrationExamResult,String>{
    List<RegistrationExamResult> getAllResults(String examId) throws SQLException, ClassNotFoundException;

}
