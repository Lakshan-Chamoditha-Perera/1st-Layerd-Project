package lk.ijse.studentsmanagement.model;

import lk.ijse.studentsmanagement.entity.InquiryIQTestDetail;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InquiryIQTestDetailModel {
    public static boolean addInquiryTestDetail(InquiryIQTestDetail inquiryIQTestDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO inquiry_iqTest_Detail (studentID,testID, result) VALUES(?,?,?)",
                inquiryIQTestDetail.getStudentId(),
                inquiryIQTestDetail.getTestId(),
                inquiryIQTestDetail.getResult()
        );
    }

    public static ArrayList<InquiryIQTestDetail> getInquiryIQTestList() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM inquiry_iqTest_Detail");
        ArrayList<InquiryIQTestDetail> list = new ArrayList<>();
        while(resultSet.next()){
            list.add(new InquiryIQTestDetail(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return list;
    }

    public static ArrayList <InquiryIQTestDetail> getInquiryIQTestList(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT inquiry.studentID, inquiry_iqTest_Detail.testID, inquiry_iqTest_Detail.result, inquiry.name " +
                "FROM inquiry " +
                "INNER JOIN inquiry_iqTest_Detail " +
                "ON inquiry.studentID = inquiry_iqTest_Detail.studentID " +
                "WHERE testID = ?",id);
        if(resultSet!=null){
            ArrayList <InquiryIQTestDetail> list = new ArrayList<>();
            while(resultSet.next()){
                list.add(
                  new InquiryIQTestDetail(
                          resultSet.getString(1),
                          resultSet.getString(2),
                          resultSet.getString(3),
                          resultSet.getString(4)
                  )
                );
            }
            return list;
        }
        return null;
    }

    public static boolean updateInquiryIQTestDetail(InquiryIQTestDetail inquiryIQTestDetail) throws SQLException, ClassNotFoundException {
        System.out.println(inquiryIQTestDetail);
        return CrudUtil.execute("UPDATE inquiry_iqTest_Detail SET result = ? WHERE studentID = ? AND testID = ?",
                inquiryIQTestDetail.getResult(),
                inquiryIQTestDetail.getStudentId(),
                inquiryIQTestDetail.getTestId()
        );
    }
}
