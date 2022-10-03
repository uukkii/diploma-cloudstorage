package ru.netology.diplomacloudstorage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.diplomacloudstorage.dto.Identity;
import ru.netology.diplomacloudstorage.exceptions.DataValidationEx;
import ru.netology.diplomacloudstorage.model.User;
import ru.netology.diplomacloudstorage.repository.UserRepository;

@Service
@RequiredArgsConstructor

public class UserService {

    public static final String DEFAULT_ROLE = "ROLE_USER";
    private static final String ACCOUNT_EXCEPTION_ANSWER = "This user is already exists! Chose another one!";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUserAccount(Identity identity) {
        if (userRepository.findByUsername(identity.getLogin()).isPresent()) {
            throw new DataValidationEx(ACCOUNT_EXCEPTION_ANSWER);
        }
        String encodedPass = passwordEncoder.encode(identity.getPassword());
        User user = User.builder()
                .username(identity.getLogin())
                .password(encodedPass)
                .role(DEFAULT_ROLE)
                .build();
        return userRepository.save(user);
    }
}
