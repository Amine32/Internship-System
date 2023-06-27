package ru.tsu.hits.internshipapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.tsu.hits.internshipapplication.dto.UserSecurityDto;
import ru.tsu.hits.internshipapplication.security.UserPrincipal;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String userServiceUrl = "https://hits-user-service.onrender.com/api/users/security/" + email;
        UserSecurityDto user = restTemplate.getForObject(userServiceUrl, UserSecurityDto.class);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(user.getEmail(), user.getPassword(), user.getRole());
    }
}
