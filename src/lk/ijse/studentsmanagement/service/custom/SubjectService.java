package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.SubjectDTO;
import lk.ijse.studentsmanagement.entity.Subject;
import lk.ijse.studentsmanagement.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface SubjectService extends SuperService <SubjectDTO>{

    List<SubjectDTO> getSubjectList() throws SQLException, ClassNotFoundException;

    Subject getLastSubjectID() throws SQLException, ClassNotFoundException;

    SubjectDTO delete(SubjectDTO subjectDTO) throws SQLException, ClassNotFoundException;

    String getSubjectName(SubjectDTO subjectDTO) throws SQLException, ClassNotFoundException;
}
