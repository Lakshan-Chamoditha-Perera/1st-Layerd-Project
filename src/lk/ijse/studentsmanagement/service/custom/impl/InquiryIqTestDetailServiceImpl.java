package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.InquiryIqTestDetailDAO;
import lk.ijse.studentsmanagement.dao.custom.QueryDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.InquiryIQTestDetailDTO;
import lk.ijse.studentsmanagement.entity.InquiryIQTestDetail;
import lk.ijse.studentsmanagement.service.custom.InquiryIqTestDetailService;
import lk.ijse.studentsmanagement.service.util.Converter;
import lk.ijse.studentsmanagement.service.util.Types;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class InquiryIqTestDetailServiceImpl implements InquiryIqTestDetailService {
    private final InquiryIqTestDetailDAO inquiryIqTestDetailDAO;
    private final Connection connection;
    private final Converter converter;
    private final QueryDAO queryDAO;

    public InquiryIqTestDetailServiceImpl() throws RuntimeException {
        try {
            connection = DBconnection.getInstance().getConnection();
            converter = new Converter();
            inquiryIqTestDetailDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.INQUIRY_IQTEST_DETAIL);
            queryDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.QUERY);
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InquiryIQTestDetailDTO save(InquiryIQTestDetailDTO dto) throws SQLException, ClassNotFoundException, RuntimeException {
        if (CrudUtil.execute("INSERT INTO inquiry_iqTest_Detail (studentID,testID, result) VALUES(?,?,?)", dto.getStudentId(), dto.getTestId(), dto.getResult()))
            return dto;
        throw new RuntimeException("inquiry_iqTest_Detail not added");
    }

    @Override
    public List<InquiryIQTestDetailDTO> getInquiryIQTestList() throws SQLException, ClassNotFoundException, RuntimeException {
        List<InquiryIQTestDetail> inquiryIQTestList = inquiryIqTestDetailDAO.getInquiryIQTestList();
        if (inquiryIQTestList.size() > 0)
            return inquiryIQTestList.stream().map(inquiryIQTestDetail -> converter.toInquiryIQTestDetailDTO(inquiryIQTestDetail, Types.InquiryIQTestDetailType1)).collect(Collectors.toList());
        throw new RuntimeException("Empty inquiry iq test list");
    }

    @Override
    public InquiryIQTestDetailDTO update(InquiryIQTestDetailDTO inquiryIQTestDetailDTO) throws SQLException, ClassNotFoundException, RuntimeException {
        if (inquiryIqTestDetailDAO.update(converter.toInquiryIqTestDetailEntity(inquiryIQTestDetailDTO, Types.InquiryIQTestDetailType2)) != null)
            return inquiryIQTestDetailDTO;
        throw new RuntimeException("InquiryIQTestDetail not updated");
    }

    @Override
    public List<InquiryIQTestDetailDTO> getInquiryIQTestList(String id) throws SQLException, ClassNotFoundException, RuntimeException {
        List<InquiryIQTestDetailDTO> inquiryIQTestList = queryDAO.getInquiryIQTestList(id);
        if (inquiryIQTestList.size() > 0) return inquiryIQTestList;
        throw new RuntimeException("Query dao error");
    }
}
