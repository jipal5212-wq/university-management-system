-- Q5: Display students in Delhi or Noida whose CGPA is above 8.
-- Practice: IN tests multiple values; AND combines conditions.
-- Check: 3 rows.
SELECT name, city, cgpa
FROM University_Assignment.student
WHERE city IN ('Delhi', 'Noida')
  AND cgpa > 8;
