package Demo.Bachatt.controller;

import Demo.Bachatt.service.GoogleAuthService;
import Demo.Bachatt.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @Autowired
    private GoogleAuthService googleAuthService;

    @PostMapping("/otp/register")
    public ResponseEntity<?> generateOTP(@RequestBody Map<String, String> payload){
        return registrationService.generateOTP(payload);
    }

    @PostMapping("/otp/verify")
    public ResponseEntity<?> verify(@RequestBody Map<String, String> payload) {
        return registrationService.verify(payload);
    }

    @PostMapping("/oauth/validate")
    public ResponseEntity<?> validate(@RequestBody Map<String, String> payload) {
        System.out.println(payload);
        return googleAuthService.handleGoogleCallback(payload);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> payload) {
        return registrationService.createUser(payload);
    }
}
