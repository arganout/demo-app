package Demo.Bachatt.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class GoogleAuthService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    VerifyUserDetailsService verifyUserDetailsService;


    /**
     * Handles Google OAuth callback and verifies the user's identity.
     *
     * - Exchanges the authorization code for an ID token.
     * - Fetches user information from Google using the ID token.
     * - Validates the email in the token against the provided email.
     * - Returns an appropriate response based on verification.
     *
     * @param validateEmail A map containing the OAuth token ("oauthToken") and user email ("email").
     * @return ResponseEntity<?> A response indicating whether the user verification was successful or failed.
     *
     * Possible Responses:
     * - 200 OK: User verified successfully.
     * - 400 BAD REQUEST: OAuth token is invalid or expired.
     * - 401 UNAUTHORIZED: User information could not be retrieved.
     * - 500 INTERNAL SERVER ERROR: Unexpected error occurred during verification.
     */

    public ResponseEntity<?> handleGoogleCallback(@RequestBody Map<String, String> validateEmail) {

        try {

            String tokenEndpoint = "https://oauth2.googleapis.com/token";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

            params.add("code", validateEmail.get("oauthToken"));
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", "https://developers.google.com/oauthplayground");
            params.add("grant_type", "authorization_code");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndpoint, request, Map.class);

            String idToken = (String) tokenResponse.getBody().get("id_token");

            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;

            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);

            if (userInfoResponse.getStatusCode() == HttpStatus.OK) {

                Map userInfo = userInfoResponse.getBody();
                String tokenEmail = (String) userInfo.get("email");

                if(!tokenEmail.equals(validateEmail.get("email"))){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OAuth token is invalid or expired");
                }

                verifyUserDetailsService.saveVerifiedEmails(validateEmail.get("email"));

                return ResponseEntity.status(HttpStatus.OK).body("User Verified");

            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (Exception e) {
            System.out.println("error "+ e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
