package com.eleveurpro.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspeceResponse {
    private Long id;
    private String nom;
    private String description;
    private Boolean actif;
}
