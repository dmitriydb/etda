package com.github.dmitriydb.etda.security;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Класс, который инкапсулирует данные пользователя приложения
 *
 * @version 0.1.2
 * @since 0.1.1
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private SecurityRole securityRole;

    @NotNull
    private Timestamp createdTS;

    private Timestamp lastLogin;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    private Locale locale = Locale.forLanguageTag("ru-RU");

    private String email;

    public User() {
        createdTS = Timestamp.from(Instant.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SecurityRole getSecurityRole() {
        return securityRole;
    }

    public void setSecurityRole(SecurityRole securityRole) {
        this.securityRole = securityRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreatedTS() {
        return createdTS;
    }

    public void setCreatedTS(Timestamp createdTS) {
        this.createdTS = createdTS;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return securityRole.equals(user.securityRole) && name.equals(user.name) && Objects.equals(email, user.email);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public int hashCode() {
        return Objects.hash(securityRole, name, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", createdTS=" + createdTS +
                ", lastLogin=" + lastLogin +
                ", name='" + name + '\'' +
                ", locale=" + locale +
                ", email='" + email + '\'' +
                '}';
    }
}
