package com.jpa.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="rev_seq")
    @SequenceGenerator(name = "rev_seq", sequenceName = "rev_seq", initialValue = 1, allocationSize=1)
    private Long id;

    private String rating;

    @ToString.Exclude
    private String description;

    @ManyToOne //OWNING SIDE OF THE RELATIONSHIP!
    @ToString.Exclude
    private Course course;

    public Review(String rating, String description) {
        this.rating = rating;
        this.description = description;
    }
}
