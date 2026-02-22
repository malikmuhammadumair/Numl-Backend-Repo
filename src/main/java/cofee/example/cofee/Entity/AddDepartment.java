package cofee.example.cofee.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "add_department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String departmentname;

    @Column(nullable = false)
    private String slug;

    @Column(length = 1000)
    private String description;
}