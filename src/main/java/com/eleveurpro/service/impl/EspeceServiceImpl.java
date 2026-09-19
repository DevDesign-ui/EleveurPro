package com.eleveurpro.service.impl;

import com.eleveurpro.dto.EspeceRequest;
import com.eleveurpro.dto.EspeceResponse;
import com.eleveurpro.dto.PageResponse;
import com.eleveurpro.entity.Espece;
import com.eleveurpro.exception.ResourceNotFoundException;
import com.eleveurpro.mapper.EspeceMapper;
import com.eleveurpro.repository.EspeceRepository;
import com.eleveurpro.service.EspeceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspeceServiceImpl implements EspeceService {

    private final EspeceRepository especeRepository;

    public EspeceServiceImpl(EspeceRepository especeRepository) {
        this.especeRepository = especeRepository;
    }

    @Override
    public EspeceResponse create(EspeceRequest request) {
        Espece espece = EspeceMapper.toEntity(request);
        return EspeceMapper.toResponse(especeRepository.save(espece));
    }

    @Override
    public EspeceResponse update(Long id, EspeceRequest request) {
        Espece espece = especeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espèce non trouvée avec l'id: " + id));
        espece.setNom(request.getNom());
        espece.setDescription(request.getDescription());
        espece.setActif(request.getActif() != null ? request.getActif() : true);
        return EspeceMapper.toResponse(especeRepository.save(espece));
    }

    @Override
    public void delete(Long id) {
        Espece espece = especeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espèce non trouvée avec l'id: " + id));
        especeRepository.delete(espece);
    }

    @Override
    public EspeceResponse getById(Long id) {
        return EspeceMapper.toResponse(especeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espèce non trouvée avec l'id: " + id)));
    }

    @Override
    public List<EspeceResponse> getAll() {
        return especeRepository.findAll().stream()
                .map(EspeceMapper::toResponse)
                .toList();
    }

    @Override
    public PageResponse<EspeceResponse> getAllPaginated(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("nom").ascending());
        Page<Espece> especePage = especeRepository.findAll(pageRequest);
        List<EspeceResponse> content = especePage.getContent().stream()
                .map(EspeceMapper::toResponse)
                .toList();
        return PageResponse.<EspeceResponse>builder()
                .content(content)
                .page(especePage.getNumber())
                .size(especePage.getSize())
                .totalElements(especePage.getTotalElements())
                .totalPages(especePage.getTotalPages())
                .last(especePage.isLast())
                .build();
    }
}
