package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.SystemUserDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.SystemUserDTO;
import lk.ijse.studentsmanagement.service.custom.SystemUserService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;

import java.sql.Connection;
import java.sql.SQLException;

public class SystemUserServiceImpl implements SystemUserService {
    private final SystemUserDAO systemUserDAO;
    private final Connection connection;

    public SystemUserServiceImpl() throws SQLException, ClassNotFoundException {
        connection = DBconnection.getInstance().getConnection();
        systemUserDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.SYSTEM_USER);
        }

    @Override
    public SystemUserDTO save(SystemUserDTO dto) throws SQLException, ClassNotFoundException, DuplicateException {
        return null;
    }
}
