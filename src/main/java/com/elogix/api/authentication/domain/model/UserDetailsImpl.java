package com.elogix.api.authentication.domain.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.elogix.api.users.domain.model.RoleModel;
import com.elogix.api.users.domain.model.UserModel;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class UserDetailsImpl extends UserModel implements UserDetails {

    public UserDetailsImpl() {
        super();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        for (RoleModel role : this.getRoles()) {
            authorities.addAll(role.getName().getAuthorities());
        }

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.isLocked();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive() && !this.isDeleted();
    }
}
