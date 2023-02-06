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

import static lk.ijse.studentsmanagement.service.util.Types.*;

public class ExamServiceImpl implements ExamService {
    private final ExamDAO examDAO;
    private final Connection connection;
    private final Converter converter;

    public ExamServiceImpl() throws RuntimeException {
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
    public List<ExamDTO> getAllExams() throws SQLException, ClassNotFoundException, RuntimeException {
        List<Exam> allExams = examDAO.getAllExams();
        if (allExams.size() > 0) {
            return allExams.stream().map(exam -> converter.toExamDTO(exam, ExamType1)).collect(Collectors.toList());
        }
        throw new RuntimeException("Empty Exam List");
    }

    @Override
    public ExamDTO getLastExamID() throws SQLException, ClassNotFoundException, RuntimeException {
        Exam lastExam = examDAO.getLastExamID();
        if (lastExam != null) converter.toExamDTO(lastExam, Types.ExamType2);
        return null;
    }

    @Override
    public List<ExamDTO> getExams(String batchId, String subjectId) throws SQLException, ClassNotFoundException, RuntimeException {
        List<Exam> exams = examDAO.getExams(batchId, subjectId);
        if (exams.size() > 0)
            return exams.stream().map(exam -> converter.toExamDTO(exam, ExamType1)).collect(Collectors.toList());
        throw new RuntimeException("no any exams yet!");
    }

    @Override
    public List<ExamDTO> getExam(ExamDTO examDTO) throws SQLException, ClassNotFoundException, RuntimeException {
        List<Exam> exam = examDAO.getExam(converter.toExamEntity(examDTO,ExamType1));
        if (exam.size() > 0) {
            return exam.stream().map(exam1 -> converter.toExamDTO(exam1, ExamType1)).collect(Collectors.toList());
        }
        throw new RuntimeException("Empty exam list");
    }

    @Override
    public Exam delete(ExamDTO examDTO) throws SQLException, ClassNotFoundException ,RuntimeException{
        Exam delete = examDAO.delete(converter.toExamEntity(examDTO, ExamType2));
        if(delete!=null) return delete;
        throw new RuntimeException("not deleted...");
    }

    @Override
    public Exam update(ExamDTO examDTO) throws SQLException, ClassNotFoundException {
        Exam update = examDAO.update(converter.toExamEntity(examDTO, ExamType3));
        if(update!=null) return update;
        throw new RuntimeException("not updated...");
    }

    @Override
    public ExamDTO view(ExamDTO examDTO) throws SQLException, ClassNotFoundException ,RuntimeException{
        Exam view = examDAO.view(converter.toExamEntity(examDTO, ExamType4));
        if(view!=null) return converter.toExamDTO(view,ExamType3);
        throw new RuntimeException("no any exam");
    }

    @Override
    public String getSubjectName(ExamDTO examDTO) throws SQLException, ClassNotFoundException,RuntimeException {
       return examDAO.getSubjectName(converter.toExamEntity(examDTO,ExamType4));
    }

}
