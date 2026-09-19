package com.eleveurpro.service.impl;

import com.eleveurpro.dto.NaissanceRequest;
import com.eleveurpro.dto.NaissanceResponse;
import com.eleveurpro.dto.ReproductionRequest;
import com.eleveurpro.dto.ReproductionResponse;
import com.eleveurpro.entity.*;
import com.eleveurpro.entity.enums.Sexe;
import com.eleveurpro.entity.enums.StatutAnimal;
import com.eleveurpro.exception.ResourceNotFoundException;
import com.eleveurpro.mapper.NaissanceMapper;
import com.eleveurpro.mapper.ReproductionMapper;
import com.eleveurpro.repository.*;
import com.eleveurpro.service.ReproductionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReproductionServiceImpl implements ReproductionService {

    private final ReproductionRepository reproductionRepository;
    private final NaissanceRepository naissanceRepository;
    private final AnimalRepository animalRepository;
    private final EspeceRepository especeRepository;
    private final RaceRepository raceRepository;

    public ReproductionServiceImpl(ReproductionRepository reproductionRepository,
                                    NaissanceRepository naissanceRepository,
                                    AnimalRepository animalRepository,
                                    EspeceRepository especeRepository,
                                    RaceRepository raceRepository) {
        this.reproductionRepository = reproductionRepository;
        this.naissanceRepository = naissanceRepository;
        this.animalRepository = animalRepository;
        this.especeRepository = especeRepository;
        this.raceRepository = raceRepository;
    }

    // ==================== Reproductions ====================

    @Override
    public List<ReproductionResponse> getAllReproductions() {
        return reproductionRepository.findAll().stream().map(ReproductionMapper::toResponse).toList();
    }

    @Override
    public ReproductionResponse getReproductionById(Long id) {
        return ReproductionMapper.toResponse(reproductionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reproduction non trouvée avec l'id: " + id)));
    }

    @Override
    public ReproductionResponse createReproduction(ReproductionRequest request) {
        Animal male = animalRepository.findById(request.getMaleId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal mâle non trouvé avec l'id: " + request.getMaleId()));
        Animal femelle = animalRepository.findById(request.getFemelleId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal femelle non trouvé avec l'id: " + request.getFemelleId()));
        Reproduction reproduction = ReproductionMapper.toEntity(request, male, femelle);
        return ReproductionMapper.toResponse(reproductionRepository.save(reproduction));
    }

    @Override
    public ReproductionResponse updateReproduction(Long id, ReproductionRequest request) {
        Reproduction reproduction = reproductionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reproduction non trouvée avec l'id: " + id));
        Animal male = animalRepository.findById(request.getMaleId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal mâle non trouvé avec l'id: " + request.getMaleId()));
        Animal femelle = animalRepository.findById(request.getFemelleId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal femelle non trouvé avec l'id: " + request.getFemelleId()));
        reproduction.setMale(male);
        reproduction.setFemelle(femelle);
        reproduction.setDateAccouplement(request.getDateAccouplement());
        reproduction.setDatePrevueMiseBas(request.getDatePrevueMiseBas());
        reproduction.setStatut(request.getStatut());
        reproduction.setObservation(request.getObservation());
        return ReproductionMapper.toResponse(reproductionRepository.save(reproduction));
    }

    @Override
    public void deleteReproduction(Long id) {
        Reproduction reproduction = reproductionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reproduction non trouvée avec l'id: " + id));
        reproductionRepository.delete(reproduction);
    }

    // ==================== Naissances ====================

    @Override
    @Transactional
    public NaissanceResponse createNaissance(NaissanceRequest request) {
        Animal mere = animalRepository.findById(request.getMereId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal mère non trouvé avec l'id: " + request.getMereId()));
        Animal pere = null;
        if (request.getPereId() != null) {
            pere = animalRepository.findById(request.getPereId())
                    .orElseThrow(() -> new ResourceNotFoundException("Animal père non trouvé avec l'id: " + request.getPereId()));
        }
        Naissance naissance = NaissanceMapper.toEntity(request, mere, pere);
        Naissance savedNaissance = naissanceRepository.save(naissance);

        if (Boolean.TRUE.equals(request.getCreerAnimaux()) && request.getEspeceId() != null) {
            Espece espece = especeRepository.findById(request.getEspeceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Espèce non trouvée avec l'id: " + request.getEspeceId()));
            Race race = null;
            if (request.getRaceId() != null) {
                race = raceRepository.findById(request.getRaceId())
                        .orElseThrow(() -> new ResourceNotFoundException("Race non trouvée avec l'id: " + request.getRaceId()));
            }
            for (int i = 0; i < request.getNombrePetits(); i++) {
                Animal petit = Animal.builder()
                        .numeroIdentification("NAISS-" + savedNaissance.getId() + "-" + (i + 1))
                        .nom(mere.getNom() != null ? mere.getNom() + "-Petit-" + (i + 1) : "Petit-" + (i + 1))
                        .espece(espece)
                        .race(race)
                        .sexe(Sexe.MALE)
                        .dateNaissance(request.getDateNaissance())
                        .dateAcquisition(request.getDateNaissance())
                        .statut(StatutAnimal.PRESENT)
                        .origine("Naissance")
                        .build();
                animalRepository.save(petit);
            }
        }

        return NaissanceMapper.toResponse(savedNaissance);
    }

    @Override
    public List<NaissanceResponse> getAllNaissances() {
        return naissanceRepository.findAll().stream().map(NaissanceMapper::toResponse).toList();
    }

    @Override
    public NaissanceResponse getNaissanceById(Long id) {
        return NaissanceMapper.toResponse(naissanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Naissance non trouvée avec l'id: " + id)));
    }

    @Override
    public NaissanceResponse updateNaissance(Long id, NaissanceRequest request) {
        Naissance naissance = naissanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Naissance non trouvée avec l'id: " + id));
        Animal mere = animalRepository.findById(request.getMereId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal mère non trouvé avec l'id: " + request.getMereId()));
        Animal pere = null;
        if (request.getPereId() != null) {
            pere = animalRepository.findById(request.getPereId())
                    .orElseThrow(() -> new ResourceNotFoundException("Animal père non trouvé avec l'id: " + request.getPereId()));
        }
        naissance.setMere(mere);
        naissance.setPere(pere);
        naissance.setDateNaissance(request.getDateNaissance());
        naissance.setNombrePetits(request.getNombrePetits());
        naissance.setObservation(request.getObservation());
        return NaissanceMapper.toResponse(naissanceRepository.save(naissance));
    }

    @Override
    public void deleteNaissance(Long id) {
        Naissance naissance = naissanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Naissance non trouvée avec l'id: " + id));
        naissanceRepository.delete(naissance);
    }
}
