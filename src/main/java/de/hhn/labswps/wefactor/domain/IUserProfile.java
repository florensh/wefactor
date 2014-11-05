package de.hhn.labswps.wefactor.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface IUserProfile {

    String getUserId();

    String getName();

    String getUsername();

    String getPassword();

    Collection<? extends GrantedAuthority> getAuthorities();

}
