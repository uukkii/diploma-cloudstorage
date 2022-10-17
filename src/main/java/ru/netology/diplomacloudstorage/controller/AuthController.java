package ru.netology.diplomacloudstorage.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import ru.netology.diplomacloudstorage.dto.AuthorizationToken;
import ru.netology.diplomacloudstorage.dto.Identity;
import ru.netology.diplomacloudstorage.model.User;
import ru.netology.diplomacloudstorage.service.JwtTokenUtil;
import ru.netology.diplomacloudstorage.service.UserService;

import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService usersService;
    
    private final static String LOGIN_PATH = "/login";
    private final static String SIGNIN_PATH = "/signin";
    
    @PostMapping(LOGIN_PATH)
    public AuthorizationToken login(@RequestBody @Valid Identity identity) {

        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(identity.getLogin(), identity.getPassword()));

        User user = (User) authenticate.getPrincipal();

        return new AuthorizationToken(jwtTokenUtil.generateToken(user));
    }

    @PostMapping(SIGNIN_PATH)
    public User signin(@RequestBody @Valid Identity identity) {
        return usersService.createUserAccount(identity);
    }
}
