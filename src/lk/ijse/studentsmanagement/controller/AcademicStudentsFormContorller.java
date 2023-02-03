package lk.ijse.studentsmanagement.controller;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;

public class AcademicStudentsFormContorller {
    public AnchorPane pane2;
    public AnchorPane pane;

    public void btnTranscriptOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ACADEMIC_TRANSCRIPT,pane);
    }

    public void btnViewStudentsDetailsOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ACADEMIC_VIEW_STUDENT_DETAILS,pane);
    }

    public void backClickOnAction(ActionEvent actionEvent) {
    }
}
