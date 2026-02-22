package cofee.example.cofee.Controller;

import cofee.example.cofee.Entity.UserEntity;
import cofee.example.cofee.Services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/get")
    public ResponseEntity<List<UserEntity>> getall() {
        List<UserEntity> coffeeEntities = userService.GetAll();
        if (coffeeEntities != null && !coffeeEntities.isEmpty()) {
            return ResponseEntity.ok(coffeeEntities);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserEntity> getbyid(@PathVariable Long id) {
        Optional<UserEntity> coffeeEntity = userService.GetById(id);
        return coffeeEntity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> deletebyusername() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        UserEntity userEntity = userService.findbyusername(authentication.getName());

        userService.DeleteByUserName(authentication.getName());
        return ResponseEntity.ok().build();


    }

    @PutMapping
    public ResponseEntity<UserEntity> Updatebycall(@RequestBody UserEntity userEntity) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String username = authentication.getName();
        UserEntity userindb = userService.findbyusername(username);


//        userindb.setEmail(userEntity.getEmail());
        userindb.setPassword(userEntity.getPassword());
        userindb.setName(userEntity.getName());
//        userindb.setRoles(userEntity.getRoles());
        userService.PostnewAll(userindb);
        return ResponseEntity.ok(userindb);
    }


}


