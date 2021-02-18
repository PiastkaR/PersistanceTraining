package com.jdbc.model;

import lombok.*;

import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Person {
    private int id;
    private String name;
    private String location;
    private Date birthDate;
}
