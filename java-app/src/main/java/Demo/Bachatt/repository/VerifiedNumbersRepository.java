package Demo.Bachatt.repository;

import Demo.Bachatt.model.VerifiedNumbers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifiedNumbersRepository extends JpaRepository<VerifiedNumbers, Long> {
}
