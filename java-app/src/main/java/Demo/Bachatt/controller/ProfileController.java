package Demo.Bachatt.controller;

import Demo.Bachatt.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(){
        return  profileService.getUserDetails();
    }

    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteProfile(){
        return profileService.deleteUser();
    }

    @PostMapping("/profile")
    public ResponseEntity<?> updateUser(@RequestBody Map<String, String> payload){
        return profileService.updateUser(payload);
    }


}
