package Demo.Bachatt.service;

import Demo.Bachatt.model.VerifiedEmails;
import Demo.Bachatt.model.VerifiedNumbers;
import Demo.Bachatt.repository.VerifiedEmailsRepository;
import Demo.Bachatt.repository.VerifiedNumbersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyUserDetailsService {

    @Autowired
    VerifiedEmailsRepository verifiedEmailsRepository;

    @Autowired
    VerifiedNumbersRepository verifiedNumbersRepository;


    public boolean isVerifiedNumber(long number){
        return verifiedNumbersRepository.findById(number).isPresent();
    }

    public boolean isVerifiedEmail(String email){
        return verifiedEmailsRepository.findById(email).isPresent();
    }

    public void saveVerifiedNumber(long number){

        System.out.println(number);
        VerifiedNumbers verifiedNumbers = new VerifiedNumbers();
        System.out.println(VerifiedNumbers.class);
        verifiedNumbers.setPhone(number);
        verifiedNumbersRepository.save(verifiedNumbers);
    }

    public void saveVerifiedEmails(String email){
        VerifiedEmails verifiedEmails = new VerifiedEmails();
        verifiedEmails.setEmail(email);
        verifiedEmailsRepository.save(verifiedEmails);
    }

}
