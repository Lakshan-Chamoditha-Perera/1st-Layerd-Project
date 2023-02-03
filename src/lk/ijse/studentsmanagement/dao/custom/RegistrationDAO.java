package lk.ijse.studentsmanagement.dao.custom;

import lk.ijse.studentsmanagement.dao.CrudDAO;
import lk.ijse.studentsmanagement.entity.Registration;

import java.sql.SQLException;
import java.util.List;

public interface RegistrationDAO extends CrudDAO <Registration,String>{
    String getLastRegistrationID() throws SQLException, ClassNotFoundException;
    List<Registration> getCourseBatchList(String courseID, String batchID) throws SQLException, ClassNotFoundException;
    String getRegistrationEmail(String text) throws SQLException, ClassNotFoundException;
    List<String> getRegistrationEmailList(String text) throws SQLException, ClassNotFoundException;
    List<Registration> loadBatchRegistrations(String value) throws SQLException, ClassNotFoundException;
}
