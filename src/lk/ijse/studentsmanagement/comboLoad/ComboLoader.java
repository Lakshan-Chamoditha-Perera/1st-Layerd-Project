package lk.ijse.studentsmanagement.comboLoad;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import lk.ijse.studentsmanagement.model.*;
import lk.ijse.studentsmanagement.entity.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class ComboLoader {

    public static void loadCoursesList(ComboBox<String> comboBox) throws SQLException, ClassNotFoundException {
        ArrayList<Course> courseArrayList = CourseModel.getCourseList();
        ObservableList<String> observableArrayList = FXCollections.observableArrayList();
        for (Course ele : courseArrayList) {
            observableArrayList.add(ele.getId());
        }
        comboBox.setItems(observableArrayList);
    }

    public static void LoadBatchIDS(ComboBox<String> comboBox, String courseName) throws SQLException, ClassNotFoundException {
        ArrayList<Batch> list = BatchModel.getBatchIDListByCourseId(courseName);
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (Batch ele : list) {
            observableList.add(ele.getId());
            System.out.println(ele.getId());
        }
        comboBox.setItems(observableList);
    }

    public static boolean loadBatchIDS(ComboBox<String> comboBox) throws SQLException, ClassNotFoundException {
        ArrayList<Batch> batches = BatchModel.getBatches();
        if(batches!=null){

            ObservableList<String> observableList = FXCollections.observableArrayList();
            for (Batch ele : batches) {
                observableList.add(ele.getId());
                System.out.println(ele.getId());
            }
            comboBox.setItems(observableList);
            return true;
        }
        return false;
    }

    public static void loadBatchCourseSubjectID(ComboBox<String> cmbSubjectID, String batchID) throws SQLException, ClassNotFoundException {
        ArrayList<CourseSubjectDetail> courseList = CourseSubjectDetailModel.getCourseList(batchID);
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (CourseSubjectDetail ele : courseList) {
            observableList.add(ele.getSubjectId());
        }
        cmbSubjectID.setItems(observableList);
    }

    public static void loadSubjectList(ComboBox<String> cmbSubject) throws SQLException, ClassNotFoundException {
        ArrayList<Subject> subjectArrayList = SubjectModel.getIDs();
        if (subjectArrayList != null) {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            for (Subject ele : subjectArrayList) {
                observableList.add(ele.getId());
            }
            cmbSubject.setItems(observableList);
        }
    }

    public static boolean loadExamId(String value, ComboBox<String> cmbExam) throws SQLException, ClassNotFoundException {
        ArrayList<Exam> exams = ExamModel.getExam(new Exam(value));
        if(exams!=null){
            ObservableList<String> observableArrayList = FXCollections.observableArrayList();
            for (Exam ele : exams) {
                observableArrayList.add(ele.getExamId());
            }
            cmbExam.setItems(observableArrayList);
            return true;
        }
        return false;
    }
}
