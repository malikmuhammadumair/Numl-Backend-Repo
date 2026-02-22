package cofee.example.cofee.Repository;

import cofee.example.cofee.Entity.JournelEntries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JounelRepository extends JpaRepository<JournelEntries,Long> {
    @Query("""
SELECT j FROM JournelEntries j
WHERE (:department IS NULL OR j.department = :department)
AND (:program IS NULL OR j.program = :program)
AND (:course IS NULL OR j.course = :course)
AND (:semester IS NULL OR j.semester = :semester)
AND (:year IS NULL OR j.year = :year)
AND (:examtype IS NULL OR j.examtype = :examtype)
""")
    List<JournelEntries> filterEntries(
            @Param("department") String department,
            @Param("program") String program,
            @Param("course") String course,
            @Param("semester") String semester,
            @Param("year") String year,
            @Param("examtype") String examtype
    );

}
