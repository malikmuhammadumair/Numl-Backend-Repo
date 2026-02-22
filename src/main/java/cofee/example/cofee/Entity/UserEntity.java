package cofee.example.cofee.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UserEntities", schema = "numl")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
     private String name;

    @Column(nullable = false)
    private String password;
    @Column(length = 300)
    private String department;
    @Column(length = 300)
    private String program;
    @ElementCollection(fetch = FetchType.EAGER)
       @Column(nullable = false)
    private List<String> roles = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<JournelEntries> papers = new ArrayList<>();
}

