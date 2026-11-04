-- Q13: Display admission years whose average CGPA is greater than 8.
-- Practice: HAVING filters groups using an aggregate.
-- Check: 2 rows: 2023 and 2024.
SELECT admission_year
FROM University_Assignment.student
GROUP BY admission_year
HAVING AVG(cgpa) > 8;
