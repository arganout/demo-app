package Demo.Bachatt.service;

import Demo.Bachatt.model.User;
import Demo.Bachatt.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RegistrationService {

    @Autowired
    Utils utils;

    @Autowired
    UserService userService;

    @Autowired
    VerifyUserDetailsService verifyUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RedisService redisService;

    /**
    * Handles phone OTP Generation.
    *
    * - Validates the provided phone number.
    * - Generates a 6-digit OTP.
    * - Stores the OTP in a cache.
    * - Returns the OTP in the response.
    *
    * @param payload A map containing the phone number.
    * @return ResponseEntity<?> A response containing the generated OTP or an error message.
    */

    public ResponseEntity<?> generateOTP(Map<String, String> payload) {

        String phone = payload.get("phone");

        if(!utils.isValidNumber(phone)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Number Provided");
        }

        String reqOtp = utils.generateOTP(6);

        redisService.set(phone, reqOtp, 50000l);

        return ResponseEntity.ok(reqOtp);
    }


    /**
    * Verifies the OTP for phone authentication.
    *
    * - Validates the provided phone number.
    * - Checks if the OTP exists in the cache.
    * - Compares the provided OTP with the stored OTP.
    * - Returns a success or error response based on verification.
    *
    * @param payload A map containing the phone number and OTP.
    * @return ResponseEntity<?> A response indicating whether the OTP verification was successful or failed.
    */
    public ResponseEntity<?> verify(Map<String, String> payload) {
        String phone = payload.get("phone");
        String reqOtp = payload.get("otp");

        if (!utils.isValidNumber(phone)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Number Provided");
        }

        String otpCache = redisService.get(phone);

        if(!otpCache.equals(reqOtp)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }

        verifyUserDetailsService.saveVerifiedNumber(Long.parseLong(phone));

        return ResponseEntity.ok("OTP Verified");
    }

    public ResponseEntity<?> createUser(Map<String, String> payload) {

        List<String> errors = new ArrayList<>();

        String name = payload.get("name");
        String email = payload.get("email");
        String phone = payload.get("phone");
        String address = payload.get("address");
        String password = payload.get("password");

        if(name == null){
            errors.add("Please provide name");
        } else if (name.trim().isEmpty()) {
            errors.add("Invalid name Provided");
        }

        if(address == null){
            errors.add("Please provide address");
        } else if (address.trim().isEmpty()) {
            errors.add("Invalid name Address");
        }

        if(!utils.isValidNumber(phone)){
            errors.add("Invalid Number Provided");
        }

        if(!utils.isValidEmail(email)){
            errors.add("Email Address Is invalid");
        }

        if(!errors.isEmpty()){
            errors.add("Errors Found!");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errors);
        }

        if(userService.findByPhone(Long.parseLong(phone)).isPresent()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Phone Number Already Registered");
        }

        if(!verifyUserDetailsService.isVerifiedNumber(Long.parseLong(phone))){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Phone Number Not Verified");
        }

        if(userService.findByEmail(email) != null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email Address Already Registered");
        }

        if(!verifyUserDetailsService.isVerifiedEmail(email)){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email Address Not Verified");
        }


        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(Long.parseLong(phone));
        user.setAddress(address);
        user.setPassword(passwordEncoder.encode(password));

        userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User Created Successfully");

    }

}