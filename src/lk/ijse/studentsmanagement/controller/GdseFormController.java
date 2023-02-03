package lk.ijse.studentsmanagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.comboLoad.ComboLoader;
import lk.ijse.studentsmanagement.comboLoad.TableLoader;
import lk.ijse.studentsmanagement.tblModels.RegistrationTM;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GdseFormController implements Initializable {
    public AnchorPane pane;
    public ComboBox<String> cmbGDSE;
    public TableColumn colBatchId;
    public TableColumn colBatchNo;
    public TableColumn colFee;
    public TableColumn colStartDate;
    public TableColumn colMaxCount;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colMobile;
    public TableColumn colEmail;
    public TableColumn colStatus;
    public TableView<RegistrationTM> tblGDSE;
    public TableView tblOnGoingBatches;

    public void backClickOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.COURSES, pane);
    }

    public void cmbOnAction(ActionEvent actionEvent) {
        if (!cmbGDSE.getValue().isEmpty()) {
            try {
                TableLoader.loadTableCourseBatch(tblGDSE, cmbGDSE.getValue(), "GDSE");
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.INFORMATION, String.valueOf(e));
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
            ComboLoader.LoadBatchIDS(cmbGDSE, "GDSE");
            TableLoader.setBatchTable(tblOnGoingBatches, "GDSE");
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.INFORMATION, String.valueOf(e));
        }


    }
}
