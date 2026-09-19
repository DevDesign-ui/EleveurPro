package com.eleveurpro.dto;

import com.eleveurpro.entity.enums.StatutReproduction;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReproductionResponse {
    private Long id;
    private Long maleId;
    private String maleNom;
    private String maleNumero;
    private Long femelleId;
    private String femelleNom;
    private String femelleNumero;
    private LocalDate dateAccouplement;
    private LocalDate datePrevueMiseBas;
    private StatutReproduction statut;
    private String observation;
}
