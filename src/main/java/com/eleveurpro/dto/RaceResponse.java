package com.eleveurpro.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaceResponse {
    private Long id;
    private String nom;
    private String description;
    private Long especeId;
    private String especeNom;
}
