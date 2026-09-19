package com.eleveurpro.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TraitementResponse {
    private Long id;
    private Long animalId;
    private String animalNom;
    private String animalNumero;
    private String medicament;
    private String dosage;
    private String frequence;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String veterinaire;
    private String observation;
}
