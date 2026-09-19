package com.eleveurpro.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NaissanceResponse {
    private Long id;
    private Long mereId;
    private String mereNom;
    private String mereNumero;
    private Long pereId;
    private String pereNom;
    private String pereNumero;
    private LocalDate dateNaissance;
    private Integer nombrePetits;
    private String observation;
}
