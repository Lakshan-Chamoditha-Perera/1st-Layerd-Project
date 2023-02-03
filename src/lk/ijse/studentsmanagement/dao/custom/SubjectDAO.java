package lk.ijse.studentsmanagement.dao.custom;

import lk.ijse.studentsmanagement.dao.CrudDAO;
import lk.ijse.studentsmanagement.entity.Subject;

import java.sql.SQLException;
import java.util.List;

public interface SubjectDAO extends CrudDAO <Subject,String>{
    List<Subject> getIDs() throws SQLException, ClassNotFoundException;
    String getSubjectName(Subject subject) throws SQLException, ClassNotFoundException;
    List<Subject> getSubjectList() throws SQLException, ClassNotFoundException;
}
