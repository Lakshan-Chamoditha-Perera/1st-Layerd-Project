package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.SubjectDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.SubjectDTO;
import lk.ijse.studentsmanagement.entity.Subject;
import lk.ijse.studentsmanagement.service.custom.SubjectService;
import lk.ijse.studentsmanagement.service.util.Converter;
import lk.ijse.studentsmanagement.service.util.Types;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class SubjectServiceImpl implements SubjectService {
    private final SubjectDAO subjectDAO;
    private final Connection connection;
    Converter converter;

    public SubjectServiceImpl() throws SQLException, ClassNotFoundException {
        converter = new Converter();
        connection = DBconnection.getInstance().getConnection();
        subjectDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.SUBJECT);
    }

    @Override
    public SubjectDTO save(SubjectDTO dto) throws SQLException, ClassNotFoundException, RuntimeException {
        Subject subject = converter.toSubjectEntity(dto, Types.SubjectType1);
        subjectDAO.save(subject);
        return dto;
    }

    @Override
    public List<SubjectDTO> getSubjectList() throws SQLException, ClassNotFoundException,RuntimeException {
        List<Subject> subjectList = subjectDAO.getSubjectList();
        if (subjectList.size() > 0) {
            return subjectList.stream().map(subject -> converter.toSubjectDTO(subject, Types.SubjectType1)).collect(Collectors.toList());
        }
        throw new RuntimeException("Empty Subject list");
    }

    @Override
    public Subject getLastSubjectID() throws SQLException, ClassNotFoundException {
        return subjectDAO.getLastSubjectID();
    }

    @Override
    public SubjectDTO delete(SubjectDTO subjectDTO) throws SQLException, ClassNotFoundException {
        subjectDAO.delete(converter.toSubjectEntity(subjectDTO, Types.SubjectType2));
        return subjectDTO;
    }
}
