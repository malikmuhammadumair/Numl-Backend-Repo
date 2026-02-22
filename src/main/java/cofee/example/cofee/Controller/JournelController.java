package cofee.example.cofee.Controller;

import cofee.example.cofee.Entity.JournelEntries;
import cofee.example.cofee.Entity.UserEntity;
import cofee.example.cofee.Services.JournelServices;
import cofee.example.cofee.Services.UserService;
import cofee.example.cofee.dto.FilterRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import cofee.example.cofee.dto.FilterRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journel")
@CrossOrigin("*")
public class JournelController {

    @Autowired
    private JournelServices journelServices;

    @Autowired
    private UserService userService;

    // Create entry with optional image
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<JournelEntries> postEntry(
            @RequestParam String title,
            @RequestParam String department,
            @RequestParam String program,
            @RequestParam String semester,
            @RequestParam String year,
            @RequestParam String examtype,
            @RequestParam String course,
            @RequestParam String teachername,
            @RequestParam String tags,


            // 🔥 FILE NOW REQUIRED
            @RequestParam("image") MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(null);
        }

        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();

        JournelEntries entry = new JournelEntries();
        entry.setTitle(title);
        entry.setDepartment(department);
        entry.setProgram(program);
        entry.setSemester(semester);
        entry.setYear(year);
        entry.setExamtype(examtype);
        entry.setCourse(course);
        entry.setTeachername(teachername);
        entry.setTags(tags);


        JournelEntries saved =
                journelServices.saveWithImage(entry, file, username);

        return ResponseEntity.ok(saved);
    }

    // Get all entries of logged-in user
    @Transactional
    @GetMapping
    public ResponseEntity<List<JournelEntries>> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userService.findbyusername(username);
        List<JournelEntries> entries = user.getPapers();

        if (entries != null && !entries.isEmpty()) {
            return ResponseEntity.ok(entries);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get entry by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userService.findbyusername(username);

        Optional<JournelEntries> entry = user.getPapers()
                .stream()
                .filter(x -> x.getId().equals(id))
                .findFirst();

        if (entry.isPresent()) {
            return ResponseEntity.ok(entry.get()); // JournelEntries
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Entry not found"); // String
        }
    }

    // Delete entry by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean deleted = journelServices.deleteById(id, username);

        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Update entry by ID
    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody JournelEntries body) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userService.findbyusername(username);

        Optional<JournelEntries> entryOpt = user.getPapers()
                .stream()
                .filter(x -> x.getId().equals(id))
                .findFirst();

        if (entryOpt.isPresent()) {
            JournelEntries existing = entryOpt.get();

            existing.setTitle(body.getTitle());
            existing.setDepartment(body.getDepartment());
            existing.setProgram(body.getProgram());
            existing.setSemester(body.getSemester());
            existing.setYear(body.getYear());
            existing.setExamtype(body.getExamtype());
            existing.setCourse(body.getCourse());
            existing.setTeachername(body.getTeachername());
            existing.setTags(body.getTags());


            journelServices.updateEntry(existing);

            return ResponseEntity.ok(existing);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entry not found");
        }
    }
    @PostMapping("/filter")
    public ResponseEntity<List<JournelEntries>> filter(@RequestBody FilterRequest req) {
        return ResponseEntity.ok(journelServices.filter(req));
    }



}
