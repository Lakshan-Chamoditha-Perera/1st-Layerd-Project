package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.SystemUserDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.SystemUserDTO;
import lk.ijse.studentsmanagement.entity.SystemUser;
import lk.ijse.studentsmanagement.service.custom.SystemUserService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;
import lk.ijse.studentsmanagement.service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;

public class SystemUserServiceImpl implements SystemUserService {
    private final SystemUserDAO systemUserDAO;
    private final Connection connection;
    Converter converter;

    public SystemUserServiceImpl() throws SQLException, ClassNotFoundException {
        converter = new Converter();
        connection = DBconnection.getInstance().getConnection();
        systemUserDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.SYSTEM_USER);
    }

    @Override
    public SystemUserDTO save(SystemUserDTO dto) throws SQLException, ClassNotFoundException, DuplicateException {
        return null;
    }

    @Override
    public SystemUserDTO view(SystemUserDTO systemUserDTO) throws SQLException, ClassNotFoundException {
        SystemUser systemUser = systemUserDAO.view(converter.toSystemUserEntity(systemUserDTO));
        if(systemUser!=null) return converter.toSystemUserDTO(systemUser);
        return null;
    }
}
