package lk.ijse.studentsmanagement.dto;

import lk.ijse.studentsmanagement.entity.SuperEntity;

public class SystemUserDTO implements SuperDTO {
    String userName;
    String password;
    String passwordHint;

    public SystemUserDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public SystemUserDTO(String userName, String password, String passwordHint) {
        this.userName = userName;
        this.password = password;
        this.passwordHint = passwordHint;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordHint() {
        return passwordHint;
    }

    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }
}
