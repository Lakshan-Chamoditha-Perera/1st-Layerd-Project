package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.CourseSubjectDetailDAO;
import lk.ijse.studentsmanagement.dao.util.CrudUtil;
import lk.ijse.studentsmanagement.entity.CourseSubjectDetail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseSubjectDetailImpl implements CourseSubjectDetailDAO {
    private final Connection connection;

    public CourseSubjectDetailImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public CourseSubjectDetail save(CourseSubjectDetail entity) throws SQLException, ClassNotFoundException,RuntimeException {
        if (CrudUtil.execute("INSERT INTO course_subject_detail VALUES(?,?)", entity.getCourseId(), entity.getSubjectId())) return entity;
        return null;
    }

    @Override
    public CourseSubjectDetail update(CourseSubjectDetail entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }

    @Override
    public CourseSubjectDetail delete(CourseSubjectDetail entity) throws SQLException, ClassNotFoundException, RuntimeException {
        if (CrudUtil.execute("DELETE FROM course_subject_detail WHERE courseId = ? AND subjectID = ? ", entity.getCourseId(), entity.getSubjectId())) return entity;
        return null;
    }

    @Override
    public CourseSubjectDetail view(CourseSubjectDetail entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }

    @Override
    public List<CourseSubjectDetail> getCourseList(String batchID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT courseId,subjectID from course_subject_detail where courseId = (SELECT courseId from batch where batchID = ?)", batchID);
        ArrayList<CourseSubjectDetail> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new CourseSubjectDetail(
                            resultSet.getString(1),
                            resultSet.getString(2)
                    ));
        }
        return list;
    }
}
