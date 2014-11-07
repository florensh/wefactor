package de.hhn.labswps.wefactor.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public abstract class User extends BaseEntity {

    protected String email;
    protected String password;
    protected String roles;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if (this.roles != null) {
            for (String role : roles.split(",")) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                        role);
                authorities.add(authority);
            }

        }
        return authorities;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}