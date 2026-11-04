-- Q24: Match teachers with courses offered by their department.
-- Practice: emulate FULL OUTER JOIN with LEFT JOIN and UNION ALL.
-- MySQL does not support FULL OUTER JOIN directly.
-- Check: 12 rows; includes the teacher with no department course and
-- the Law course whose department has no teacher (NULL on the other side).
SELECT t.name AS teacher_name, c.course_title
FROM University_Assignment.teacher AS t
LEFT JOIN University_Assignment.course AS c
    ON c.dept_id = t.dept_id
UNION ALL
SELECT t.name AS teacher_name, c.course_title
FROM University_Assignment.course AS c
LEFT JOIN University_Assignment.teacher AS t
    ON t.dept_id = c.dept_id
WHERE t.teacher_id IS NULL;
