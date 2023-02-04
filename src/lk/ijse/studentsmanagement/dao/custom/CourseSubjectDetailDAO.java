package lk.ijse.studentsmanagement.dao.custom;

import lk.ijse.studentsmanagement.dao.CrudDAO;
import lk.ijse.studentsmanagement.dao.SuperDAO;
import lk.ijse.studentsmanagement.entity.CourseSubjectDetail;

import java.sql.SQLException;
import java.util.List;

public interface CourseSubjectDetailDAO extends CrudDAO<CourseSubjectDetail,String> {
    List<CourseSubjectDetail> getCourseList(String batchID) throws SQLException, ClassNotFoundException;
}
