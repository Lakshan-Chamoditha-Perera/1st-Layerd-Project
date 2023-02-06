package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.InquiryIqTestDetailDAO;
import lk.ijse.studentsmanagement.entity.InquiryIQTestDetail;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InquiryIqTestDetailImpl implements InquiryIqTestDetailDAO {
    private final Connection connection;

    public InquiryIqTestDetailImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public InquiryIQTestDetail save(InquiryIQTestDetail entity) throws SQLException, ClassNotFoundException, RuntimeException {
        if (CrudUtil.execute(
                "INSERT INTO inquiry_iqTest_Detail (studentID,testID, result) VALUES(?,?,?)",
                entity.getStudentId(),
                entity.getTestId(),
                entity.getResult()
        )) return entity;
       return null;
    }

    @Override
    public InquiryIQTestDetail update(InquiryIQTestDetail entity) throws SQLException, ClassNotFoundException, RuntimeException {
        if (CrudUtil.execute("UPDATE inquiry_iqTest_Detail SET result = ? WHERE studentID = ? AND testID = ?",
                entity.getResult(),
                entity.getStudentId(),
                entity.getTestId()
        )) return entity;
        return null;
    }

    @Override
    public InquiryIQTestDetail delete(InquiryIQTestDetail entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }

    @Override
    public InquiryIQTestDetail view(InquiryIQTestDetail entity) throws SQLException, ClassNotFoundException, RuntimeException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM inquiry_iqTest_Detail WHERE studentID = ? AND testID = ?", entity.getStudentId(), entity.getTestId());
        if (!resultSet.next()) return entity;
        return null;
    }

    @Override
    public List<InquiryIQTestDetail> getInquiryIQTestList() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM inquiry_iqTest_Detail");
        List<InquiryIQTestDetail> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new InquiryIQTestDetail(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return list;
    }
}
