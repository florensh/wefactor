package de.hhn.labswps.wefactor.web.DataObjects;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import de.hhn.labswps.wefactor.web.validation.constraints.FieldMatch;
import de.hhn.labswps.wefactor.web.validation.constraints.UniqueEmail;
import de.hhn.labswps.wefactor.web.validation.constraints.UniqueUsername;

@FieldMatch.List({ @FieldMatch(first = "password", second = "repeatPassword", message = "The password fields must match") })
public class RegisterFormDataObject {

    @Size(min = 3, max = 30)
    @UniqueUsername(message = "already exists")
    private String username;

    @Email
    @UniqueEmail(message = "already exists")
    @NotEmpty
    private String email;

    @Size(min = 8, max = 30)
    private String password;

    private String repeatPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

}
