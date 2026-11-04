-- Q22: Display every department and its students, including empty departments.
-- Practice: RIGHT JOIN keeps every row from the right (department) table.
-- Check: 12 rows; departments without students show NULL for student_name.
SELECT d.dept_name, s.name AS student_name
FROM University_Assignment.student AS s
RIGHT JOIN University_Assignment.department AS d
    ON d.dept_id = s.dept_id;
