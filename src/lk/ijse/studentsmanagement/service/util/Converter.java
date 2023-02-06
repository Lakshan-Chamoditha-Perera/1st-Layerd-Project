package lk.ijse.studentsmanagement.service.util;

import lk.ijse.studentsmanagement.dto.*;
import lk.ijse.studentsmanagement.entity.*;
import lk.ijse.studentsmanagement.tblModels.AttendanceTM;

import java.util.Objects;

public class Converter {
    public InquiryDTO toInquiryDTO(Inquiry entity) {
        return new InquiryDTO(entity.getStudentID(), entity.getName(), entity.getCity(), entity.getEmail(), entity.getMobile(), entity.getDate(), entity.getGender(), entity.getStatus());
    }

    public Inquiry toInquiryEntity(InquiryDTO inquiryDTO, Types type) throws RuntimeException {
        switch (type) {
            case InquiryType1:
                return new Inquiry(inquiryDTO.getStudentID(), inquiryDTO.getName(), inquiryDTO.getCity(), inquiryDTO.getEmail(), inquiryDTO.getMobile(), inquiryDTO.getDate(), inquiryDTO.getGender(), inquiryDTO.getStatus());
            case InquiryType2:
                return new Inquiry(inquiryDTO.getStudentID());
            default:
                throw new RuntimeException("Inquiry type does not match");
        }
    }

    // public TestPaymentDTO toTestPaymentDTO(TestPayment entity) {
    //   return null;
    // }

    public TestPayment toTestPaymentEntity(TestPaymentDTO testPaymentDTO) {
        return new TestPayment(testPaymentDTO.getId(), testPaymentDTO.getStudentID(), testPaymentDTO.getDate(), testPaymentDTO.getRemark(), testPaymentDTO.getAmount(), testPaymentDTO.getIqTestId());
    }

    public InquiryIQTestDetail toInquiryIqTestDetailEntity(InquiryIQTestDetailDTO inquiryIQTestDetailDTO, Types type) {
        switch (type) {
            case InquiryIQTestDetailType1:
                return new InquiryIQTestDetail(inquiryIQTestDetailDTO.getStudentId(), inquiryIQTestDetailDTO.getTestId(), inquiryIQTestDetailDTO.getResult(), inquiryIQTestDetailDTO.getName());
            case InquiryIQTestDetailType2:
                return new InquiryIQTestDetail(inquiryIQTestDetailDTO.getStudentId(), inquiryIQTestDetailDTO.getTestId(), inquiryIQTestDetailDTO.getResult());
            default:
                throw new RuntimeException("Inquiry type does not match");
        }
    }

    public IQTestDTO toIQTestDTO(IQTest iqTest) {
        return new IQTestDTO(iqTest.getId(), iqTest.getDate(), iqTest.getTime(), iqTest.getLab(), iqTest.getAmount());
    }

    public ExamDTO toExamDTO(Exam exam, Types type) {
        switch (type) {
            case ExamType1:
                return new ExamDTO(exam.getExamId(), exam.getSubjectId(), exam.getBatchId(), exam.getDescription(), exam.getExamDate(), exam.getLab(), exam.getTime());
            case ExamType2:
                return new ExamDTO(exam.getExamId());
            case ExamType3:
                return new ExamDTO(exam.getExamId(), exam.getSubjectId(), exam.getBatchId(), exam.getDescription(), exam.getExamDate());
            default:
                throw new RuntimeException("Exam type does not match");
        }
    }

    public CourseDTO toCourseDTO(Course course) {
        return new CourseDTO(course.getId(), course.getName(), course.getDuration());
    }

    public BatchDTO toBatchDTO(Batch lastOngoingBatch, Types type) throws RuntimeException {
        switch (type) {
            case BatchType1:
                return new BatchDTO(lastOngoingBatch.getId(), lastOngoingBatch.getBatchNo(), lastOngoingBatch.getCourseId(), lastOngoingBatch.getFee(), lastOngoingBatch.getStarting_date(), lastOngoingBatch.getMaxStdCount());
            case BatchType2:
                return new BatchDTO(lastOngoingBatch.getBatchNo());
            case BatchType3:
                return new BatchDTO(lastOngoingBatch.getId());
            case BatchType4:
                return new BatchDTO(lastOngoingBatch.getId(), lastOngoingBatch.getCourseId());
            default:
                throw new RuntimeException("Batch type does not match");
        }
    }

    public Guardian toGuardianEntity(GuardianDTO guardianDTO, Types types) {
        switch (types) {
            case GuardianType1:
                return new Guardian(guardianDTO.getId(), guardianDTO.getName(), guardianDTO.getAddress(), guardianDTO.getMobile(), guardianDTO.getEmail(), guardianDTO.getDesignation(), guardianDTO.getWorkingPlace());
            case GuardianType2:
                return new Guardian(guardianDTO.getId());
            default:
                throw new RuntimeException("Guardian type does not match");
        }
    }

