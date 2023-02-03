package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.QueryDAO;
import lk.ijse.studentsmanagement.dto.InquiryIQTestDetailDTO;
import lk.ijse.studentsmanagement.dto.RegistrationExamResultDTO;
import lk.ijse.studentsmanagement.entity.Registration;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryImpl implements QueryDAO {
    private final Connection connection;

    public QueryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<InquiryIQTestDetailDTO> getInquiryIQTestList(String id) throws SQLException, ClassNotFoundException {
        System.out.println("QueryDAO...");
        ResultSet resultSet = CrudUtil.execute(
                "SELECT inquiry.studentID, inquiry_iqTest_Detail.testID, inquiry_iqTest_Detail.result, inquiry.name " +
                        "FROM inquiry " +
                        "INNER JOIN inquiry_iqTest_Detail " +
                        "ON inquiry.studentID = inquiry_iqTest_Detail.studentID " +
                        "WHERE testID = ?", id);
        List<InquiryIQTestDetailDTO> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new InquiryIQTestDetailDTO(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return list;
    }

    @Override
    public List<RegistrationExamResultDTO> getTranscript(Registration registration) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT registration_exam_results.marks," +
                        "subject.name, " +
                        "registration_exam_results.result " +
                        "FROM registration_exam_results " +
                        "INNER JOIN exam ON registration_exam_results.exam_id = exam.id " +
                        "INNER JOIN subject ON exam.subjectID = subject.id " +
                        "WHERE registration_id = ?",
                registration.getRegistrationId());
        List<RegistrationExamResultDTO> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new RegistrationExamResultDTO(
                            Integer.parseInt(resultSet.getString(1)),
                            resultSet.getString(3),
                            resultSet.getString(2)
                    )
            );
        }
        return list;
    }

}
