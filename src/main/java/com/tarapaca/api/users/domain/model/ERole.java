package com.tarapaca.api.users.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum ERole {
    SUPER_ADMIN(Set.of(
            EPermission.SUPER_ADMIN_READ,
            EPermission.SUPER_ADMIN_UPDATE,
            EPermission.SUPER_ADMIN_DELETE,
            EPermission.SUPER_ADMIN_CREATE,
            EPermission.SUPER_ADMIN_ALL
    )),
    ADMIN(Set.of(
            EPermission.ADMIN_READ,
            EPermission.ADMIN_UPDATE,
            EPermission.ADMIN_DELETE,
            EPermission.ADMIN_CREATE,
            EPermission.ADMIN_ALL
    )),
    GLOBAL_MANAGER(Set.of(
            EPermission.MANAGER_READ,
            EPermission.MANAGER_UPDATE,
            EPermission.MANAGER_DELETE,
            EPermission.MANAGER_CREATE,
            EPermission.MANAGER_ALL
    )),
    MANAGER_COMMERCIAL(Set.of(
            EPermission.COMMERCIAL_READ,
            EPermission.COMMERCIAL_UPDATE,
            EPermission.COMMERCIAL_DELETE,
            EPermission.COMMERCIAL_CREATE,
            EPermission.COMMERCIAL_ALL
    )),
    MANAGER_PRODUCTION(Set.of(
            EPermission.PRODUCTION_READ,
            EPermission.PRODUCTION_UPDATE,
            EPermission.PRODUCTION_DELETE,
            EPermission.PRODUCTION_CREATE,
            EPermission.PRODUCTION_ALL
    )),
    MANAGER_DISPATCH(Set.of(
            EPermission.DISPATCH_READ,
            EPermission.DISPATCH_UPDATE,
            EPermission.DISPATCH_DELETE,
            EPermission.DISPATCH_CREATE,
            EPermission.DISPATCH_ALL
    )),
    MANAGER_BILLING(Set.of(
            EPermission.BILLING_READ,
            EPermission.BILLING_UPDATE,
            EPermission.BILLING_DELETE,
            EPermission.BILLING_CREATE,
            EPermission.BILLING_ALL
    )),
    USER_COMMERCIAL(Set.of(
            EPermission.COMMERCIAL_READ,
            EPermission.COMMERCIAL_UPDATE,
            EPermission.COMMERCIAL_CREATE
    )),
    USER_PRODUCTION(Set.of(
            EPermission.PRODUCTION_READ,
            EPermission.PRODUCTION_UPDATE,
            EPermission.PRODUCTION_CREATE
    )),
    USER_DISPATCH(Set.of(
            EPermission.DISPATCH_READ,
            EPermission.DISPATCH_UPDATE,
            EPermission.DISPATCH_CREATE
    )),
    USER_BILLING(Set.of(
            EPermission.BILLING_READ,
            EPermission.BILLING_UPDATE,
            EPermission.BILLING_CREATE
    )),
    USER(Set.of(
            EPermission.USER_READ
    )),
    ;

    @Getter
    private final Set<EPermission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        authorities.add(new SimpleGrantedAuthority(this.name()));

        return authorities;
    }
}
