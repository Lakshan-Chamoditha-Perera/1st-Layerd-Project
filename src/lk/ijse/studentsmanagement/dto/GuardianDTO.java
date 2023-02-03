package lk.ijse.studentsmanagement.dto;

import lk.ijse.studentsmanagement.entity.SuperEntity;

public class GuardianDTO implements SuperDTO {
    String id;
    String name;
    String address;
    String mobile;
    String email;
    String designation;
    String workingPlace;

    RegistrationDTO registration;

    public RegistrationDTO getRegistration() {
        return registration;
    }

    public void setRegistration(RegistrationDTO registration) {
        this.registration = registration;
    }

    public GuardianDTO(String id, String name, String address, String mobile, String email, String designation, String workingPlace) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.designation = designation;
        this.workingPlace = workingPlace;
    }
    public GuardianDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getDesignation() {
        return designation;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    @Override
    public String toString() {
        return "Gardian{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", designation='" + designation + '\'' +
                ", workingPlace='" + workingPlace + '\'' +
                ", registration=" + registration +
                '}';
    }
}
