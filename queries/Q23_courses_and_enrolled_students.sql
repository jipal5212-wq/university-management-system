-- Q23: Display every course and enrolled student ID, including empty courses.
-- Practice: RIGHT JOIN keeps every row from the right (course) table.
-- Check: 13 rows; Introduction to Law has NULL for student_id.
SELECT c.course_title, e.student_id
FROM University_Assignment.enrollment AS e
RIGHT JOIN University_Assignment.course AS c
    ON c.course_id = e.course_id;
