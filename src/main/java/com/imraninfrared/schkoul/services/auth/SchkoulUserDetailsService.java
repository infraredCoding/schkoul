package com.imraninfrared.schkoul.services.auth;

import com.imraninfrared.schkoul.domain.models.SchkoulUser;
import com.imraninfrared.schkoul.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SchkoulUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SchkoulUser> user = userRepository.findByUsername(username);
        return user.map(u -> new User(
                u.getUsername(), u.getPassword(),
                true, true, true,
                true, List.of(new SimpleGrantedAuthority("student"))
        )).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
