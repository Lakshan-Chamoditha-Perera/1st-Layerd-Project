package lk.ijse.studentsmanagement.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class MainFormController{

    public AnchorPane pane;
    public Label lblName;

    public void btnStartClickOnAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        Navigation.navigate(Routes.LOGIN, pane);
    }

    @FXML
    private CubicCurve curve;

    @FXML
    void rootOnMouseExisted(MouseEvent event) {
        curve.setControlX2(10);
        //curve.setControlY2(-10);

    }

    @FXML
    void rootOnMouseMoved(MouseEvent event) {
        curve.setControlX2(event.getX()-100);
        // System.out.println(event.getX());
    }


}
