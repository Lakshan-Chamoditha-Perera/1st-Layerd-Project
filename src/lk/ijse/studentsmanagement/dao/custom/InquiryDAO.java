package lk.ijse.studentsmanagement.dao.custom;

import lk.ijse.studentsmanagement.dao.CrudDAO;
import lk.ijse.studentsmanagement.entity.Inquiry;

import java.sql.SQLException;
import java.util.List;

public interface InquiryDAO extends CrudDAO <Inquiry,String>{
    List<Inquiry> getAllInquires() throws SQLException, ClassNotFoundException;
    boolean updateInquiryStatus(String id) throws SQLException, ClassNotFoundException;
    int getRegisteredStdCount() throws SQLException, ClassNotFoundException;
    int getInquiriesCount() throws SQLException, ClassNotFoundException;
    int getUnregisteredStdCount() throws SQLException, ClassNotFoundException;
    Inquiry getEmail(Inquiry inquiry) throws SQLException, ClassNotFoundException;
}
