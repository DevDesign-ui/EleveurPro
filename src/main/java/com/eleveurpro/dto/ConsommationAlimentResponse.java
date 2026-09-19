package com.eleveurpro.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsommationAlimentResponse {
    private Long id;
    private Long animalId;
    private String animalNom;
    private String animalNumero;
    private Long alimentId;
    private String alimentNom;
    private Double quantite;
    private LocalDate date;
    private BigDecimal cout;
    private String observation;
}
