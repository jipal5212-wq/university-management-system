-- Q17: Display each teacher with every course they teach.
-- Practice: joining across a many-to-many link table (teaches).
-- Check: 6 teacher-course assignments.
SELECT t.name AS teacher_name, c.course_title
FROM University_Assignment.teacher AS t
INNER JOIN University_Assignment.teaches AS tc
    ON tc.teacher_id = t.teacher_id
INNER JOIN University_Assignment.course AS c
    ON c.course_id = tc.course_id;
