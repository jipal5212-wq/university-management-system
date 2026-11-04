package edu.sharda.university.repository;

import edu.sharda.university.model.Teaches;
import edu.sharda.university.model.TeachesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachesRepository extends JpaRepository<Teaches, TeachesId> {
}
