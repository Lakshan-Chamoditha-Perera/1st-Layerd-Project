package lk.ijse.studentsmanagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.comboLoad.ComboLoader;
import lk.ijse.studentsmanagement.comboLoad.TableLoader;
import lk.ijse.studentsmanagement.model.CourseModel;
import lk.ijse.studentsmanagement.model.CourseSubjectDetailModel;
import lk.ijse.studentsmanagement.model.SubjectModel;
import lk.ijse.studentsmanagement.tblModels.CourseSubjectDetailTM;
import lk.ijse.studentsmanagement.tblModels.SubjectTM;
import lk.ijse.studentsmanagement.entity.Course;
import lk.ijse.studentsmanagement.entity.CourseSubjectDetail;
import lk.ijse.studentsmanagement.entity.Subject;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AcademicAddSubjectToCourseFormController implements Initializable {

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
                    boolean isAdded = add();
                    if (isAdded) {
                        new Alert(Alert.AlertType.INFORMATION, "ADDED").showAndWait();
                        ComboLoader.loadSubjectList(cmbSubject);
                        TableLoader.loadCourseSubjectDetailJOIN(tblCourseSubjectDetail, cmbCourse.getSelectionModel().getSelectedItem());
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                    }
                } else {
                    lblSelectSubject.setVisible(true);
                }
            } else {
                lblSelectCourse.setVisible(true);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private boolean add() throws SQLException, ClassNotFoundException {
        return CourseSubjectDetailModel.addCourseSubjectDetail(
                new CourseSubjectDetail(
                        cmbCourse.getSelectionModel().getSelectedItem(),
                        cmbSubject.getSelectionModel().getSelectedItem()
                )
        );
    }

    @FXML
    void btnBackOnAction(ActionEvent event){
        try {
            Navigation.navigate(Routes.ACADEMIC_MANAGE_SUBJECTS,pane);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            if (tblCourseSubjectDetail.getSelectionModel().getSelectedItem() != null) {
               // boolean isDeleted = ;
                if (delete()) {
                    ComboLoader.loadSubjectList(cmbSubject);
                    TableLoader.loadCourseSubjectDetailJOIN(tblCourseSubjectDetail, cmbCourse.getSelectionModel().getSelectedItem());
                    new Alert(Alert.AlertType.INFORMATION, "DELETED").showAndWait();
                } else {
                    new Alert(Alert.AlertType.ERROR, "not deleted!").show();
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Select Item First!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private boolean delete() throws SQLException, ClassNotFoundException {
        return CourseSubjectDetailModel.deleteCourseSubjectDetail(
                new CourseSubjectDetail(
                        tblCourseSubjectDetail.getSelectionModel().getSelectedItem().getCourseId(),
                        tblCourseSubjectDetail.getSelectionModel().getSelectedItem().getSubjectId()
                )
        );
    }

    @FXML
    void cmbCourseOnAction(ActionEvent event) {
        try {
            if (cmbCourse.getSelectionModel().getSelectedItem() != null) {
                lblCourseName.setText(
                        CourseModel.getCourseDetail(
                                new Course(
                                        cmbCourse.getSelectionModel().getSelectedItem()
                                )
                        ).getName()
                );
                TableLoader.loadCourseSubjectDetailJOIN(tblCourseSubjectDetail, cmbCourse.getSelectionModel().getSelectedItem());
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    @FXML
    void cmbSubjectOnAction(ActionEvent event) {
        try {
            if (cmbSubject.getSelectionModel().getSelectedItem() != null) {
                lblSubjectName.setText(
                        SubjectModel.getSubjectName(
                                new Subject(
                                        cmbSubject.getSelectionModel().getSelectedItem()
                                )
                        )
                );
            } else {
                lblSubjectName.setText("");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        try {
            boolean isAdded = TableLoader.loadSubjectTable(tblSubjects);
            if(!isAdded){
                new Alert(Alert.AlertType.INFORMATION,"Subjects Not added yet").show();
            }
            ComboLoader.loadCoursesList(cmbCourse);
            ComboLoader.loadSubjectList(cmbSubject);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public void cmbSubjectOnMouseClicked(MouseEvent mouseEvent) {
        lblSelectSubject.setVisible(false);
    }

    public void cmbCourseOnMouseClicked(MouseEvent mouseEvent) {
        cmbSubject.setDisable(!cmbCourse.isFocused());
        lblSelectCourse.setVisible(false);
    }
}
