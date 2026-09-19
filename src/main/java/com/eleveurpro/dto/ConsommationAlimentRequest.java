package com.eleveurpro.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsommationAlimentRequest {
    @NotNull(message = "L'animal est obligatoire")
    private Long animalId;

    @NotNull(message = "L'aliment est obligatoire")
    private Long alimentId;

    @NotNull(message = "La quantité est obligatoire")
    @Positive(message = "La quantité doit être positive")
    private Double quantite;

    @NotNull(message = "La date est obligatoire")
    @PastOrPresent(message = "La date ne doit pas être dans le futur")
    private LocalDate date;

    @PositiveOrZero(message = "Le coût doit être positif ou nul")
    private BigDecimal cout;

    @Size(max = 500, message = "L'observation ne doit pas dépasser 500 caractères")
    private String observation;
}
