package com.jpa.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
//@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)//Single_Table is default
@DiscriminatorColumn(name = "EmployeeType") //Default:DTYPE
public abstract class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="cour_seq")
    @SequenceGenerator(name = "cour_seq", sequenceName = "cour_seq", initialValue = 1, allocationSize=1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public Employee(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Employee[%s]", name);
    }
}
