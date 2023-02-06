package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.InquiryDAO;
import lk.ijse.studentsmanagement.dao.custom.PaymentDAO;
import lk.ijse.studentsmanagement.dao.custom.QueryDAO;
import lk.ijse.studentsmanagement.dao.custom.RegistrationDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.RegistrationDTO;
import lk.ijse.studentsmanagement.dto.RegistrationExamResultDTO;
import lk.ijse.studentsmanagement.entity.Registration;
import lk.ijse.studentsmanagement.service.custom.RegistrationService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;
import lk.ijse.studentsmanagement.service.util.Converter;
import lk.ijse.studentsmanagement.service.util.Types;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static lk.ijse.studentsmanagement.service.util.Types.RegistrationType1;
import static lk.ijse.studentsmanagement.service.util.Types.RegistrationType3;

public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationDAO registrationDAO;
    private final PaymentDAO paymentDAO;
    private final InquiryDAO inquiryDAO;
    QueryDAO queryDAO;
    private final Connection connection;
    private final Converter converter;

    public RegistrationServiceImpl() throws SQLException, ClassNotFoundException {
        converter = new Converter();
        connection = DBconnection.getInstance().getConnection();
        registrationDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.REGISTRATION);
        paymentDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.PAYMENTS);
        inquiryDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.INQUIRY);
        queryDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.QUERY);
    }

    @Override
    public RegistrationDTO save(RegistrationDTO dto) throws SQLException, ClassNotFoundException, DuplicateException {
        try {
            connection.setAutoCommit(false);
            if (registrationDAO.save(converter.toRegistrationEntity(dto, RegistrationType1)) != null) {
                if (paymentDAO.save(converter.toPaymentEntity(dto.getPayment())) != null) {
                    if (inquiryDAO.updateInquiryStatus(dto.getNic())) {
                        connection.commit();
                        return dto;
                    }
                    connection.rollback();
                    throw new RuntimeException("Inquiry not updated");
                }
                connection.rollback();
                throw new RuntimeException("Payment not added");
            }
            connection.rollback();
            throw new RuntimeException("Registration not added");
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public String getLastRegistrationID() throws SQLException, ClassNotFoundException {
        return registrationDAO.getLastRegistrationID();
    }

    @Override
    public RegistrationDTO view(RegistrationDTO registrationDTO) throws SQLException, ClassNotFoundException, RuntimeException {
        Registration registration = registrationDAO.view(converter.toRegistrationEntity(registrationDTO, Types.RegistrationType2));
        if (registration != null) return converter.toRegistrationDTO(registration, RegistrationType1);
        throw new RuntimeException("Registration not found");
    }

    @Override
    public RegistrationDTO update(RegistrationDTO registrationDTO) throws SQLException, ClassNotFoundException {
        Registration registration = registrationDAO.update(converter.toRegistrationEntity(registrationDTO, RegistrationType3));
        if (registration != null) return registrationDTO;
        throw new RuntimeException("Registration not updated");
    }

    @Override
    public List<RegistrationDTO> getCourseBatchList(String course, String batch) throws SQLException, ClassNotFoundException, RuntimeException {
        List<Registration> courseBatchList = registrationDAO.getCourseBatchList(course, batch);
        if (courseBatchList.size() > 0)
            return courseBatchList.stream().map(registration -> converter.toRegistrationDTO(registration, Types.RegistrationType2)).collect(Collectors.toList());
        throw new RuntimeException("Empty registration list");
    }

    @Override
    public String getRegistrationEmail(String text) throws SQLException, ClassNotFoundException, RuntimeException {
        String registrationEmail = registrationDAO.getRegistrationEmail(text);
        if (registrationEmail != null) return registrationEmail;
        throw new RuntimeException("Empty email");
    }

    @Override
    public List<String> getRegistrationEmailList(String value) throws SQLException, ClassNotFoundException, RuntimeException {
        List<String> registrationEmailList = registrationDAO.getRegistrationEmailList(value);
        if (registrationEmailList.size() > 0) return registrationEmailList;
        throw new RuntimeException("Empty registration list");
    }

    @Override
    public List<RegistrationDTO> loadBatchRegistrations(String value) throws SQLException, ClassNotFoundException, RuntimeException {
        List<Registration> registrations = registrationDAO.loadBatchRegistrations(value);
        if (registrations.size() > 0)
            return registrations.stream().map(registration -> converter.toRegistrationDTO(registration, RegistrationType3)).collect(Collectors.toList());
        throw new RuntimeException("No any student in Batch ");
    }

    @Override
    public List<RegistrationExamResultDTO> getTranscript(RegistrationDTO registrationDTO) throws SQLException, ClassNotFoundException,RuntimeException {
        List<RegistrationExamResultDTO> transcript = queryDAO.getTranscript(converter.toRegistrationEntity(registrationDTO, Types.RegistrationType2));
        if(transcript.size()>0){
            return transcript;
        }
        throw new RuntimeException("Empty list");
    }
}
