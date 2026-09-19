package com.eleveurpro.entity;

import com.eleveurpro.entity.enums.StatutReproduction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "reproductions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reproduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "male_id", nullable = false)
    @JsonIgnore
    private Animal male;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "femelle_id", nullable = false)
    @JsonIgnore
    private Animal femelle;

    @Column(name = "date_accouplement", nullable = false)
    private LocalDate dateAccouplement;

    @Column(name = "date_prevue_mise_bas")
    private LocalDate datePrevueMiseBas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private StatutReproduction statut;

    @Column(length = 500)
    private String observation;
}
