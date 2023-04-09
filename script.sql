create database project;
use project;
create table if not exists course
(
    id       varchar(10) not null
        primary key,
    name     varchar(50) null,
    duration varchar(50) null
)
    collate = utf8mb4_0900_ai_ci;

create table if not exists batch
(
    batchID           varchar(10) not null,
    batch_no          int         not null,
    courseId          varchar(10) not null,
    fee               double      null,
    start_date        date        null,
    maximum_std_count int         null,
    primary key (batchID, batch_no, courseId),
    constraint batch_ibfk_1
        foreign key (courseId) references course (id)
)
    collate = utf8mb4_0900_ai_ci;

create index courseId
    on batch (courseId);

create table if not exists gardian
(
    id           varchar(15) not null
        primary key,
    name         varchar(45) null,
    address      varchar(50) null,
    mobile       varchar(15) null,
    email        varchar(80) null,
    designation  varchar(50) null,
    workingPlace varchar(60) null
)
    collate = utf8mb4_0900_ai_ci;

create table if not exists inquiry
(
    studentID varchar(20)                         not null
        primary key,
    name      varchar(20)                         null,
    city      varchar(20)                         null,
    email     varchar(60)                         null,
    mobile    varchar(15)                         null,
    date      varchar(20)                         null,
    gender    varchar(10)                         null,
    status    varchar(20) default 'not registerd' null
)
    collate = utf8mb4_0900_ai_ci;

create table if not exists iqTest
(
    id     varchar(10) not null
        primary key,
    date   varchar(15) null,
    time   varchar(15) null,
    lab    varchar(10) null,
    amount double      null
)
    collate = utf8mb4_0900_ai_ci;

create table if not exists inquiry_iqTest_Detail
(
    studentID varchar(20)                         not null,
    testID    varchar(10)                         not null,
    result    varchar(20) default 'not published' null,
    name      varchar(45)                         null,
    primary key (studentID, testID),
    constraint inquiry_iqTest_Detail_ibfk_1
        foreign key (studentID) references inquiry (studentID)
            on update cascade on delete cascade,
    constraint inquiry_iqTest_Detail_ibfk_2
        foreign key (testID) references iqTest (id)
            on update cascade on delete cascade
)
    collate = utf8mb4_0900_ai_ci;

create index testID
    on inquiry_iqTest_Detail (testID);

create table if not exists loginCredential
(
    userName     varchar(50) null,
    password     varchar(50) null,
    passwordHint varchar(50) null
)
    collate = utf8mb4_0900_ai_ci;

create table if not exists registration
(
    registration_id varchar(20)                  not null
        primary key,
    nic_num         varchar(20)                  null,
    batchID         varchar(10)                  null,
    courseID        varchar(10)                  null,
    gardian_id      varchar(10)                  null,
    full_name       varchar(60)                  null,
    address         varchar(100)                 null,
    city            varchar(40)                  null,
    postal_code     varchar(15)                  null,
    mobile_number   varchar(15)                  null,
    email           varchar(45)                  null,
    dob             date                         null,
    gender          enum ('Male', 'Female')      not null,
    school          varchar(45)                  null,
    higherEDU       varchar(50)                  not null,
    status          varchar(50) default 'Active' null,
    constraint registration_ibfk_1
        foreign key (courseID) references course (id)
            on update cascade on delete cascade,
    constraint registration_ibfk_2
        foreign key (batchID) references batch (batchID)
            on update cascade on delete cascade,
    constraint registration_ibfk_3
        foreign key (nic_num) references inquiry (studentID)
            on update cascade on delete cascade,
    constraint registration_ibfk_4
        foreign key (gardian_id) references gardian (id)
            on update cascade on delete cascade
)
    collate = utf8mb4_0900_ai_ci;

create table if not exists payments
(
    paymentId       varchar(25)                                not null
        primary key,
    registration_id varchar(20)                                null,
    type            enum ('registration', 'fee') default 'fee' null,
    remark          varchar(45)                                null,
    amount          double                                     null,
    date            date                                       null,
    constraint payments_ibfk_1
        foreign key (registration_id) references registration (registration_id)
)
    collate = utf8mb4_0900_ai_ci;

create index registration_id
    on payments (registration_id);

create index batchID
    on registration (batchID);

create index courseID
    on registration (courseID);

create index gardian_id
    on registration (gardian_id);

create index nic_num
    on registration (nic_num);

create table if not exists subject
(
    id             varchar(10) not null
        primary key,
    name           varchar(30) null,
    learning_hours varchar(15) null
)
    collate = utf8mb4_0900_ai_ci;

create table if not exists course_subject_detail
(
    courseId  varchar(10) not null,
    subjectID varchar(10) not null,
    primary key (courseId, subjectID),
    constraint course_subject_detail_ibfk_1
        foreign key (courseId) references course (id)
            on update cascade on delete cascade,
    constraint course_subject_detail_ibfk_2
        foreign key (subjectID) references subject (id)
            on update cascade on delete cascade
)
    collate = utf8mb4_0900_ai_ci;

create index subjectID
    on course_subject_detail (subjectID);

create table if not exists exam
(
    id          varchar(10) not null
        primary key,
    subjectID   varchar(10) null,
    batchId     varchar(10) null,
    description varchar(20) null,
    date        date        null,
    lab         varchar(10) null,
    time        time        null,
    constraint exam_ibfk_1
        foreign key (subjectID) references subject (id)
            on update cascade on delete cascade,
    constraint exam_ibfk_2
        foreign key (batchId) references batch (batchID)
            on update cascade on delete cascade
)
    collate = utf8mb4_0900_ai_ci;

create index batchId
    on exam (batchId);

create index subjectID
    on exam (subjectID);

create table if not exists registration_exam_results
(
    exam_id         varchar(10) not null,
    registration_id varchar(20) not null,
    marks           int         null,
    result          varchar(3)  null,
    primary key (exam_id, registration_id),
    constraint registration_exam_results_ibfk_1
        foreign key (exam_id) references exam (id)
            on update cascade on delete cascade,
    constraint registration_exam_results_ibfk_2
        foreign key (registration_id) references registration (registration_id)
            on update cascade on delete cascade
)
    collate = utf8mb4_0900_ai_ci;

create index registration_id
    on registration_exam_results (registration_id);

create table if not exists test_Payment
(
    id        varchar(45) not null
        primary key,
    studentID varchar(20) null,
    date      varchar(15) null,
    remark    varchar(15) null,
    amount    double      null,
    iqTestId  varchar(10) null,
    constraint test_Payment_ibfk_1
        foreign key (iqTestId) references iqTest (id)
            on update cascade on delete cascade,
    constraint test_Payment_ibfk_2
        foreign key (studentID) references inquiry (studentID)
            on update cascade on delete cascade
)
    collate = utf8mb4_0900_ai_ci;

create index iqTestId
    on test_Payment (iqTestId);

create index studentID
    on test_Payment (studentID);


