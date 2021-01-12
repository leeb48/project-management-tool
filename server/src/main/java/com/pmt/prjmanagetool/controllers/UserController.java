package com.pmt.prjmanagetool.controllers;

import com.pmt.prjmanagetool.domain.User;
import com.pmt.prjmanagetool.payload.JWTLoginSuccessResponse;
import com.pmt.prjmanagetool.payload.LoginRequest;
import com.pmt.prjmanagetool.security.JwtTokenProvider;
import com.pmt.prjmanagetool.services.MapValidationErrorService;
import com.pmt.prjmanagetool.services.UserService;
import com.pmt.prjmanagetool.validator.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.pmt.prjmanagetool.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = "/api/users";

    private final UserService userService;
    private final MapValidationErrorService mapValidationErrorService;
    private final UserValidator userValidator;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, MapValidationErrorService mapValidationErrorService, UserValidator userValidator, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.mapValidationErrorService = mapValidationErrorService;
        this.userValidator = userValidator;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {

        if (result.hasErrors()) {
            return mapValidationErrorService.MapValidationService(result);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {


        // TODO: validate passwords match
        userValidator.validate(user, result);

        if (result.hasErrors()) {
            return mapValidationErrorService.MapValidationService(result);
        }

        User newUser = userService.saveUser(user);

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }
}





























