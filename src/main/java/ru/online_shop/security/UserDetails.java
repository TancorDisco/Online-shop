package ru.online_shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import ru.online_shop.models.User;

import java.util.Collection;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final org.springframework.security.core.userdetails.User user;

    @Autowired
    public UserDetails(org.springframework.security.core.userdetails.User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }
}
