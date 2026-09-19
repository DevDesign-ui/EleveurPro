package com.eleveurpro.dto;

import com.eleveurpro.entity.enums.StatutConsultation;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationSanteResponse {
    private Long id;
    private Long animalId;
    private String animalNom;
    private String animalNumero;
    private Long maladieId;
    private String maladieNom;
    private LocalDate date;
    private String symptomes;
    private String diagnostic;
    private String traitement;
    private String veterinaire;
    private String observation;
    private StatutConsultation statut;
}
