package com.eleveurpro.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationResponse {
    private Long id;
    private Long animalId;
    private String animalNom;
    private String animalNumero;
    private String vaccin;
    private LocalDate dateVaccination;
    private LocalDate prochaineDate;
    private String veterinaire;
    private String observation;
}
