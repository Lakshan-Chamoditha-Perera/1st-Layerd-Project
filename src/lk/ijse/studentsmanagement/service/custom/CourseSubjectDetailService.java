package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.CourseSubjectDetailDTO;
import lk.ijse.studentsmanagement.service.SuperService;
import lk.ijse.studentsmanagement.dto.tblModels.CourseSubjectDetailTM;

import java.sql.SQLException;
import java.util.List;

public interface CourseSubjectDetailService extends SuperService<CourseSubjectDetailDTO> {

    List<CourseSubjectDetailDTO> getCourseList(String batchID) throws SQLException, ClassNotFoundException;

    List<CourseSubjectDetailTM> getCourseSubjectDetailList(String courseId) throws SQLException, ClassNotFoundException;

    CourseSubjectDetailDTO delete(CourseSubjectDetailDTO courseSubjectDetailDTO) throws SQLException, ClassNotFoundException;
}
