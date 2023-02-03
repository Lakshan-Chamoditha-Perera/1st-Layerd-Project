package lk.ijse.studentsmanagement.dao.custom;

import lk.ijse.studentsmanagement.dao.CrudDAO;
import lk.ijse.studentsmanagement.entity.InquiryIQTestDetail;

import java.sql.SQLException;
import java.util.List;

public interface InquiryIqTestDetailDAO extends CrudDAO <InquiryIQTestDetail,String>{
    List<InquiryIQTestDetail> getInquiryIQTestList() throws SQLException, ClassNotFoundException;
}
