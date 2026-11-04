-- Q18: Display students, course titles, and marks for marks >= 80.
-- Practice: joining three tables and filtering joined rows.
-- Check: 8 rows; every marks value should be at least 80.
SELECT s.name AS student_name, c.course_title, e.marks
FROM University_Assignment.enrollment AS e
INNER JOIN University_Assignment.student AS s
    ON s.student_id = e.student_id
INNER JOIN University_Assignment.course AS c
    ON c.course_id = e.course_id
WHERE e.marks >= 80;
