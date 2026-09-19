package com.eleveurpro.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "consommations_aliment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsommationAliment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    @JsonIgnore
    private Animal animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aliment_id", nullable = false)
    @JsonIgnore
    private Aliment aliment;

    @Column(nullable = false)
    private Double quantite;

    @Column(nullable = false)
    private LocalDate date;

    @Column(precision = 12, scale = 2)
    private BigDecimal cout;

    @Column(length = 500)
    private String observation;
}
