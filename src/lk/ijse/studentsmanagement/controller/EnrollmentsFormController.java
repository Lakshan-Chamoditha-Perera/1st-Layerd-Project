package lk.ijse.studentsmanagement.controller;

import com.google.zxing.WriterException;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import lk.ijse.studentsmanagement.dto.*;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.*;
import lk.ijse.studentsmanagement.service.util.mailService.Mail;
import lk.ijse.studentsmanagement.service.util.qr.QRGenerator;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.util.Routes;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class EnrollmentsFormController implements Initializable {

    public Label lblSelectCourse;
    public Label lblInvalidEmail;
    public Label lblSelectDob;
    public Label lblSelectCourseFirst;
    public Label lblInvalidSearchDetail;
    public JFXTextField txtStdNic;
    public Label lblInvalidStdNic;
    public AnchorPane panelRegistration;
    public AnchorPane panelGuardian;
    public AnchorPane panelPayment;
    public JFXButton btnView;
    public JFXButton btnEnroll;
    public Label lblInvalidAmount;
    public Label lblInvalidRemark;
    CourseService courseService;
    PaymentService paymentService;
    RegistrationService registrationService;
    BatchService batchService;
    GuardianService guardianService;
    SystemUserService systemUserService;
    @FXML
    private AnchorPane pane;
    @FXML
    private JFXTextField txtStdAddress;
    @FXML
    private JFXTextField txtStdCity;
    @FXML
    private JFXTextField txtStdMobileNumber;
    @FXML
    private JFXTextField txtStdEmail;
    @FXML
    private JFXTextField txtSchool;
    @FXML
    private JFXRadioButton rBtnMale;
    @FXML
    private ToggleGroup gender;
    @FXML
    private JFXRadioButton rBtnFemale;
    @FXML
    private JFXDatePicker calDob;
    @FXML
    private JFXTextField txtStdName;
    @FXML
    private JFXRadioButton rBtnMaster;
    @FXML
    private ToggleGroup edu;
    @FXML
    private JFXRadioButton rBtnDegree;
    @FXML
    private JFXRadioButton rBtnDiploma;
    @FXML
    private JFXRadioButton rBtnAL;
    @FXML
    private JFXRadioButton rBtnOL;
    @FXML
    private Label lblRegID;
    @FXML
    private JFXTextField txtPostalCode;
    @FXML
    private Label lblInvalidName;
    @FXML
    private Label lblInvalidAddress;
    @FXML
    private Label lblInvalidCity;
    @FXML
    private Label lblInvalidPostalCode;
    @FXML
    private Label lblInvalidMobileNumber;
    @FXML
    private Label lblInvalidMobileNumber1;
    @FXML
    private Label lblInvalidSchool;
    @FXML
    private JFXRadioButton rBtnYes;
    @FXML
    private ToggleGroup familyMember;
    @FXML
    private JFXRadioButton rBtnNo;
    @FXML
    private JFXTextField txtParentAddress;
    @FXML
    private JFXTextField txtParentMobile;
    @FXML
    private JFXTextField txtParentName;
    @FXML
    private JFXTextField txtParentEmail;
    @FXML
    private JFXTextField txtParentDesignation;
    @FXML
    private JFXTextField txtParentWorkPlace;
    @FXML
    private JFXTextField txtParentID;
    @FXML
    private JFXTextField txtSearchParent;
    @FXML
    private JFXButton btnSearch;
    @FXML
    private Label lblInvalidParentID;
    @FXML
    private Label lblInvalidParentName;
    @FXML
    private Label lblInvalidParentAddress;
    @FXML
    private Label lblInvalidParentMobile;
    @FXML
    private Label lblInvalidParentEmail;
    @FXML
    private Label lblInvalidParentDesignaion;
    @FXML
    private Label lblInvalidParentWorkingPlace;
    @FXML
    private Label lblPaymentID;
    @FXML
    private JFXTextField txtAmount;
    @FXML
    private JFXTextField txtRemark;
    @FXML
    private Label txtBatch;
    @FXML
    private JFXComboBox<String> cmbCourse;

    @FXML
    void btnEnrollClickOnAction(ActionEvent event) {
        SuperDTO dto;
        RegistrationDTO registrationDTO = isStdDetailCorrect();
        try {
            //    System.out.println("registrationDTO not null");
            GuardianDTO guardianDTO = setGuardianDetailDTO();
            //   System.out.println("guardian not null");
            PaymentDTO paymentDTO = setPaymentDTO();
            //   System.out.println("paymentDTO not null");
            if (!rBtnYes.isSelected()) {
                //add with parent
                System.out.println("add with parent");
                registrationDTO.setPayment(paymentDTO);
                guardianDTO.setRegistration(registrationDTO);
                dto = guardianService.save(guardianDTO);
            } else {
                //add without parent
                System.out.println("add without parent");
                registrationDTO.setGardianId(txtParentID.getText());
                registrationDTO.setPayment(paymentDTO);
                dto = registrationService.save(registrationDTO);
            }
            if (dto != null) {
                new Alert(Alert.AlertType.INFORMATION, "Enroll Successes!").showAndWait();
                alertGenerateQRCodeAndPrintBill(registrationDTO);
                sendMail(registrationDTO);
                Navigation.navigate(Routes.ENROLLMENTS, pane);
            }

        } catch (SQLException | IOException | ClassNotFoundException | RuntimeException | WriterException |
                 JRException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMail(RegistrationDTO registration) {
        String conclusion = "\n\n\n\n\nThis email and any attachment transmitted herewith are confidential and is intended solely for the use of the individual or entity to which they are addressed and may contain information that is privileged or otherwise protected from disclosure. If you are not the intended recipient, you are hereby notified that disclosing, copying, distributing, or taking any action in reliance on this email and the information it contains is strictly prohibited. If you have received this email in error, please notify the sender immediately by reply email and discard all of its contents by deleting this email and the attachment, if any, from your system";
        String header = "\t \t \t WELCOME TO INSTITUTE OF JAVA AND SOFTWARE ENGINEERING \n" +
                "Dear " + registration.getName() + ", Greetings from the Student Enrollment Unit!\n\n" +
                "Your Students ID is : " + registration.getRegistrationId() +
                "\n\nThank You!..." + conclusion;
        String subject = "Welcome to Institute of Software Engineering";
        try {
            systemUserService.sendMail(new Mail(
                    header,
                    registration.getEmail(),
                    subject,
                    null));
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.INFORMATION, String.valueOf(e)).show();
        }
    }

    private void alertGenerateQRCodeAndPrintBill(RegistrationDTO registration) throws IOException, WriterException, JRException {
        printReport();
        QRGenerator instance = QRGenerator.getInstance();
        instance.setId(lblRegID.getText());
        Thread thread = new Thread(instance);
        thread.start();

//        QRGenerator.getGenerator(registration.toString());
//        String msg2 = "\n\n\n\n\nThis email and any attachment transmitted herewith are confidential and is intended solely for the use of the individual or entity to which they are addressed and may contain information that is privileged or otherwise protected from disclosure. If you are not the intended recipient, you are hereby notified that disclosing, copying, distributing, or taking any action in reliance on this email and the information it contains is strictly prohibited. If you have received this email in error, please notify the sender immediately by reply email and discard all of its contents by deleting this email and the attachment, if any, from your system";
//        String msg = "\t \t \t WELCOME TO INSTITUTE OF JAVA AND SOFTWARE ENGINEERING \n" +
//                "Dear " + registration.getName() + ", Greetings from the Student Enrollment Unit!\n\n" +
//                "Your Students ID is : " + registration.getRegistrationId() +
//                "\n\nThank You!..." + msg2;
//        String subject = "Welcome to Institute of Software Engineering";
//        try {
//            Mail.outMail(msg, registration.getEmail(), subject);
//        } catch (MessagingException e) {
//            new Alert(Alert.AlertType.INFORMATION, String.valueOf(e)).show();
//        }

    }

    private void generateQrCode() {
    }

    private void printReport() throws JRException {
        HashMap hashMap = new HashMap<>();
        hashMap.put("receptNo", lblPaymentID.getText());
        hashMap.put("regId", lblRegID.getText());
        hashMap.put("name", txtStdName.getText());
        hashMap.put("batchID", txtBatch.getText());
        hashMap.put("remark", txtRemark.getText());
        hashMap.put("amount", txtAmount.getText());
        hashMap.put("total", txtAmount.getText());
        hashMap.put("nic", txtStdNic.getText());
        JasperReport compileReport = JasperCompileManager.compileReport(
                JRXmlLoader.load(
                        getClass().getResourceAsStream(
                                "/RegistrationReceiptNew.jrxml"
                        )
                )
        );
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, hashMap, new JREmptyDataSource());
        JasperViewer.viewReport(jasperPrint, false);
    }

    private PaymentDTO setPaymentDTO() throws RuntimeException {
        if (RegExPatterns.getDoublePattern().matcher(txtAmount.getText()).matches()) {
            if (RegExPatterns.getNamePattern().matcher(txtRemark.getText()).matches()) {
                return new PaymentDTO(
                        lblPaymentID.getText(),
                        lblRegID.getText(),
                        "registration",
                        "Registration Payment",
                        Double.parseDouble(txtAmount.getText()),
                        Date.valueOf(LocalDate.now())
                );
            } else {
                txtRemark.setFocusColor(Color.RED);
                lblInvalidRemark.setVisible(true);
            }
        } else {
            txtAmount.setFocusColor(Color.RED);
            lblInvalidAmount.setVisible(true);
        }
        throw new RuntimeException("Incorrect Payment Details");
    }

    private GuardianDTO setGuardianDetailDTO() throws RuntimeException {
        System.out.println("guardian regex");
        if (RegExPatterns.getOldIDPattern().matcher(txtParentID.getText()).matches()) {
            System.out.println("Id check");
            if (RegExPatterns.getNamePattern().matcher(txtParentName.getText()).matches()) {
                System.out.println("name check");
                if (RegExPatterns.getAddressPattern().matcher(txtParentAddress.getText()).matches()) {
                    System.out.println("address check");
                    if (RegExPatterns.getMobilePattern().matcher(txtParentMobile.getText()).matches()) {
                        System.out.println("mobile check");
                        if (RegExPatterns.getEmailPattern().matcher(txtParentEmail.getText()).matches()) {
                            System.out.println("email check");
                            if (RegExPatterns.getNamePattern().matcher(txtParentDesignation.getText()).matches()) {
                                System.out.println("designation check");
                                if (RegExPatterns.getNamePattern().matcher(txtParentWorkPlace.getText()).matches()) {
                                    System.out.println("work place check");
                                    return new GuardianDTO(
                                            txtParentID.getText(),
                                            txtParentName.getText(),
                                            txtParentAddress.getText(),
                                            txtParentMobile.getText(),
                                            txtParentEmail.getText(),
                                            txtParentDesignation.getText(),
                                            txtParentWorkPlace.getText()
                                    );
                                } else {
                                    lblInvalidParentWorkingPlace.setVisible(true);
                                    txtParentWorkPlace.setFocusColor(Color.RED);
                                }
                            } else {
                                lblInvalidParentDesignaion.setVisible(true);
                                txtParentDesignation.setFocusColor(Color.RED);
                            }
                        } else {
                            lblInvalidParentEmail.setVisible(true);
                            txtParentEmail.setFocusColor(Color.RED);
                        }
                    } else {
                        lblInvalidParentMobile.setVisible(true);
                        txtParentMobile.setFocusColor(Color.RED);
                    }
                } else {
                    lblInvalidParentAddress.setVisible(true);
                    txtParentAddress.setFocusColor(Color.RED);
                }
            } else {
                lblInvalidParentName.setVisible(true);
                txtParentName.setFocusColor(Color.RED);
            }
        } else {
            lblInvalidParentID.setVisible(true);
            txtParentID.setFocusColor(Color.RED);
        }
        throw new RuntimeException("Incorrect Guardian Details");
    }

    private RegistrationDTO isStdDetailCorrect() throws RuntimeException {
        if (RegExPatterns.getNamePattern().matcher(txtStdName.getText()).matches()) {
            if (RegExPatterns.getIdPattern().matcher(txtStdNic.getText()).matches()) {
                if (RegExPatterns.getAddressPattern().matcher(txtStdAddress.getText()).matches()) {
                    if (RegExPatterns.getCityPattern().matcher(txtStdCity.getText()).matches()) {
                        if (RegExPatterns.getPostalCodePattern().matcher(txtPostalCode.getText()).matches()) {
                            if (RegExPatterns.getMobilePattern().matcher(txtStdMobileNumber.getText()).matches()) {
                                if (RegExPatterns.getEmailPattern().matcher(txtStdEmail.getText()).matches()) {
                                    if (calDob.getValue() != null) {
                                        if (RegExPatterns.getNamePattern().matcher(txtSchool.getText()).matches()) {
                                            if (cmbCourse.getValue() != null) {
                                                return new RegistrationDTO(

                                                        lblRegID.getText(),
                                                        txtStdNic.getText(),
                                                        txtBatch.getText(),
                                                        cmbCourse.getValue(),
                                                        txtParentID.getText(),
                                                        txtStdName.getText(),
                                                        txtStdAddress.getText(),
                                                        txtStdCity.getText(),
                                                        txtPostalCode.getText(),
                                                        txtStdMobileNumber.getText(),
                                                        txtStdEmail.getText(),
                                                        Date.valueOf(calDob.getValue()),
                                                        (rBtnMale.isSelected()) ? "Male" :
                                                                "Female",
                                                        txtSchool.getText(),
                                                        (rBtnOL.isSelected()) ? "Ordinary Level" :
                                                                (rBtnAL.isSelected()) ? "Advanced Level" :
                                                                        (rBtnDiploma.isSelected()) ? "Diploma Level" :
                                                                                (rBtnDegree.isSelected()) ? "Degree Level" :
                                                                                        "Master",
                                                        "Active"
                                                );
                                            } else {
                                                lblSelectCourseFirst.setVisible(true);
                                            }
                                        } else {
                                            txtSchool.setFocusColor(Color.RED);
                                            lblInvalidSchool.setVisible(true);
                                        }
                                    } else {
                                        lblSelectDob.setVisible(true);
                                    }
                                } else {
                                    txtStdEmail.setFocusColor(Color.RED);
                                    lblInvalidEmail.setVisible(true);
                                }
                            } else {
                                txtStdMobileNumber.setFocusColor(Color.RED);
                                lblInvalidMobileNumber.setVisible(true);
                            }
                        } else {
                            txtPostalCode.setFocusColor(Color.RED);
                            lblInvalidPostalCode.setVisible(true);
                        }
                    } else {
                        txtStdCity.setFocusColor(Color.RED);
                        lblInvalidCity.setVisible(true);
                    }
                } else {
                    txtStdAddress.setFocusColor(Color.RED);
                    lblInvalidAddress.setVisible(true);
                }
            } else {
                txtStdNic.setFocusColor(Color.RED);
                lblInvalidStdNic.setVisible(true);
            }
        } else {
            txtStdName.setFocusColor(Color.RED);
            lblInvalidName.setVisible(true);
        }
        throw new RuntimeException("Incorrect Registration Details");
    }

    @FXML
    void btnSearchOnaction(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            if (RegExPatterns.getOldIDPattern().matcher(txtSearchParent.getText()).matches()) {
                System.out.println("txt :" + txtSearchParent.getText());
                GuardianDTO guardianDTO = guardianService.view(new GuardianDTO(txtSearchParent.getText()));
                if (guardianDTO != null) {
                    txtParentID.setText(guardianDTO.getId());
                    txtParentName.setText(guardianDTO.getName());
                    txtParentEmail.setText(guardianDTO.getEmail());
                    txtParentMobile.setText(guardianDTO.getMobile());
                    txtParentWorkPlace.setText(guardianDTO.getWorkingPlace());
                    txtParentAddress.setText(guardianDTO.getAddress());
                    txtParentDesignation.setText(guardianDTO.getDesignation());
                }
            } else {
                lblInvalidSearchDetail.setVisible(true);
                txtSearchParent.setFocusColor(Color.RED);
            }
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnViewOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEW_REGISTRATION, pane);
    }

    @FXML
    void cmbCourseOnAction(ActionEvent event) {
        BatchDTO lastOngoingBatch = null;
        try {
            lastOngoingBatch = batchService.getLastOngoingBatch(cmbCourse.getValue());
            if (lastOngoingBatch != null) {
                System.out.println(lastOngoingBatch.getId());
                txtBatch.setText(lastOngoingBatch.getId());
                activate(false);
            } else {
                txtBatch.setText("No any Ongoing Batch");
                activate(true);
            }
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void activate(boolean active) {
        panelRegistration.setDisable(active);
        panelPayment.setDisable(active);
        panelGuardian.setDisable(active);
        btnEnroll.setDisable(active);
        btnView.setDisable(active);
    }

    @FXML
    void rBtnNoOnAction(ActionEvent event) {
        if (rBtnNo.isSelected()) {
            setTextEnable(false);
        }
    }

    private void setTextEnable(boolean flag) {
        txtParentID.setDisable(flag);
        txtParentName.setDisable(flag);
        txtParentAddress.setDisable(flag);
        txtParentMobile.setDisable(flag);
        txtParentEmail.setDisable(flag);
        txtParentDesignation.setDisable(flag);
        txtParentWorkPlace.setDisable(flag);
        txtSearchParent.setDisable(!flag);
        btnSearch.setDisable(!flag);

    }

    @FXML
    void rBtnYesOnAction(ActionEvent event) {
        if (rBtnYes.isSelected()) {
            setTextEnable(true);
        }
    }

    @FXML
    void txtParentAddressOnAction(MouseEvent event) {
        lblInvalidParentAddress.setVisible(false);
    }

    @FXML
    void txtParentDesignationOnAction(MouseEvent event) {
        lblInvalidParentDesignaion.setVisible(false);
    }

    @FXML
    void txtParentEmailOnAction(MouseEvent event) {
        lblInvalidParentEmail.setVisible(false);
    }

    @FXML
    void txtParentIDOnAction(MouseEvent event) {
        lblInvalidParentID.setVisible(false);
    }

    @FXML
    void txtParentMobileOnAction(MouseEvent event) {
        lblInvalidParentMobile.setVisible(false);
    }

    @FXML
    void txtParentNameOnAction(MouseEvent event) {
        lblInvalidParentName.setVisible(false);
    }

    @FXML
    void txtWorkingPlaceOnAction(MouseEvent event) {
        lblInvalidParentWorkingPlace.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            systemUserService = ServiceFactory.getInstance().getService(ServiceTypes.SYSTEM_USER);
            courseService = ServiceFactory.getInstance().getService(ServiceTypes.COURSE);
            paymentService = ServiceFactory.getInstance().getService(ServiceTypes.PAYMENTS);
            registrationService = ServiceFactory.getInstance().getService(ServiceTypes.REGISTRATION);
            batchService = ServiceFactory.getInstance().getService(ServiceTypes.BATCH);
            guardianService = ServiceFactory.getInstance().getService(ServiceTypes.GUARDIAN);

            activate(true);
            loadCoursesList(cmbCourse);

            setLblRegPaymentID(lblPaymentID);
            setRegistrationID(lblRegID);

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void setRegistrationID(Label lblRegistrationID) throws SQLException, ClassNotFoundException {
        String lastRegistrationId = registrationService.getLastRegistrationID();
        if (lastRegistrationId == null) {
            lblRegistrationID.setText("IT000001");
        } else {
            String[] split = lastRegistrationId.split("[I][T]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            lblRegistrationID.setText(String.format("IT%06d", lastDigits));
        }
    }

    public void setLblRegPaymentID(Label lblPaymentID) throws SQLException, ClassNotFoundException {
        String lastTestPaymentID = paymentService.getLastPaymentID();
        if (lastTestPaymentID == null) {
            lblPaymentID.setText("P000001");
        } else {
            String[] split = lastTestPaymentID.split("[P]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            lblPaymentID.setText(String.format("P%06d", lastDigits));
        }
    }

    public void loadCoursesList(ComboBox<String> comboBox) throws SQLException, ClassNotFoundException {
        List<CourseDTO> courseArrayList = courseService.getCourseList();
        ObservableList<String> observableArrayList = FXCollections.observableArrayList();
        for (CourseDTO ele : courseArrayList) observableArrayList.add(ele.getId());
        comboBox.setItems(observableArrayList);
    }

    public void txtStdAddressOnMouseClick(MouseEvent mouseEvent) {
        lblInvalidAddress.setVisible(false);
    }

    public void txtStdCityOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidCity.setVisible(false);
    }

    public void txtStdMobileOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidMobileNumber.setVisible(false);
    }

    public void txtStdEmailOnMouseClick(MouseEvent mouseEvent) {
        lblInvalidEmail.setVisible(false);
    }

    public void txtStdSchoolOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidSchool.setVisible(false);
    }

    public void txtStdNameOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidName.setVisible(false);
    }

    public void txtStdPostalCodeOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidPostalCode.setVisible(false);
    }

    public void calDobOnMouseClicked(MouseEvent mouseEvent) {
        lblSelectDob.setVisible(false);
    }

    public void cmbCourseOnMouseClicked(MouseEvent mouseEvent) {
        lblSelectCourseFirst.setVisible(false);
    }

    public void txtSearchOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidSearchDetail.setVisible(false);
    }

    public void txtStdNicOnAction(MouseEvent mouseEvent) {
        lblInvalidStdNic.setVisible(false);
    }

    public void txtAmountOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidAmount.setVisible(false);
    }

    public void txtRegistrationOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidRemark.setVisible(false);
    }

}



