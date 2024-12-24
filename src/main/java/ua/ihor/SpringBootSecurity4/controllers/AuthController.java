package ua.ihor.SpringBootSecurity4.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import ua.ihor.SpringBootSecurity4.dtos.AuthenticationDTO;
import ua.ihor.SpringBootSecurity4.dtos.MyUserDTO;
import ua.ihor.SpringBootSecurity4.models.MyUser;
import ua.ihor.SpringBootSecurity4.usersDetails.JWTUtil;

import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthenticationDTO dto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
           return Map.of("error", e.getMessage());
        }

        String token = jwtUtil.generateToken(authenticationToken.getPrincipal().toString());
        return Map.of("jwt_token", token);
    }

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/reg")
    public Map<String, String> performRegistration(@RequestBody MyUserDTO myUserDTO) {
        MyUser myUser = convertToMyUser(myUserDTO);

        String token = jwtUtil.generateToken(myUser.getName());
        return Map.of("jwt_token", token);
    }

    private MyUser convertToMyUser(MyUserDTO myUserDTO) {
        return modelMapper.map(myUserDTO, MyUser.class);
    }
}
