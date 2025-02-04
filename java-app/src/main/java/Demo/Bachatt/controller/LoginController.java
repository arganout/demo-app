package Demo.Bachatt.controller;

import Demo.Bachatt.model.User;
import Demo.Bachatt.service.JWTService;
import Demo.Bachatt.service.LoginService;
import Demo.Bachatt.service.RedisService;
import Demo.Bachatt.service.UserService;
import Demo.Bachatt.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/otp/send")
    public ResponseEntity<?> getOtp(@RequestBody Map<String, String> payload){
        return loginService.generateOTP(payload);
    }

    @PostMapping("/otp/login")
    public ResponseEntity<String> phoneLogin(@RequestBody Map<String, String> payload){
        User user = userService.findByPhone(Long.parseLong(payload.get("phone"))).orElse(null);

        if ( user == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User Not Found");
        }


        return ResponseEntity.ok(jwtService.generateToken(user.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<String> emailLogin(@RequestBody Map<String, String> payload){

        User user = userService.findByEmail(payload.get("email"));
        String password = payload.get("password");

        if(password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Provide Password!");
        }

        if(user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Not Found!");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect Password Provided");
        }

        return ResponseEntity.ok(jwtService.generateToken(user.getEmail()));
    }

}
