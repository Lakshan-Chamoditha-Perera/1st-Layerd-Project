package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.PaymentDTO;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.PaymentService;
import lk.ijse.studentsmanagement.tblModels.PaymentsTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AcademicRegistrationPaymentsFormController implements Initializable {

    PaymentService paymentService;
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
                                    "/RegistrationPaymentReportNew.jrxml"
                            )
                    )
            );
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBconnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (SQLException | ClassNotFoundException | JRException e) {
            new Alert(Alert.AlertType.ERROR, e + "").show();
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
        try {
            paymentService = ServiceFactory.getInstance().getService(ServiceTypes.PAYMENTS);
            loadAllPayments();
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadAllPayments() throws SQLException, ClassNotFoundException {
        List<PaymentDTO> paymentDTOS = paymentService.loadAllPayments();
        //  ArrayList<Payment> paymentArrayList = PaymentModel.getAllPayments();
        ObservableList<PaymentsTM> observableList = FXCollections.observableArrayList();
        for (PaymentDTO ele : paymentDTOS) {
            observableList.add(new PaymentsTM(
                    ele.getId(),
                    ele.getRegistrationId(),
                    ele.getType(),
                    ele.getRemark(),
                    ele.getAmount(),
                    ele.getDate()
            ));
        }
        tblPayments.setItems(observableList);
    }
}
