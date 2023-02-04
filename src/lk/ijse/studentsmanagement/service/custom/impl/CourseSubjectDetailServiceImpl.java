package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.CourseSubjectDetailDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.CourseSubjectDetailDTO;
import lk.ijse.studentsmanagement.entity.CourseSubjectDetail;
import lk.ijse.studentsmanagement.service.custom.CourseSubjectDetailService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;
import lk.ijse.studentsmanagement.service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static lk.ijse.studentsmanagement.dao.DaoTypes.COURSE_SUBJECT_DETAIL;

public class CourseSubjectDetailServiceImpl implements CourseSubjectDetailService {
    CourseSubjectDetailDAO courseSubjectDetailDAO;
    Connection connection;
    Converter converter;
    public CourseSubjectDetailServiceImpl() throws SQLException, ClassNotFoundException {
        connection = DBconnection.getInstance().getConnection();
        converter = new Converter();
        courseSubjectDetailDAO  = DAOFactory.getInstance().getDAO(connection,COURSE_SUBJECT_DETAIL);
    }

    @Override
    public CourseSubjectDetailDTO save(CourseSubjectDetailDTO dto) throws SQLException, ClassNotFoundException, DuplicateException {
        return null;
    }

    @Override
    public List<CourseSubjectDetailDTO> getCourseList(String batchID) throws SQLException, ClassNotFoundException,RuntimeException {
        List<CourseSubjectDetail> list = courseSubjectDetailDAO.getCourseList(batchID);
        if(list.size()>0) return list.stream().map(courseSubjectDetail -> converter.toCourseSubjectDetailDTO(courseSubjectDetail)).collect(Collectors.toList());
        throw new RuntimeException("courseSubjectDetail empty list");
    }
}
