package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.SystemUserDTO;
import lk.ijse.studentsmanagement.service.SuperService;

import java.sql.SQLException;

public interface SystemUserService extends SuperService <SystemUserDTO> {
    SystemUserDTO view(SystemUserDTO systemUserDTO) throws SQLException, ClassNotFoundException;

}
