package com.eleveurpro.service;

import com.eleveurpro.dto.DashboardStatsResponse;
import com.eleveurpro.entity.*;
import com.eleveurpro.entity.enums.Sexe;
import com.eleveurpro.entity.enums.StatutAnimal;
import com.eleveurpro.repository.*;
import com.eleveurpro.service.impl.DashboardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock private AnimalRepository animalRepository;
    @Mock private NaissanceRepository naissanceRepository;
    @Mock private ConsultationSanteRepository consultationRepository;
    @Mock private VaccinationRepository vaccinationRepository;
    @Mock private AlimentRepository alimentRepository;
    @Mock private DepenseRepository depenseRepository;
    @Mock private RevenuRepository revenuRepository;
    @Mock private VenteRepository venteRepository;
    @Mock private ConsommationAlimentRepository consommationRepository;

    @InjectMocks private DashboardServiceImpl dashboardService;

    @BeforeEach
    void setUp() {
        when(animalRepository.count()).thenReturn(10L);
        when(animalRepository.countByStatut(StatutAnimal.PRESENT)).thenReturn(7L);
        when(animalRepository.countByStatut(StatutAnimal.VENDU)).thenReturn(2L);
        when(animalRepository.countByStatut(StatutAnimal.DECEDE)).thenReturn(1L);
        when(naissanceRepository.findAll()).thenReturn(Collections.emptyList());
        when(consultationRepository.findAll()).thenReturn(Collections.emptyList());
        when(vaccinationRepository.findVaccinationsProchaines(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()))
                .thenReturn(Collections.emptyList());
        when(alimentRepository.findByActifTrue()).thenReturn(Collections.emptyList());
        when(alimentRepository.findStockBas()).thenReturn(Collections.emptyList());
        when(depenseRepository.findAll()).thenReturn(Collections.emptyList());
        when(revenuRepository.findAll()).thenReturn(Collections.emptyList());
        when(venteRepository.findByDateBetweenOrderByDateDesc(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()))
                .thenReturn(Collections.emptyList());
        when(naissanceRepository.findByDateNaissanceBetweenOrderByDateNaissanceDesc(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()))
                .thenReturn(Collections.emptyList());
        when(depenseRepository.findByDateBetweenOrderByDateDesc(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()))
                .thenReturn(Collections.emptyList());
        when(revenuRepository.findByDateBetweenOrderByDateDesc(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()))
                .thenReturn(Collections.emptyList());
        when(consommationRepository.findByDateBetweenOrderByDateDesc(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()))
                .thenReturn(Collections.emptyList());
        when(animalRepository.findAll()).thenReturn(Collections.emptyList());
    }

    @Test
    void getStats_shouldReturnCorrectCounts() {
        DashboardStatsResponse stats = dashboardService.getStats();

        assertNotNull(stats);
        assertEquals(10L, stats.getTotalAnimaux());
        assertEquals(7L, stats.getAnimauxPresents());
        assertEquals(2L, stats.getAnimauxVendus());
        assertEquals(1L, stats.getAnimauxDecedes());
        assertEquals(0L, stats.getTotalNaissances());
        assertEquals(0L, stats.getVaccinationsProchaines());
        assertEquals(BigDecimal.ZERO, stats.getTotalDepenses());
        assertEquals(BigDecimal.ZERO, stats.getTotalRevenus());
        assertEquals(BigDecimal.ZERO, stats.getBenefice());
    }

    @Test
    void getStats_shouldReturnMonthlyData() {
        DashboardStatsResponse stats = dashboardService.getStats();

        assertNotNull(stats.getRevenusMensuels());
        assertNotNull(stats.getDepensesMensuelles());
        assertNotNull(stats.getVentesMensuelles());
        assertNotNull(stats.getNaissancesMensuelles());
        assertNotNull(stats.getMortaliteMensuelle());
        assertNotNull(stats.getConsommationMensuelle());
        assertEquals(12, stats.getRevenusMensuels().size());
    }
}
