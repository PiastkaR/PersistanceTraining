package com.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {
    private String line1;
    private String line2;
    private String city;
}
