package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.custom.CourseSubjectDetailDAO;
import lk.ijse.studentsmanagement.dao.custom.QueryDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.CourseSubjectDetailDTO;
import lk.ijse.studentsmanagement.entity.CourseSubjectDetail;
import lk.ijse.studentsmanagement.service.custom.CourseSubjectDetailService;
import lk.ijse.studentsmanagement.service.util.Converter;
import lk.ijse.studentsmanagement.dto.tblModels.CourseSubjectDetailTM;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static lk.ijse.studentsmanagement.dao.DaoTypes.COURSE_SUBJECT_DETAIL;
import static lk.ijse.studentsmanagement.dao.DaoTypes.QUERY;

public class CourseSubjectDetailServiceImpl implements CourseSubjectDetailService {
    CourseSubjectDetailDAO courseSubjectDetailDAO;
    Connection connection;
    Converter converter;
    QueryDAO queryDAO;
    public CourseSubjectDetailServiceImpl() throws SQLException, ClassNotFoundException {
        connection = DBconnection.getInstance().getConnection();
        converter = new Converter();
        courseSubjectDetailDAO  = DAOFactory.getInstance().getDAO(connection,COURSE_SUBJECT_DETAIL);
        queryDAO = DAOFactory.getInstance().getDAO(connection,QUERY);
    }

    @Override
    public CourseSubjectDetailDTO save(CourseSubjectDetailDTO dto) throws SQLException, ClassNotFoundException, RuntimeException {
        CourseSubjectDetail save = courseSubjectDetailDAO.save(converter.toCourseSubjectDetailEntity(dto));
        if(save != null) return dto;
        throw new RuntimeException("Course Subject Detail not added");
    }

    @Override
    public List<CourseSubjectDetailDTO> getCourseList(String batchID) throws SQLException, ClassNotFoundException,RuntimeException {
        List<CourseSubjectDetail> list = courseSubjectDetailDAO.getCourseList(batchID);
        if(list.size()>0) return list.stream().map(courseSubjectDetail -> converter.toCourseSubjectDetailDTO(courseSubjectDetail)).collect(Collectors.toList());
        throw new RuntimeException("courseSubjectDetail empty list");
    }

    @Override
    public List<CourseSubjectDetailTM> getCourseSubjectDetailList(String courseId) throws SQLException, ClassNotFoundException ,RuntimeException{
        List<CourseSubjectDetailTM> courseSubjectDetailList = queryDAO.getCourseSubjectDetailList(courseId);
        if(courseSubjectDetailList.size()>0) return courseSubjectDetailList;
        throw new RuntimeException("Subjects Not added yet");
    }

    @Override
    public CourseSubjectDetailDTO delete(CourseSubjectDetailDTO courseSubjectDetailDTO) throws SQLException, ClassNotFoundException ,RuntimeException{
        CourseSubjectDetail delete = courseSubjectDetailDAO.delete(converter.toCourseSubjectDetailEntity(courseSubjectDetailDTO));
        if (delete!=null) return courseSubjectDetailDTO;
        throw new RuntimeException("not deleted....");
    }
}
