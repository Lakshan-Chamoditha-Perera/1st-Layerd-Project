package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.dto.AttendanceDTO;
import lk.ijse.studentsmanagement.dto.RegistrationDTO;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.AttendanceService;
import lk.ijse.studentsmanagement.service.custom.RegistrationService;
import lk.ijse.studentsmanagement.service.util.qr.QRScanner;
import lk.ijse.studentsmanagement.tblModels.AttendanceTM;
import lk.ijse.studentsmanagement.util.RegExPatterns;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
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
    RegistrationService registrationService;
    AttendanceService attendanceService;
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

    public static void remove() {
        System.out.println("Close");
    }

    @FXML
    void btnMarkOnAction() throws InterruptedException {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            attendanceService = ServiceFactory.getInstance().getService(ServiceTypes.ATTENDANCE);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colBatch.setCellValueFactory(new PropertyValueFactory<>("batchID"));
        lblInvalidID.setVisible(false);

//        StackPane stackPane = new StackPane();
//        stackPane.getChildren().add(qrScanner.getVideoPanel());
//        Stage stage = new Stage();
//
//
//        stage.setScene(new Scene(stackPane, 800, 600));
//        stage.show();
//        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                QRScanner.webcam.close();
//                qrScanner.thread.stop();
//            }
//        });

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
        return loadMarkAttendanceTable(date, tblAttendance);
    }

    public boolean loadMarkAttendanceTable(Date day, TableView<AttendanceTM> tblAttendance) throws SQLException, ClassNotFoundException {
        List<AttendanceDTO> dtoList = attendanceService.loadDayAttendance(day);
        //ArrayList<Attendance> list = AttendanceModel.loadDayAttendance(day);
        if (dtoList != null) {
            ObservableList<AttendanceTM> observableList = FXCollections.observableArrayList();
            for (AttendanceDTO ele : dtoList) {
                observableList.add(new AttendanceTM(ele.getRegistrationId(), ele.getDate(), ele.getBatchId(), ele.getStatus()));
            }
            tblAttendance.setItems(observableList);
            return true;
        }
        return false;
    }


    public void txtRegistrationIDOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidID.setVisible(false);
    }

    public void txtRegistrationIDOnAction(ActionEvent actionEvent) {
        try {
            if (RegExPatterns.getRegistrationIdPattern().matcher(txtRegistrationId.getText()).matches()) {
                RegistrationDTO registrationDTO = registrationService.view(new RegistrationDTO(txtRegistrationId.getText()));
                // Registration registrationDTO = RegistrationModel.getRegistrationDetails(txtRegistrationId.getText());
                if (registrationDTO != null) {
                    lblName.setText(registrationDTO.getName());
                    lblBatch.setText(registrationDTO.getBatchId());
                    boolean added = attendanceService.addAttendance(new AttendanceDTO(txtRegistrationId.getText(), Date.valueOf(LocalDate.now()), registrationDTO.getBatchId(), "PRESENT"));
                    //   boolean isAdded = AttendanceModel.addAttendance(new Attendance(txtRegistrationId.getText(), Date.valueOf(LocalDate.now()), registrationDTO.getBatchId(), "PRESENT"));
                    if (added) {
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
                List<AttendanceDTO> attendanceDTOS = attendanceService.loadDayAttendance(Date.valueOf(calender.getValue()));
                //   ArrayList<Attendance> attendances = loadDayAttendance(Date.valueOf(calender.getValue()));
                if (attendanceDTOS != null) {
                    loadAttendanceTable(Date.valueOf(calender.getValue()));
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "No any attendance").show();
                    loadAttendanceTable(Date.valueOf(LocalDate.now()));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }
}
