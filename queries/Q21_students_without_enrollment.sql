-- Q21: Display students who are not enrolled in any course.
-- Practice: LEFT JOIN plus IS NULL finds unmatched rows.
-- Check: 2 students.
SELECT s.student_id, s.name
FROM University_Assignment.student AS s
LEFT JOIN University_Assignment.enrollment AS e
    ON e.student_id = s.student_id
WHERE e.student_id IS NULL;
