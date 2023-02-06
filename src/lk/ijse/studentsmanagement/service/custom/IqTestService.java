package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.IQTestDTO;
import lk.ijse.studentsmanagement.dto.InquiryDTO;
import lk.ijse.studentsmanagement.entity.IQTest;
import lk.ijse.studentsmanagement.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface IqTestService extends SuperService <IQTestDTO>{
    List<IQTestDTO> getIQTestList() throws SQLException, ClassNotFoundException;

    InquiryDTO view(InquiryDTO inquiryDTO);

    IQTestDTO getIQTestDetailsByDate(String s) throws SQLException, ClassNotFoundException;

    IQTestDTO getExamDetails(IQTestDTO iqTestDTO) throws SQLException, ClassNotFoundException,RuntimeException;

    String getLastExamID() throws SQLException, ClassNotFoundException;

    void delete(IQTestDTO iqTestDTO) throws SQLException, ClassNotFoundException;
}
