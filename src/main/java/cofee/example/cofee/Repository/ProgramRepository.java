
package cofee.example.cofee.Repository;

import cofee.example.cofee.Entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    // You can add custom queries later if needed
}