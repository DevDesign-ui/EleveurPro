package com.eleveurpro.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "especes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Espece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nom;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean actif = true;
}
