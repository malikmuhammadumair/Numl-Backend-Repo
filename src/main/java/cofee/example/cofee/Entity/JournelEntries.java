package cofee.example.cofee.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "JournelEntries", schema = "numl")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JournelEntries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String program;

    @Column(nullable = false)
    private String semester;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private String examtype;

    @Column(nullable = false)
    private String course;

    @Column(nullable = false)
    private String teachername;

    @Column(nullable = false)
    private String tags;

    @Column(nullable = false)
    private String status="pending";

    @Column(nullable = true)
    private String publicId; // Cloudinary image ID

    @Column(nullable = true)
    private String url; // Cloudinary secure URL
       @Column(nullable = true)
    private Boolean trending=false; // Cloudinary secure URL


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private UserEntity user;
}