    public Registration toRegistrationEntity(RegistrationDTO registrationDTO, Types type) throws RuntimeException {
        switch (type) {
            case RegistrationType1:
                return new Registration(registrationDTO.getRegistrationId(), registrationDTO.getNic(), registrationDTO.getBatchId(), registrationDTO.getCourseId(), registrationDTO.getGardianId(), registrationDTO.getName(), registrationDTO.getAddress(), registrationDTO.getCity(), registrationDTO.getPostalCode(), registrationDTO.getMobile(), registrationDTO.getEmail(), registrationDTO.getDob(), registrationDTO.getGender(), registrationDTO.getSchool(), registrationDTO.getHigherEDU(), registrationDTO.getStatus());
            case RegistrationType2:
                return new Registration(registrationDTO.getRegistrationId());
            case RegistrationType3:
                return new Registration(registrationDTO.getRegistrationId(), registrationDTO.getName(), registrationDTO.getAddress(), registrationDTO.getCity(), registrationDTO.getPostalCode(), registrationDTO.getMobile(), registrationDTO.getEmail(), registrationDTO.getDob(), registrationDTO.getSchool());
            default:
                throw new RuntimeException("Registration type does not match");
        }
    }

    public Payment toPaymentEntity(PaymentDTO paymentDTO) {
        return new Payment(paymentDTO.getId(), paymentDTO.getRegistrationId(), paymentDTO.getType(), paymentDTO.getRemark(), paymentDTO.getAmount(), paymentDTO.getDate());
    }

    public GuardianDTO toGuardianDTO(Guardian guardian) {
        return new GuardianDTO(guardian.getId(), guardian.getName(), guardian.getAddress(), guardian.getMobile(), guardian.getEmail(), guardian.getDesignation(), guardian.getWorkingPlace());
    }

    public InquiryIQTestDetailDTO toInquiryIQTestDetailDTO(InquiryIQTestDetail inquiryIQTestDetail, Types type) throws RuntimeException {
        if (Objects.requireNonNull(type) == Types.InquiryIQTestDetailType1) {
            return new InquiryIQTestDetailDTO(inquiryIQTestDetail.getStudentId(), inquiryIQTestDetail.getTestId(), inquiryIQTestDetail.getResult(), inquiryIQTestDetail.getName());
        }
        throw new RuntimeException("InquiryIQTestDetail does not match");
    }

    public PaymentDTO toPaymentDTO(Payment payment) {
        return new PaymentDTO(payment.getId(), payment.getRegistrationId(), payment.getType(), payment.getRemark(), payment.getAmount(), payment.getDate());
    }

    public RegistrationDTO toRegistrationDTO(Registration registration, Types type) throws RuntimeException {
        switch (type) {
            case RegistrationType1:
                return new RegistrationDTO(registration.getRegistrationId(), registration.getNic(), registration.getBatchId(), registration.getCourseId(), registration.getGardianId(), registration.getName(), registration.getAddress(), registration.getCity(), registration.getPostalCode(), registration.getMobile(), registration.getEmail(), registration.getDob(), registration.getGender(), registration.getSchool(), registration.getHigherEDU(), registration.getStatus());
            case RegistrationType2:
                return new RegistrationDTO(registration.getRegistrationId(), registration.getName(), registration.getMobile(), registration.getEmail(), registration.getStatus());
            case RegistrationType3:
                return new RegistrationDTO(registration.getRegistrationId(), registration.getBatchId(), registration.getName(), registration.getStatus());
            default:
                throw new RuntimeException("Registration type does not found");
        }
    }

    public IQTest toIQTestEntity(IQTestDTO iqTestDTO, Types type) {
        switch (type) {
            case IQTestType1:
                return new IQTest(iqTestDTO.getId());
            case IQTestType2:
                return new IQTest(iqTestDTO.getId(), iqTestDTO.getDate(), iqTestDTO.getTime(), iqTestDTO.getLab(), iqTestDTO.getAmount());
            default:
                throw new RuntimeException("IQ_Test type does not found");
        }
    }

    public Course toCourseEntity(CourseDTO courseDTO, Types type) {
        if (Objects.requireNonNull(type) == Types.CourseType1) {
            return new Course(courseDTO.getId());
        }
        throw new RuntimeException("Registration type does not match");
    }

