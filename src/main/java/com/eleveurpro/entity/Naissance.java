package com.eleveurpro.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "naissances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Naissance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mere_id", nullable = false)
    @JsonIgnore
    private Animal mere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pere_id")
    @JsonIgnore
    private Animal pere;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "nombre_petits", nullable = false)
    private Integer nombrePetits;

    @Column(length = 500)
    private String observation;
}
