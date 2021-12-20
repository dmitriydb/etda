package com.github.dmitriydb.etda.security;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDTO {

    @Size(min = 3, max = 10, message = "Длина имени должна быть от 3 до 10 символов")
    @Pattern(regexp = "[a-zA-Z0-9]*", message = "В имени пользователя должны быть только буквы a-z и цифры")
    private String username;

    @NotNull
    @Size(min = 5, max = 16, message = "Требуемая длина пароля - от 5 до 16 символов")
    private String password;

    @NotNull
    @Size(min = 5, max = 16, message = "Требуемая длина пароля - от 5 до 16 символов")
    private String matchingPassword;

    @NotNull
    @Size(max = 100, message = "Длина почты не должна превышать 100 символов")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", matchingPassword='" + matchingPassword + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
