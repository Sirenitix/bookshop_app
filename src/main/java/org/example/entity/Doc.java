package org.example.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Doc {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Setter
    @Getter
    private Integer id;

    @Setter
    @Getter
    private String fileName;

    @Setter
    @Getter
    @Lob
    private byte[] file;

    public Doc() {}

}
