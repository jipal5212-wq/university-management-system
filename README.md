# University Management System

A Spring Boot CRUD application for the six-table `University_Assignment` MySQL
schema. It includes a small browser interface and JSON REST APIs for all six
tables, including the composite-key `teaches` and `enrollment` tables.

## Requirements

- Java 17 or newer
- Maven 3.9 or newer
- The local MySQL server used for the DBMS assignment

## Run locally

The default connection is `localhost:3306`, database `University_Assignment`,
username `root`. Set your local MySQL password in the environment rather than
in a tracked configuration file. In PowerShell:

```powershell
$env:DB_PASSWORD = "your-local-mysql-password"
$env:DB_USERNAME = "root"
.\mvnw.cmd spring-boot:run
```

The app opens at <http://localhost:8080>. It validates the existing database
schema at startup and does not create, drop, or modify tables. If setting up a
fresh database, run [`University_Database_Assignment.sql`](./University_Database_Assignment.sql)
once before starting the app. That script also inserts sample rows.

You can override the database connection with `DB_URL`, `DB_USERNAME`, and
`DB_PASSWORD` environment variables. `DB_URL` defaults to:

```text
jdbc:mysql://localhost:3306/University_Assignment?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
```

## CRUD API

All IDs follow the assignment schema and are provided by the caller; they are
not auto-generated.

| Table | Base endpoint | Primary key |
|---|---|---|
| department | `/api/departments` | `deptId` |
| teacher | `/api/teachers` | `teacherId` |
| student | `/api/students` | `studentId` |
| course | `/api/courses` | `courseId` |
| teaches | `/api/teaches` | `teacherId`, `courseId` |
| enrollment | `/api/enrollments` | `studentId`, `courseId` |

Each resource supports `GET` collection, `GET` by key, `POST`, `PUT`, and
`DELETE`. Single-key resources use `/{id}` for item operations. Composite-key
resources use `/{firstId}/{secondId}`. For example:

```http
GET    /api/students
GET    /api/students/201
POST   /api/students
PUT    /api/students/201
DELETE /api/students/201
```

Example request bodies:

```json
{
  "studentId": 212,
  "name": "Student Name",
  "city": "Noida",
  "admissionYear": 2025,
  "cgpa": 8.25,
  "deptId": 1
}
```

```json
{
  "studentId": 212,
  "courseId": 1001,
  "marks": 88.5,
  "attendance": 90
}
```

Related IDs are checked before saving; a missing related record returns 404.
Conflicting IDs return 409, missing requested records return 404, and invalid
required fields return 400.

## Project structure

- `src/main/java/edu/sharda/university/model` — six JPA entity classes
- `src/main/java/edu/sharda/university/repository` — six Spring Data repositories
- `src/main/java/edu/sharda/university/controller` — six REST controllers
- `src/main/resources/static` — browser CRUD interface
- `University_Database_Assignment.sql` — original assignment schema and sample data
- `queries/` — numbered DBMS assignment queries

To follow and learn the API design, start with
[`DepartmentController`](./src/main/java/edu/sharda/university/controller/DepartmentController.java)
for a simple primary key and
[`EnrollmentController`](./src/main/java/edu/sharda/university/controller/EnrollmentController.java)
for a composite key and foreign-key validation.
