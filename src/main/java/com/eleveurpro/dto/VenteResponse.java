package com.eleveurpro.dto;

import com.eleveurpro.entity.enums.ModePaiement;
import com.eleveurpro.entity.enums.StatutVente;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenteResponse {
    private Long id;
    private Long animalId;
    private String animalNom;
    private String animalNumero;
    private String client;
    private LocalDate date;
    private BigDecimal prix;
    private ModePaiement modePaiement;
    private StatutVente statut;
    private String observation;
}
