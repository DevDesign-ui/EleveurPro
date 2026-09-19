package com.eleveurpro.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "maladies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Maladie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(length = 1000)
    private String description;
}
