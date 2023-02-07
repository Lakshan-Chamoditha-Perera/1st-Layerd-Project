package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.GuardianDAO;
import lk.ijse.studentsmanagement.dao.custom.InquiryDAO;
import lk.ijse.studentsmanagement.dao.custom.PaymentDAO;
import lk.ijse.studentsmanagement.dao.custom.RegistrationDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.GuardianDTO;
import lk.ijse.studentsmanagement.entity.Guardian;
import lk.ijse.studentsmanagement.service.custom.GuardianService;
import lk.ijse.studentsmanagement.service.util.Converter;
import lk.ijse.studentsmanagement.service.util.Types;

import java.sql.Connection;
import java.sql.SQLException;

public class GuardianServiceImpl implements GuardianService {
    private final GuardianDAO guardianDAO;
    private final Connection connection;
    private final RegistrationDAO registrationDAO;
    private final PaymentDAO paymentDAO;

    private final InquiryDAO inquiryDAO;
    private Converter converter;

    public GuardianServiceImpl() throws RuntimeException {
        try {
            converter = new Converter();
            connection = DBconnection.getInstance().getConnection();
            guardianDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.GUARDIAN);
            registrationDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.REGISTRATION);
            paymentDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.PAYMENTS);
            inquiryDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.INQUIRY);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public GuardianDTO save(GuardianDTO dto) throws SQLException, ClassNotFoundException, RuntimeException {
        try {
            connection.setAutoCommit(false);
            if (guardianDAO.save(converter.toGuardianEntity(dto, Types.GuardianType1)) != null) {
               // System.out.println("");
                if (registrationDAO.save(converter.toRegistrationEntity(dto.getRegistration(),Types.RegistrationType1)) != null) {
                    if (paymentDAO.save(converter.toPaymentEntity(dto.getRegistration().getPayment())) != null) {
                        if (inquiryDAO.updateInquiryStatus(dto.getRegistration().getNic())){
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
            }
            connection.rollback();
            throw new RuntimeException("Guardian not added");
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public GuardianDTO view(GuardianDTO guardianDTO) throws SQLException, ClassNotFoundException, RuntimeException {
        System.out.println(guardianDTO);
        Guardian guardian = guardianDAO.view(converter.toGuardianEntity(guardianDTO, Types.GuardianType2));
        if(guardian!=null){
            return converter.toGuardianDTO(guardian);
        }
        throw new RuntimeException("Guardian Does Not exists");
    }
}
