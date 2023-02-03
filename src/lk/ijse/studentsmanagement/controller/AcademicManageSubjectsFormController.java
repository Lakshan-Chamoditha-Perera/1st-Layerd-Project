package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.autogenerater.AutoGenerateID;
import lk.ijse.studentsmanagement.comboLoad.TableLoader;
import lk.ijse.studentsmanagement.model.SubjectModel;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.tblModels.SubjectTM;
import lk.ijse.studentsmanagement.entity.Subject;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AcademicManageSubjectsFormController implements Initializable {

    public Label lblInvalidName;
    public Label lblInvalidHour;
    public JFXButton btnAddSubjectToCourse;
    public Label lblSubID;
    @FXML
    private AnchorPane pane;

    @FXML
    private JFXTextField txtSubName;

    @FXML
    private JFXTextField txtSubHours;

    @FXML
    private TableView<SubjectTM> tblSubjects;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colHours;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    void btnAddSubjectOnAction(ActionEvent event) {
        try {
            if (RegExPatterns.getNamePattern().matcher(txtSubName.getText()).matches()) {
                if (txtSubHours.getText() != null) {
                    boolean isAdded = add();
                    if (isAdded) {
                        new Alert(Alert.AlertType.INFORMATION, "ADDED").showAndWait();
                        Navigation.navigate(Routes.ACADEMIC_MANAGE_SUBJECTS, pane);
                    } else {
                        new Alert(Alert.AlertType.INFORMATION, "Something went wrong").show();
                    }
                } else {
                    lblInvalidHour.setVisible(true);
                    txtSubHours.setFocusColor(javafx.scene.paint.Color.RED);
                }
            } else {
                lblInvalidName.setVisible(true);
                txtSubName.setFocusColor(javafx.scene.paint.Color.RED);
            }

        } catch (SQLException | ClassNotFoundException | IOException e) {
            System.out.println(e);
        }
    }

    private boolean add() throws SQLException, ClassNotFoundException {
        return SubjectModel.addSubject(
                new Subject(
                        lblSubID.getText(),
                        txtSubName.getText(),
                        txtSubHours.getText()
                )
        );
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            if (!tblSubjects.getSelectionModel().isEmpty()) {
                SubjectTM selectedItem = tblSubjects.getSelectionModel().getSelectedItem();
                boolean isDeleted = delete(selectedItem);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "DELETED").showAndWait();
                    Navigation.navigate(Routes.ACADEMIC_MANAGE_SUBJECTS, pane);
                } else {
                    new Alert(Alert.AlertType.ERROR, "ERROR").show();
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Add values First").show();
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            System.out.println(e);
        }
    }

    private boolean delete(SubjectTM selectedItem) throws SQLException, ClassNotFoundException {
        return SubjectModel.deleteSubject(
                new Subject(
                        selectedItem.getId()
                )
        );
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblInvalidHour.setVisible(false);
        lblInvalidName.setVisible(false);

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colHours.setCellValueFactory(new PropertyValueFactory<>("hours"));

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        try {
            AutoGenerateID.generateSubjectID(lblSubID);
            refreshTable();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        boolean isAdded = TableLoader.loadSubjectTable(tblSubjects);
        if (!isAdded) {
            new Alert(Alert.AlertType.INFORMATION, "Subjects Not added yet").show();
        }
    }
    public void txtLearningHoursOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidHour.setVisible(false);
    }
    public void txtNameMouseClicked(MouseEvent mouseEvent) {
        lblInvalidName.setVisible(false);
    }
    public void tblSubjectsOnMouseClicked(MouseEvent mouseEvent) {
        if (tblSubjects.getSelectionModel().getSelectedItem() != null) {
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void btnAddSubjectToCourseOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ACADEMIC_ADD_SUBJECT_TO_COURSE, pane);
    }
}
