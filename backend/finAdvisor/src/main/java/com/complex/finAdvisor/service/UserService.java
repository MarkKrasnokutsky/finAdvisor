package com.complex.finAdvisor.service;

import com.complex.finAdvisor.entity.UserEntity;
import com.complex.finAdvisor.repository.UserRepository;
import com.complex.finAdvisor.config.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User %s not found", username)
        ));

        return UserDetailsImpl.build(userEntity);
    }
    public void setTgNickname(String username, String tgNickname) {
        Optional<UserEntity> currentUser = userRepository.findByUsername(username);
        currentUser.ifPresent(userEntity -> {
            userEntity.setTgNickname(tgNickname);
            userRepository.save(userEntity);
        });
    }
    public String getTgNickname(String username) {
        Optional<UserEntity> currentUser = userRepository.findByUsername(username);
        var ref = new Object() {
            String telegramNickname;
        };
        currentUser.ifPresent(userEntity -> {
            ref.telegramNickname = userEntity.getTgNickname();
        });
        return ref.telegramNickname;
    }
}
