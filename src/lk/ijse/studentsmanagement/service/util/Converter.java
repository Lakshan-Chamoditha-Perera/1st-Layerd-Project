package lk.ijse.studentsmanagement.service.util;

import lk.ijse.studentsmanagement.dto.*;
import lk.ijse.studentsmanagement.entity.*;

import java.sql.Date;

public class Converter {
    public InquiryDTO toInquiryDTO(Inquiry entity) {
        return new InquiryDTO(entity.getStudentID(), entity.getName(), entity.getCity(), entity.getEmail(), entity.getMobile(), entity.getDate(), entity.getGender(), entity.getStatus());
    }

    public Inquiry toInquiryEntity(InquiryDTO dto, Types type) throws RuntimeException {
        switch (type) {
            case InquiryType1:
                return new Inquiry(dto.getStudentID(), dto.getName(), dto.getCity(), dto.getEmail(), dto.getMobile(), dto.getDate(), dto.getGender(), dto.getStatus());
            case InquiryType2:
                return new Inquiry(dto.getStudentID());
            default:
                throw new RuntimeException("Inquiry type does not match");
        }
    }

    // public TestPaymentDTO toTestPaymentDTO(TestPayment entity) {
    //   return null;
    // }

    public TestPayment toTestPaymentEntity(TestPaymentDTO dto) {
        return new TestPayment(dto.getId(), dto.getStudentID(), dto.getDate(), dto.getRemark(), dto.getAmount(), dto.getIqTestId());
    }

    public InquiryIQTestDetail toInquiryIqTestDetailEntity(InquiryIQTestDetailDTO dto, Types type) {
        switch (type) {
            case InquiryIQTestDetailType1:
                return new InquiryIQTestDetail(dto.getStudentId(), dto.getTestId(), dto.getResult(), dto.getName());
            case InquiryIQTestDetailType2:
                return new InquiryIQTestDetail(dto.getStudentId(), dto.getTestId(), dto.getResult());
            default:
                throw new RuntimeException("Inquiry type does not match");
        }
    }

    public IQTestDTO toIQTestDTO(IQTest iqTest) {
        return new IQTestDTO(iqTest.getId(), iqTest.getDate(), iqTest.getTime(), iqTest.getLab(), iqTest.getAmount());
    }

    public ExamDTO toExamDTO(Exam exam) {
        return new ExamDTO(exam.getExamId(), exam.getSubjectId(), exam.getBatchId(), exam.getDescription(), exam.getExamDate(), exam.getLab(), exam.getTime());
    }

    public CourseDTO toCourseDTO(Course course) {
        return new CourseDTO(course.getId(), course.getName(), course.getDuration());
    }

    public BatchDTO toBatchDTO(Batch lastOngoingBatch, Types type) throws RuntimeException {
        switch(type){
            case BatchType1: return new BatchDTO(lastOngoingBatch.getId(), lastOngoingBatch.getBatchNo(), lastOngoingBatch.getCourseId(), lastOngoingBatch.getFee(), lastOngoingBatch.getStarting_date(), lastOngoingBatch.getMaxStdCount());
            case BatchType2: return new BatchDTO(lastOngoingBatch.getBatchNo());
            default: throw new RuntimeException("Batch type does not match");
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

    public Registration toRegistrationEntity(RegistrationDTO registration, Types type) throws RuntimeException {
        switch (type) {
            case RegistrationType1:
                return new Registration(registration.getRegistrationId(), registration.getNic(), registration.getBatchId(), registration.getCourseId(), registration.getGardianId(), registration.getName(), registration.getAddress(), registration.getCity(), registration.getPostalCode(), registration.getMobile(), registration.getEmail(), registration.getDob(), registration.getGender(), registration.getSchool(), registration.getHigherEDU(), registration.getStatus());
            case RegistrationType2:
                return new Registration(registration.getRegistrationId());
            case RegistrationType3:
                return new Registration(registration.getRegistrationId(), registration.getName(), registration.getAddress(), registration.getCity(), registration.getPostalCode(), registration.getMobile(), registration.getEmail(), registration.getDob(), registration.getSchool());
            default:
                throw new RuntimeException("Registration type does not match");
        }
    }

    public Payment toPaymentEntity(PaymentDTO payment) {
        return new Payment(payment.getId(), payment.getRegistrationId(), payment.getType(), payment.getRemark(), payment.getAmount(), payment.getDate());
    }

    public GuardianDTO toGuardianDTO(Guardian guardian) {
        return new GuardianDTO(guardian.getId(), guardian.getName(), guardian.getAddress(), guardian.getMobile(), guardian.getEmail(), guardian.getDesignation(), guardian.getWorkingPlace());
    }

    public InquiryIQTestDetailDTO toInquiryIQTestDetailDTO(InquiryIQTestDetail inquiryIQTestDetail, Types type) throws RuntimeException {
        switch(type) {
            case InquiryIQTestDetailType1:return new InquiryIQTestDetailDTO(inquiryIQTestDetail.getStudentId(), inquiryIQTestDetail.getTestId(), inquiryIQTestDetail.getResult(), inquiryIQTestDetail.getName());
            default:   throw new RuntimeException("InquiryIQTestDetail does not match");
        }}

    public PaymentDTO toPaymentDTO(Payment payment) {
        return new PaymentDTO(payment.getId(), payment.getRegistrationId(), payment.getType(), payment.getRemark(), payment.getAmount(), payment.getDate());
    }

    public RegistrationDTO toRegistrationDTO(Registration registration) {
        return new RegistrationDTO(registration.getRegistrationId(), registration.getNic(), registration.getBatchId(), registration.getCourseId(), registration.getGardianId(), registration.getName(), registration.getAddress(), registration.getCity(), registration.getPostalCode(), registration.getMobile(), registration.getEmail(), registration.getDob(), registration.getGender(), registration.getSchool(), registration.getHigherEDU(), registration.getStatus());
    }

    public IQTest toIQTestEntity(IQTestDTO iqTestDTO) {
        return new IQTest(iqTestDTO.getId());
    }

    public Course toCourseEntity(CourseDTO courseDTO, Types type) {
        switch (type){
            case CourseType1: return new Course(courseDTO.getId());
            default:
                throw new RuntimeException("Registration type does not match");
        }
    }

    public Batch toBatchEntity(BatchDTO dto) {
        return new Batch(
                dto.getId(),
                dto.getBatchNo(),
                dto.getCourseId(),
                dto.getFee(),
                dto.getStarting_date(),
                dto.getMaxStdCount()
        );
    }
}
