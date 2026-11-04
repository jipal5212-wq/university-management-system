-- Q4: Display students with CGPA from 7.5 through 9.0, including both limits.
-- Practice: BETWEEN includes both boundary values.
-- Check: 7 rows; each CGPA should be between 7.5 and 9.0.
SELECT name, cgpa
FROM University_Assignment.student
WHERE cgpa BETWEEN 7.5 AND 9.0;
