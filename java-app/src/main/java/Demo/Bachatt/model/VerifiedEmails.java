package Demo.Bachatt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Table(name = "verified_emails")

public class VerifiedEmails {

    @Id
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "emailVerified", nullable = false)
    private boolean emailVerified = true;

    public void setEmail(String email) {
        this.email = email;
    }
}