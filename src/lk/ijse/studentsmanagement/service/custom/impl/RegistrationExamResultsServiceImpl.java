package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.RegistrationExamResultsDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.RegistrationDTO;
import lk.ijse.studentsmanagement.service.custom.RegistrationExamResultsService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;

import java.sql.Connection;
import java.sql.SQLException;

public class RegistrationExamResultsServiceImpl implements RegistrationExamResultsService {
    private final RegistrationExamResultsDAO registrationExamResultsDAO;
    private final Connection connection;

    public RegistrationExamResultsServiceImpl() throws SQLException, ClassNotFoundException {
        connection = DBconnection.getInstance().getConnection();
        registrationExamResultsDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.REGISTRATION_EXAM_RESULTS);
    }

    @Override
    public RegistrationDTO save(RegistrationDTO dto) throws SQLException, ClassNotFoundException, DuplicateException {
        return null;
    }
}
