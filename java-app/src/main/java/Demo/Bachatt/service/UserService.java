package Demo.Bachatt.service;
import Demo.Bachatt.model.User;
import Demo.Bachatt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByPhone(long number){
        return userRepository.findByPhone(number);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findByUid(int uid){
        return userRepository.findById(uid).orElse(null);
    }

    public User editUser(User user){
        return Optional.of(userRepository.save(user)).orElse(null);
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }
}