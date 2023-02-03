package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.studentsmanagement.dto.IQTestDTO;
import lk.ijse.studentsmanagement.dto.InquiryIQTestDetailDTO;
import lk.ijse.studentsmanagement.entity.IQTest;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.InquiryIqTestDetailService;
import lk.ijse.studentsmanagement.service.custom.IqTestService;
import lk.ijse.studentsmanagement.tblModels.InquiryIQTestDetailTM;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AcademicAddIQtestMarksFormController implements Initializable {

    public Label lblStdName;
    public Label lblStdID;
    public Button btnAdd;
    public Label txtExamTime;
    public Label txtLab;
    public Label txtExamDate;
    public AnchorPane tblPane;
    IqTestService iqTestService;
    InquiryIqTestDetailService inquiryIqTestDetailService;
    @FXML
    private AnchorPane pane;
    @FXML
    private ComboBox<String> cmbExamID;
    @FXML
    private TableView<InquiryIQTestDetailTM> tblStdList;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colExamId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colResult;
    @FXML
    private JFXRadioButton rbtnPass;
    @FXML
    private ToggleGroup results;
    @FXML
    private JFXRadioButton rbtnFail;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            String result = (rbtnPass.isSelected()) ? "Pass" : "Fail";
            InquiryIQTestDetailDTO inquiryIQTestDetailDTO = inquiryIqTestDetailService.update(new InquiryIQTestDetailDTO(tblStdList.getSelectionModel().getSelectedItem().getStudentId(), tblStdList.getSelectionModel().getSelectedItem().getTestId(), result));
            new Alert(Alert.AlertType.INFORMATION, "Done").show();
            loadTableResults();
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.EXAMS, pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            iqTestService = ServiceFactory.getInstance().getService(ServiceTypes.IQTEST);
            inquiryIqTestDetailService = ServiceFactory.getInstance().getService(ServiceTypes.INQUIRY_IQTEST_DETAIL);
            tblPane.setDisable(true);
            cmbExamID.setDisable(true);

            colId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colExamId.setCellValueFactory(new PropertyValueFactory<>("testId"));
            colResult.setCellValueFactory(new PropertyValueFactory<>("result"));

            loadIQExamIDComboBox();
            cmbExamID.setDisable(false);
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadIQExamIDComboBox() throws SQLException, ClassNotFoundException, RuntimeException {
        List<IQTestDTO> iqTestList = iqTestService.getIQTestList();
        ObservableList<String> observableArrayList = FXCollections.observableArrayList();
        for (IQTestDTO ele : iqTestList) observableArrayList.add(ele.getId());
        cmbExamID.setItems(observableArrayList);
    }

    private void loadTableResults() throws SQLException, ClassNotFoundException, RuntimeException {
        lblStdID.setText("");
        lblStdName.setText("");
        btnAdd.setDisable(true);
        loadInquiryIQTestResults(new IQTest(cmbExamID.getValue()));
    }

    public void loadInquiryIQTestResults(IQTest iqTest) throws SQLException, ClassNotFoundException, RuntimeException {
        List<InquiryIQTestDetailDTO> inquiryIQTestDetailDTOS = inquiryIqTestDetailService.getInquiryIQTestList(iqTest.getId());
        ObservableList<InquiryIQTestDetailTM> observableArrayList = FXCollections.observableArrayList();
        for (InquiryIQTestDetailDTO ele : inquiryIQTestDetailDTOS)
            observableArrayList.add(new InquiryIQTestDetailTM(ele.getStudentId(), ele.getTestId(), ele.getResult(), ele.getName()));

        tblStdList.setItems(observableArrayList);
    }

    public void cmbExamIDOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, RuntimeException {
        try {
            if (!cmbExamID.getValue().isEmpty()) {
                loadTableResults();
                IQTestDTO iqTestDTO = iqTestService.getExamDetails(new IQTestDTO(cmbExamID.getValue()));

                txtExamDate.setText(iqTestDTO.getDate().toString());
                txtLab.setText(iqTestDTO.getLab());
                txtExamTime.setText(iqTestDTO.getTime().toString());

                tblPane.setDisable(false);
            }
        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void tblStdListOnAction(MouseEvent mouseEvent) {
        lblStdID.setText("");
        lblStdName.setText("");
        if (tblStdList.getSelectionModel().getSelectedItem() != null) {
            lblStdID.setText(tblStdList.getSelectionModel().getSelectedItem().getStudentId());
            lblStdName.setText(tblStdList.getSelectionModel().getSelectedItem().getName());
            btnAdd.setDisable(false);
        } else {
            btnAdd.setDisable(true);
        }
    }
}
