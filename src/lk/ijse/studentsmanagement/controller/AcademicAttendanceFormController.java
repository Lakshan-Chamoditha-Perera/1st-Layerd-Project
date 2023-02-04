package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lk.ijse.studentsmanagement.comboLoad.TableLoader;
import lk.ijse.studentsmanagement.model.AttendanceModel;
import lk.ijse.studentsmanagement.model.RegistrationModel;
import lk.ijse.studentsmanagement.service.util.qr.QRScanner;
import lk.ijse.studentsmanagement.tblModels.AttendanceTM;
import lk.ijse.studentsmanagement.entity.Attendance;
import lk.ijse.studentsmanagement.entity.Registration;
import lk.ijse.studentsmanagement.util.RegExPatterns;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AcademicAttendanceFormController implements Initializable {

    public static String scan;
    static QRScanner qrScanner;
    public TableColumn colBatch;
    public Label lblName;
    public Label lblBatch;
    public Label lblInvalidID;
    public AnchorPane calenderOnAction;
    public JFXDatePicker calender;
    @FXML
    private TableView<AttendanceTM> tblAttendance;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colStatus1;

    @FXML
    private TextField txtRegistrationId;

    @FXML
    private Button btnMark;

    @FXML
    void btnMarkOnAction() throws InterruptedException {

    }


    public static void remove() {
        System.out.println("Close");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colId.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colBatch.setCellValueFactory(new PropertyValueFactory<>("batchID"));
        lblInvalidID.setVisible(false);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(qrScanner.getVideoPanel());
        Stage stage = new Stage();


        stage.setScene(new Scene(stackPane, 800, 600));
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                QRScanner.webcam.close();
                qrScanner.thread.stop();
            }
        });

        try {
            boolean isLoaded = loadAttendanceTable(Date.valueOf(LocalDate.now()));
            if (!isLoaded) {
                new Alert(Alert.AlertType.INFORMATION, "No any Attendance yet!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private boolean loadAttendanceTable(Date date) throws SQLException, ClassNotFoundException {
        return TableLoader.loadMarkAttendanceTable(date, tblAttendance);
    }

    public void txtRegistrationIDOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidID.setVisible(false);
    }

    public void txtRegistrationIDOnAction(ActionEvent actionEvent) {
        try {
            if (RegExPatterns.getRegistrationIdPattern().matcher(txtRegistrationId.getText()).matches()) {

                Registration registrationDetails = RegistrationModel.getRegistrationDetails(txtRegistrationId.getText());
                if (registrationDetails != null) {
                    lblName.setText(registrationDetails.getName());
                    lblBatch.setText(registrationDetails.getBatchId());
                    boolean isAdded = AttendanceModel.addAttendance(new Attendance(
                            txtRegistrationId.getText(),
                            Date.valueOf(LocalDate.now()),
                            registrationDetails.getBatchId(),
                            "PRESENT"
                    ));
                    if (isAdded) {
                        txtRegistrationId.setText(null);
                        loadAttendanceTable(Date.valueOf(LocalDate.now()));
                        lblName.setText("");
                        lblBatch.setText("");
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Student Does not exists!").show();
                }
            } else {
                lblInvalidID.setVisible(true);
            }
        } catch (SQLException | ClassNotFoundException e) {

            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }

    public void calenderOnAction(ActionEvent actionEvent) {
        try {
            if (calender.getValue() != null) {
                ArrayList<Attendance> attendances = AttendanceModel.loadDayAttendance(Date.valueOf(calender.getValue()));
                if(!attendances.isEmpty()){
                    loadAttendanceTable(Date.valueOf(calender.getValue()));
                }else{
                    new Alert(Alert.AlertType.INFORMATION, "No any attendance").show();
                    loadAttendanceTable(Date.valueOf(LocalDate.now()));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }
}
