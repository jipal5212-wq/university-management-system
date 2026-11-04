-- Q15: For attendance >= 75, show each course's count and average marks,
-- keeping only courses whose qualifying students average above 75.
-- Practice: WHERE filters rows before grouping; HAVING filters groups after.
-- Check: 5 rows.
SELECT course_id,
       COUNT(*) AS qualifying_student_count,
       AVG(marks) AS average_marks
FROM University_Assignment.enrollment
WHERE attendance >= 75
GROUP BY course_id
HAVING AVG(marks) > 75;
