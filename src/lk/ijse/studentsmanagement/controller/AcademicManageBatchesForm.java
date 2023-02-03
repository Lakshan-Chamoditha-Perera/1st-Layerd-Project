package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
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
import javafx.scene.paint.Color;
import lk.ijse.studentsmanagement.comboLoad.TableLoader;
import lk.ijse.studentsmanagement.model.BatchModel;
import lk.ijse.studentsmanagement.tblModels.BatchTM;
import lk.ijse.studentsmanagement.entity.Batch;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AcademicManageBatchesForm implements Initializable {

    public TableView<BatchTM> tableBatches;
    public TableColumn colBatchID;
    public TableColumn colBatchNo;
    public TableColumn colCourseId;
    public TableColumn colFee;
    public TableColumn colDate;
    public TableColumn colMaxCount;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public Label lblInvalidDate;
    public JFXDatePicker cmbDate;
    public Label lblInvalidCount;
    public JFXTextField txtCrowd;
    public Label lblInvalidAmount;
    public JFXTextField txtFee;
    @FXML
    private AnchorPane pane;

    @FXML
    void btnAddNewBatchOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.ACADEMIC_ADD_NEW_BATCH, pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colBatchID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBatchNo.setCellValueFactory(new PropertyValueFactory<>("batchNo"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("starting_date"));
        colMaxCount.setCellValueFactory(new PropertyValueFactory<>("maxStdCount"));

        try {
            TableLoader.loadAllBatches(tableBatches);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }

        lblInvalidAmount.setVisible(false);
        lblInvalidCount.setVisible(false);
        lblInvalidDate.setVisible(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            BatchTM selectedItem = tableBatches.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                boolean isDeleted = BatchModel.deleteBatch(new Batch(selectedItem.getId()));
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Deleted").showAndWait();
                    TableLoader.loadAllBatches(tableBatches);
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Something went Wrong!").show();
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Select Batch First!").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        BatchTM selectedItem = tableBatches.getSelectionModel().getSelectedItem();
        try {
            if (selectedItem != null) {
                if (RegExPatterns.getDoublePattern().matcher(txtFee.getText()).matches()) {
                    if (!cmbDate.getValue().isBefore(LocalDate.now())) {
                        if (RegExPatterns.getIntPattern().matcher(txtCrowd.getText()).matches()) {
                            boolean isUpdated = update();
                            if (isUpdated) {
                                new Alert(Alert.AlertType.INFORMATION, "Updated").showAndWait();
                                TableLoader.loadAllBatches(tableBatches);
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                            }
                        } else {
                            lblInvalidCount.setVisible(true);
                            txtCrowd.setFocusColor(Color.RED);
                        }
                    } else {
                        lblInvalidDate.setVisible(true);
                    }
                } else {
                    lblInvalidAmount.setVisible(true);
                    txtFee.setFocusColor(Color.RED);
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Select Batch First!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.INFORMATION, String.valueOf(e)).show();
        }
    }

    private boolean update() throws SQLException, ClassNotFoundException {
        BatchTM selectedItem = tableBatches.getSelectionModel().getSelectedItem();
        return BatchModel.updateBatchDetails(
                new Batch(
                        selectedItem.getId(),
                        Double.parseDouble(txtFee.getText()),
                        Date.valueOf(cmbDate.getValue()),
                        Integer.parseInt(txtCrowd.getText())
                )
        );
    }

    public void cmbDateOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidDate.setVisible(false);
    }

    public void txtMaxCroedOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidCount.setVisible(false);
    }

    public void txtFeeOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidAmount.setVisible(false);
    }

    public void tblOnMouseClicked(MouseEvent mouseEvent) {
        BatchTM selectedItem = tableBatches.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);

            txtCrowd.setText(String.valueOf(selectedItem.getMaxStdCount()));
            txtFee.setText(String.valueOf(selectedItem.getFee()));

            cmbDate.setValue(selectedItem.getStarting_date().toLocalDate());
        }
    }
}
