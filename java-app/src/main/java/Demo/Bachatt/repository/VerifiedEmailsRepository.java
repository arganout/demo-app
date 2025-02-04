package Demo.Bachatt.repository;

import Demo.Bachatt.model.VerifiedEmails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifiedEmailsRepository extends JpaRepository<VerifiedEmails, String> {
}
