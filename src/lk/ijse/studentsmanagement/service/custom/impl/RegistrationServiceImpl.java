package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.InquiryDAO;
import lk.ijse.studentsmanagement.dao.custom.PaymentDAO;
import lk.ijse.studentsmanagement.dao.custom.RegistrationDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.RegistrationDTO;
import lk.ijse.studentsmanagement.entity.Registration;
import lk.ijse.studentsmanagement.service.custom.RegistrationService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;
import lk.ijse.studentsmanagement.service.util.Converter;
import lk.ijse.studentsmanagement.service.util.Types;

import java.sql.Connection;
import java.sql.SQLException;

public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationDAO registrationDAO;
    private final PaymentDAO paymentDAO;
    private final InquiryDAO inquiryDAO;
    private final Connection connection;
    private final Converter converter;

    public RegistrationServiceImpl() throws SQLException, ClassNotFoundException {
        converter = new Converter();
        connection = DBconnection.getInstance().getConnection();
        registrationDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.REGISTRATION);
        paymentDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.PAYMENTS);
        inquiryDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.INQUIRY);
    }

    @Override
    public RegistrationDTO save(RegistrationDTO dto) throws SQLException, ClassNotFoundException, DuplicateException {
        try {
            connection.setAutoCommit(false);
            if (registrationDAO.save(converter.toRegistrationEntity(dto, Types.RegistrationType1)) != null) {
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
    public RegistrationDTO view(RegistrationDTO registrationDTO) throws SQLException, ClassNotFoundException,RuntimeException {
        Registration registration = registrationDAO.view(converter.toRegistrationEntity(registrationDTO, Types.RegistrationType2));
        if(registration!=null) return converter.toRegistrationDTO(registration);
        throw new RuntimeException("Registration not found");
    }

    @Override
    public RegistrationDTO update(RegistrationDTO registrationDTO) throws SQLException, ClassNotFoundException {
        Registration registration = registrationDAO.update(converter.toRegistrationEntity(registrationDTO, Types.RegistrationType3));
        if(registration!=null) return registrationDTO;
        throw new RuntimeException("Registration not updated");
    }
}
