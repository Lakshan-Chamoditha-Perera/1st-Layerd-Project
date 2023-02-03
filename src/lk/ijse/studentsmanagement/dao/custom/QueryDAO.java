package lk.ijse.studentsmanagement.dao.custom;

import lk.ijse.studentsmanagement.dao.SuperDAO;
import lk.ijse.studentsmanagement.dto.InquiryIQTestDetailDTO;
import lk.ijse.studentsmanagement.dto.RegistrationExamResultDTO;
import lk.ijse.studentsmanagement.entity.Registration;

import java.sql.SQLException;
import java.util.List;

public interface QueryDAO  extends SuperDAO {
    List <InquiryIQTestDetailDTO> getInquiryIQTestList(String id) throws SQLException, ClassNotFoundException;
    List<RegistrationExamResultDTO> getTranscript(Registration registration) throws SQLException, ClassNotFoundException;
}
