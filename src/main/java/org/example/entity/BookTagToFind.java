package org.example.entity;

import lombok.*;

import javax.validation.constraints.*;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class BookTagToFind {

    @Setter
    @Getter
    @NotBlank
    private String findbook;

    public BookTagToFind() {}
}
