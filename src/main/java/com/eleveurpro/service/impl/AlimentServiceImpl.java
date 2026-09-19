package com.eleveurpro.service.impl;

import com.eleveurpro.dto.AlimentRequest;
import com.eleveurpro.dto.AlimentResponse;
import com.eleveurpro.dto.ConsommationAlimentRequest;
import com.eleveurpro.dto.ConsommationAlimentResponse;
import com.eleveurpro.entity.Aliment;
import com.eleveurpro.entity.Animal;
import com.eleveurpro.entity.ConsommationAliment;
import com.eleveurpro.exception.InsufficientStockException;
import com.eleveurpro.exception.ResourceNotFoundException;
import com.eleveurpro.mapper.AlimentMapper;
import com.eleveurpro.mapper.ConsommationAlimentMapper;
import com.eleveurpro.repository.AlimentRepository;
import com.eleveurpro.repository.AnimalRepository;
import com.eleveurpro.repository.ConsommationAlimentRepository;
import com.eleveurpro.service.AlimentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlimentServiceImpl implements AlimentService {

    private final AlimentRepository alimentRepository;
    private final AnimalRepository animalRepository;
    private final ConsommationAlimentRepository consommationRepository;

    public AlimentServiceImpl(AlimentRepository alimentRepository,
                              AnimalRepository animalRepository,
                              ConsommationAlimentRepository consommationRepository) {
        this.alimentRepository = alimentRepository;
        this.animalRepository = animalRepository;
        this.consommationRepository = consommationRepository;
    }

    @Override
    public AlimentResponse create(AlimentRequest request) {
        Aliment aliment = AlimentMapper.toEntity(request);
        return AlimentMapper.toResponse(alimentRepository.save(aliment));
    }

    @Override
    public AlimentResponse update(Long id, AlimentRequest request) {
        Aliment aliment = alimentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aliment non trouvé avec l'id: " + id));
        aliment.setNom(request.getNom());
        aliment.setType(request.getType());
        aliment.setUnite(request.getUnite());
        if (request.getQuantiteStock() != null) {
            aliment.setQuantiteStock(request.getQuantiteStock());
        }
        aliment.setPrixUnitaire(request.getPrixUnitaire());
        aliment.setSeuilAlerte(request.getSeuilAlerte());
        aliment.setDateExpiration(request.getDateExpiration());
        aliment.setFournisseur(request.getFournisseur());
        aliment.setActif(request.getActif() != null ? request.getActif() : true);
        return AlimentMapper.toResponse(alimentRepository.save(aliment));
    }

    @Override
    public void delete(Long id) {
        Aliment aliment = alimentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aliment non trouvé avec l'id: " + id));
        alimentRepository.delete(aliment);
    }

    @Override
    public AlimentResponse getById(Long id) {
        return AlimentMapper.toResponse(alimentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aliment non trouvé avec l'id: " + id)));
    }

    @Override
    public List<AlimentResponse> getAll() {
        return alimentRepository.findAll().stream()
                .map(AlimentMapper::toResponse)
                .toList();
    }

    @Override
    public List<AlimentResponse> getStockBas() {
        return alimentRepository.findStockBas().stream()
                .map(AlimentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ConsommationAlimentResponse createConsommation(ConsommationAlimentRequest request) {
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal non trouvé avec l'id: " + request.getAnimalId()));
        Aliment aliment = alimentRepository.findById(request.getAlimentId())
                .orElseThrow(() -> new ResourceNotFoundException("Aliment non trouvé avec l'id: " + request.getAlimentId()));

        if (aliment.getQuantiteStock() == null || aliment.getQuantiteStock() < request.getQuantite()) {
            throw new InsufficientStockException(
                    "Stock insuffisant pour l'aliment '" + aliment.getNom() +
                    "'. Stock disponible: " + (aliment.getQuantiteStock() != null ? aliment.getQuantiteStock() : 0) +
                    ", quantité demandée: " + request.getQuantite());
        }

        aliment.setQuantiteStock(aliment.getQuantiteStock() - request.getQuantite());
        alimentRepository.save(aliment);

        ConsommationAliment consommation = ConsommationAlimentMapper.toEntity(request, animal, aliment);
        return ConsommationAlimentMapper.toResponse(consommationRepository.save(consommation));
    }

    @Override
    public List<ConsommationAlimentResponse> getAllConsommations() {
        return consommationRepository.findAll().stream()
                .map(ConsommationAlimentMapper::toResponse)
                .toList();
    }

    @Override
    public List<ConsommationAlimentResponse> getConsommationsByAnimal(Long animalId) {
        return consommationRepository.findByAnimalIdOrderByDateDesc(animalId).stream()
                .map(ConsommationAlimentMapper::toResponse)
                .toList();
    }
}
