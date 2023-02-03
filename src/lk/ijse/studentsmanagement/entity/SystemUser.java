package lk.ijse.studentsmanagement.entity;

public class SystemUser implements SuperEntity{
    String userName;
    String password;
    String passwordHint;

    public SystemUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public SystemUser(String userName, String password, String passwordHint) {
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
