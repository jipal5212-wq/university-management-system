-- Q11: Show enrolled student count and average marks for each enrolled course.
-- Practice: GROUP BY course_id with COUNT and AVG.
-- Check: 5 rows; courses without enrollments are not included.
SELECT course_id,
       COUNT(*) AS enrolled_student_count,
       AVG(marks) AS average_marks
FROM University_Assignment.enrollment
GROUP BY course_id;
