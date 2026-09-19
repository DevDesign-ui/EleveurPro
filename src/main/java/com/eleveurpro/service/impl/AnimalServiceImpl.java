package com.eleveurpro.service.impl;

import com.eleveurpro.dto.AnimalRequest;
import com.eleveurpro.dto.AnimalResponse;
import com.eleveurpro.dto.PageResponse;
import com.eleveurpro.entity.Animal;
import com.eleveurpro.entity.Espece;
import com.eleveurpro.entity.Race;
import com.eleveurpro.entity.enums.StatutAnimal;
import com.eleveurpro.exception.ResourceAlreadyExistsException;
import com.eleveurpro.exception.ResourceNotFoundException;
import com.eleveurpro.mapper.AnimalMapper;
import com.eleveurpro.repository.AnimalRepository;
import com.eleveurpro.repository.EspeceRepository;
import com.eleveurpro.repository.RaceRepository;
import com.eleveurpro.service.AnimalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final EspeceRepository especeRepository;
    private final RaceRepository raceRepository;

    public AnimalServiceImpl(AnimalRepository animalRepository,
                             EspeceRepository especeRepository,
                             RaceRepository raceRepository) {
        this.animalRepository = animalRepository;
        this.especeRepository = especeRepository;
        this.raceRepository = raceRepository;
    }

    @Override
    public AnimalResponse create(AnimalRequest request) {
        if (animalRepository.existsByNumeroIdentification(request.getNumeroIdentification())) {
            throw new ResourceAlreadyExistsException("Un animal avec ce numéro d'identification existe déjà: " + request.getNumeroIdentification());
        }
        Espece espece = especeRepository.findById(request.getEspeceId())
                .orElseThrow(() -> new ResourceNotFoundException("Espèce non trouvée avec l'id: " + request.getEspeceId()));
        Race race = null;
        if (request.getRaceId() != null) {
            race = raceRepository.findById(request.getRaceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Race non trouvée avec l'id: " + request.getRaceId()));
        }
        Animal animal = AnimalMapper.toEntity(request, espece, race);
        return AnimalMapper.toResponse(animalRepository.save(animal));
    }

    @Override
    public AnimalResponse update(Long id, AnimalRequest request) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal non trouvé avec l'id: " + id));
        if (!animal.getNumeroIdentification().equals(request.getNumeroIdentification()) &&
                animalRepository.existsByNumeroIdentification(request.getNumeroIdentification())) {
            throw new ResourceAlreadyExistsException("Un animal avec ce numéro d'identification existe déjà: " + request.getNumeroIdentification());
        }
        Espece espece = especeRepository.findById(request.getEspeceId())
                .orElseThrow(() -> new ResourceNotFoundException("Espèce non trouvée avec l'id: " + request.getEspeceId()));
        Race race = null;
        if (request.getRaceId() != null) {
            race = raceRepository.findById(request.getRaceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Race non trouvée avec l'id: " + request.getRaceId()));
        }
        animal.setNumeroIdentification(request.getNumeroIdentification());
        animal.setNom(request.getNom());
        animal.setEspece(espece);
        animal.setRace(race);
        animal.setSexe(request.getSexe());
        animal.setDateNaissance(request.getDateNaissance());
        animal.setPoids(request.getPoids());
        animal.setCouleur(request.getCouleur());
        animal.setDateAcquisition(request.getDateAcquisition());
        animal.setPrixAcquisition(request.getPrixAcquisition());
        animal.setOrigine(request.getOrigine());
        animal.setStatut(request.getStatut());
        animal.setPhotoUrl(request.getPhotoUrl());
        animal.setDescription(request.getDescription());
        return AnimalMapper.toResponse(animalRepository.save(animal));
    }

    @Override
    public void delete(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal non trouvé avec l'id: " + id));
        animalRepository.delete(animal);
    }

    @Override
    public AnimalResponse getById(Long id) {
        return AnimalMapper.toResponse(animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal non trouvé avec l'id: " + id)));
    }

    @Override
    public List<AnimalResponse> getAll() {
        return animalRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream()
                .map(AnimalMapper::toResponse)
                .toList();
    }

    @Override
    public PageResponse<AnimalResponse> getAllPaginated(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Animal> animalPage = animalRepository.findAll(pageRequest);
        List<AnimalResponse> content = animalPage.getContent().stream()
                .map(AnimalMapper::toResponse)
                .toList();
        return PageResponse.<AnimalResponse>builder()
                .content(content)
                .page(animalPage.getNumber())
                .size(animalPage.getSize())
                .totalElements(animalPage.getTotalElements())
                .totalPages(animalPage.getTotalPages())
                .last(animalPage.isLast())
                .build();
    }

    @Override
    public PageResponse<AnimalResponse> search(String numero, String nom, Long especeId, Long raceId, StatutAnimal statut, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Animal> animalPage = animalRepository.search(numero, nom, especeId, raceId, statut, pageRequest);
        List<AnimalResponse> content = animalPage.getContent().stream()
                .map(AnimalMapper::toResponse)
                .toList();
        return PageResponse.<AnimalResponse>builder()
                .content(content)
                .page(animalPage.getNumber())
                .size(animalPage.getSize())
                .totalElements(animalPage.getTotalElements())
                .totalPages(animalPage.getTotalPages())
                .last(animalPage.isLast())
                .build();
    }
}
