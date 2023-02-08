package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.PaymentDAO;
import lk.ijse.studentsmanagement.dao.util.CrudUtil;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.PaymentDTO;
import lk.ijse.studentsmanagement.dto.RegistrationDTO;
import lk.ijse.studentsmanagement.entity.Payment;
import lk.ijse.studentsmanagement.service.custom.PaymentService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;
import lk.ijse.studentsmanagement.service.util.Converter;
import lk.ijse.studentsmanagement.service.util.Types;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentDAO paymentDAO;
    private final Connection connection;
    private final Converter converter;

    public PaymentServiceImpl() throws SQLException, ClassNotFoundException {
        connection = DBconnection.getInstance().getConnection();
        paymentDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.PAYMENTS);
        converter = new Converter();
    }

    @Override
    public PaymentDTO save(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException, DuplicateException {
        if (paymentDAO.save(converter.toPaymentEntity(paymentDTO))!=null)
            return paymentDTO;
        return null;
    }

    @Override
    public String getLastPaymentID() throws SQLException, ClassNotFoundException {
        return paymentDAO.getLastPaymentId();
    }

    @Override
    public List<PaymentDTO> getPayments(RegistrationDTO registrationDTO) throws SQLException, ClassNotFoundException, RuntimeException {
        List<Payment> payments = paymentDAO.getPayments(converter.toRegistrationEntity(registrationDTO, Types.RegistrationType1));
        if (payments.size() > 0) {
            return payments.stream().map(converter::toPaymentDTO).collect(Collectors.toList());
        }
        throw new RuntimeException("Empty Payments list...");
    }

    @Override
    public double getPaymentsSum() throws SQLException, ClassNotFoundException {
        return paymentDAO.getPaymentsSum();
    }

    @Override
    public List<PaymentDTO> loadAllPayments() throws SQLException, ClassNotFoundException, RuntimeException {
        List<Payment> allPayments = paymentDAO.getAllPayments();
        if (allPayments.size() > 0)
            return allPayments.stream().map(converter::toPaymentDTO).collect(Collectors.toList());
        throw new RuntimeException("no any payment yet!");
    }

}
