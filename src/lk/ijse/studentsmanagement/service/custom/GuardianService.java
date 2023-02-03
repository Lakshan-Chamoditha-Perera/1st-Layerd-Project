package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.GuardianDTO;
import lk.ijse.studentsmanagement.service.SuperService;

import java.sql.SQLException;

public interface GuardianService extends SuperService <GuardianDTO> {

    GuardianDTO view(GuardianDTO guardianDTO) throws SQLException, ClassNotFoundException;
}
