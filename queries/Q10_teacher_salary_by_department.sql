-- Q10: Show teacher count and salary statistics for each teacher department.
-- Practice: GROUP BY with COUNT, MAX, MIN, and AVG.
-- Check: 4 rows, for departments that have teachers.
SELECT dept_id,
       COUNT(*) AS teacher_count,
       MAX(salary) AS highest_salary,
       MIN(salary) AS lowest_salary,
       AVG(salary) AS average_salary
FROM University_Assignment.teacher
GROUP BY dept_id;
