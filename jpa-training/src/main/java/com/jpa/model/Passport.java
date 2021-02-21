package com.jpa.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pas_seq")
    @SequenceGenerator(name = "pas_seq", sequenceName = "pas_seq", initialValue = 1, allocationSize=1)
    private Long id;

    @Column(name = "number", nullable = false)
    private String number;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "passport")
    @ToString.Exclude
    private Student student;

    public Passport(String number) {
        this.number = number;
    }
}
