package lk.ijse.studentsmanagement.model;

import lk.ijse.studentsmanagement.tblModels.CourseSubjectDetailTM;
import lk.ijse.studentsmanagement.entity.CourseSubjectDetail;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseSubjectDetailModel {
    public static ArrayList<CourseSubjectDetail> getCourseList(String batchId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT courseId,subjectID from course_subject_detail where courseId = (SELECT courseId from batch where batchID = ?)", batchId);
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

    public static ArrayList<CourseSubjectDetailTM> getCourseSubjecDetailList(String courseId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT course_subject_detail.courseId, course_subject_detail.subjectID, subject.name FROM course_subject_detail INNER JOIN subject ON course_subject_detail.subjectID = subject.id WHERE courseId = ?", courseId);
        if (resultSet != null) {
            ArrayList<CourseSubjectDetailTM> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(
                        new CourseSubjectDetailTM(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3)
                        ));
            }
            return list;
        }
        return null;
    }

    public static boolean addCourseSubjectDetail(CourseSubjectDetail courseSubjectDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO course_subject_detail VALUES(?,?)", courseSubjectDetail.getCourseId(),courseSubjectDetail.getSubjectId());
    }

    public static boolean deleteCourseSubjectDetail(CourseSubjectDetail courseSubjectDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM course_subject_detail WHERE courseId = ? AND subjectID = ? ", courseSubjectDetail.getCourseId(),courseSubjectDetail.getSubjectId());

    }
}
