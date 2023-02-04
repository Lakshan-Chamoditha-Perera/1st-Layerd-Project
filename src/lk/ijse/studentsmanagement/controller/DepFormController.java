package lk.ijse.studentsmanagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.dto.BatchDTO;
import lk.ijse.studentsmanagement.dto.RegistrationDTO;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.BatchService;
import lk.ijse.studentsmanagement.service.custom.RegistrationService;
import lk.ijse.studentsmanagement.tblModels.BatchTM;
import lk.ijse.studentsmanagement.tblModels.RegistrationTM;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DepFormController implements Initializable {
    public AnchorPane pane;
    public TableView<BatchTM> tblOnGoingBatches;
    BatchService batchService;
    RegistrationService registrationService;
    @FXML
    private TableView<RegistrationTM> tblDep;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colMobile;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private TableColumn<?, ?> colStatus;
    @FXML
    private ComboBox<String> cmbDep;
    @FXML
    private TableColumn<?, ?> colBatchId;
    @FXML
    private TableColumn<?, ?> colBatchNo;
    @FXML
    private TableColumn<?, ?> colFee;
    @FXML
    private TableColumn<?, ?> colStartDate;
    @FXML
    private TableColumn<?, ?> colMaxCount;

    public void backClickOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.COURSES, pane);
    }

    public void cmbDepOnAction(ActionEvent actionEvent) {
        if (!cmbDep.getValue().isEmpty()) {
            try {
                loadTableCourseBatch(cmbDep.getValue(), "DEP");
            } catch (SQLException | ClassNotFoundException | RuntimeException e) {
                new Alert(Alert.AlertType.INFORMATION, String.valueOf(e)).show();
            }
        }
    }

    public void loadTableCourseBatch(String batch, String course) throws SQLException, ClassNotFoundException, RuntimeException {
        List<RegistrationDTO> registrationsArrayList = registrationService.getCourseBatchList(course, batch);
        //load students
        ObservableList<RegistrationTM> observableArrayList = FXCollections.observableArrayList();
        for (RegistrationDTO ele : registrationsArrayList) {
            observableArrayList.add(new RegistrationTM(ele.getRegistrationId(), ele.getName(), ele.getMobile(), ele.getEmail(), ele.getStatus()));
        }
        tblDep.setItems(observableArrayList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            batchService = ServiceFactory.getInstance().getService(ServiceTypes.BATCH);
            registrationService = ServiceFactory.getInstance().getService(ServiceTypes.REGISTRATION);

            colId.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            colBatchId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colBatchNo.setCellValueFactory(new PropertyValueFactory<>("batchNo"));
            colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
            colStartDate.setCellValueFactory(new PropertyValueFactory<>("starting_date"));
            colMaxCount.setCellValueFactory(new PropertyValueFactory<>("maxStdCount"));

            LoadBatchIDS("DEP");
            setBatchTable("DEP");
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.INFORMATION, String.valueOf(e)).show();
        }
    }

    public void LoadBatchIDS(String courseName) throws SQLException, ClassNotFoundException, RuntimeException {
        List<BatchDTO> list = batchService.getBatches(courseName);
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (BatchDTO ele : list) observableList.add(ele.getId());
        cmbDep.setItems(observableList);
    }

    public void setBatchTable(String course) throws SQLException, ClassNotFoundException, RuntimeException {
        //load batch table in specific course
        List<BatchDTO> batches = batchService.getBatches(course);
        ObservableList<BatchTM> observableListBatchTM = FXCollections.observableArrayList();
        for (BatchDTO ele : batches) {
            observableListBatchTM.add(new BatchTM(ele.getId(), ele.getBatchNo(), ele.getFee(), ele.getStarting_date(), ele.getMaxStdCount()));
        }
        tblOnGoingBatches.setItems(observableListBatchTM);
    }

}
