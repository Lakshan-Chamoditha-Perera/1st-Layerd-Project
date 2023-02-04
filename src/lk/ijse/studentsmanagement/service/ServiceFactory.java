package lk.ijse.studentsmanagement.service;

import lk.ijse.studentsmanagement.service.custom.impl.*;

import java.sql.SQLException;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;
    private ServiceFactory(){

    }
    public static ServiceFactory getInstance(){
        return (serviceFactory==null)?(serviceFactory = new ServiceFactory()):serviceFactory;
    }

    public <T extends SuperService> T getService(ServiceTypes type) throws SQLException, ClassNotFoundException {
        switch(type){
            case ATTENDANCE: return (T) new AttendanceServiceImpl();
            case BATCH: return (T) new BatchServiceImpl();
            case COURSE: return (T) new CourseServiceImpl();
            case EXAM: return (T) new ExamServiceImpl();
            case GUARDIAN: return (T) new GuardianServiceImpl();
            case INQUIRY: return (T) new InquiryServiceImpl();
            case INQUIRY_IQTEST_DETAIL: return (T) new InquiryIqTestDetailServiceImpl();
            case IQTEST: return (T) new IqTestServiceImpl();
            case PAYMENTS: return (T) new PaymentServiceImpl();
            case REGISTRATION: return (T) new RegistrationServiceImpl();
            case REGISTRATION_EXAM_RESULTS: return (T) new RegistrationExamResultsServiceImpl();
            case SUBJECT: return (T) new SubjectServiceImpl();
            case SYSTEM_USER: return (T) new SystemUserServiceImpl();
            case TEST_PAYMENTS: return (T) new TestPaymentServiceImpl();
            case COURSE_SUBJECT_DETAIL: return (T) new CourseSubjectDetailServiceImpl();
        }
        return null;
    }

}
