package cofee.example.cofee.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "programs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String duration;

    @Column(length = 1000)
    private String semester;
    @Column(length = 1000)
    private String description;
}