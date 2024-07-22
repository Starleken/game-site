package org.leafall.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.leafall.authservice.entity.UserDetailsImpl;
import org.leafall.authservice.entity.UserEntity;
import org.leafall.authservice.repository.UserRepository;
import org.leafall.authservice.utils.ExceptionUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.leafall.authservice.utils.ExceptionUtils.*;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var found = userRepository.findByEmail(username)
                .orElseThrow(() -> getEntityNotFoundException(UserEntity.class));

        return new UserDetailsImpl(found);
    }
}
