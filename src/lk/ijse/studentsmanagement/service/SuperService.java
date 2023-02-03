package lk.ijse.studentsmanagement.service;

import lk.ijse.studentsmanagement.dto.SuperDTO;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;

import java.io.Serializable;
import java.sql.SQLException;

public interface SuperService <T extends SuperDTO>  extends Serializable {
    T save (T dto)  throws SQLException, ClassNotFoundException, DuplicateException;

}
