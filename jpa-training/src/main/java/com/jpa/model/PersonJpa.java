package com.jpa.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "find_all_persons", query = "select p from PersonJpa p")
public class PersonJpa {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String location;
    private Date birthDate;
}
