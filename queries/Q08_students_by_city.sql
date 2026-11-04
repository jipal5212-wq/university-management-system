-- Q8: Count students in each city.
-- Practice: GROUP BY creates one group for each city.
-- Check: 5 rows, one for each city.
SELECT city, COUNT(*) AS student_count
FROM University_Assignment.student
GROUP BY city;
