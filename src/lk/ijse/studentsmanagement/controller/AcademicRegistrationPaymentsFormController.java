package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.comboLoad.TableLoader;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.tblModels.PaymentsTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AcademicRegistrationPaymentsFormController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<PaymentsTM> tblPayments;

    @FXML
    private TableColumn<?, ?> colPaymentID;

    @FXML
    private TableColumn<?, ?> colRegistrationID;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colRemark;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private JFXButton btnPrint;

    @FXML
    private Button btnSearch;

    @FXML
    void btnPrintOnAction(ActionEvent event) {
        printReport();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }
    private void printReport() {

        try {
            JasperReport compileReport = JasperCompileManager.compileReport(
                    JRXmlLoader.load(
                            getClass().getResourceAsStream(
                                    "/lk/ijse/studentsmanagement/report/RegistrationPaymentReport.jrxml"
                            )
                    )
            );
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,null, DBconnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (SQLException | ClassNotFoundException |JRException e) {
            new Alert(Alert.AlertType.ERROR, e+"").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colPaymentID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRegistrationID.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colRemark.setCellValueFactory(new PropertyValueFactory<>("remark"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        boolean isLoaded = false;
        try {
            isLoaded = TableLoader.loadAllPayments(tblPayments);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
        if(!isLoaded){
            new Alert(Alert.AlertType.INFORMATION, "Payments Not added yet").show();
        }
    }
}
