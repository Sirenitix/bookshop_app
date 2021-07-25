package org.example.entity;

import lombok.*;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class LoginForm {

    @Setter
    @Getter
    private String username;

    @Setter
    @Getter
    private String password;

    public LoginForm() {}
}
