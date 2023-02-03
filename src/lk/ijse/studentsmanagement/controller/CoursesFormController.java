package lk.ijse.studentsmanagement.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class CoursesFormController {
    @FXML
    private AnchorPane pane;
    public void gdseOnAction(javafx.event.ActionEvent actionEvent) throws IOException {Navigation.navigate(Routes.GDSE,pane);}
    public void cmjdOnAction(javafx.event.ActionEvent actionEvent) throws IOException {Navigation.navigate(Routes.CMJD,pane);}
    public void rmadOnAction(javafx.event.ActionEvent actionEvent) throws IOException {Navigation.navigate(Routes.RWAD,pane);}
    public void depOnAction(javafx.event.ActionEvent actionEvent) throws IOException {Navigation.navigate(Routes.DEP,pane);}
}
