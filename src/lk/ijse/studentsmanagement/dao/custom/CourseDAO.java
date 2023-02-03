package lk.ijse.studentsmanagement.dao.custom;

import lk.ijse.studentsmanagement.dao.CrudDAO;
import lk.ijse.studentsmanagement.dao.SuperDAO;
import lk.ijse.studentsmanagement.entity.Course;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CourseDAO  extends SuperDAO {
    Course getCourseDetail(Course selectedCourse)throws SQLException, ClassNotFoundException;
    List<Course> getCourseList() throws SQLException, ClassNotFoundException;
}
