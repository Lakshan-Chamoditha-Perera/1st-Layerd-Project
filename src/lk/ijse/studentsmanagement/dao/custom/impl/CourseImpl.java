package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.CourseDAO;
import lk.ijse.studentsmanagement.entity.Course;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseImpl implements CourseDAO {
    private final Connection connection;

    public CourseImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Course getCourseDetail(Course selectedCourse) throws SQLException, ClassNotFoundException ,RuntimeException{
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM course WHERE id = ?", selectedCourse.getId());
        if(resultSet.next()){
            return new Course(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        throw new RuntimeException("Course details not found");
    }

    @Override
    public List<Course> getCourseList() throws SQLException, ClassNotFoundException {
        List<Course> list = new ArrayList<>();
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
}
