-- Q2: Display names and cities for students living in Ghaziabad.
-- Practice: WHERE filters rows; SELECT chooses the columns shown.
-- Check: 4 rows; every city should be Ghaziabad.
SELECT name, city
FROM University_Assignment.student
WHERE city = 'Ghaziabad';
