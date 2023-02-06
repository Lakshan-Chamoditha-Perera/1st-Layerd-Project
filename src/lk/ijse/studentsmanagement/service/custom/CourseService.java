package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.CourseDTO;
import lk.ijse.studentsmanagement.dto.CourseSubjectDetailDTO;
import lk.ijse.studentsmanagement.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface CourseService extends SuperService <CourseDTO>{
    List<CourseDTO> getCourseList() throws SQLException, ClassNotFoundException;

    CourseDTO view(CourseDTO courseDTO)throws SQLException, ClassNotFoundException,RuntimeException;

}
