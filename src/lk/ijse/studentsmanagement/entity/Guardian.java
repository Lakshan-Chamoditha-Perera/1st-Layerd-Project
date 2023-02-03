package lk.ijse.studentsmanagement.entity;

public class Guardian implements SuperEntity{
    String id;
    String name;
    String address;
    String mobile;
    String email;
    String designation;
    String workingPlace;

    Registration registration;

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Guardian(String id, String name, String address, String mobile, String email, String designation, String workingPlace) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.designation = designation;
        this.workingPlace = workingPlace;
    }
    public Guardian(String id) {
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
