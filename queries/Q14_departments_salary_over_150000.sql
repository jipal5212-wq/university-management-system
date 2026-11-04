-- Q14: Display departments whose teachers' combined salary exceeds 150000.
-- Practice: SUM calculates a group total; HAVING filters that total.
-- Check: 3 rows: departments 1, 2, and 3.
SELECT dept_id
FROM University_Assignment.teacher
GROUP BY dept_id
HAVING SUM(salary) > 150000;
