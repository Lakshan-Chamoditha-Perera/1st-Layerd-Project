package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.CourseDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.CourseDTO;
import lk.ijse.studentsmanagement.entity.Course;
import lk.ijse.studentsmanagement.service.custom.CourseService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;
import lk.ijse.studentsmanagement.service.util.Converter;
import lk.ijse.studentsmanagement.service.util.Types;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CourseServiceImpl  implements CourseService {
    private final CourseDAO courseDAO;
    private final Connection connection;
    private Converter converter;

    public CourseServiceImpl() {
        try {
            converter = new Converter();
            connection = DBconnection.getInstance().getConnection();
            courseDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.COURSE);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public CourseDTO save(CourseDTO dto) throws SQLException, ClassNotFoundException, DuplicateException {
        return null;
    }

    @Override
    public List<CourseDTO> getCourseList() throws SQLException, ClassNotFoundException, RuntimeException {
        List<Course> courseList = courseDAO.getCourseList();
        if(courseList.size()>0) return courseList.stream().map(course -> converter.toCourseDTO(course)).collect(Collectors.toList());
        throw new RuntimeException("Course List is empty...");
    }

    @Override
    public CourseDTO view(CourseDTO courseDTO) throws SQLException, ClassNotFoundException, RuntimeException {
          return converter.toCourseDTO(courseDAO.getCourseDetail(converter.toCourseEntity(courseDTO, Types.CourseType1)));
    }
}
