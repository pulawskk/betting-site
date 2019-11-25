package com.pulawskk.bettingsite.entities;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "new_generator")
    @SequenceGenerator(name="new_generator", sequenceName = "role_seq", initialValue = 5)
    private Integer id;

    @Column(name = "role")
    private String role;
}
