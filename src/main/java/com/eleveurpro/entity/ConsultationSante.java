package com.eleveurpro.entity;

import com.eleveurpro.entity.enums.StatutConsultation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "consultations_sante")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationSante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    @JsonIgnore
    private Animal animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maladie_id")
    @JsonIgnore
    private Maladie maladie;

    @Column(nullable = false)
    private LocalDate date;

    @Column(length = 1000)
    private String symptomes;

    @Column(length = 1000)
    private String diagnostic;

    @Column(length = 1000)
    private String traitement;

    @Column(length = 100)
    private String veterinaire;

    @Column(length = 500)
    private String observation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private StatutConsultation statut;
}
