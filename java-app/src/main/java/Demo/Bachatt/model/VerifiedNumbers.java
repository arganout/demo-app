package Demo.Bachatt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "verified_numbers")
@Data
public class VerifiedNumbers {

    @Id
    @Column(name = "phone", nullable = false)
    private long phone;

    @NotNull
    @Column(name = "phoneVerified", nullable = false)
    private final boolean phoneVerified = true;

    public void setPhone(long phone) {
        this.phone = phone;
    }
}
