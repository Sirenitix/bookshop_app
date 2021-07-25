package org.example.entity;

import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Book {

    @Setter
    @Getter
    private Integer id;

    @Setter
    @Getter
    @Pattern(regexp = "^[a-zA-Z ]*$")
    @NotEmpty
    private String author;

    @Setter
    @Getter
    @NotEmpty
    private String title;

    @Setter
    @Getter
    @Digits(integer=4, fraction=0)
    @NotNull
    private Integer size;

    public Book() {}
}
