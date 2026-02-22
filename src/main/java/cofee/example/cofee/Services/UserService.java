package cofee.example.cofee.Services;

import cofee.example.cofee.Entity.JournelEntries;
import cofee.example.cofee.Entity.UserEntity;
import cofee.example.cofee.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordencorder = new BCryptPasswordEncoder();

    public UserEntity PostnewAll(UserEntity userEntity) {
        userEntity.setPassword(passwordencorder.encode(userEntity.getPassword()));
        userEntity.setRoles(Arrays.asList("USER"));
        return userRepository.save(userEntity);
    }

    //    for admin Creation g
    public UserEntity Addadmin(UserEntity userEntity) {
        userEntity.setPassword(passwordencorder.encode(userEntity.getPassword()));
        userEntity.setRoles(Arrays.asList("ADMIN"));
        return userRepository.save(userEntity);
    }

    public UserEntity PostAll(UserEntity userEntity) {
        return userRepository.save(userEntity);

    }

    public UserEntity PutALL(UserEntity userEntity) {
        userEntity.setPassword(passwordencorder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }
@Transactional
    public List<UserEntity> GetAll() {
        return userRepository.findAll();

    }
    public Long Getpapers() {
        return userRepository.countUploadedPapers();

    }
    public Long GetPendingRequests() {
        return userRepository.countpendindPapers();

    }


    public Optional<UserEntity> GetById(Long id) {
        return userRepository.findById(id);

    }

    public void DeleteByUserName(String username) {
        userRepository.deleteByEmail(username);

    }

    public UserEntity findbyusername(String username) {
        return userRepository.findByEmail(username);
    }


}




