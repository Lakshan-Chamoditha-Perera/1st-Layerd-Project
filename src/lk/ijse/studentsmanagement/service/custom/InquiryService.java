package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.InquiryDTO;
import lk.ijse.studentsmanagement.entity.Inquiry;
import lk.ijse.studentsmanagement.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface InquiryService extends SuperService <InquiryDTO>{
    int getRegisteredStdCount() throws SQLException, ClassNotFoundException;
    int getInquiriesCount() throws SQLException, ClassNotFoundException;
    int getUnregisteredStdCount() throws SQLException, ClassNotFoundException;
    List<InquiryDTO> getAllInquires() throws SQLException, ClassNotFoundException;

    InquiryDTO view(InquiryDTO inquiryDTO) throws SQLException, ClassNotFoundException;
    InquiryDTO updateInquiryDetails(InquiryDTO inquiryDTO) throws SQLException, ClassNotFoundException;

    InquiryDTO getEmail(InquiryDTO inquiryDTO);
}
