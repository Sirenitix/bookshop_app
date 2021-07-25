package org.example.entity;

import lombok.*;

import javax.validation.constraints.*;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class BookTagToRemove {

    @Setter
    @Getter
    private Integer id;

    @Setter
    @Getter
    @NotBlank
    private String Tag;

    @Setter
    @Getter
    private String author;

    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private Integer size;

    public BookTagToRemove() {}
}
