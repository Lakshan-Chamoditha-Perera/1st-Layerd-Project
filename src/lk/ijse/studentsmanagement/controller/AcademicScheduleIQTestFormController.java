package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import lk.ijse.studentsmanagement.dto.IQTestDTO;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.IqTestService;
import lk.ijse.studentsmanagement.dto.tblModels.IQTestTM;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.RegExPatterns;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AcademicScheduleIQTestFormController implements Initializable {

    public JFXTextField txtLabId;
    public JFXDatePicker cmbDate;
    public JFXTimePicker cmbTime;
    public JFXTextField txtAmount;
    public TableView<IQTestTM> tblIqTest;
    public TableColumn colID;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colLab;
    public TableColumn colAmount;
    public Label lblId;
    public Label lblInvalidLab;
    public Label lblInvalidAmount;
    public Label lblInvalidDate;
    public Label lblInvalidTime;
    public JFXButton btnDelete;
    IqTestService iqTestService;
    @FXML
    private AnchorPane pane;

    @FXML
    void backClickOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.EXAMS, pane);
    }

    @FXML
    void btnSheduleOnAction(ActionEvent event) {
        try {
            if (txtLabId.getText() != null) {
                if (RegExPatterns.getDoublePattern().matcher(txtAmount.getText()).matches()) {
                    if (!cmbDate.getValue().isBefore(LocalDate.now())) {
                        if (cmbTime.getValue() != null) {
                            addIQTest(new IQTestDTO(lblId.getText(), Date.valueOf(cmbDate.getValue()), Time.valueOf(cmbTime.getValue()), txtLabId.getText(), Double.parseDouble(txtAmount.getText())));
                        } else {
                            lblInvalidTime.setVisible(true);
                            new Alert(Alert.AlertType.ERROR, "Select Time").show();
                        }
                    } else {
                        lblInvalidDate.setVisible(true);
                        new Alert(Alert.AlertType.ERROR, "Select Date").show();
                    }
                } else {
                    lblInvalidAmount.setVisible(true);
                    txtAmount.setFocusColor(Color.RED);
                    new Alert(Alert.AlertType.ERROR, "Enter amount").show();
                }
            } else {
                lblInvalidLab.setVisible(true);
                txtLabId.setFocusColor(Color.RED);
                new Alert(Alert.AlertType.ERROR, "Enter Lab").show();
            }
        } catch (SQLException | ClassNotFoundException | IOException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void addIQTest(IQTestDTO iqTestDTO) throws SQLException, ClassNotFoundException, RuntimeException, IOException {
        iqTestService.save(iqTestDTO);
        new Alert(Alert.AlertType.INFORMATION, "Done").showAndWait();
        Navigation.navigate(Routes.SCHEDULE_IQTEST, pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnDelete.setDisable(true);

        lblInvalidTime.setVisible(false);
        lblInvalidLab.setVisible(false);
        lblInvalidDate.setVisible(false);
        lblInvalidAmount.setVisible(false);

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colLab.setCellValueFactory(new PropertyValueFactory<>("lab"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        try {
            iqTestService = ServiceFactory.getInstance().getService(ServiceTypes.IQTEST);
            loadIQTestIDS();
            loadIQTests();
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadIQTestIDS() throws SQLException, ClassNotFoundException, RuntimeException {
        String lastExamID = iqTestService.getLastExamID();
        // IQTest lastExamID = IQTestModel.getExamID();
        if (lastExamID == null) {
            lblId.setText("IQ0001");
        } else {
            String id = lastExamID;
            String[] split = id.split("[I][Q]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            lblId.setText(String.format("IQ%04d", lastDigits));
        }
    }

    public void loadIQTests() throws SQLException, ClassNotFoundException, RuntimeException {
        List<IQTestDTO> iqTestList = iqTestService.getIQTestList();
        //    ArrayList<IQTest> list = IQTestModel.getIQTestList();
        ObservableList<IQTestTM> observableArrayList = FXCollections.observableArrayList();
        for (IQTestDTO ele : iqTestList) {
            observableArrayList.add(new IQTestTM(ele.getId(), ele.getDate(), ele.getTime(), ele.getLab(), ele.getAmount()));
        }
        tblIqTest.setItems(observableArrayList);
    }

    public void cmbTimeOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidTime.setVisible(false);
    }

    public void txtAmountOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidAmount.setVisible(false);
    }

    public void txtLabOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidLab.setVisible(false);
    }

    public void cmbDateOnMouseClicked(MouseEvent mouseEvent) {
        lblInvalidTime.setVisible(false);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        IQTestTM selectedItem = tblIqTest.getSelectionModel().getSelectedItem();
        try {
            if (selectedItem != null) {
                deleteExam();
            } else {
                new Alert(Alert.AlertType.ERROR, "Select Exam First!").show();
            }
        } catch (SQLException | ClassNotFoundException | IOException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void deleteExam() throws SQLException, ClassNotFoundException, RuntimeException, IOException {
        iqTestService.delete(new IQTestDTO(tblIqTest.getSelectionModel().getSelectedItem().getId()));
        new Alert(Alert.AlertType.ERROR, "deleted...").showAndWait();
        Navigation.navigate(Routes.SCHEDULE_IQTEST, pane);
    }

    public void tblIqTestOnMouseClicked(MouseEvent mouseEvent) {
        btnDelete.setDisable(tblIqTest.getSelectionModel().getSelectedItem() == null);
    }
}
