package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.SubjectDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.SubjectDTO;
import lk.ijse.studentsmanagement.service.custom.SubjectService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;

import java.sql.Connection;
import java.sql.SQLException;

public class SubjectServiceImpl implements SubjectService {
   private final SubjectDAO subjectDAO;
   private final Connection connection;

    public SubjectServiceImpl() throws SQLException, ClassNotFoundException {
        connection = DBconnection.getInstance().getConnection();
        subjectDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.SUBJECT);
    }

    @Override
    public SubjectDTO save(SubjectDTO dto) throws SQLException, ClassNotFoundException, DuplicateException {
        return null;
    }
}
