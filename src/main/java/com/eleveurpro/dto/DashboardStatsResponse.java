package com.eleveurpro.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStatsResponse {
    private Long totalAnimaux;
    private Long animauxPresents;
    private Long animauxVendus;
    private Long animauxDecedes;
    private Long totalNaissances;
    private Long animauxMalades;
    private Long vaccinationsProchaines;
    private Long stockAliments;
    private BigDecimal totalDepenses;
    private BigDecimal totalRevenus;
    private BigDecimal benefice;

    private Map<String, BigDecimal> revenusMensuels;
    private Map<String, BigDecimal> depensesMensuelles;
    private Map<String, Long> ventesMensuelles;
    private Map<String, Long> naissancesMensuelles;
    private Map<String, Long> mortaliteMensuelle;
    private Map<String, BigDecimal> consommationMensuelle;
    private List<AlimentResponse> alimentsStockBas;
}
