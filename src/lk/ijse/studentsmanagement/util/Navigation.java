package lk.ijse.studentsmanagement.util;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Navigation {
    private static AnchorPane pane;

    public static void navigate(Routes route, AnchorPane pane) throws IOException {
       // new FadeOut(pane).play();
        Navigation.pane = pane;
        Navigation.pane.getChildren().clear();
        Stage window = (Stage) Navigation.pane.getScene().getWindow();


        switch (route) {

            case MAIN:
                window.setTitle("");
                initUI("MainForm.fxml");
                break;

            case LOGIN:
                window.setTitle("LOGIN PAGE");
                initUI("LoginPage.fxml");
                break;

            case COUNSELOR:
                window.setTitle("Counselor Main PAGE");
                initUI("CounsilorDashboard.fxml");
                break;

            case INQUIRIES:
                window.setTitle("Inquiries ");
                initUI("InquariesForm.fxml");
                break;

            case ADD_STUDENT:
                window.setTitle("Inquiries > Add Inquiry");
                initUI("AddInquiryForm.fxml");
                break;

            case VIEW_STUDENT:
                window.setTitle("Inquiries > View Inquiry");
                initUI("ViewStudentDetailsForm.fxml");
                break;

            case UPDATE_STUDENT:
                window.setTitle("Inquiries > Update Inquiry");
                initUI("UpdateInquaryForm.fxml");
                break;

            case IQTest:
                window.setTitle("IQTests");
                initUI("IQTestsForm.fxml");
                break;

            case MAIL_SERVICE_COUNSELOR:
                window.setTitle("MAIL_SERVICE");
                initUI("MailsCounselorForm.fxml");
                break;

            case ENROLLMENTS:
                window.setTitle("Enrollments");
                initUI("EnrollmentsForm.fxml");
                break;

            case VIEW_REGISTRATION:
                window.setTitle("VIEW_REGISTRATION");
                initUI("ViewStudentEnrollmentForm.fxml");
                break;


            case PAYMENTS:
                window.setTitle("Payments");
                initUI("PaymentsForm.fxml");
                break;


            case COURSES:
                window.setTitle("Courses");
                initUI("CoursesForm.fxml");
                break;

            case GDSE:
                window.setTitle("GDSE");
                initUI("GdseForm.fxml");
                break;

            case CMJD:
                window.setTitle("CMJD");
                initUI("CmjdForm.fxml");
                break;

            case DEP:
                window.setTitle("DEP");
                initUI("DepForm.fxml");
                break;

            case RWAD:
                window.setTitle("RWAD");
                initUI("RwadForm.fxml");
                break;

            case ACADEMIC:
                window.setTitle("Academic");
                initUI("AcademicDashboardForm.fxml");
                break;

            case EXAMS:
                window.setTitle("Exams");
                initUI("AcademicExamForm.fxml");
                break;

            case SCHEDULE_EXAMS:
                window.setTitle("SCHEDULE_EXAMS");
                initUI("AcademicScheduleExamForm.fxml");
                break;

            case SCHEDULE_IQTEST:
                window.setTitle("SCHEDULE_IQTEST");
                initUI("AcademicSheduleIQTestForm.fxml");
                break;

            case ACADEMIC_ADDIQMARKS:
                window.setTitle("ACADEMIC_ADDIQMARKS");
                initUI("AcademicAddIQtestMarksForm.fxml");
                break;

            case ACADEMIC_STUDENTFORM:
                window.setTitle("ACADEMIC_STUDENTFORM");
                initUI("AcademicStudentsForm.fxml");
                break;


            case ACADEMIC_TRANSCRIPT:
                window.setTitle("ACADEMIC_TRANSCRIPT");
                initUI("AcademicTranscriptForm.fxml");
                break;

            case ACADEMIC_VIEW_RESULTS_BY_BATCH:
                window.setTitle("ACADEMIC_VIEW_RESULTS_BY_BATCH");
                initUI("AcademicViewResultsForm.fxml");
                break;

            case ACADEMIC_MANAGE_EXAMS:
                window.setTitle("ACADEMIC_MANAGE_EXAMS");
                initUI("AcademicManageExamsForm.fxml");
                break;

            case ACADEMIC_VIEW_STUDENT_DETAILS:
                window.setTitle("ACADEMIC_VIEW_STUDENT_DETAILS");
                initUI("AcademicViewStudentsDetailsForm.fxml");
                break;
            case ACADEMIC_ADD_NEW_BATCH:
                window.setTitle("ACADEMIC_ADD_NEW_BATCH");
                initUI("AcademicAddNewBatchForm.fxml");
                break;

            case ACADEMIC_MANAGE_BATCHES:
                window.setTitle("ACADEMIC_MANAGE_BATCHES");
                initUI("AcademicManageBatchesForm.fxml");
                break;
            case SEND_MAIL_TO_REGISTERED:
                window.setTitle("Send Mail");
                initUI("SendMailToRegisterdStudentForm.fxml");
                break;
            case ACADEMIC_MANAGE_SUBJECTS:
                window.setTitle("ACADEMIC_MANAGE_SUBJECTS");
                initUI("AcademicManageSubjectsForm.fxml");
                break;
            case ACADEMIC_ADD_SUBJECT_TO_COURSE:
                window.setTitle("ACADEMIC_MANAGE_SUBJECTS");
                initUI("AcademicAddSubjectToCourseForm.fxml");
                break;

            case ACADEMIC_PAYMENTS:
                window.setTitle("ACADEMIC_PAYMENTS");
                initUI("AcademicPaymentsForm.fxml");
                break;
            case ACADEMIC_REGISTRATION_PAYMENTS:
                window.setTitle("ACADEMIC_REGISTRATION_PAYMENTS");
                initUI("AcademicRegistrationPaymentsForm.fxml");
                break;
            case ACADEMIC_INQUIRY_PAYMENTS:
                window.setTitle("ACADEMIC_INQUIRY_PAYMENTS");
                initUI("AcademicInqauiryPaymentsForm.fxml");
                break;


            case MARK_ATTENDANCE:
                window.setTitle("MARK_ATTENDANCE");
                initUI("AcademicAttendanceForm.fxml");
                break;

            case ACADEMIC_ADD_RESULTS:
                window.setTitle("ACADEMIC_ADD_RESULTS");
                initUI("AcademicAddResultsForm.fxml");
                break;

            default:
                new Alert(Alert.AlertType.ERROR, "UI Not Found!").show();
                break;

                

        }
    }

    public static void initUI(String location) throws IOException {
        new FadeIn(pane).play();
        Navigation.pane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(Navigation.class.getResource("/"+location))));
    }
}
