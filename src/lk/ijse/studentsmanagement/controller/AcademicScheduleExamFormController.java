package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import lk.ijse.studentsmanagement.comboLoad.TableLoader;
import lk.ijse.studentsmanagement.dto.BatchDTO;
import lk.ijse.studentsmanagement.dto.CourseSubjectDetailDTO;
import lk.ijse.studentsmanagement.dto.ExamDTO;
import lk.ijse.studentsmanagement.entity.Subject;
import lk.ijse.studentsmanagement.model.SubjectModel;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.BatchService;
import lk.ijse.studentsmanagement.service.custom.CourseSubjectDetailService;
import lk.ijse.studentsmanagement.service.custom.ExamService;
import lk.ijse.studentsmanagement.tblModels.ExamTM;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AcademicScheduleExamFormController implements Initializable {

    public Label lblExamId;
    public TableColumn colExamID;
    public TableView tblExam;
    public TableColumn colSubjectID;
    public TableColumn colBatchId;
    public TableColumn colDescription;
    public TableColumn colDate;
    public TableColumn colLab;
    public TableColumn colTime;
    public JFXButton btnScheduleExam;
    public JFXButton btnCancel;
    public ComboBox<String> cmbBatchID;
    public ComboBox<String> cmbSubjectID;
    public Label lblSubjectName;
    public JFXTimePicker cmbTime;
    public Label lblSelectBatch;
    public Label lblSelectSubject;
    public Label lblSelectDescription;
    public Label lblPickDate;
    public Label lblPickTime;
    public Label lblEnterLab;
    BatchService batchService;
    ExamService examService;
    CourseSubjectDetailService courseSubjectDetailService;
    @FXML
    private AnchorPane pane;
    @FXML
    private JFXTextField txtDescription;
    @FXML
    private JFXDatePicker cmbDate;
    @FXML
    private JFXTextField txtLab;

    @FXML
    void backClickOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.EXAMS, pane);
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
    }

    @FXML
    void btnSheduleOnAction(ActionEvent event) {
        try {
            if (cmbBatchID.getValue() != null) {
                if (cmbSubjectID.getValue() != null) {
                    if (RegExPatterns.getNamePattern().matcher(txtDescription.getText()).matches()) {
                        if (!cmbDate.getValue().isBefore(LocalDate.now())) {
                            if (cmbTime.getValue() != null) {
                                if (txtLab.getText() != null) {
                                    ExamDTO save = examService.save(new ExamDTO(lblExamId.getText(), cmbSubjectID.getValue(), cmbBatchID.getValue(), txtDescription.getText(), Date.valueOf(cmbDate.getValue()), txtLab.getText(), Time.valueOf(cmbTime.getValue())));
                                    if (save != null) {
                                        new Alert(Alert.AlertType.INFORMATION, "Done").show();
                                        Navigation.navigate(Routes.SCHEDULE_EXAMS, pane);
                                    }
                                } else {
                                    new Alert(Alert.AlertType.INFORMATION, "Select Lab").show();
                                    lblEnterLab.setVisible(true);
                                    txtLab.setFocusColor(Color.RED);
                                }
                            } else {
                                new Alert(Alert.AlertType.INFORMATION, "Select Time").show();
                                lblPickTime.setVisible(true);
                            }
                        } else {
                            new Alert(Alert.AlertType.INFORMATION, "Select Date").show();
                            lblPickDate.setVisible(true);
                        }
                    } else {
                        txtDescription.setFocusColor(Color.RED);
                        lblSelectDescription.setVisible(true);
                    }
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Select Subject").show();
                    lblSelectSubject.setVisible(true);
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Select batch first !").show();
                lblSelectBatch.setVisible(true);
            }
        } catch (SQLException | ClassNotFoundException | IOException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }

    public void cmbBatchIdOnAction(ActionEvent actionEvent) {
        try {
            lblSubjectName.setText(null);
            BatchDTO batchDTO = batchService.getCourseID(cmbBatchID.getValue());
            loadBatchCourseSubjectID(batchDTO.getId());

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }

    public void loadBatchCourseSubjectID(String batchID) throws SQLException, ClassNotFoundException, RuntimeException {
        List<CourseSubjectDetailDTO> list = courseSubjectDetailService.getCourseList(batchID);
        //    ArrayList<CourseSubjectDetail> courseList = CourseSubjectDetailModel.getCourseList(batchID);
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (CourseSubjectDetailDTO ele : list) {
            observableList.add(ele.getSubjectId());
        }
        cmbSubjectID.setItems(observableList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colExamID.setCellValueFactory(new PropertyValueFactory<>("examId"));
        colSubjectID.setCellValueFactory(new PropertyValueFactory<>("subjectId"));
        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("lab"));
        colLab.setCellValueFactory(new PropertyValueFactory<>("time"));

        try {
            courseSubjectDetailService = ServiceFactory.getInstance().getService(ServiceTypes.COURSE_SUBJECT_DETAIL);
            examService = ServiceFactory.getInstance().getService(ServiceTypes.EXAM);
            batchService = ServiceFactory.getInstance().getService(ServiceTypes.BATCH);
            loadExamID();
            loadBatchIDS();
            loadAllExams();
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadAllExams() throws SQLException, ClassNotFoundException {
        List<ExamDTO> allExams = examService.getAllExams();
        //ArrayList<Exam> list = ExamModel.getAllExams();
        ObservableList<ExamTM> observableArrayList = FXCollections.observableArrayList();
        for (ExamDTO ele : allExams) {
            observableArrayList.add(new ExamTM(ele.getExamId(), ele.getSubjectId(), ele.getBatchId(), ele.getDescription(), ele.getExamDate(), ele.getLab(), ele.getTime()));
        }
        tblExam.setItems(observableArrayList);
    }

    public void loadBatchIDS() throws SQLException, ClassNotFoundException {
        List<BatchDTO> allBatchID = batchService.getAllBatchID();
        //ArrayList<Batch> allBatchID = BatchModel.getBatches();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (BatchDTO ele : allBatchID) {
            observableList.add(ele.getId());
        }
        cmbBatchID.setItems(observableList);
    }

    public void loadExamID() throws SQLException, ClassNotFoundException {
        ExamDTO lastExamID = examService.getLastExamID();
        //  Exam lastExamID = ExamModel.getExamID();
        if (lastExamID == null) {
            lblExamId.setText("EX00001");
        } else {
            String id = lastExamID.getBatchId();
            String[] split = id.split("[E][X]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            lblExamId.setText(String.format("EX%05d", lastDigits));
        }
    }

    public void cmbSubjectOnAction(ActionEvent actionEvent) {
        try {
            if (cmbSubjectID.getValue() != null) {
                String name = SubjectModel.getSubjectName(new Subject(cmbSubjectID.getValue()));
                if (name != null) {
                    lblSubjectName.setText(name);
                    loadExamTable(cmbBatchID.getValue(), cmbSubjectID.getValue());
                } else {
                    new Alert(Alert.AlertType.ERROR, "Can not find subject name").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "ERROR").show();
        }
    }

    private void loadExamTable(String batchId, String subjectId) throws SQLException, ClassNotFoundException {
        if (batchId != null | subjectId != null) {
            TableLoader.loadExamBatchSubjectTable(tblExam, batchId, subjectId);
        }
    }


    public void cmbTimeOnMouseClicked(MouseEvent mouseEvent) {
        lblPickTime.setVisible(false);
    }

    public void txtLabOnMouseClicked(MouseEvent mouseEvent) {
        lblEnterLab.setVisible(false);
    }

    public void cmbDateOnMouseClicked(MouseEvent mouseEvent) {
        lblPickDate.setVisible(false);
    }

    public void txtDescriptionOnMouseClicked(MouseEvent mouseEvent) {
        lblSelectDescription.setVisible(false);
    }

    public void cmbSubjectOnMouseClicked(MouseEvent mouseEvent) {
        lblSelectSubject.setVisible(false);
    }

    public void cmbBatchOnMouseClicked(MouseEvent mouseEvent) {
        lblSelectBatch.setVisible(false);
    }
}
