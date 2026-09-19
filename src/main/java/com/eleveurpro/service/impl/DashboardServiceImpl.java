package com.eleveurpro.service.impl;

import com.eleveurpro.dto.AlimentResponse;
import com.eleveurpro.dto.DashboardStatsResponse;
import com.eleveurpro.dto.VenteResponse;
import com.eleveurpro.dto.ConsommationAlimentResponse;
import com.eleveurpro.entity.*;
import com.eleveurpro.entity.enums.StatutAnimal;
import com.eleveurpro.entity.enums.StatutConsultation;
import com.eleveurpro.mapper.AlimentMapper;
import com.eleveurpro.mapper.ConsommationAlimentMapper;
import com.eleveurpro.mapper.VenteMapper;
import com.eleveurpro.repository.*;
import com.eleveurpro.service.DashboardService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final AnimalRepository animalRepository;
    private final NaissanceRepository naissanceRepository;
    private final ConsultationSanteRepository consultationRepository;
    private final VaccinationRepository vaccinationRepository;
    private final AlimentRepository alimentRepository;
    private final DepenseRepository depenseRepository;
    private final RevenuRepository revenuRepository;
    private final VenteRepository venteRepository;
    private final ConsommationAlimentRepository consommationRepository;

    public DashboardServiceImpl(AnimalRepository animalRepository,
                                 NaissanceRepository naissanceRepository,
                                 ConsultationSanteRepository consultationRepository,
                                 VaccinationRepository vaccinationRepository,
                                 AlimentRepository alimentRepository,
                                 DepenseRepository depenseRepository,
                                 RevenuRepository revenuRepository,
                                 VenteRepository venteRepository,
                                 ConsommationAlimentRepository consommationRepository) {
        this.animalRepository = animalRepository;
        this.naissanceRepository = naissanceRepository;
        this.consultationRepository = consultationRepository;
        this.vaccinationRepository = vaccinationRepository;
        this.alimentRepository = alimentRepository;
        this.depenseRepository = depenseRepository;
        this.revenuRepository = revenuRepository;
        this.venteRepository = venteRepository;
        this.consommationRepository = consommationRepository;
    }

    @Override
    public DashboardStatsResponse getStats() {
        long totalAnimaux = animalRepository.count();
        long animauxPresents = animalRepository.countByStatut(StatutAnimal.PRESENT);
        long animauxVendus = animalRepository.countByStatut(StatutAnimal.VENDU);
        long animauxDecedes = animalRepository.countByStatut(StatutAnimal.DECEDE);
        long totalNaissances = naissanceRepository.findAll().stream()
                .mapToInt(Naissance::getNombrePetits).sum();
        long animauxMalades = consultationRepository.findAll().stream()
                .filter(c -> c.getStatut() == StatutConsultation.EN_COURS || c.getStatut() == StatutConsultation.SUIVI)
                .map(c -> c.getAnimal().getId())
                .distinct().count();

        LocalDate today = LocalDate.now();
        LocalDate in30Days = today.plusDays(30);
        long vaccinationsProchaines = vaccinationRepository.findVaccinationsProchaines(today, in30Days).size();

        long stockAliments = alimentRepository.findByActifTrue().size();

        BigDecimal totalDepenses = depenseRepository.findAll().stream()
                .map(Depense::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalRevenus = revenuRepository.findAll().stream()
                .map(Revenu::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal benefice = totalRevenus.subtract(totalDepenses);

        List<AlimentResponse> alimentsStockBas = alimentRepository.findStockBas().stream()
                .map(AlimentMapper::toResponse).toList();

        return DashboardStatsResponse.builder()
                .totalAnimaux(totalAnimaux)
                .animauxPresents(animauxPresents)
                .animauxVendus(animauxVendus)
                .animauxDecedes(animauxDecedes)
                .totalNaissances(totalNaissances)
                .animauxMalades(animauxMalades)
                .vaccinationsProchaines(vaccinationsProchaines)
                .stockAliments(stockAliments)
                .totalDepenses(totalDepenses)
                .totalRevenus(totalRevenus)
                .benefice(benefice)
                .revenusMensuels(buildMonthlyRevenus())
                .depensesMensuelles(buildMonthlyDepenses())
                .ventesMensuelles(buildMonthlyVentes())
                .naissancesMensuelles(buildMonthlyNaissances())
                .mortaliteMensuelle(buildMonthlyMortalite())
                .consommationMensuelle(buildMonthlyConsommation())
                .alimentsStockBas(alimentsStockBas)
                .build();
    }

    private Map<String, BigDecimal> buildMonthlyRevenus() {
        Map<String, BigDecimal> map = new LinkedHashMap<>();
        YearMonth current = YearMonth.now();
        for (int i = 11; i >= 0; i--) {
            YearMonth ym = current.minusMonths(i);
            String key = ym.getYear() + "-" + String.format("%02d", ym.getMonthValue());
            BigDecimal total = revenuRepository.findByDateBetweenOrderByDateDesc(
                    ym.atDay(1), ym.atEndOfMonth()).stream()
                    .map(Revenu::getMontant)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            map.put(key, total);
        }
        return map;
    }

    private Map<String, BigDecimal> buildMonthlyDepenses() {
        Map<String, BigDecimal> map = new LinkedHashMap<>();
        YearMonth current = YearMonth.now();
        for (int i = 11; i >= 0; i--) {
            YearMonth ym = current.minusMonths(i);
            String key = ym.getYear() + "-" + String.format("%02d", ym.getMonthValue());
            BigDecimal total = depenseRepository.findByDateBetweenOrderByDateDesc(
                    ym.atDay(1), ym.atEndOfMonth()).stream()
                    .map(Depense::getMontant)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            map.put(key, total);
        }
        return map;
    }

    private Map<String, Long> buildMonthlyVentes() {
        Map<String, Long> map = new LinkedHashMap<>();
        YearMonth current = YearMonth.now();
        for (int i = 11; i >= 0; i--) {
            YearMonth ym = current.minusMonths(i);
            String key = ym.getYear() + "-" + String.format("%02d", ym.getMonthValue());
            long count = venteRepository.findByDateBetweenOrderByDateDesc(
                    ym.atDay(1), ym.atEndOfMonth()).size();
            map.put(key, count);
        }
        return map;
    }

    private Map<String, Long> buildMonthlyNaissances() {
        Map<String, Long> map = new LinkedHashMap<>();
        YearMonth current = YearMonth.now();
        for (int i = 11; i >= 0; i--) {
            YearMonth ym = current.minusMonths(i);
            String key = ym.getYear() + "-" + String.format("%02d", ym.getMonthValue());
            long count = naissanceRepository.findByDateNaissanceBetweenOrderByDateNaissanceDesc(
                    ym.atDay(1), ym.atEndOfMonth()).stream()
                    .mapToInt(Naissance::getNombrePetits).sum();
            map.put(key, count);
        }
        return map;
    }

    private Map<String, Long> buildMonthlyMortalite() {
        Map<String, Long> map = new LinkedHashMap<>();
        YearMonth current = YearMonth.now();
        for (int i = 11; i >= 0; i--) {
            YearMonth ym = current.minusMonths(i);
            String key = ym.getYear() + "-" + String.format("%02d", ym.getMonthValue());
            long count = animalRepository.findAll().stream()
                    .filter(a -> a.getStatut() == StatutAnimal.DECEDE)
                    .filter(a -> a.getUpdatedAt() != null)
                    .filter(a -> {
                        YearMonth animalYm = YearMonth.from(a.getUpdatedAt());
                        return animalYm.equals(ym);
                    })
                    .count();
            map.put(key, count);
        }
        return map;
    }

    private Map<String, BigDecimal> buildMonthlyConsommation() {
        Map<String, BigDecimal> map = new LinkedHashMap<>();
        YearMonth current = YearMonth.now();
        for (int i = 11; i >= 0; i--) {
            YearMonth ym = current.minusMonths(i);
            String key = ym.getYear() + "-" + String.format("%02d", ym.getMonthValue());
            BigDecimal total = consommationRepository.findByDateBetweenOrderByDateDesc(
                    ym.atDay(1), ym.atEndOfMonth()).stream()
                    .map(c -> c.getCout() != null ? c.getCout() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            map.put(key, total);
        }
        return map;
    }
}
