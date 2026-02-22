package cofee.example.cofee.Repository;

import cofee.example.cofee.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByEmail(String name);
    UserEntity deleteByEmail(String name);
    @Query("SELECT COUNT(u.url) FROM JournelEntries u WHERE u.url IS NOT NULL")
    long countUploadedPapers();


    @Query("SELECT COUNT(u.status) FROM JournelEntries u WHERE u.status IS NOT NULL")
    long countpendindPapers();
}
