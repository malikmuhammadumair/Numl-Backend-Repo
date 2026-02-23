package cofee.example.cofee.Controller;

import cofee.example.cofee.Entity.JournelEntries;
import cofee.example.cofee.Entity.UserEntity;
import cofee.example.cofee.Services.JournelServices;
import cofee.example.cofee.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/public")
@RestController
@CrossOrigin("*")
public class PublicRequest {
    @Autowired
    private UserService userService;
    @Autowired
    private JournelServices journelServices;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/post")
    public ResponseEntity<UserEntity> postall(@RequestBody UserEntity coffeeEntity) {
        try {
            UserEntity coffeeEntity1 = userService.PostnewAll(coffeeEntity);
            return ResponseEntity.ok(coffeeEntity1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/check")
    public ResponseEntity<?> CheckInfo(@RequestBody UserEntity userEntity) {
        // Find user by email
        UserEntity user = userService.findbyusername(userEntity.getEmail());

        if (user == null) {
            // User not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else if (passwordEncoder.matches(userEntity.getPassword(), user.getPassword())) {
            // Password matches, return roles
            return ResponseEntity.ok(user.getRoles());
        } else {
            // Password mismatch
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }
    }
    @GetMapping("/recent")
    public ResponseEntity<?> getRecentUrls() {
        List<JournelEntries> all = journelServices.getRecent6Urls();

        if (all != null && !all.isEmpty()) {
            // last 6 entries
            List<JournelEntries> lastSix = all.stream()
                    .sorted((a, b) -> b.getId().compareTo(a.getId())) // latest first
                    .limit(6)
                    .toList();

            return new ResponseEntity<>(lastSix, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//    Front page trending corse heart logic
    @PutMapping("/trending/{id}")
    public ResponseEntity<JournelEntries> updatetrend(
            @PathVariable Long id,
            @RequestParam boolean heart) {

        JournelEntries entry = journelServices.findJournalById(id);

        if (entry == null) {
            return ResponseEntity.notFound().build();
        }

        entry.setTrending(heart); // "true" ya "false"
        JournelEntries updated = journelServices.saveJournal(entry);

        return ResponseEntity.ok(updated);
    }
// get trending pages
@GetMapping("/trending")
public ResponseEntity<List<JournelEntries>> getTrendingPapers() {
    List<JournelEntries> all = journelServices.getAllTrendingPapers();

    if (all != null && !all.isEmpty()) {
        // filter trending + latest 6 entries
        List<JournelEntries> lastSix = all.stream()
             
                .sorted((a, b) -> b.getId().compareTo(a.getId())) // latest first
                .limit(6)
                .toList();

        return new ResponseEntity<>(lastSix, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
}

