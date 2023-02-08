package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lk.ijse.studentsmanagement.dto.IQTestDTO;
import lk.ijse.studentsmanagement.dto.InquiryDTO;
import lk.ijse.studentsmanagement.dto.InquiryIQTestDetailDTO;
import lk.ijse.studentsmanagement.dto.TestPaymentDTO;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.InquiryIqTestDetailService;
import lk.ijse.studentsmanagement.service.custom.InquiryService;
import lk.ijse.studentsmanagement.service.custom.IqTestService;
import lk.ijse.studentsmanagement.service.custom.TestPaymentService;
import lk.ijse.studentsmanagement.dto.tblModels.IQTestTM;
import lk.ijse.studentsmanagement.dto.tblModels.InquiryIQTestDetailTM;
import lk.ijse.studentsmanagement.util.RegExPatterns;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class IQTestsFormController implements Initializable {
    public TableView tblIQTestDetail;
    public TableColumn colTestID;
    public TableColumn colTestDate;
    public TableColumn colTestTime;
    public TableColumn colTestLab;
    public JFXButton btnPayment;
    public TableColumn colStdID;
    public TableView tblIQTestInquiryDetail;
    public TableColumn colExamID;
    public TableColumn colResults;
    public TableColumn colAmount;
    public Label lblPaymentID;
    public Label lblInvalidStdID;
    public Label lblSelectExamID;
    public JFXTextField txtStudentID;
    public JFXComboBox cmbExamDate;
    IqTestService iqTestService;
    InquiryIqTestDetailService inquiryIqTestDetailService;
    TestPaymentService testPaymentService;
    InquiryService inquiryService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            lblInvalidStdID.setVisible(false);
            lblSelectExamID.setVisible(false);

            iqTestService = ServiceFactory.getInstance().getService(ServiceTypes.IQTEST);
            inquiryIqTestDetailService = ServiceFactory.getInstance().getService(ServiceTypes.INQUIRY_IQTEST_DETAIL);
            testPaymentService = ServiceFactory.getInstance().getService(ServiceTypes.TEST_PAYMENTS);
            inquiryService = ServiceFactory.getInstance().getService(ServiceTypes.INQUIRY);

            addIQTestDetail();
            addInquiryIQTestDetail();
            setLblPaymentID();
            loadIQExamDatesComboBox();
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
//            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadIQExamDatesComboBox() throws SQLException, ClassNotFoundException {
        List<IQTestDTO> iqTestList = iqTestService.getIQTestList();
        ObservableList<String> observableArrayList = FXCollections.observableArrayList();
        for (IQTestDTO ele : iqTestList) {
            observableArrayList.add(ele.getDate().toString());
        }
        cmbExamDate.setItems(observableArrayList);
    }

    private void addInquiryIQTestDetail() throws SQLException, ClassNotFoundException, RuntimeException {
        setLblPaymentID();

        colExamID.setCellValueFactory(new PropertyValueFactory<>("testId"));
        colResults.setCellValueFactory(new PropertyValueFactory<>("result"));
        colStdID.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        List<InquiryIQTestDetailDTO> inquiryIQTestDetailList = inquiryIqTestDetailService.getInquiryIQTestList();
        ObservableList<InquiryIQTestDetailTM> observableArrayList = FXCollections.observableArrayList();
        for (InquiryIQTestDetailDTO ele : inquiryIQTestDetailList) {
            observableArrayList.add(
                    new InquiryIQTestDetailTM(
                            ele.getStudentId(),
                            ele.getTestId(),
                            ele.getResult()
                    )
            );
        }
        tblIQTestInquiryDetail.setItems(observableArrayList);
    }

    public void setLblPaymentID() throws SQLException, ClassNotFoundException, RuntimeException {
        String lastTestPaymentID = testPaymentService.getLastPaymentID();
        if (lastTestPaymentID == null) {
            lblPaymentID.setText("T00001");
        } else {
            String[] split = lastTestPaymentID.split("[T]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            lblPaymentID.setText(String.format("T%05d", lastDigits));
        }
    }

    private void addIQTestDetail() throws SQLException, ClassNotFoundException {
        colTestID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTestDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTestTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colTestLab.setCellValueFactory(new PropertyValueFactory<>("lab"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        List<IQTestDTO> iqTestList = iqTestService.getIQTestList();
        ObservableList<IQTestTM> list = FXCollections.observableArrayList();
        for (IQTestDTO ele : iqTestList) {
            list.add(new IQTestTM(
                    ele.getId(),
                    ele.getDate(),
                    ele.getTime(),
                    ele.getLab(),
                    ele.getAmount()
            ));
        }
        tblIQTestDetail.setItems(list);
    }

    public void cmbExamIDOnMouseClicked(MouseEvent mouseEvent) {
        lblSelectExamID.setVisible(false);
    }

    public void btnPaymentOnAction(ActionEvent actionEvent) {
        try {
            if (RegExPatterns.getIdPattern().matcher(txtStudentID.getText()).matches()) {
                if (isExists(txtStudentID.getText())) {
                    if (cmbExamDate.getValue() != null) {
                        boolean isAdded = registerToNewIQTest();
                        String text = (isAdded) ? "added" : "error";
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, text);
                        alert.show();
                        addInquiryIQTestDetail();
                        if (isAdded) {
                            ObservableList<IQTestTM> items = tblIQTestDetail.getItems();
                            IQTestTM iqTest = new IQTestTM();
                            for (IQTestTM ele : items) {
                                if (String.valueOf(ele.getDate()).equals(cmbExamDate.getValue().toString())) {
                                    iqTest.setAmount(ele.getAmount());
                                    iqTest.setId(ele.getId());
                                    iqTest.setTime(ele.getTime());
                                    iqTest.setLab(ele.getLab());
                                    iqTest.setDate(ele.getDate());
                                }
                            }
//                            String msg = "\t \t \t WELCOME TO INSTITUTE OF JAVA AND SOFTWARE ENGINEERING \n" +
//                                    "\nPayment ID  " + lblPaymentID.getText() +
//                                    "                Payment Date " + LocalDate.now() +
//                                    "\nNIC   :" + txtStudentID.getText() +
//                                    "\nTotal Amount = Rs." + iqTest.getAmount() +
//                                    "\n \n----YOUR TEST DETAILS----" +
//                                    "\nTEST ID    : " + iqTest.getId() +
//                                    "\nDate       : " + iqTest.getDate() +
//                                    "\nLab        : " + iqTest.getLab() +
//                                    "\nStart Time : " + iqTest.getTime() +
//                                    "\n\nThank You!...";
//
//                            InquiryDTO inquiryDTO = inquiryService.getEmail(new InquiryDTO(txtStudentID.getText()));
//                            if (inquiryDTO != null) Mail.outMail(msg, inquiryDTO.getEmail(), "OFFICIAL INQUIRY PAYMENT RECEIPT - INSTITUTE OF JAVA AND SOFTWARE ENGINEERING ");
                        }
                    } else {
                        lblSelectExamID.setVisible(true);
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Student not exists. Please register first").show();
                }
            } else {
                txtStudentID.setFocusColor(Color.RED);
                lblInvalidStdID.setVisible(true);
            }
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean isExists(String studentID) throws SQLException, ClassNotFoundException {
        return inquiryService.view(new InquiryDTO(studentID)) != null;
    }

    private boolean registerToNewIQTest() throws SQLException, ClassNotFoundException, RuntimeException {
        IQTestDTO iqTestDetails = iqTestService.getIQTestDetailsByDate(cmbExamDate.getValue().toString());
        //added
        return testPaymentService.addTestPaymentTransaction(
                new TestPaymentDTO(
                        lblPaymentID.getText(),
                        txtStudentID.getText(),
                        Date.valueOf(LocalDate.now()),
                        "Test Payment",
                        iqTestDetails.getAmount(),
                        iqTestDetails.getId(),
                        new InquiryIQTestDetailDTO(
                                txtStudentID.getText(),
                                iqTestDetails.getId(),
                                "not-added"
                        )
                )
        ) != null;
    }

    public void txtStudentIDOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidStdID.setVisible(false);
    }
}