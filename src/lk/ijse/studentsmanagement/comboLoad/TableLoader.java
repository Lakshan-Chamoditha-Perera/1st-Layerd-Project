package lk.ijse.studentsmanagement.comboLoad;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.studentsmanagement.model.*;
import lk.ijse.studentsmanagement.tblModels.*;
import lk.ijse.studentsmanagement.entity.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableLoader {
    public static void loadAllBatches(TableView tableBatches) throws SQLException, ClassNotFoundException {
        ArrayList<Batch> list = BatchModel.getAllBAtches();
        ObservableList<BatchTM> observableArrayList = FXCollections.observableArrayList();
        for (Batch ele : list) {
            observableArrayList.add(
                    new BatchTM(
                            ele.getId(),
                            ele.getBatchNo(),
                            ele.getCourseId(),
                            ele.getFee(),
                            ele.getStarting_date(),
                            ele.getMaxStdCount()
                    )
            );
        }
        tableBatches.setItems(observableArrayList);
    }

    public static void loadExamBatchSubjectTable(TableView tblExam, String batchId, String subjectId) throws SQLException, ClassNotFoundException {
        ArrayList<Exam> list = ExamModel.getExams(batchId, subjectId);
        ObservableList<ExamTM> observableArrayList = FXCollections.observableArrayList();
        for (Exam ele : list) {
            observableArrayList.add(
                    new ExamTM(
                            ele.getExamId(),
                            ele.getSubjectId(),
                            ele.getBatchId(),
                            ele.getDescription(),
                            ele.getExamDate(),
                            ele.getLab(),
                            ele.getTime()
                    )
            );
        }
        tblExam.setItems(observableArrayList);
    }

    public static void loadIQTests(TableView tblIqTest) throws SQLException, ClassNotFoundException {
        method(tblIqTest);
    }

    public static void method(TableView tblIqTest) throws SQLException, ClassNotFoundException {
        ArrayList<IQTest> list = IQTestModel.getIQTestList();
        ObservableList<IQTestTM> observableArrayList = FXCollections.observableArrayList();
        for (IQTest ele : list) {
            observableArrayList.add(
                    new IQTestTM(
                            ele.getId(),
                            ele.getDate(),
                            ele.getTime(),
                            ele.getLab(),
                            ele.getAmount()
                    )
            );
        }
        tblIqTest.setItems(observableArrayList);
    }


    public static boolean loadAllExams(TableView<ExamTM> tblExams) throws SQLException, ClassNotFoundException {
        ArrayList<Exam> list = ExamModel.getAllExams();
        if (list != null) {
            ObservableList<ExamTM> observableArrayList = FXCollections.observableArrayList();
            for (Exam ele : list) {
                observableArrayList.add(
                        new ExamTM(
                                ele.getExamId(),
                                ele.getSubjectId(),
                                ele.getBatchId(),
                                ele.getDescription(),
                                ele.getExamDate(),
                                ele.getLab(),
                                ele.getTime()
                        )
                );
            }
            tblExams.setItems(observableArrayList);
            return true;
        }
        return false;
    }


    public static void loadCourseSubjectDetailJOIN(TableView<CourseSubjectDetailTM> tblCourseSubjectDetail, String courseId) throws SQLException, ClassNotFoundException {
        ArrayList<CourseSubjectDetailTM> list = CourseSubjectDetailModel.getCourseSubjecDetailList(courseId);
        if (list != null) {
            ObservableList<CourseSubjectDetailTM> observableList = FXCollections.observableArrayList();
            observableList.addAll(list);
            tblCourseSubjectDetail.setItems(observableList);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Subjects Not added yet").show();
        }
    }


    public static boolean loadAllPayments(TableView<PaymentsTM> tblPayments) throws SQLException, ClassNotFoundException {
        ArrayList<Payment> paymentArrayList = PaymentModel.getAllPayments();
        if (paymentArrayList != null) {
            ObservableList<PaymentsTM> observableList = FXCollections.observableArrayList();
            for (Payment ele : paymentArrayList) {
                observableList.add(new PaymentsTM(
                        ele.getId(),
                        ele.getRegistrationId(),
                        ele.getType(),
                        ele.getRemark(),
                        ele.getAmount(),
                        ele.getDate()
                ));
            }
            tblPayments.setItems(observableList);
            return true;
        }
        return false;
    }


    public static boolean loadMarkAttendanceTable(Date valueOf, TableView<AttendanceTM> tblAttendance) throws SQLException, ClassNotFoundException {
        ArrayList<Attendance> list = AttendanceModel.loadDayAttendance(valueOf);
        if(list!=null){
            ObservableList<AttendanceTM> observableList = FXCollections.observableArrayList();
            for (Attendance ele: list) {
                observableList.add(
                  new AttendanceTM(
                          ele.getRegistrationId(),
                          ele.getDate(),
                          ele.getBatchId(),
                          ele.getStatus()
                  )
                );
            }
            tblAttendance.setItems(observableList);
            return true;
        }
        return false;
    }

    public static boolean loadBatchRegistrations(TableView<RegistrationTM> tblStudents, String value) throws SQLException, ClassNotFoundException {
        ArrayList<Registration> list = RegistrationModel.loadBatchRegistrations(value);
        if(list!=null){
            ObservableList<RegistrationTM> observableList = FXCollections.observableArrayList();
            for (Registration ele: list) {
                observableList.add(
                        new RegistrationTM(
                                ele.getRegistrationId(),
                                ele.getBatchId(),
                                ele.getName(),
                                ele.getStatus()
                        )
                );
            }
            tblStudents.setItems(observableList);
            return true;
        }
        return false;
    }

    public static boolean loadRegistrationEaxmResults(TableView<RegistrationExamResultTM> tblResults, String examId) throws SQLException, ClassNotFoundException {
        ArrayList<RegistrationExamResult> list = RegistrationExamResultModel.getResults(examId);
        if(list!=null){
            ObservableList<RegistrationExamResultTM> registrationExamResultTMS = FXCollections.observableArrayList();

            for (RegistrationExamResult ele: list) {
                registrationExamResultTMS.add(
                  new RegistrationExamResultTM(
                          ele.getExamId(),
                          ele.getRegistrationId(),
                          ele.getMark(),
                          ele.getResult()
                  )
                );
            }
            tblResults.setItems(registrationExamResultTMS);
            return true;

        }
        return false;
    }

    public static boolean loadTableTranscript(Registration registration, TableView tblResults) throws SQLException, ClassNotFoundException {
        ArrayList<RegistrationExamResult> transcript = RegistrationExamResultModel.getTranscript(registration);
        if(transcript.size()!=0){
            ObservableList<RegistrationExamResultTM> registrationExamResultTMS = FXCollections.observableArrayList();
            for (RegistrationExamResult ele: transcript) {;
                registrationExamResultTMS.add(
                        new RegistrationExamResultTM(
                                ele.getMark(),
                                ele.getResult(),
                                ele.getSubject()
                        )
                );
            }
            tblResults.setItems(registrationExamResultTMS);
            return true;
        }
        return false;
    }
}