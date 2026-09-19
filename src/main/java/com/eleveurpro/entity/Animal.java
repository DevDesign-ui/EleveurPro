package com.eleveurpro.entity;

import com.eleveurpro.entity.enums.Sexe;
import com.eleveurpro.entity.enums.StatutAnimal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "animals",
        uniqueConstraints = @UniqueConstraint(columnNames = "numero_identification"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_identification", nullable = false, unique = true, length = 100)
    private String numeroIdentification;

    @Column(length = 100)
    private String nom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "espece_id", nullable = false)
    @JsonIgnore
    private Espece espece;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id")
    @JsonIgnore
    private Race race;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Sexe sexe;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(precision = 10, scale = 2)
    private BigDecimal poids;

    @Column(length = 50)
    private String couleur;

    @Column(name = "date_acquisition")
    private LocalDate dateAcquisition;

    @Column(name = "prix_acquisition", precision = 12, scale = 2)
    private BigDecimal prixAcquisition;

    @Column(length = 200)
    private String origine;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private StatutAnimal statut;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Column(length = 1000)
    private String description;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
