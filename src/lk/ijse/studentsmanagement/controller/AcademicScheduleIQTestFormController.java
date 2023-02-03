package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
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
import javafx.scene.paint.Color;
import lk.ijse.studentsmanagement.autogenerater.AutoGenerateID;
import lk.ijse.studentsmanagement.comboLoad.TableLoader;
import lk.ijse.studentsmanagement.model.IQTestModel;
import lk.ijse.studentsmanagement.tblModels.IQTestTM;
import lk.ijse.studentsmanagement.entity.IQTest;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AcademicScheduleIQTestFormController implements Initializable {

    public JFXTextField txtLabId;
    public JFXDatePicker cmbDate;
    public JFXTimePicker cmbTime;
    public JFXTextField txtAmount;
    public TableView<IQTestTM> tblIqTest;
    public TableColumn colID;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colLab;
    public TableColumn colAmount;
    public Label lblId;
    public Label lblInvalidLab;
    public Label lblInvalidAmount;
    public Label lblInvalidDate;
    public Label lblInvalidTime;
    public JFXButton btnDelete;
    @FXML
    private AnchorPane pane;

    @FXML
    void backClickOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.EXAMS, pane);
    }

    @FXML
    void btnSheduleOnAction(ActionEvent event) {
        try {
            if (txtLabId.getText() != null) {
                if (RegExPatterns.getDoublePattern().matcher(txtAmount.getText()).matches()) {
                    if (!cmbDate.getValue().isBefore(LocalDate.now())) {
                        if (cmbTime.getValue() != null) {
                            boolean isAdded = IQTestModel.addIQTest(
                                    new IQTest(
                                            lblId.getText(),
                                            Date.valueOf(cmbDate.getValue()),
                                            Time.valueOf(cmbTime.getValue()),
                                            txtLabId.getText(),
                                            Double.parseDouble(txtAmount.getText())
                                    )
                            );
                            if (isAdded) {
                                new Alert(Alert.AlertType.INFORMATION, "Done").show();
                                Navigation.navigate(Routes.SCHEDULE_IQTEST, pane);
                            }
                        } else {
                            lblInvalidTime.setVisible(true);
                            new Alert(Alert.AlertType.ERROR, "Select Time").show();
                        }
                    } else {
                        lblInvalidDate.setVisible(true);
                        new Alert(Alert.AlertType.ERROR, "Select Date").show();
                    }
                } else {

                    lblInvalidAmount.setVisible(true);
                    txtAmount.setFocusColor(Color.RED);
                    new Alert(Alert.AlertType.ERROR, "Enter amount").show();
                }
            } else {
                lblInvalidLab.setVisible(true);
                txtLabId.setFocusColor(Color.RED);
                new Alert(Alert.AlertType.ERROR, "Enter Lab").show();
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnDelete.setDisable(true);

        lblInvalidTime.setVisible(false);
        lblInvalidLab.setVisible(false);
        lblInvalidDate.setVisible(false);
        lblInvalidAmount.setVisible(false);

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colLab.setCellValueFactory(new PropertyValueFactory<>("lab"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        try {
            AutoGenerateID.loadIQTestIDS(lblId);
            TableLoader.loadIQTests(tblIqTest);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public void cmbTimeOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidTime.setVisible(false);
    }

    public void txtAmountOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidAmount.setVisible(false);
    }

    public void txtLabOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidLab.setVisible(false);
    }

    public void cmbDateOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidTime.setVisible(false);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        IQTestTM selectedItem = tblIqTest.getSelectionModel().getSelectedItem();
        try {
            if (selectedItem != null) {
                boolean isDeleted = IQTestModel.deleteTest(new IQTest(selectedItem.getId()));
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Done").showAndWait();
                    Navigation.navigate(Routes.SCHEDULE_IQTEST, pane);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Select Exam First!").show();
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }

    public void tblIqTestOnMouseClicked(MouseEvent mouseEvent) {
        btnDelete.setDisable(tblIqTest.getSelectionModel().getSelectedItem() == null);

    }
}
