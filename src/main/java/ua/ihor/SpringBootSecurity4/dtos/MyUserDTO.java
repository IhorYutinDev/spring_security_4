package ua.ihor.SpringBootSecurity4.dtos;

import lombok.Data;

@Data
public class MyUserDTO {
    private Long id;
    private String name;
    private String password;
}
