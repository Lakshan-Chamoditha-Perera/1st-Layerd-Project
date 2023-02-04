package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.RegistrationDTO;
import lk.ijse.studentsmanagement.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface RegistrationService extends SuperService <RegistrationDTO> {

    String getLastRegistrationID() throws SQLException, ClassNotFoundException;
    RegistrationDTO view (RegistrationDTO registrationDTO) throws SQLException,ClassNotFoundException;

    RegistrationDTO update(RegistrationDTO registrationDTO) throws SQLException,ClassNotFoundException;

    List<RegistrationDTO> getCourseBatchList(String course, String batch) throws SQLException, ClassNotFoundException;

    String getRegistrationEmail(String text) throws SQLException, ClassNotFoundException,RuntimeException;

    List<String> getRegistrationEmailList(String value) throws SQLException, ClassNotFoundException;
}
