-- Q9: Show average CGPA for each admission year.
-- Practice: GROUP BY with AVG.
-- Check: 3 rows, for admission years 2022, 2023, and 2024.
SELECT admission_year, AVG(cgpa) AS average_cgpa
FROM University_Assignment.student
GROUP BY admission_year;
