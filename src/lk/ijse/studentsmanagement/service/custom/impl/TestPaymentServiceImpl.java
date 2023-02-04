package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.InquiryIqTestDetailDAO;
import lk.ijse.studentsmanagement.dao.custom.TestPaymentDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.TestPaymentDTO;
import lk.ijse.studentsmanagement.entity.TestPayment;
import lk.ijse.studentsmanagement.service.custom.TestPaymentService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;
import lk.ijse.studentsmanagement.service.util.Converter;
import lk.ijse.studentsmanagement.service.util.Types;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class TestPaymentServiceImpl implements TestPaymentService {
    TestPaymentDAO testPaymentDAO;
    Connection connection;
    InquiryIqTestDetailDAO inquiryIqTestDetailDAO;
    Converter converter;

    public TestPaymentServiceImpl() throws SQLException, ClassNotFoundException {
        connection = DBconnection.getInstance().getConnection();
        testPaymentDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.TEST_PAYMENTS);
        inquiryIqTestDetailDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.INQUIRY_IQTEST_DETAIL);
        converter = new Converter();
    }

    @Override
    public TestPaymentDTO save(TestPaymentDTO dto) throws SQLException, ClassNotFoundException, DuplicateException {
        return null;
    }

    @Override
    public String getLastPaymentID() throws SQLException, ClassNotFoundException {
        String lastPaymentID = testPaymentDAO.getLastPaymentID();
        return lastPaymentID;
    }

    @Override
    public TestPaymentDTO addTestPaymentTransaction(TestPaymentDTO testPayment) throws SQLException, ClassNotFoundException, RuntimeException {
        try {
            connection.setAutoCommit(false);
            if (testPaymentDAO.save(converter.toTestPaymentEntity(testPayment)) != null) {
                if (inquiryIqTestDetailDAO.view(converter.toInquiryIqTestDetailEntity(testPayment.getInquiryIQTestDetailDTO(), Types.InquiryIQTestDetailType1)) != null) {
                    if (inquiryIqTestDetailDAO.save(converter.toInquiryIqTestDetailEntity(testPayment.getInquiryIQTestDetailDTO(), Types.InquiryIQTestDetailType1)) != null) {
                        connection.commit();
                        return testPayment;
                    }
                    connection.rollback();
                    throw new RuntimeException("Inquiry IQ Test Detail not added");
                }
                connection.rollback();
                throw new RuntimeException("Duplicate Entity");
            }
            connection.rollback();
            throw new RuntimeException("test payment not added");
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public double getPaymentsSum() throws SQLException, ClassNotFoundException {
        return testPaymentDAO.getPaymentsSum();
    }

    @Override
    public List<TestPaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException, RuntimeException {
        List<TestPayment> allPayments = testPaymentDAO.getAllPayments();
        if (allPayments.size() > 0)
            return allPayments.stream().map(testPayment -> converter.toTestPaymentDTO(testPayment)).collect(Collectors.toList());
        throw new RuntimeException("Empty Test Payment List");
    }
}
