package com.eleveurpro.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RapportResponse {
    private String type;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal totalMontant;
    private long nombreEntries;
    private List<?> data;
}
