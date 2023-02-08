package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
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
import javafx.scene.paint.Color;
import lk.ijse.studentsmanagement.dto.BatchDTO;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.BatchService;
import lk.ijse.studentsmanagement.dto.tblModels.BatchTM;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
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
    BatchService batchService;
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
            batchService = ServiceFactory.getInstance().getService(ServiceTypes.BATCH);
            loadAllBatches();
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        lblInvalidAmount.setVisible(false);
        lblInvalidCount.setVisible(false);
        lblInvalidDate.setVisible(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    public void loadAllBatches() throws SQLException, ClassNotFoundException {
        List<BatchDTO> allBatch = batchService.getAll();
        //ArrayList<Batch> list = BatchModel.getAllBAtches();
        ObservableList<BatchTM> observableArrayList = FXCollections.observableArrayList();
        for (BatchDTO ele : allBatch) {
            observableArrayList.add(new BatchTM(ele.getId(), ele.getBatchNo(), ele.getCourseId(), ele.getFee(), ele.getStarting_date(), ele.getMaxStdCount()));
        }
        tableBatches.setItems(observableArrayList);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            BatchTM selectedItem = tableBatches.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                BatchDTO batchDTO = batchService.delete(new BatchDTO(selectedItem.getId()));
                //boolean isDeleted = BatchModel.deleteBatch(new Batch(selectedItem.getId()));
                if (batchDTO != null) {
                    new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
                    loadAllBatches();
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Select Batch First!").show();
            }
        } catch (ClassNotFoundException | SQLException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        BatchTM selectedItem = tableBatches.getSelectionModel().getSelectedItem();
        try {
            if (selectedItem != null) {
                if (RegExPatterns.getDoublePattern().matcher(txtFee.getText()).matches()) {
                    if (!cmbDate.getValue().isBefore(LocalDate.now())) {
                        if (RegExPatterns.getIntPattern().matcher(txtCrowd.getText()).matches()) {
                            update();
                            loadAllBatches();
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
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
        }
    }

    private void update() throws SQLException, ClassNotFoundException, RuntimeException {
        BatchTM selectedItem = tableBatches.getSelectionModel().getSelectedItem();
        BatchDTO batchDTO = batchService.updateBatchDetail(new BatchDTO(selectedItem.getId(), Double.parseDouble(txtFee.getText()), Date.valueOf(cmbDate.getValue()), Integer.parseInt(txtCrowd.getText())));
        new Alert(Alert.AlertType.INFORMATION, "Updated").show();
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
