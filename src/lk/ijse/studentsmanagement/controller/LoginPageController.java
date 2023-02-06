package lk.ijse.studentsmanagement.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import lk.ijse.studentsmanagement.dto.SystemUserDTO;
import lk.ijse.studentsmanagement.service.ServiceFactory;
import lk.ijse.studentsmanagement.service.ServiceTypes;
import lk.ijse.studentsmanagement.service.custom.SystemUserService;
import lk.ijse.studentsmanagement.util.Navigation;
import lk.ijse.studentsmanagement.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LoginPageController implements Initializable {
    public AnchorPane pane;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public Label lblPassword;
    public Label lblUsername;
    Pattern[] pattern = new Pattern[2];
    SystemUserService systemUserService;

    public void btnClickOnAction(ActionEvent actionEvent) throws IOException {
        if (pattern[0].matcher(txtUserName.getText()).matches()) {
            if (pattern[1].matcher(txtPassword.getText()).matches()) {
                login();
            } else {
                txtPassword.setFocusColor(Color.valueOf("RED"));
                txtPassword.requestFocus();
            }
        } else {
            txtUserName.setFocusColor(Color.valueOf("RED"));
            txtUserName.requestFocus();
        }
    }

    private void login() throws IOException {
        try {
            SystemUserDTO systemUser = systemUserService.view(new SystemUserDTO(txtUserName.getText(), txtPassword.getText()));
            if (systemUser != null) {
                if (systemUser.getUserName().equals(txtUserName.getText())) {
                    if (systemUser.getPassword().equals(txtPassword.getText())) {
                        switch (systemUser.getUserName()) {
                            case "counselor":
//                                Mail.outMail(
//                                        "New login to system." +
//                                                "\n\t Time: "+
//                                                Date.valueOf(LocalDate.now())+
//                                                " : "+
//                                                Time.valueOf(LocalTime.now()),
//                                        "perera.alc2000@gmail.com",
//                                        "Alert!");
                                Navigation.navigate(Routes.COUNSELOR, pane);
                                break;

                            case "admin":
                                Navigation.navigate(Routes.ACADEMIC, pane);
                                break;
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Invalid Password").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid Username").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid User").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            systemUserService = ServiceFactory.getInstance().getService(ServiceTypes.SYSTEM_USER);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
        }
        pattern[0] = Pattern.compile("^[a-z0-9A-Z]{4,}$");//username
        pattern[1] = Pattern.compile("^[0-9a-zA-Z]{3,}$");//password

        lblUsername.setVisible(false);
        lblPassword.setVisible(false);

    }
}
