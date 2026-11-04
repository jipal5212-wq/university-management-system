CREATE DATABASE IF NOT EXISTS University_Assignment;
USE University_Assignment;

CREATE TABLE department (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(100) NOT NULL,
    building VARCHAR(100),
    budget DECIMAL(12, 2)
);

CREATE TABLE teacher (
    teacher_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    designation VARCHAR(100),
    experience INT,
    salary DECIMAL(10, 2),
    dept_id INT,
    CONSTRAINT fk_teacher_department
        FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);

CREATE TABLE student (
    student_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    city VARCHAR(100),
    admission_year INT,
    cgpa DECIMAL(4, 2),
    dept_id INT,
    CONSTRAINT fk_student_department
        FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);

CREATE TABLE course (
    course_id INT PRIMARY KEY,
    course_title VARCHAR(150) NOT NULL,
    credits INT,
    dept_id INT,
    CONSTRAINT fk_course_department
        FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);

CREATE TABLE teaches (
    teacher_id INT,
    course_id INT,
    PRIMARY KEY (teacher_id, course_id),
    CONSTRAINT fk_teaches_teacher
        FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id),
    CONSTRAINT fk_teaches_course
        FOREIGN KEY (course_id) REFERENCES course(course_id)
);

CREATE TABLE enrollment (
    student_id INT,
    course_id INT,
    marks DECIMAL(5, 2),
    attendance DECIMAL(5, 2),
    PRIMARY KEY (student_id, course_id),
    CONSTRAINT fk_enrollment_student
        FOREIGN KEY (student_id) REFERENCES student(student_id),
    CONSTRAINT fk_enrollment_course
        FOREIGN KEY (course_id) REFERENCES course(course_id)
);

INSERT INTO department (dept_id, dept_name, building, budget) VALUES
(1, 'Computer Science', 'Tech Block', 2500000.00),
(2, 'Electronics', 'Innovation Block', 2200000.00),
(3, 'Commerce', 'Business Block', 1500000.00),
(4, 'Mathematics', 'Science Block', 1200000.00),
(5, 'Law', 'Knowledge Block', 1000000.00);

INSERT INTO teacher
    (teacher_id, name, designation, experience, salary, dept_id)
VALUES
(101, 'Dr. Aditi Sharma', 'Professor', 12, 160000.00, 1),
(102, 'Mr. Rohan Mehta', 'Associate Professor', 9, 125000.00, 1),
(103, 'Ms. Neha Kapoor', 'Assistant Professor', 5, 85000.00, 2),
(104, 'Dr. Kabir Singh', 'Professor', 15, 180000.00, 2),
(105, 'Mr. Arjun Verma', 'Lecturer', 3, 65000.00, 3),
(106, 'Ms. Isha Gupta', 'Associate Professor', 7, 110000.00, 3),
(107, 'Dr. Meera Rao', 'Assistant Professor', 10, 100000.00, 4);

INSERT INTO student
    (student_id, name, city, admission_year, cgpa, dept_id)
VALUES
(201, 'Aanya Gupta', 'Ghaziabad', 2022, 8.70, 1),
(202, 'Vivaan Sharma', 'Ghaziabad', 2023, 7.90, 1),
(203, 'Saanvi Verma', 'Ghaziabad', 2024, 9.10, 1),
(204, 'Aarav Singh', 'Noida', 2022, 8.20, 1),
(205, 'Anaya Patel', 'Delhi', 2023, 8.60, 2),
(206, 'Reyansh Kumar', 'Delhi', 2024, 7.40, 2),
(207, 'Diya Mehta', 'Noida', 2023, 9.00, 2),
(208, 'Arjun Rao', 'Lucknow', 2022, 6.80, 3),
(209, 'Myra Jain', 'Lucknow', 2024, 8.10, 3),
(210, 'Kabir Das', 'Ghaziabad', 2022, 7.20, 3),
(211, 'Ira Malhotra', 'Jaipur', 2024, 8.40, 5);

INSERT INTO course (course_id, course_title, credits, dept_id) VALUES
(1001, 'Data Structures', 4, 1),
(1002, 'Database Systems', 4, 1),
(2001, 'Digital Circuits', 3, 2),
(2002, 'Signal Processing', 3, 2),
(3001, 'Financial Accounting', 3, 3),
(5001, 'Introduction to Law', 3, 5);

INSERT INTO teaches (teacher_id, course_id) VALUES
(101, 1001),
(101, 1002),
(102, 1002),
(103, 2001),
(104, 2002),
(105, 3001);

INSERT INTO enrollment (student_id, course_id, marks, attendance) VALUES
(201, 1001, 88, 90),
(201, 1002, 92, 85),
(202, 1001, 76, 78),
(202, 2001, 83, 86),
(203, 1002, 81, 74),
(204, 2001, 69, 80),
(205, 2001, 85, 92),
(206, 2002, 91, 88),
(207, 3001, 78, 76),
(208, 3001, 88, 82),
(209, 1001, 72, 69),
(209, 2002, 80, 75);
