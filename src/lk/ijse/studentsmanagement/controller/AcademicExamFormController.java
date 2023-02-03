package lk.ijse.studentsmanagement.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeInDownBig;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AcademicExamFormController implements Initializable {
    public AnchorPane pane;
    @FXML
    private JFXButton btnAddResults;

    @FXML
    private JFXButton btnAddExams;

    @FXML
    private JFXButton btnAddIqTest;

    @FXML
    private JFXButton btnAddIqMarks;

    @FXML
    private JFXButton btnViewResults;

    @FXML
    private JFXButton btnUpdateExams;


    public void btnAddMarksOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ACADEMIC_ADD_RESULTS, pane);
    }

    public void btnAddExamsOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SCHEDULE_EXAMS, pane);
    }

    public void btnIQTestOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SCHEDULE_IQTEST, pane);
    }

    public void btnAddIQTestMarksOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ACADEMIC_ADDIQMARKS, pane);
    }

    public void btnViewResultsOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ACADEMIC_VIEW_RESULTS_BY_BATCH, pane);
    }

    public void btnManageExamsOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ACADEMIC_MANAGE_EXAMS, pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button[] buttons = {btnAddExams, btnAddResults, btnAddIqTest, btnAddIqMarks, btnViewResults, btnUpdateExams};
        FadeInDownBig fadeInDownBig = new FadeInDownBig();
        for (Button ele : buttons) {
            fadeInDownBig.setNode(ele);
            fadeInDownBig.play();
        }
    }
}
