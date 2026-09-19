package com.eleveurpro.service.impl;

import com.eleveurpro.dto.DepenseRequest;
import com.eleveurpro.dto.DepenseResponse;
import com.eleveurpro.dto.RevenuRequest;
import com.eleveurpro.dto.RevenuResponse;
import com.eleveurpro.entity.Depense;
import com.eleveurpro.entity.Revenu;
import com.eleveurpro.exception.ResourceNotFoundException;
import com.eleveurpro.mapper.DepenseMapper;
import com.eleveurpro.mapper.RevenuMapper;
import com.eleveurpro.repository.DepenseRepository;
import com.eleveurpro.repository.RevenuRepository;
import com.eleveurpro.service.FinanceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceServiceImpl implements FinanceService {

    private final DepenseRepository depenseRepository;
    private final RevenuRepository revenuRepository;

    public FinanceServiceImpl(DepenseRepository depenseRepository, RevenuRepository revenuRepository) {
        this.depenseRepository = depenseRepository;
        this.revenuRepository = revenuRepository;
    }

    // ==================== Dépenses ====================

    @Override
    public List<DepenseResponse> getAllDepenses() {
        return depenseRepository.findAll().stream().map(DepenseMapper::toResponse).toList();
    }

    @Override
    public DepenseResponse getDepenseById(Long id) {
        return DepenseMapper.toResponse(depenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dépense non trouvée avec l'id: " + id)));
    }

    @Override
    public DepenseResponse createDepense(DepenseRequest request) {
        return DepenseMapper.toResponse(depenseRepository.save(DepenseMapper.toEntity(request)));
    }

    @Override
    public DepenseResponse updateDepense(Long id, DepenseRequest request) {
        Depense depense = depenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dépense non trouvée avec l'id: " + id));
        depense.setCategorie(request.getCategorie());
        depense.setMontant(request.getMontant());
        depense.setDate(request.getDate());
        depense.setDescription(request.getDescription());
        return DepenseMapper.toResponse(depenseRepository.save(depense));
    }

    @Override
    public void deleteDepense(Long id) {
        Depense depense = depenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dépense non trouvée avec l'id: " + id));
        depenseRepository.delete(depense);
    }

    // ==================== Revenus ====================

    @Override
    public List<RevenuResponse> getAllRevenus() {
        return revenuRepository.findAll().stream().map(RevenuMapper::toResponse).toList();
    }

    @Override
    public RevenuResponse getRevenuById(Long id) {
        return RevenuMapper.toResponse(revenuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Revenu non trouvé avec l'id: " + id)));
    }

    @Override
    public RevenuResponse createRevenu(RevenuRequest request) {
        return RevenuMapper.toResponse(revenuRepository.save(RevenuMapper.toEntity(request)));
    }

    @Override
    public RevenuResponse updateRevenu(Long id, RevenuRequest request) {
        Revenu revenu = revenuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Revenu non trouvé avec l'id: " + id));
        revenu.setCategorie(request.getCategorie());
        revenu.setMontant(request.getMontant());
        revenu.setDate(request.getDate());
        revenu.setDescription(request.getDescription());
        return RevenuMapper.toResponse(revenuRepository.save(revenu));
    }

    @Override
    public void deleteRevenu(Long id) {
        Revenu revenu = revenuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Revenu non trouvé avec l'id: " + id));
        revenuRepository.delete(revenu);
    }
}
