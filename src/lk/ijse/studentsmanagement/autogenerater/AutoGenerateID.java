package lk.ijse.studentsmanagement.autogenerater;
import javafx.scene.control.Label;
import lk.ijse.studentsmanagement.model.*;
import lk.ijse.studentsmanagement.entity.*;

import java.sql.SQLException;
public class AutoGenerateID {



    public static void loadExamID(Label lblExamId) throws SQLException, ClassNotFoundException {
        Exam lastExamID = ExamModel.getExamID();
        if(lastExamID==null){
            lblExamId.setText("EX00001");
        }else{
            String id = lastExamID.getBatchId();
            String[] split = id.split("[E][X]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            lblExamId.setText(String.format("EX%05d", lastDigits));
        }
    }

    public static void loadIQTestIDS(Label lblExamID) throws SQLException, ClassNotFoundException {
        IQTest lastExamID = IQTestModel.getExamID();
        if(lastExamID==null){
            lblExamID.setText("IQ0001");
        }else{
            String id = lastExamID.getId();
            String[] split = id.split("[I][Q]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            lblExamID.setText(String.format("IQ%04d", lastDigits));
        }
    }

//    public static void generateSubjectID(Label lblSubID) throws SQLException, ClassNotFoundException {
//        Subject lastSubjectID = Subject.getLastSubjectID();
//        if(lastSubjectID==null){
//            lblSubID.setText("ITS0001");
//        }else{
//            String id = lastSubjectID.getId();
//            String[] split = id.split("[I][T][S]");
//            int lastDigits = Integer.parseInt(split[1]);
//            lastDigits++;
//            lblSubID.setText(String.format("ITS%04d", lastDigits));
//        }
//    }
}
