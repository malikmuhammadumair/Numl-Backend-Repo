package cofee.example.cofee.Controller;

import cofee.example.cofee.Entity.*;
import cofee.example.cofee.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private JournelServices journelServices;

    @GetMapping("/all_users")
    public ResponseEntity<?> getall() {
        List<UserEntity> all = userService.GetAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    //    getting all papers
    @GetMapping("/all_paperscount")
    public ResponseEntity<?> getallpapers() {
        long count = userService.Getpapers();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    //getpending papers
    @GetMapping("/all_pending")
    public ResponseEntity<?> getallpendingpapers() {
        long count = userService.GetPendingRequests();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PostMapping("/create_admin")
    public void createuser(@RequestBody UserEntity user) {
        userService.Addadmin(user);
    }

    @PutMapping("/pending/{id}")
    public ResponseEntity<JournelEntries> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        JournelEntries entry = journelServices.findJournalById(id);

        if (entry == null) {
            return ResponseEntity.notFound().build();
        }

        entry.setStatus(status); // "approved" or "rejected"
        JournelEntries updated = journelServices.saveJournal(entry);

        return ResponseEntity.ok(updated);
    }
//    Adddepartment logic
@Autowired
    private  AddDepartmentService service;



    // CREATE
    @PostMapping("/department")
    public ResponseEntity<?> addDepartment(@RequestBody AddDepartment dept) {
        return ResponseEntity.ok(service.addDepartment(dept));
    }

    // GET ALL
    @GetMapping("/department")
    public ResponseEntity<List<AddDepartment>> getAll() {
        return ResponseEntity.ok(service.getAllDepartments());
    }

    // UPDATE
    @PutMapping("/department/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AddDepartment dept) {
        AddDepartment updated = service.updateDepartment(id, dept);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE
    @DeleteMapping("/department/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteDepartment(id));
    }
//    add programms

    @Autowired
    private AddProgramService addProgramService;

    // CREATE Program
    @PostMapping("/program")
    public ResponseEntity<Program> addProgram(@RequestBody Program program) {
        return ResponseEntity.ok(addProgramService.addProgram(program));
    }

    // GET ALL Programs
    @GetMapping("/program")
    public ResponseEntity<List<Program>> getAllPrograms() {
        return ResponseEntity.ok(addProgramService.getAllPrograms());
    }

    // UPDATE Program
    @PutMapping("/program/{id}")
    public ResponseEntity<Program> updateProgram(@PathVariable Long id, @RequestBody Program program) {
        Program updated = addProgramService.updateProgram(id, program);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    // DELETE Program
    @DeleteMapping("/program/{id}")
    public ResponseEntity<String> deleteProgram(@PathVariable Long id) {
        return ResponseEntity.ok(addProgramService.deleteProgram(id));
    }

//    add courses

    @Autowired
    private CourseService courseService;

    // CREATE
    @PostMapping("/course")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.addCourse(course));
    }

    // GET ALL
    @GetMapping("/course")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    // UPDATE
    @PutMapping("course/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        Course updated = courseService.updateCourse(id, course);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    // DELETE
    @DeleteMapping("course/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.deleteCourse(id));
    }
}

