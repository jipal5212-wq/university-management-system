package edu.sharda.university.repository;

import edu.sharda.university.model.Enrollment;
import edu.sharda.university.model.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {
}
