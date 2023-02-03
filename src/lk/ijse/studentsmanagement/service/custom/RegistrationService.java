package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.RegistrationDTO;
import lk.ijse.studentsmanagement.service.SuperService;

import java.sql.SQLException;

public interface RegistrationService extends SuperService <RegistrationDTO> {

    String getLastRegistrationID() throws SQLException, ClassNotFoundException;
    RegistrationDTO view (RegistrationDTO registrationDTO) throws SQLException,ClassNotFoundException;

    RegistrationDTO update(RegistrationDTO registrationDTO) throws SQLException,ClassNotFoundException;
}
