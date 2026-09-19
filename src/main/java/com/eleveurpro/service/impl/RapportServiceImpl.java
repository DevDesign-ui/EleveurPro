package com.eleveurpro.service.impl;

import com.eleveurpro.dto.RapportResponse;
import com.eleveurpro.entity.*;
import com.eleveurpro.entity.enums.StatutAnimal;
import com.eleveurpro.mapper.*;
import com.eleveurpro.repository.*;
import com.eleveurpro.service.RapportService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RapportServiceImpl implements RapportService {

    private final AnimalRepository animalRepository;
    private final VenteRepository venteRepository;
    private final DepenseRepository depenseRepository;
    private final RevenuRepository revenuRepository;
    private final ConsultationSanteRepository consultationRepository;
    private final NaissanceRepository naissanceRepository;
    private final ConsommationAlimentRepository consommationRepository;
    private final AchatRepository achatRepository;

    public RapportServiceImpl(AnimalRepository animalRepository,
                               VenteRepository venteRepository,
                               DepenseRepository depenseRepository,
                               RevenuRepository revenuRepository,
                               ConsultationSanteRepository consultationRepository,
                               NaissanceRepository naissanceRepository,
                               ConsommationAlimentRepository consommationRepository,
                               AchatRepository achatRepository) {
        this.animalRepository = animalRepository;
        this.venteRepository = venteRepository;
        this.depenseRepository = depenseRepository;
        this.revenuRepository = revenuRepository;
        this.consultationRepository = consultationRepository;
        this.naissanceRepository = naissanceRepository;
        this.consommationRepository = consommationRepository;
        this.achatRepository = achatRepository;
    }

    @Override
    public RapportResponse rapportCheptel(LocalDate debut, LocalDate fin) {
        List<Animal> animaux = animalRepository.findAll();
        return RapportResponse.builder()
                .type("CHEPTEL")
                .dateDebut(debut)
                .dateFin(fin)
                .nombreEntries(animaux.size())
                .data(animaux.stream().map(AnimalMapper::toResponse).toList())
                .build();
    }

    @Override
    public RapportResponse rapportVentes(LocalDate debut, LocalDate fin) {
        List<Vente> ventes = venteRepository.findByDateBetweenOrderByDateDesc(debut, fin);
        BigDecimal total = ventes.stream()
                .map(Vente::getPrix)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return RapportResponse.builder()
                .type("VENTES")
                .dateDebut(debut)
                .dateFin(fin)
                .totalMontant(total)
                .nombreEntries(ventes.size())
                .data(ventes.stream().map(VenteMapper::toResponse).toList())
                .build();
    }

    @Override
    public RapportResponse rapportDepenses(LocalDate debut, LocalDate fin) {
        List<Depense> depenses = depenseRepository.findByDateBetweenOrderByDateDesc(debut, fin);
        BigDecimal total = depenses.stream()
                .map(Depense::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return RapportResponse.builder()
                .type("DEPENSES")
                .dateDebut(debut)
                .dateFin(fin)
                .totalMontant(total)
                .nombreEntries(depenses.size())
                .data(depenses.stream().map(DepenseMapper::toResponse).toList())
                .build();
    }

    @Override
    public RapportResponse rapportRevenus(LocalDate debut, LocalDate fin) {
        List<Revenu> revenus = revenuRepository.findByDateBetweenOrderByDateDesc(debut, fin);
        BigDecimal total = revenus.stream()
                .map(Revenu::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return RapportResponse.builder()
                .type("REVENUS")
                .dateDebut(debut)
                .dateFin(fin)
                .totalMontant(total)
                .nombreEntries(revenus.size())
                .data(revenus.stream().map(RevenuMapper::toResponse).toList())
                .build();
    }

    @Override
    public RapportResponse rapportSante(LocalDate debut, LocalDate fin) {
        List<ConsultationSante> consultations = consultationRepository.findByDateBetween(debut, fin);
        return RapportResponse.builder()
                .type("SANTE")
                .dateDebut(debut)
                .dateFin(fin)
                .nombreEntries(consultations.size())
                .data(consultations.stream().map(ConsultationSanteMapper::toResponse).toList())
                .build();
    }

    @Override
    public RapportResponse rapportNaissances(LocalDate debut, LocalDate fin) {
        List<Naissance> naissances = naissanceRepository.findByDateNaissanceBetweenOrderByDateNaissanceDesc(debut, fin);
        int totalPetits = naissances.stream().mapToInt(Naissance::getNombrePetits).sum();
        return RapportResponse.builder()
                .type("NAISSANCES")
                .dateDebut(debut)
                .dateFin(fin)
                .totalMontant(BigDecimal.valueOf(totalPetits))
                .nombreEntries(naissances.size())
                .data(naissances.stream().map(NaissanceMapper::toResponse).toList())
                .build();
    }

    @Override
    public RapportResponse rapportMortalite(LocalDate debut, LocalDate fin) {
        List<Animal> decedes = animalRepository.findAll().stream()
                .filter(a -> a.getStatut() == StatutAnimal.DECEDE)
                .filter(a -> a.getUpdatedAt() != null)
                .filter(a -> !a.getUpdatedAt().toLocalDate().isBefore(debut) && !a.getUpdatedAt().toLocalDate().isAfter(fin))
                .toList();
        return RapportResponse.builder()
                .type("MORTALITE")
                .dateDebut(debut)
                .dateFin(fin)
                .nombreEntries(decedes.size())
                .data(decedes.stream().map(AnimalMapper::toResponse).toList())
                .build();
    }

    @Override
    public RapportResponse rapportAlimentation(LocalDate debut, LocalDate fin) {
        List<ConsommationAliment> consommations = consommationRepository.findByDateBetweenOrderByDateDesc(debut, fin);
        BigDecimal totalCout = consommations.stream()
                .map(c -> c.getCout() != null ? c.getCout() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return RapportResponse.builder()
                .type("ALIMENTATION")
                .dateDebut(debut)
                .dateFin(fin)
                .totalMontant(totalCout)
                .nombreEntries(consommations.size())
                .data(consommations.stream().map(ConsommationAlimentMapper::toResponse).toList())
                .build();
    }
}
