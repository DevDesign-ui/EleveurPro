package com.eleveurpro.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepenseResponse {
    private Long id;
    private String categorie;
    private BigDecimal montant;
    private LocalDate date;
    private String description;
}
