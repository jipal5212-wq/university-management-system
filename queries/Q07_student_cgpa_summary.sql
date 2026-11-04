-- Q7: Show student count and highest, lowest, and average CGPA.
-- Practice: aggregate functions COUNT, MAX, MIN, and AVG.
-- Check: 1 summary row; student count is 11.
SELECT COUNT(*) AS total_students,
       MAX(cgpa) AS highest_cgpa,
       MIN(cgpa) AS lowest_cgpa,
       AVG(cgpa) AS average_cgpa
FROM University_Assignment.student;
