package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.IqTestDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.IQTestDTO;
import lk.ijse.studentsmanagement.dto.InquiryDTO;
import lk.ijse.studentsmanagement.entity.IQTest;
import lk.ijse.studentsmanagement.service.custom.IqTestService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;
import lk.ijse.studentsmanagement.service.util.Converter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static lk.ijse.studentsmanagement.service.util.Types.IQTestType1;
import static lk.ijse.studentsmanagement.service.util.Types.IQTestType2;

public class IqTestServiceImpl implements IqTestService {
    private final IqTestDAO iqTestDAO;
    private final Connection connection;
    private final Converter converter;

    public IqTestServiceImpl() throws SQLException, ClassNotFoundException {
        connection = DBconnection.getInstance().getConnection();
        iqTestDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.IQTEST);
        converter = new Converter();
    }

    @Override
    public IQTestDTO save(IQTestDTO dto) throws SQLException, ClassNotFoundException, RuntimeException {
        IQTest save = iqTestDAO.save(converter.toIQTestEntity(dto, IQTestType2));
        if(save!=null) return dto;
        throw new RuntimeException("IQ Test not added!");
    }

    @Override
    public List<IQTestDTO> getIQTestList() throws SQLException, ClassNotFoundException, RuntimeException {
        List<IQTestDTO> collect = iqTestDAO.getList().stream().map(iqTest -> converter.toIQTestDTO(iqTest)).collect(Collectors.toList());
        if (collect.size() > 0) return collect;
        throw new RuntimeException("IQ Test List is empty !");
    }

    @Override
    public InquiryDTO view(InquiryDTO inquiryDTO) {
        return null;
    }

    @Override
    public IQTestDTO getIQTestDetailsByDate(String s) throws SQLException, ClassNotFoundException, RuntimeException {
        IQTest examByDate = iqTestDAO.getExamByDate(Date.valueOf(s));
        if(examByDate!=null) return converter.toIQTestDTO(examByDate);
        return null;
    }

    @Override
    public IQTestDTO getExamDetails(IQTestDTO iqTestDTO) throws SQLException, ClassNotFoundException, RuntimeException {
        IQTest iqTest = iqTestDAO.getExamDetails(converter.toIQTestEntity(iqTestDTO,IQTestType1));
        return converter.toIQTestDTO(iqTest);
    }

    @Override
    public String getLastExamID() throws SQLException, ClassNotFoundException {
        IQTest lastExam = iqTestDAO.getLastExam();
        if (lastExam != null) {
            return lastExam.getId();
        }
        return null;
    }

    @Override
    public void delete(IQTestDTO iqTestDTO) throws SQLException, ClassNotFoundException, RuntimeException {
        IQTest delete = iqTestDAO.delete(converter.toIQTestEntity(iqTestDTO,IQTestType1));
        if (delete == null) throw new RuntimeException("not deleted..");
    }


}
