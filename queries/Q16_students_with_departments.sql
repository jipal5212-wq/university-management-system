-- Q16: Display every student with their department name.
-- Practice: INNER JOIN keeps rows with matching department IDs in both tables.
-- Check: 11 rows.
SELECT s.name AS student_name, d.dept_name
FROM University_Assignment.student AS s
INNER JOIN University_Assignment.department AS d
    ON d.dept_id = s.dept_id;
