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
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="rev_seq")
    @SequenceGenerator(name = "rev_seq", sequenceName = "rev_seq", initialValue = 1, allocationSize=1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReviewRating rating;

    @ToString.Exclude
    private String description;

    @ManyToOne //OWNING SIDE OF THE RELATIONSHIP! - Will add column
    @ToString.Exclude
    private Course course;

    @ManyToOne
    private Student student;

    public Review(ReviewRating rating, String description) {
        this.rating = rating;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rating='" + rating + '\'' +
                ", description='" + description + '\'' +
                ", student=" + student +
                '}';
    }
}
