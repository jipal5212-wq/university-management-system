-- Q12: Display cities with at least 3 students and their counts.
-- Practice: HAVING filters grouped results after GROUP BY.
-- Check: 1 row: Ghaziabad, with 4 students.
SELECT city, COUNT(*) AS student_count
FROM University_Assignment.student
GROUP BY city
HAVING COUNT(*) >= 3;
