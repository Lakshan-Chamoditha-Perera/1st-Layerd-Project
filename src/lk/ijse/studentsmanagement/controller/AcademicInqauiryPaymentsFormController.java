package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.comboLoad.TableLoader;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.tblModels.TestPaymentsTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AcademicInqauiryPaymentsFormController implements Initializable {

    public AreaChart graph;
    public TextField txtStdID;
    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<TestPaymentsTM> tblPayments;

    @FXML
    private TableColumn<?, ?> colPaymentID;

    @FXML
    private TableColumn<?, ?> colStudentID;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colRemark;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRemark.setCellValueFactory(new PropertyValueFactory<>("remark"));
        colPaymentID.setCellValueFactory(new PropertyValueFactory<>("id"));

        try {
            boolean loadTestPayments = TableLoader.loadTestPayments(tblPayments);
            if (!loadTestPayments) {
                new Alert(Alert.AlertType.INFORMATION, "Test Payments Not added yet").show();
            }else{
            //    graph.set
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }
    private void printReport() {

        try {
            JasperReport compileReport = JasperCompileManager.compileReport(
                    JRXmlLoader.load(
                            getClass().getResourceAsStream(
                                    "/lk/ijse/studentsmanagement/report/IQTestPaymentReport.jrxml"
                            )
                    )
            );
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,null, DBconnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.INFORMATION, String.valueOf(e)).show();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e+"").show();
        }
    }
}
