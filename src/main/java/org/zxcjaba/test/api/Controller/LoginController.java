package org.zxcjaba.test.api.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zxcjaba.test.api.Dto.*;
import org.zxcjaba.test.api.Sevices.AuthenticationService;
import org.zxcjaba.test.api.Sevices.JwtService;
import org.zxcjaba.test.persistence.entity.UserEntity;

@RequestMapping("/auth")
@RestController
public class LoginController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public LoginController(JwtService jwtService,AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }



    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto loginDto) {

        UserDto authenticatedUser=authenticationService.authenticate(loginDto);


        String token=jwtService.generateToken(UserEntity.builder()
                .id(authenticatedUser.getId())
                .name(authenticatedUser.getName())
                .password(authenticatedUser.getPassword())
                        .role(authenticatedUser.getRole())
                .build()
        );


        LoginResponse response=new LoginResponse();
        response.setToken(token);
        response.setExpirationTime(jwtService.getExpirationTime());

        return ResponseEntity.ok(response);
    }



}