package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.InquiryIQTestDetailDTO;
import lk.ijse.studentsmanagement.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface InquiryIqTestDetailService extends SuperService <InquiryIQTestDetailDTO>{

    List<InquiryIQTestDetailDTO> getInquiryIQTestList() throws SQLException, ClassNotFoundException, RuntimeException;

    InquiryIQTestDetailDTO update(InquiryIQTestDetailDTO inquiryIQTestDetailDTO)throws SQLException, ClassNotFoundException,RuntimeException;

    List<InquiryIQTestDetailDTO> getInquiryIQTestList(String id) throws SQLException, ClassNotFoundException,RuntimeException;
}
