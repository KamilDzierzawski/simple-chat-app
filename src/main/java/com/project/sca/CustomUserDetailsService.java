package com.project.sca;

import com.project.sca.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByNickname(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        } else {
            return org.springframework.security.core.userdetails.User.withUsername(user.get().getNickname())
                    .password(user.get().getPassword())
                    .build();
        }
    }
}
