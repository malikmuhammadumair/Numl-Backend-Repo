package cofee.example.cofee.Services;

import cofee.example.cofee.Entity.UserEntity;
import cofee.example.cofee.Repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user=userRepository.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("User not Found"+username);
        }
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
//                .roles("USER")
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }

}
