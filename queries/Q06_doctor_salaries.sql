-- Q6: Display Dr.-named teachers, highest salary first.
-- Practice: LIKE matches a text pattern; % means any following characters.
-- Check: 3 rows, ordered by salary from highest to lowest.
SELECT name, salary
FROM University_Assignment.teacher
WHERE name LIKE 'Dr.%'
ORDER BY salary DESC;
