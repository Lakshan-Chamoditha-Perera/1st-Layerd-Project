package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import lk.ijse.studentsmanagement.dto.SubjectDTO;
import lk.ijse.studentsmanagement.dto.tblModels.SubjectTM;
import lk.ijse.studentsmanagement.entity.Subject;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.SubjectService;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AcademicManageSubjectsFormController implements Initializable {

    public Label lblInvalidName;
    public Label lblInvalidHour;
    public JFXButton btnAddSubjectToCourse;
    public Label lblSubID;
    SubjectService subjectService;
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
                    SubjectDTO add = add();
                    if (add != null){
                        new Alert(Alert.AlertType.INFORMATION, "ADDED").showAndWait();
                        Navigation.navigate(Routes.ACADEMIC_MANAGE_SUBJECTS, pane);
                    }
                } else {
                    lblInvalidHour.setVisible(true);
                    txtSubHours.setFocusColor(javafx.scene.paint.Color.RED);
                }
            } else {
                lblInvalidName.setVisible(true);
                txtSubName.setFocusColor(javafx.scene.paint.Color.RED);
            }

        } catch (SQLException | ClassNotFoundException | IOException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private SubjectDTO add() throws SQLException, ClassNotFoundException, RuntimeException {
        return subjectService.save(
                new SubjectDTO(
                        lblSubID.getText(),
                        txtSubName.getText(),
                        txtSubHours.getText())
        );
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            if (!tblSubjects.getSelectionModel().isEmpty()) {
                SubjectTM selectedItem = tblSubjects.getSelectionModel().getSelectedItem();
                if (delete(selectedItem) != null) {
                    new Alert(Alert.AlertType.INFORMATION, "DELETED").showAndWait();
                    Navigation.navigate(Routes.ACADEMIC_MANAGE_SUBJECTS, pane);
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Add values First").show();
            }
        } catch (SQLException | ClassNotFoundException | IOException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    private SubjectDTO delete(SubjectTM selectedItem) throws SQLException, ClassNotFoundException {
        SubjectDTO subjectDTO = subjectService.delete(new SubjectDTO(selectedItem.getId()));
        return subjectDTO;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            subjectService = ServiceFactory.getInstance().getService(ServiceTypes.SUBJECT);

            lblInvalidHour.setVisible(false);
            lblInvalidName.setVisible(false);

            colID.setCellValueFactory(new PropertyValueFactory<>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colHours.setCellValueFactory(new PropertyValueFactory<>("hours"));

            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            generateSubjectID();
            refreshTable();
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void generateSubjectID() throws SQLException, ClassNotFoundException, RuntimeException {
        Subject subject = subjectService.getLastSubjectID();
        if (subject == null) {
            lblSubID.setText("ITS0001");
        } else {
            String id = subject.getId();
            String[] split = id.split("[I][T][S]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            lblSubID.setText(String.format("ITS%04d", lastDigits));
        }
    }

    private void refreshTable() throws SQLException, ClassNotFoundException, RuntimeException {
        loadSubjectTable();
    }

    private void loadSubjectTable() throws SQLException, ClassNotFoundException, RuntimeException {
        List<SubjectDTO> subjectList = subjectService.getSubjectList();
        //  ArrayList<Subject> list = SubjectModel.getSubjectList();
        ObservableList<SubjectTM> observableList = FXCollections.observableArrayList();
        for (SubjectDTO subjectDTO : subjectList) {
            observableList.add(
                    new SubjectTM(
                            subjectDTO.getId(),
                            subjectDTO.getName(),
                            subjectDTO.getHours()
                    )
            );
        }
        tblSubjects.setItems(observableList);
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
