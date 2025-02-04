package Demo.Bachatt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class  User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private int uid;

    @NotNull
    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @NotNull
    @Email
    @Column(name = "email", nullable = false, length = 25, unique = true)
    private String email;

    @NotNull
    @Column(name = "phone", nullable = false)
    private long phone;

    @NotNull
    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "phoneVerified", nullable = false)
    private boolean phoneVerified = true;

    @Column(name = "emailVerified", nullable = false)
    private boolean emailVerified = true;

    @NotNull
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    @NotNull
    public long getPhone() {
        return phone;
    }

    public void setPhone(@NotNull long phone) {
        this.phone = phone;
    }

    public @NotNull String getAddress() {
        return address;
    }

    public void setAddress(@NotNull String address) {
        this.address = address;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }
}
