package com.eleveurpro.dto;

import com.eleveurpro.entity.enums.Sexe;
import com.eleveurpro.entity.enums.StatutAnimal;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NaissanceRequest {
    @NotNull(message = "La mère est obligatoire")
    private Long mereId;

    private Long pereId;

    @NotNull(message = "La date de naissance est obligatoire")
    @PastOrPresent(message = "La date de naissance ne doit pas être dans le futur")
    private LocalDate dateNaissance;

    @NotNull(message = "Le nombre de petits est obligatoire")
    @Positive(message = "Le nombre de petits doit être positif")
    private Integer nombrePetits;

    @Size(max = 500, message = "L'observation ne doit pas dépasser 500 caractères")
    private String observation;

    private Boolean creerAnimaux = false;

    private Long especeId;

    private Long raceId;
}
