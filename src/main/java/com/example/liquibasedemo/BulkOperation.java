package com.example.liquibasedemo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class BulkOperation {

  private List<Person> personList;

  private boolean isFailed;

  private String message;
}
