package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import lk.ijse.studentsmanagement.dto.IQTestDTO;
import lk.ijse.studentsmanagement.dto.InquiryDTO;
import lk.ijse.studentsmanagement.dto.InquiryIQTestDetailDTO;
import lk.ijse.studentsmanagement.dto.TestPaymentDTO;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.InquiryService;
import lk.ijse.studentsmanagement.service.custom.IqTestService;
import lk.ijse.studentsmanagement.service.custom.TestPaymentService;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.util.Routes;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class AddInquiryFormController implements Initializable {
    public JFXComboBox<String> cmbExamDates;
    public Label lblInvalidID;
    public Label lblDate;
    public Label lblPaymentID;
    public Label lblTestID;
    public Label lblTestLab;
    public Label lblTestTime;
    public Label lblAmount;
    IqTestService iqTestService;
    TestPaymentService testPaymentService;
    InquiryService inquiryService;
    @FXML
    private Label lblInvalidName;
    @FXML
    private Label lblInvalidEmail;
    @FXML
    private Label lblInvalidCity;
    @FXML
    private Label lblInvalidMobile;
    @FXML
    private AnchorPane pane;
    @FXML
    private JFXRadioButton rBtnMale;
    @FXML
    private ToggleGroup gender;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtMobile;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtCity;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton btnAddIQTest;

    @FXML
    void backClickOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.INQUIRIES, pane);
    }

    @FXML
    void cmbExamDateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        lblDate.setVisible(false);
        IQTestDTO iqTestDetails = iqTestService.getIQTestDetailsByDate(cmbExamDates.getValue());
        if (iqTestDetails != null) {
            lblTestID.setText(iqTestDetails.getId());
            lblTestLab.setText(iqTestDetails.getLab());
            lblTestTime.setText(iqTestDetails.getTime().toString());
            lblAmount.setText(String.valueOf(iqTestDetails.getAmount()));
        }
    }

    @FXML
    void txtCityOnMouseClicked(MouseEvent event) {
        lblInvalidCity.setVisible(false);
    }

    @FXML
    void txtEmailOnMouseClicked(MouseEvent event) {
        lblInvalidEmail.setVisible(false);
    }

    @FXML
    void txtIdOnMouseClicked(MouseEvent event) {
        lblInvalidID.setVisible(false);
    }

    @FXML
    void txtMobileOnMouseClicked(MouseEvent event) {
        lblInvalidMobile.setVisible(false);
    }

    @FXML
    void txtNameOnMouseClicked(MouseEvent event) {
        lblInvalidName.setVisible(false);
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            if (RegExPatterns.getIdPattern().matcher(txtId.getText()).matches()) {
                if (RegExPatterns.getNamePattern().matcher(txtName.getText()).matches()) {
                    if (RegExPatterns.getEmailPattern().matcher(txtEmail.getText()).matches()) {
                        if (RegExPatterns.getCityPattern().matcher(txtCity.getText()).matches()) {
                            if (RegExPatterns.getMobilePattern().matcher(txtMobile.getText()).matches()) {
                                if (cmbExamDates.getValue() != null) {
                                    add();
                                } else {
                                    lblDate.setVisible(true);
                                }
                            } else {
                                txtMobile.setFocusColor(Color.valueOf("RED"));
                                lblInvalidMobile.setVisible(true);
                            }
                        } else {
                            txtCity.setFocusColor(Color.valueOf("RED"));
                            lblInvalidCity.setVisible(true);
                        }
                    } else {
                        txtEmail.setFocusColor(Color.valueOf("RED"));
                        lblInvalidEmail.setVisible(true);
                    }
                } else {
                    txtName.setFocusColor(Color.valueOf("RED"));
                    lblInvalidName.setVisible(true);
                }
            } else {
                txtId.setFocusColor(Color.valueOf("RED"));
                lblInvalidID.setVisible(true);
            }
        } catch (SQLException | ClassNotFoundException | RuntimeException | IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void add() throws SQLException, ClassNotFoundException, RuntimeException, IOException {
        InquiryIQTestDetailDTO inquiryIQTestDetail = new InquiryIQTestDetailDTO(txtId.getText(), lblTestID.getText(), "not added", txtName.getText());
        TestPaymentDTO testPayment = new TestPaymentDTO(lblPaymentID.getText(), txtId.getText(), java.sql.Date.valueOf(LocalDate.now()), "Test Payment", Double.parseDouble(lblAmount.getText()), lblTestID.getText(), inquiryIQTestDetail);
        InquiryDTO inquiry = new InquiryDTO(txtId.getText(), txtName.getText(), txtCity.getText(), txtMobile.getText(), txtEmail.getText(), new SimpleDateFormat("dd-MM-20yy").format(new Date()), (rBtnMale.isSelected()) ? "Male" : "Female", "not-registered", testPayment);
        InquiryDTO inquiryDTO = inquiryService.save(inquiry);
        if (inquiryDTO != null) {
            // printReport();
//            String msg2 = "This email and any attachment transmitted herewith are confidential and is intended solely for the use of the individual or entity to which they are addressed and may contain information that is privileged or otherwise protected from disclosure. If you are not the intended recipient, you are hereby notified that disclosing, copying, distributing, or taking any action in reliance on this email and the information it contains is strictly prohibited. If you have received this email in error, please notify the sender immediately by reply email and discard all of its contents by deleting this email and the attachment, if any, from your system";
//            String msg = "\t \t \t WELCOME TO INSTITUTE OF JAVA AND SOFTWARE ENGINEERING \n" + "\nPayment ID  " + lblPaymentID.getText() + "                Payment Date " + LocalDate.now() + "\nNIC   :" + txtId.getText() + "\nTotal Amount = Rs." + Double.parseDouble(lblAmount.getText()) + "  PAID\n \n----YOUR TEST DETAILS----" + "\nTEST ID    : " + lblTestID.getText() + "\nDate       : " + cmbExamDates.getValue() + "\nLab        : " + lblTestLab.getText() + "\nStart Time : " + lblTestTime.getText() + "\n\nThank You!...\n\n\n\n\n\n" + msg2;
//            try {
//                     Mail.outMail(msg, txtEmail.getText(), "OFFICIAL INQUIRY PAYMENT RECEIPT - INSTITUTE OF JAVA AND SOFTWARE ENGINEERING ");
//            } catch (MessagingException e) {
//               throw new RuntimeException(e);
//            }
            new Alert(Alert.AlertType.INFORMATION, "Your Registration Succeed!").show();
            Navigation.navigate(Routes.ADD_STUDENT, pane);
        }
    }

    private void printReport() {
        HashMap hashMap = new HashMap<>();

        hashMap.put("receptNo", lblPaymentID.getText());
        hashMap.put("nic", txtId.getText());
        hashMap.put("name", txtName.getText());
        hashMap.put("testDate", lblDate.getText());
        hashMap.put("time", lblTestTime.getText());
        hashMap.put("lab", lblTestLab.getText());
        hashMap.put("total", lblAmount.getText());
        try {
            JasperReport compileReport = JasperCompileManager.compileReport(JRXmlLoader.load(getClass().getResourceAsStream("/OfficialReceiptNew.jrxml")));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, hashMap, new JREmptyDataSource());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.INFORMATION, String.valueOf(e)).show();
        }
    }

    private void clearAll() throws SQLException, ClassNotFoundException {
        txtId.clear();
        lblTestID.setText(null);
        //  lblPaymentID.setText(null);
        txtId.clear();
        lblAmount.setText(null);
        txtName.clear();
        txtCity.clear();
        txtEmail.clear();
        txtMobile.clear();
        lblTestLab.setText(null);
        lblTestTime.setText(null);
        setLblPaymentID();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            iqTestService = ServiceFactory.getInstance().getService(ServiceTypes.IQTEST);
            testPaymentService = ServiceFactory.getInstance().getService(ServiceTypes.TEST_PAYMENTS);
            inquiryService = ServiceFactory.getInstance().getService(ServiceTypes.INQUIRY);
            setLabelVisible();
            loadIQExamDatesComboBox();
            setLblPaymentID();
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setLabelVisible() {
        lblInvalidID.setVisible(false);
        lblInvalidCity.setVisible(false);
        lblInvalidEmail.setVisible(false);
        lblInvalidName.setVisible(false);
        lblInvalidMobile.setVisible(false);
        lblDate.setVisible(false);
    }

    public void loadIQExamDatesComboBox() throws SQLException, ClassNotFoundException, RuntimeException {
        List<IQTestDTO> iqTestList = iqTestService.getIQTestList();
        ObservableList<String> observableArrayList = FXCollections.observableArrayList();
        for (IQTestDTO ele : iqTestList) observableArrayList.add(ele.getDate().toString());
        cmbExamDates.setItems(observableArrayList);
    }

    public void setLblPaymentID() throws SQLException, ClassNotFoundException {
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
}