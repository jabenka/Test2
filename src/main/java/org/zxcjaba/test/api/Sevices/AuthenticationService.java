package org.zxcjaba.test.api.Sevices;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zxcjaba.test.api.Dto.LoginDto;
import org.zxcjaba.test.api.Dto.RegistrationDto;
import org.zxcjaba.test.api.Dto.UserDto;
import org.zxcjaba.test.api.Exceptions.UserNotFoundException;
import org.zxcjaba.test.persistence.entity.Role;
import org.zxcjaba.test.persistence.entity.UserEntity;
import org.zxcjaba.test.persistence.repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    public UserDto authenticate(LoginDto loginDto) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getName(), loginDto.getPassword()
        ));
        UserEntity entity=userRepository.findByName(loginDto.getName())
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        if(entity.getRole().equals(Role.ADMIN)){

        }
        return UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .password(entity.getPassword())
                .role(entity.getRole())
                .build();

    }


    //simple method to add a new user
    public UserDto signUp(RegistrationDto registrationDto) {
        UserEntity user=new UserEntity();

        user.setName(registrationDto.getName());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        userRepository.saveAndFlush(user);

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .role(user.getRole())
                .build();

    }


}















//арбуз арбуз