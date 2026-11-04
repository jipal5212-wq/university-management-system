# University database queries

Each `Qnn_*.sql` file contains one numbered assignment query, a short note about
the SQL concept it practices, and the expected result-row count for the provided
sample data.

## Run a query in VS Code

1. Connect to the **Sharda University** MySQL connection in SQLTools.
2. Open one `Qnn_*.sql` file from this folder.
3. Press **Ctrl+A** to select the file, then press **Ctrl+E** twice to run the
   selected query with SQLTools.
4. Read the result grid that opens. Check both the values and the row count
   against the comment at the top of that query file.
5. Repeat with the next question.

The setup script is `../University_Database_Assignment.sql`. It creates the
`University_Assignment` database, tables, and sample data. It has already been
run for this workspace; do not run it again, because its `CREATE TABLE` and
`INSERT` statements are not intended to be repeated.

## Verified result-row counts

| Question | Rows | Question | Rows | Question | Rows |
|---|---:|---|---:|---|---:|
| Q1 | 11 | Q10 | 4 | Q19 | 4 |
| Q2 | 4 | Q11 | 5 | Q20 | 5 |
| Q3 | 4 | Q12 | 1 | Q21 | 2 |
| Q4 | 7 | Q13 | 2 | Q22 | 12 |
| Q5 | 3 | Q14 | 3 | Q23 | 13 |
| Q6 | 3 | Q15 | 5 | Q24 | 12 |
| Q7 | 1 | Q16 | 11 | Q25 | 22 |
| Q8 | 5 | Q17 | 6 |  |  |
| Q9 | 3 | Q18 | 8 |  |  |

Q24 and Q25 use `LEFT JOIN` plus `UNION ALL` because MySQL does not support
`FULL OUTER JOIN` directly.
