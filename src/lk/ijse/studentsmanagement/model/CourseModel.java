package lk.ijse.studentsmanagement.model;

import lk.ijse.studentsmanagement.entity.Course;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseModel {
    public static ArrayList<Course> getCourseList() throws SQLException, ClassNotFoundException {
        ArrayList<Course> list = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM course");
        while (resultSet.next()) {
            list.add(new Course(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return list;
    }

    public static Course getCourseDetail(Course selectedCourse) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM course WHERE id = ?", selectedCourse.getId());
        Course course = null;
        if(resultSet.next()){
            course= new Course(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        return course;
    }
}
