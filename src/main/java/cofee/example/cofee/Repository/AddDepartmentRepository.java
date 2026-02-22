package cofee.example.cofee.Repository;

import cofee.example.cofee.Entity.AddDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddDepartmentRepository extends JpaRepository<AddDepartment, Long> {
}
