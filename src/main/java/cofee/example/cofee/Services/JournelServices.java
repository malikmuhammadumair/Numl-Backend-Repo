package cofee.example.cofee.Services;

import cofee.example.cofee.Entity.JournelEntries;
import cofee.example.cofee.Entity.UserEntity;
import cofee.example.cofee.Repository.JounelRepository;
import cofee.example.cofee.dto.FilterRequest;
import com.cloudinary.Cloudinary;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class JournelServices {

    @Autowired
    private JounelRepository coffeeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinary;

    // Save entry for a user
    @Transactional
    public JournelEntries saveEntry(JournelEntries journelEntries, String username) {
        UserEntity user = userService.findbyusername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }

        journelEntries.setUser(user);
        JournelEntries saved = coffeeRepository.save(journelEntries);

        user.getPapers().add(saved);
        userService.PostAll(user);

        return saved;
    }

    // Update an existing entry
    public JournelEntries updateEntry(JournelEntries journelEntries) {
        return coffeeRepository.save(journelEntries);
    }

    // Delete an entry by ID for a specific user
    @Transactional
    public boolean deleteById(Long id, String username) {
        UserEntity user = userService.findbyusername(username);
        boolean removed = user.getPapers().removeIf(x -> x.getId().equals(id));
        if (!removed) return false;

        userService.PostAll(user);
        coffeeRepository.deleteById(id);
        return true;
    }

    // Get all entries
    public List<JournelEntries> getAll() {
        return coffeeRepository.findAll();
    }

    // Get entry by ID
    public Optional<JournelEntries> getById(Long id) {
        return coffeeRepository.findById(id);
    }

    // Upload image to Cloudinary and create JournelEntries
    @Transactional
    public JournelEntries saveWithImage(
            JournelEntries entry,
            MultipartFile file,
            String username) {

        try {
            // 🚫 double safety
            if (file == null || file.isEmpty()) {
                throw new RuntimeException("Image file is required");
            }

            UserEntity user = userService.findbyusername(username);
            if (user == null) {
                throw new RuntimeException("User not found: " + username);
            }

            Map uploadResult = cloudinary.uploader()
                    .upload(file.getBytes(), Map.of("folder", "uploads"));

            entry.setPublicId((String) uploadResult.get("public_id"));
            entry.setUrl((String) uploadResult.get("secure_url"));
            entry.setUser(user);

            JournelEntries saved = coffeeRepository.save(entry);
            user.getPapers().add(saved);
            userService.PostAll(user);

            return saved;

        } catch (Exception e) {
            throw new RuntimeException("Failed to save journal entry", e);
        }
    }
    public List<JournelEntries> filter(FilterRequest req) {

        return coffeeRepository.filterEntries(
                req.getDepartment(),
                req.getProgram(),
                req.getCourse(),
                req.getSemester(),
                req.getYear(),
                req.getExamtype()
        );
    }


    public JournelEntries findJournalById(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    public JournelEntries saveJournal(JournelEntries entry) {
        return coffeeRepository.save(entry);
    }
    public List<JournelEntries> getRecent6Urls() {
        return coffeeRepository.findAll();
    }
    //    find trending papers for front page
public List<JournelEntries> getAllTrendingPapers() {
    return coffeeRepository.findAllTrending();
}
}

