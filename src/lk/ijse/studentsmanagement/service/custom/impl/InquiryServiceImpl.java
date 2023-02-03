package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.InquiryDAO;
import lk.ijse.studentsmanagement.dao.custom.InquiryIqTestDetailDAO;
import lk.ijse.studentsmanagement.dao.custom.TestPaymentDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.InquiryDTO;
import lk.ijse.studentsmanagement.entity.Inquiry;
import lk.ijse.studentsmanagement.service.custom.InquiryService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;
import lk.ijse.studentsmanagement.service.util.Converter;
import lk.ijse.studentsmanagement.service.util.Types;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class InquiryServiceImpl implements InquiryService {
    private final InquiryDAO inquiryDAO;
    private final Connection connection;
    private final TestPaymentDAO testPaymentDAO;

    InquiryIqTestDetailDAO inquiryIqTestDetailDAO;
    private final Converter converter;

    public InquiryServiceImpl() throws RuntimeException {
        try {
            connection = DBconnection.getInstance().getConnection();
            inquiryDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.INQUIRY);
            testPaymentDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.TEST_PAYMENTS);
            inquiryIqTestDetailDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.INQUIRY_IQTEST_DETAIL);
            converter = new Converter();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InquiryDTO save(InquiryDTO dto) throws RuntimeException, SQLException, ClassNotFoundException {
        if (inquiryDAO.view(converter.toInquiryEntity(dto,Types.InquiryType1)) != null)
            throw new DuplicateException("member already exists");
        try {
            connection.setAutoCommit(false);
            if (inquiryDAO.save(converter.toInquiryEntity(dto,Types.InquiryType1)) != null) {
                if (testPaymentDAO.save(converter.toTestPaymentEntity(dto.getTestPayment())) != null) {
                    if (inquiryIqTestDetailDAO.save(
                            converter.toInquiryIqTestDetailEntity(
                                    dto.getTestPayment().getInquiryIQTestDetailDTO(),Types.InquiryIQTestDetailType1)
                    ) != null) {
                        connection.commit();
                        return dto;
                    }
                    connection.rollback();
                    throw new RuntimeException("Inquiry_IQTest not added");
                }
                connection.rollback();
                throw new RuntimeException("Test Payment not added");
            }
            connection.rollback();
            throw new RuntimeException("Inquiry not added");
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public int getRegisteredStdCount() throws SQLException, ClassNotFoundException {
        return inquiryDAO.getRegisteredStdCount();
    }

    @Override
    public int getInquiriesCount() throws SQLException, ClassNotFoundException {
        return inquiryDAO.getInquiriesCount();
    }

    @Override
    public int getUnregisteredStdCount() throws SQLException, ClassNotFoundException {
        return inquiryDAO.getUnregisteredStdCount();
    }
    @Override
    public List<InquiryDTO> getAllInquires() throws SQLException, ClassNotFoundException {
        return inquiryDAO.getAllInquires().stream().map(converter::toInquiryDTO).collect(Collectors.toList());
    }

    @Override
    public InquiryDTO view(InquiryDTO inquiryDTO) throws SQLException, ClassNotFoundException,RuntimeException {
        Inquiry inquiry = inquiryDAO.view(converter.toInquiryEntity(inquiryDTO,Types.InquiryType1));
        if(inquiry!=null){
            return converter.toInquiryDTO(inquiry);
        }
        throw new  RuntimeException("Inquiry not found");
    }

    @Override
    public InquiryDTO updateInquiryDetails(InquiryDTO inquiryDTO) throws SQLException, ClassNotFoundException , RuntimeException {
        try{
            Inquiry inquiry = inquiryDAO.update(converter.toInquiryEntity(inquiryDTO,Types.InquiryType1));
            if(inquiry!=null){
                return inquiryDTO;
            }
            throw new RuntimeException("Inquiry does not update");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InquiryDTO getEmail(InquiryDTO inquiryDTO) {
        return null;
    }
}
