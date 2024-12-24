package ua.ihor.SpringBootSecurity4.dtos;

import lombok.Data;

@Data
public class AuthenticationDTO {
    private String username;
    private String password;
}
