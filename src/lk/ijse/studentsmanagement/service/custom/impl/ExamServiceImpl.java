package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.ExamDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.ExamDTO;
import lk.ijse.studentsmanagement.entity.Exam;
import lk.ijse.studentsmanagement.service.custom.ExamService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;
import lk.ijse.studentsmanagement.service.util.Converter;
import lk.ijse.studentsmanagement.service.util.Types;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ExamServiceImpl implements ExamService {
    private final ExamDAO examDAO;
   private final Connection connection;
   private Converter converter;

    public ExamServiceImpl() throws RuntimeException{
        try {
            connection = DBconnection.getInstance().getConnection();
            converter = new Converter();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        examDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.EXAM);
    }

    @Override
    public ExamDTO save(ExamDTO dto) throws SQLException, ClassNotFoundException, DuplicateException {
        return null;
    }

    @Override
    public List<ExamDTO> getAllExams() throws SQLException, ClassNotFoundException,RuntimeException {
        List<Exam> allExams = examDAO.getAllExams();
        if(allExams.size()>0){
           return allExams.stream().map(exam -> converter.toExamDTO(exam, Types.ExamType1)).collect(Collectors.toList());
        }
        throw new RuntimeException("Empty Exam List");
    }

    @Override
    public ExamDTO getLastExamID() throws SQLException, ClassNotFoundException,RuntimeException {
        Exam lastExam = examDAO.getLastExamID();
        if(lastExam!=null) converter.toExamDTO(lastExam,Types.ExamType2);
        return null;
    }
}
