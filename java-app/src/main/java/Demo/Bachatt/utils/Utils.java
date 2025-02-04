package Demo.Bachatt.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class Utils {

    private static final SecureRandom secureRandom = new SecureRandom();

    public String generateOTP(int length) {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(secureRandom.nextInt(10)); // Numeric OTP
        }

        return otp.toString();
    }

    public boolean isValidNumber(Object number) {

        if (number == null) return false;

        String num = String.valueOf(number).trim();

        // Check if it's a numeric value and has a valid length (Assuming 10-digit phone number)
        if (!num.matches("\\d{10}")) {
            return false;
        }

        return num.charAt(0) >= '6';
    }

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

}
