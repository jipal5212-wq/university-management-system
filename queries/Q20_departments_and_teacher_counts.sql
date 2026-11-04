-- Q20: Display every department, including those without teachers.
-- Practice: LEFT JOIN keeps every row from the left (department) table.
-- Check: 5 rows; Law and Mathematics have 0 teachers.
SELECT d.dept_name, COUNT(t.teacher_id) AS teacher_count
FROM University_Assignment.department AS d
LEFT JOIN University_Assignment.teacher AS t
    ON t.dept_id = d.dept_id
GROUP BY d.dept_id, d.dept_name;
