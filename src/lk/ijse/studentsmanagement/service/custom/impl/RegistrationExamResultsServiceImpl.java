package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.RegistrationExamResultsDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.RegistrationExamResultDTO;
import lk.ijse.studentsmanagement.entity.RegistrationExamResult;
import lk.ijse.studentsmanagement.service.custom.RegistrationExamResultsService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;
import lk.ijse.studentsmanagement.service.util.Converter;
import lk.ijse.studentsmanagement.service.util.Types;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static lk.ijse.studentsmanagement.service.util.Types.RegistrationExamResultType1;
import static lk.ijse.studentsmanagement.service.util.Types.RegistrationExamResultType2;

public class RegistrationExamResultsServiceImpl implements RegistrationExamResultsService {
    private final RegistrationExamResultsDAO registrationExamResultsDAO;
    private final Connection connection;
    Converter converter;

    public RegistrationExamResultsServiceImpl() throws SQLException, ClassNotFoundException {
        converter = new Converter();
        connection = DBconnection.getInstance().getConnection();
        registrationExamResultsDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.REGISTRATION_EXAM_RESULTS);
    }

    @Override
    public List<RegistrationExamResultDTO> getResults(String examId) throws SQLException, ClassNotFoundException {
        List<RegistrationExamResult> allResults = registrationExamResultsDAO.getAllResults(examId);
        if(allResults.size()>0) return allResults.stream().map(registrationExamResult -> converter.toRegistrationExamResultDTO(registrationExamResult)).collect(Collectors.toList());
        throw new RuntimeException();
    }

    @Override
    public boolean delete(RegistrationExamResultDTO registrationExamResultDTO) throws SQLException, ClassNotFoundException,RuntimeException {
        RegistrationExamResult registrationExamResult = registrationExamResultsDAO.delete(converter.toRegistrationExamResultsEntity(registrationExamResultDTO,RegistrationExamResultType1));
        return registrationExamResult != null;
    }

    @Override
    public boolean update(RegistrationExamResultDTO registrationExamResultDTO) throws SQLException, ClassNotFoundException,RuntimeException {
        RegistrationExamResult registrationExamResult = registrationExamResultsDAO.update(converter.toRegistrationExamResultsEntity(registrationExamResultDTO, RegistrationExamResultType2));
        return registrationExamResult!=null;
    }

    @Override
    public RegistrationExamResultDTO save(RegistrationExamResultDTO registrationExamResultDTO) throws SQLException, ClassNotFoundException, RuntimeException {
        RegistrationExamResult registrationExamResult = registrationExamResultsDAO.save(converter.toRegistrationExamResultsEntity(registrationExamResultDTO, RegistrationExamResultType2));
        if(registrationExamResult!=null){
            return registrationExamResultDTO;
        }
        throw new RuntimeException("not added");
    }
}
