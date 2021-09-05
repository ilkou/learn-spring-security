package io.github.ilkou.learnspringsecurity.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.github.ilkou.learnspringsecurity.security.ApplicationUserRole.*;

@Repository("inMemoryDB")
@RequiredArgsConstructor
public class ApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> applicationUsers = new ArrayList<>();

        ApplicationUser ilkou = ApplicationUser.builder()
                .username("ilkou")
                .password(passwordEncoder.encode("test"))
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
        applicationUsers.add(ilkou);

        ApplicationUser achraf = ApplicationUser.builder()
                .username("achraf")
                .password(passwordEncoder.encode("1234"))
                .authorities(CUSTOMER.getGrantedAuthorities())
                .build();
        applicationUsers.add(achraf);

        ApplicationUser saad = ApplicationUser.builder()
                .username("saad")
                .password(passwordEncoder.encode("1234"))
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();
        applicationUsers.add(saad);

        return applicationUsers;
    }
}
