-- Q3: Display teachers with more than 8 years of experience.
-- Practice: WHERE with the greater-than comparison operator.
-- Check: 4 rows; each experience value should be greater than 8.
SELECT name, designation, experience
FROM University_Assignment.teacher
WHERE experience > 8;
