package io.github.ilkou.learnspringsecurity.security;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static io.github.ilkou.learnspringsecurity.security.ApplicationUserPermission.*;

@RequiredArgsConstructor
public enum ApplicationUserRole {
    CUSTOMER(Sets.newHashSet(PRODUCT_READ)),
    ADMIN(Sets.newHashSet(CUSTOMER_READ, CUSTOMER_WRITE, PRODUCT_READ, PRODUCT_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(CUSTOMER_READ, PRODUCT_READ));

    @Getter
    private final Set<ApplicationUserPermission> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> collectPermissions = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        collectPermissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return collectPermissions;

    }

}
