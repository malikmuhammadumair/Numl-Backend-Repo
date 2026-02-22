package cofee.example.cofee.dto;


import lombok.Data;

@Data
public class FilterRequest {
    private String department;
    private String program;
    private String course;
    private String semester;
    private String year;
    private String examtype;
}
