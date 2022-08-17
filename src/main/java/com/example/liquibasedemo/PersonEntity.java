package com.example.liquibasedemo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
@Getter
@Setter
@ToString
@Entity
@Table(name="tbl_person")
public class PersonEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;
    private String firstName;

    private String lastName;

    private OffsetDateTime dateOfBirth;

    private String street1;

    private String street2;

    private String phoneNumber1;

    private String phoneNumber2;

}
