-- Q19: Display departments that have students and each student count.
-- Practice: INNER JOIN and GROUP BY department.
-- Check: 4 rows; departments without students are excluded by INNER JOIN.
SELECT d.dept_name, COUNT(s.student_id) AS student_count
FROM University_Assignment.department AS d
INNER JOIN University_Assignment.student AS s
    ON s.dept_id = d.dept_id
GROUP BY d.dept_id, d.dept_name;
