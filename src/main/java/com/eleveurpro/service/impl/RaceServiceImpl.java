package com.eleveurpro.service.impl;

import com.eleveurpro.dto.RaceRequest;
import com.eleveurpro.dto.RaceResponse;
import com.eleveurpro.entity.Espece;
import com.eleveurpro.entity.Race;
import com.eleveurpro.exception.ResourceNotFoundException;
import com.eleveurpro.mapper.RaceMapper;
import com.eleveurpro.repository.EspeceRepository;
import com.eleveurpro.repository.RaceRepository;
import com.eleveurpro.service.RaceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceServiceImpl implements RaceService {

    private final RaceRepository raceRepository;
    private final EspeceRepository especeRepository;

    public RaceServiceImpl(RaceRepository raceRepository, EspeceRepository especeRepository) {
        this.raceRepository = raceRepository;
        this.especeRepository = especeRepository;
    }

    @Override
    public RaceResponse create(RaceRequest request) {
        Espece espece = especeRepository.findById(request.getEspeceId())
                .orElseThrow(() -> new ResourceNotFoundException("Espèce non trouvée avec l'id: " + request.getEspeceId()));
        Race race = RaceMapper.toEntity(request, espece);
        return RaceMapper.toResponse(raceRepository.save(race));
    }

    @Override
    public RaceResponse update(Long id, RaceRequest request) {
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Race non trouvée avec l'id: " + id));
        Espece espece = especeRepository.findById(request.getEspeceId())
                .orElseThrow(() -> new ResourceNotFoundException("Espèce non trouvée avec l'id: " + request.getEspeceId()));
        race.setNom(request.getNom());
        race.setDescription(request.getDescription());
        race.setEspece(espece);
        return RaceMapper.toResponse(raceRepository.save(race));
    }

    @Override
    public void delete(Long id) {
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Race non trouvée avec l'id: " + id));
        raceRepository.delete(race);
    }

    @Override
    public RaceResponse getById(Long id) {
        return RaceMapper.toResponse(raceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Race non trouvée avec l'id: " + id)));
    }

    @Override
    public List<RaceResponse> getAll() {
        return raceRepository.findAll().stream()
                .map(RaceMapper::toResponse)
                .toList();
    }

    @Override
    public List<RaceResponse> getByEspeceId(Long especeId) {
        return raceRepository.findByEspeceId(especeId).stream()
                .map(RaceMapper::toResponse)
                .toList();
    }
}
