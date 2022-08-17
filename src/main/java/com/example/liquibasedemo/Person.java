package com.example.liquibasedemo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

/**
 * Date of creation 05-Aug-2022
 *
 * @author Mariom Mishu
 * @since 0.0.1
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {

  private String firstName;

  private String lastName;

  private OffsetDateTime dateOfBirth;

  private String street1;

  private String street2;

  private String phoneNumber1;

  private String phoneNumber2;


}
