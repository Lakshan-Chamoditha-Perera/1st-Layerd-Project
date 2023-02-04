package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.TestPaymentDTO;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.TestPaymentService;
import lk.ijse.studentsmanagement.tblModels.TestPaymentsTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AcademicInqauiryPaymentsFormController implements Initializable {

    TestPaymentService testPaymentService;
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
    void btnPrintOnAction(ActionEvent event) {
        //  printReport();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRemark.setCellValueFactory(new PropertyValueFactory<>("remark"));
        colPaymentID.setCellValueFactory(new PropertyValueFactory<>("id"));

        try {
            testPaymentService = ServiceFactory.getInstance().getService(ServiceTypes.TEST_PAYMENTS);
            loadTestPayments();
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadTestPayments() throws SQLException, ClassNotFoundException {
        List<TestPaymentDTO> list = testPaymentService.getAllPayments();
        //    ArrayList<TestPayment> list = TestPaymentModel.getAllPayments();
        ObservableList<TestPaymentsTM> observableList = FXCollections.observableArrayList();
        for (TestPaymentDTO testPaymentDTO : list)
            observableList.add(new TestPaymentsTM(testPaymentDTO.getId(), testPaymentDTO.getStudentID(), testPaymentDTO.getDate(), testPaymentDTO.getRemark(), testPaymentDTO.getAmount()));

        tblPayments.setItems(observableList);
    }

    private void printReport() {
        try {
            JasperReport compileReport = JasperCompileManager.compileReport(JRXmlLoader.load(getClass().getResourceAsStream("/IQTestPaymentReport.jrxml")));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBconnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (SQLException | ClassNotFoundException | JRException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
