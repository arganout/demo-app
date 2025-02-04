package Demo.Bachatt.service;

import Demo.Bachatt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
public class ProfileService {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseEntity<?> getUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User byEmail = userService.findByEmail(email);
        return ResponseEntity.ok(byEmail);
    }

    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User byEmail = userService.findByEmail(email);
        userService.deleteUser(byEmail);
        return ResponseEntity.ok("User Deleted");
    }

    public ResponseEntity<?> updateUser(Map<String, String> payload){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User editUser = userService.findByEmail(email);

        String name = payload.get("name");
        if(name!=null){
            editUser.setName(name);
        }

        String password = payload.get("password");
        if(password!=null){
            editUser.setPassword(passwordEncoder.encode(password));
        }

        String address = payload.get("password");
        if(address!=null){
            editUser.setPassword(address);
        }

        userService.saveUser(editUser);

        return  ResponseEntity.ok(userService.findByEmail(email));
    }
}
