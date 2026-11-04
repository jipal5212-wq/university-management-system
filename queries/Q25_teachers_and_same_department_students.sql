-- Q25: Match teachers and students in the same department, keeping unmatched rows.
-- Practice: emulate FULL OUTER JOIN with LEFT JOIN and UNION ALL.
-- MySQL does not support FULL OUTER JOIN directly.
-- Check: 22 rows; includes the Mathematics teacher with no students and
-- the Law student with no teacher.
SELECT t.name AS teacher_name, s.name AS student_name
FROM University_Assignment.teacher AS t
LEFT JOIN University_Assignment.student AS s
    ON s.dept_id = t.dept_id
UNION ALL
SELECT t.name AS teacher_name, s.name AS student_name
FROM University_Assignment.student AS s
LEFT JOIN University_Assignment.teacher AS t
    ON t.dept_id = s.dept_id
WHERE t.teacher_id IS NULL;
