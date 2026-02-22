package cofee.example.cofee.Services;

import cofee.example.cofee.Entity.Course;
import cofee.example.cofee.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository repository;

    // CREATE course
    public Course addCourse(Course course) {
        course.setId(null); // always new
        return repository.save(course);
    }

    // GET all courses
    public List<Course> getAllCourses() {
        return repository.findAll();
    }

    // UPDATE course
    public Course updateCourse(Long id, Course updatedCourse) {
        Optional<Course> optional = repository.findById(id);
        if (optional.isPresent()) {
            Course course = optional.get();
            course.setName(updatedCourse.getName());
            course.setCode(updatedCourse.getCode());
            course.setCreditHours(updatedCourse.getCreditHours());
            course.setDescription(updatedCourse.getDescription());
            return repository.save(course);
        }
        return null;
    }

    // DELETE course
    public String deleteCourse(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Course deleted successfully";
        }
        return "Course not found";
    }
}
