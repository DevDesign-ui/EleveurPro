package com.eleveurpro.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaladieResponse {
    private Long id;
    private String nom;
    private String description;
}
