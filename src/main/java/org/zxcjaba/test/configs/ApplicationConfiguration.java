package org.zxcjaba.test.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.zxcjaba.test.api.Exceptions.NotFoundException;
import org.zxcjaba.test.persistence.repository.UserRepository;

@Configuration
public class ApplicationConfiguration {


    private final UserRepository userRepository;
    public ApplicationConfiguration(UserRepository userRepository) {

        this.userRepository = userRepository;

    }


    @Bean
    UserDetailsService userDetailsService() {

        return username->userRepository.findByName(username)
                .orElseThrow(()->new NotFoundException("User not found"));
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


}