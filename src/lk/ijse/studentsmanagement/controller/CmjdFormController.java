package lk.ijse.studentsmanagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.comboLoad.ComboLoader;
import lk.ijse.studentsmanagement.comboLoad.TableLoader;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CmjdFormController implements Initializable {
    public AnchorPane pane;

    public void backClickOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.COURSES,pane);
    }

    @FXML
    private ComboBox<String> cmbCMJD;

    @FXML
    private TableView<?> tblOnGoingBatches;

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

    @FXML
    private TableView<?> tblCMJD;

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
    void cmbOnAction(ActionEvent event) {
        if(!cmbCMJD.getValue().isEmpty()){
            try {
                TableLoader.loadTableCourseBatch(tblCMJD,cmbCMJD.getValue(),"CMJD");
            } catch (SQLException | ClassNotFoundException e) {
                new Alert( Alert.AlertType.INFORMATION,String.valueOf(e));
            }
        }
        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn[] columns = {
                colId,
                colName,
                colMobile,
                colEmail,
                colStatus,
                colBatchId,
                colBatchNo,
                colFee,
                colStartDate,
                colMaxCount
        };
        TableLoader.setCellValues(columns);
        try {
            ComboLoader.LoadBatchIDS(cmbCMJD,"CMJD");
            TableLoader.setBatchTable(tblOnGoingBatches, "CMJD");
        } catch (SQLException | ClassNotFoundException e) {
            new Alert( Alert.AlertType.INFORMATION,String.valueOf(e));
        }
    }
}
