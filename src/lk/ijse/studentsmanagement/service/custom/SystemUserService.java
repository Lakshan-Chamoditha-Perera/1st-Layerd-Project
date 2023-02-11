package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.SystemUserDTO;
import lk.ijse.studentsmanagement.service.SuperService;
import lk.ijse.studentsmanagement.service.util.mailService.Mail;

import java.sql.SQLException;

public interface SystemUserService extends SuperService <SystemUserDTO> {
    SystemUserDTO view(SystemUserDTO systemUserDTO) throws SQLException, ClassNotFoundException;
    void sendMail(Mail mail);
}
