package cofee.example.cofee.Repository;

import cofee.example.cofee.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // Optional: you can add findByCode or other queries later
}