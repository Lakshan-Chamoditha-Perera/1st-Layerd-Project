package lk.ijse.studentsmanagement.dao;

import lk.ijse.studentsmanagement.dao.custom.impl.*;

import java.sql.Connection;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getInstance() {
        return (daoFactory == null) ? (daoFactory = new DAOFactory()) : daoFactory;
    }

    public <T extends SuperDAO> T getDAO(Connection connection, DaoTypes type) {

        switch (type) {
            case ATTENDANCE:
                return (T) new AttendanceImpl(connection);

            case BATCH:
                return (T) new BatchImpl(connection);

            case COURSE:
                return (T) new CourseImpl(connection);

            case EXAM:
                return (T) new ExamImpl(connection);

            case GUARDIAN:
                return (T) new GuardianImpl(connection);

            case INQUIRY:
                return (T) new InquiryImpl(connection);

            case INQUIRY_IQTEST_DETAIL:
                return (T) new InquiryIqTestDetailImpl(connection);

            case IQTEST:
                return (T) new IqTestImpl(connection);

            case PAYMENTS:
                return (T) new PaymentImpl(connection);

            case QUERY:
                return (T) new QueryImpl(connection);

            case REGISTRATION:
                return (T) new RegistrationImpl(connection);

            case REGISTRATION_EXAM_RESULTS:
                return (T) new RegistrationExamResultsImpl(connection);

            case SUBJECT:
                return (T) new SubjectImpl(connection);

            case SYSTEM_USER:
                return (T) new SystemUserImpl(connection);

            case TEST_PAYMENTS:
                return (T) new TestPaymentImpl(connection);

            default:
                return null;
        }
    }
}
