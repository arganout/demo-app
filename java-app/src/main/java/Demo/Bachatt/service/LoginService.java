package Demo.Bachatt.service;

import Demo.Bachatt.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    Utils utils;

    @Autowired
    RedisService redisService;

    public ResponseEntity<?> generateOTP(Map<String, String> payload) {

        String phone = payload.get("phone");

        if(!utils.isValidNumber(phone)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Number Provided");
        }

        String reqOtp = utils.generateOTP(6);

        redisService.set(phone, reqOtp, 50000l);

        return ResponseEntity.ok(reqOtp);
    }
}
