package lk.ijse.studentsmanagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.dto.CourseDTO;
import lk.ijse.studentsmanagement.dto.CourseSubjectDetailDTO;
import lk.ijse.studentsmanagement.dto.SubjectDTO;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.CourseService;
import lk.ijse.studentsmanagement.service.custom.CourseSubjectDetailService;
import lk.ijse.studentsmanagement.service.custom.SubjectService;
import lk.ijse.studentsmanagement.dto.tblModels.CourseSubjectDetailTM;
import lk.ijse.studentsmanagement.dto.tblModels.SubjectTM;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

//
public class AcademicAddSubjectToCourseFormController implements Initializable {
    //
    SubjectService subjectService;
    CourseSubjectDetailService courseSubjectDetailService;
    CourseService courseService;
    @FXML
    private AnchorPane pane;
    @FXML
    private ComboBox<String> cmbCourse;
    @FXML
    private ComboBox<String> cmbSubject;
    @FXML
    private Button btnAdd;
    @FXML
    private Label lblCourseName;
    @FXML
    private Label lblSubjectName;
    @FXML
    private Label lblSelectCourse;
    @FXML
    private Label lblSelectSubject;
    @FXML
    private TableView<SubjectTM> tblSubjects;
    @FXML
    private TableColumn<?, ?> colSubID;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colHours;
    @FXML
    private TableView<CourseSubjectDetailTM> tblCourseSubjectDetail;
    @FXML
    private TableColumn<?, ?> colCourseID;
    @FXML
    private TableColumn<?, ?> colSubjectID;
    @FXML
    private TableColumn<?, ?> colSubjectName;
    @FXML
    private Button btnDelete;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            if (cmbCourse.getSelectionModel().getSelectedItem() != null) {
                if (cmbSubject.getSelectionModel().getSelectedItem() != null) {
                    CourseSubjectDetailDTO add = add();
                    if (add != null) {
                        new Alert(Alert.AlertType.INFORMATION, "ADDED").show();
                        loadSubjectList();
                        loadCourseSubjectDetailJOIN(cmbCourse.getSelectionModel().getSelectedItem());
                    }
                } else {
                    lblSelectSubject.setVisible(true);
                }
            } else {
                lblSelectCourse.setVisible(true);
            }
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadCourseSubjectDetailJOIN(String courseId) throws SQLException, ClassNotFoundException {
        List<CourseSubjectDetailTM> list = courseSubjectDetailService.getCourseSubjectDetailList(courseId);
        //  ArrayList<CourseSubjectDetailTM> list = CourseSubjectDetailModel.getCourseSubjecDetailList(courseId);
        ObservableList<CourseSubjectDetailTM> observableList = FXCollections.observableArrayList();
        observableList.addAll(list);
        tblCourseSubjectDetail.setItems(observableList);
    }

    private CourseSubjectDetailDTO add() throws SQLException, ClassNotFoundException, RuntimeException {
        return courseSubjectDetailService.save(new CourseSubjectDetailDTO(cmbCourse.getSelectionModel().getSelectedItem(), cmbSubject.getSelectionModel().getSelectedItem()));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        try {
            Navigation.navigate(Routes.ACADEMIC_MANAGE_SUBJECTS, pane);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            if (tblCourseSubjectDetail.getSelectionModel().getSelectedItem() != null) {
                if (delete()) {
                    loadSubjectList();
                    loadCourseSubjectDetailJOIN(cmbCourse.getSelectionModel().getSelectedItem());
                    new Alert(Alert.AlertType.INFORMATION, "DELETED").showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Select Item First!").show();
            }
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    private boolean delete() throws SQLException, ClassNotFoundException {
        CourseSubjectDetailDTO courseSubjectDetailDTO = courseSubjectDetailService.delete(new CourseSubjectDetailDTO(tblCourseSubjectDetail.getSelectionModel().getSelectedItem().getCourseId(), tblCourseSubjectDetail.getSelectionModel().getSelectedItem().getSubjectId()));
        return courseSubjectDetailDTO != null;
    }

    @FXML
    void cmbCourseOnAction(ActionEvent event) {
        try {
            if (cmbCourse.getSelectionModel().getSelectedItem() != null) {
                CourseDTO courseDTO = courseService.view(new CourseDTO(cmbCourse.getSelectionModel().getSelectedItem()));
                lblCourseName.setText(courseDTO.getName());
                loadCourseSubjectDetailJOIN(cmbCourse.getSelectionModel().getSelectedItem());
            }
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbSubjectOnAction(ActionEvent event) {
        try {
            if (cmbSubject.getSelectionModel().getSelectedItem() != null) {
                lblSubjectName.setText(
                        subjectService.getSubjectName(new SubjectDTO(cmbSubject.getSelectionModel().getSelectedItem())));
            } else {
                lblSubjectName.setText("");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            subjectService = ServiceFactory.getInstance().getService(ServiceTypes.SUBJECT);
            courseService = ServiceFactory.getInstance().getService(ServiceTypes.COURSE);
            courseSubjectDetailService = ServiceFactory.getInstance().getService(ServiceTypes.COURSE_SUBJECT_DETAIL);

            lblSelectCourse.setVisible(false);
            lblSelectSubject.setVisible(false);
            lblCourseName.setText("");
            lblSubjectName.setText("");

            colSubID.setCellValueFactory(new PropertyValueFactory<>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colHours.setCellValueFactory(new PropertyValueFactory<>("hours"));

            colSubjectID.setCellValueFactory(new PropertyValueFactory<>("subjectId"));
            colCourseID.setCellValueFactory(new PropertyValueFactory<>("courseId"));
            colSubjectName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));

            loadSubjectTable();

            loadCoursesList();
            loadSubjectList();
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadSubjectList() throws SQLException, ClassNotFoundException, RuntimeException {
        List<SubjectDTO> subjectList = subjectService.getSubjectList();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (SubjectDTO ele : subjectList) {
            observableList.add(ele.getId());
        }
        cmbSubject.setItems(observableList);
    }

    public void loadCoursesList() throws SQLException, ClassNotFoundException, RuntimeException {
        List<CourseDTO> courseList = courseService.getCourseList();
        //ArrayList<Course> courseArrayList = CourseModel.getCourseList();
        ObservableList<String> observableArrayList = FXCollections.observableArrayList();
        for (CourseDTO ele : courseList) {
            observableArrayList.add(ele.getId());
        }
        cmbCourse.setItems(observableArrayList);
    }

    public void loadSubjectTable() throws SQLException, ClassNotFoundException, RuntimeException {
        List<SubjectDTO> list = subjectService.getSubjectList();
        //  ArrayList<Subject> list = SubjectModel.getSubjectList();
        ObservableList<SubjectTM> observableList = FXCollections.observableArrayList();
        for (SubjectDTO ele : list) {
            observableList.add(new SubjectTM(ele.getId(), ele.getName(), ele.getHours()));
        }
        tblSubjects.setItems(observableList);
    }

    public void cmbSubjectOnMouseClicked(MouseEvent mouseEvent) {
        lblSelectSubject.setVisible(false);
    }

    public void cmbCourseOnMouseClicked(MouseEvent mouseEvent) {
        cmbSubject.setDisable(!cmbCourse.isFocused());
        lblSelectCourse.setVisible(false);
    }
}
