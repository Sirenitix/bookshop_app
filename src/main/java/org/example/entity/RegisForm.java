package org.example.entity;

import lombok.*;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class RegisForm {

    @Setter
    @Getter
    private String username_reg;

    @Setter
    @Getter
    private String password_first;

    @Setter
    @Getter
    private String password_second;

    public RegisForm() {}
}