    public Batch toBatchEntity(BatchDTO batchDTO, Types type) {
        switch (type) {
            case BatchType1:
                return new Batch(batchDTO.getId(), batchDTO.getBatchNo(), batchDTO.getCourseId(), batchDTO.getFee(), batchDTO.getStarting_date(), batchDTO.getMaxStdCount());
            case BatchType2:
                return new Batch(batchDTO.getId());
            case BatchType3:
                return new Batch(batchDTO.getId(), batchDTO.getFee(), batchDTO.getStarting_date(), batchDTO.getMaxStdCount());
            default:
                throw new RuntimeException("Batch type does not match");
        }
    }

    public SubjectDTO toSubjectDTO(Subject subject, Types type) throws RuntimeException {
        switch (type) {
            case SubjectType1:
                return new SubjectDTO(subject.getId(), subject.getName(), subject.getHours());
            case SubjectType2:
                return null;
            default:
                throw new RuntimeException("Subject type does not match");
        }
    }

    public Subject toSubjectEntity(SubjectDTO subjectDTO, Types type) throws RuntimeException {
        switch (type) {
            case SubjectType1:
                return new Subject(subjectDTO.getId(), subjectDTO.getName(), subjectDTO.getHours());
            case SubjectType2:
                return new Subject(subjectDTO.getId());
            default:
                throw new RuntimeException("Subject type does not match");
        }

    }

    public TestPaymentDTO toTestPaymentDTO(TestPayment testPayment) {
        return new TestPaymentDTO(testPayment.getId(), testPayment.getStudentID(), testPayment.getDate(), testPayment.getRemark(), testPayment.getAmount());
    }

    public CourseSubjectDetailDTO toCourseSubjectDetailDTO(CourseSubjectDetail courseSubjectDetail) {
        return new CourseSubjectDetailDTO(courseSubjectDetail.getCourseId(), courseSubjectDetail.getSubjectId());
    }

    public CourseSubjectDetail toCourseSubjectDetailEntity(CourseSubjectDetailDTO courseSubjectDetailDTO) {
        return new CourseSubjectDetail(courseSubjectDetailDTO.getCourseId(), courseSubjectDetailDTO.getSubjectId());
    }

    public Exam toExamEntity(ExamDTO examDTO, Types types) throws RuntimeException {
        switch (types) {
            case ExamType1:
                return new Exam(examDTO.getBatchId());
            case ExamType2:
                return new Exam(examDTO.getExamId(), examDTO.getSubjectId(), examDTO.getBatchId(), examDTO.getDescription(), examDTO.getExamDate());
            case ExamType3:
                return new Exam(examDTO.getExamId(), examDTO.getSubjectId(), examDTO.getBatchId(), examDTO.getDescription(), examDTO.getExamDate(), examDTO.getLab(), examDTO.getTime());
            case ExamType4:
                return new Exam(examDTO.getExamId(), examDTO.getBatchId());
            default:
                throw new RuntimeException("Exam Type does not match");
        }

    }

    public RegistrationExamResultDTO toRegistrationExamResultDTO(RegistrationExamResult registrationExamResult) {
        return new RegistrationExamResultDTO(registrationExamResult.getExamId(), registrationExamResult.getRegistrationId(), registrationExamResult.getMark(), registrationExamResult.getResult());
    }

    public RegistrationExamResult toRegistrationExamResultsEntity(RegistrationExamResultDTO registrationExamResultDTO, Types type) {
        switch (type) {
            case RegistrationExamResultType1:
                return new RegistrationExamResult(registrationExamResultDTO.getExamId(), registrationExamResultDTO.getRegistrationId());
            case RegistrationExamResultType2:
                return new RegistrationExamResult(registrationExamResultDTO.getExamId(), registrationExamResultDTO.getRegistrationId(), registrationExamResultDTO.getMark(), registrationExamResultDTO.getResult());
            default:
                throw new RuntimeException("RegistrationExamResult Type does not match");
        }
    }

    public SystemUser toSystemUserEntity(SystemUserDTO systemUserDTO) {
        return new SystemUser(
                systemUserDTO.getUserName(), systemUserDTO.getPassword(), systemUserDTO.getPasswordHint()
        );
    }

    public SystemUserDTO toSystemUserDTO(SystemUser systemUser) {
        return new SystemUserDTO(
                systemUser.getUserName(), systemUser.getPassword(), systemUser.getPasswordHint()
        );
    }

    public AttendanceDTO toAttendanceDTO(Attendance attendance) {
        return new AttendanceDTO(attendance.getRegistrationId(), attendance.getDate(), attendance.getBatchId(), attendance.getStatus());
    }

    public Attendance toAttendanceEntity(AttendanceDTO attendance) {
        return new Attendance(attendance.getRegistrationId(), attendance.getDate(), attendance.getBatchId(), attendance.getStatus());
    }
}
