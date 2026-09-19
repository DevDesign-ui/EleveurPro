package com.eleveurpro.service;

import com.eleveurpro.dto.ConsultationSanteRequest;
import com.eleveurpro.dto.ConsultationSanteResponse;
import com.eleveurpro.dto.MaladieRequest;
import com.eleveurpro.dto.MaladieResponse;
import com.eleveurpro.dto.TraitementRequest;
import com.eleveurpro.dto.TraitementResponse;
import com.eleveurpro.dto.VaccinationRequest;
import com.eleveurpro.dto.VaccinationResponse;

import java.util.List;

public interface SanteService {
    // Maladies
    List<MaladieResponse> getAllMaladies();
    MaladieResponse getMaladieById(Long id);
    MaladieResponse createMaladie(MaladieRequest request);
    MaladieResponse updateMaladie(Long id, MaladieRequest request);
    void deleteMaladie(Long id);

    // Consultations
    List<ConsultationSanteResponse> getAllConsultations();
    ConsultationSanteResponse getConsultationById(Long id);
    ConsultationSanteResponse createConsultation(ConsultationSanteRequest request);
    ConsultationSanteResponse updateConsultation(Long id, ConsultationSanteRequest request);
    void deleteConsultation(Long id);

    // Vaccinations
    List<VaccinationResponse> getAllVaccinations();
    VaccinationResponse getVaccinationById(Long id);
    VaccinationResponse createVaccination(VaccinationRequest request);
    VaccinationResponse updateVaccination(Long id, VaccinationRequest request);
    void deleteVaccination(Long id);

    // Traitements
    List<TraitementResponse> getAllTraitements();
    TraitementResponse getTraitementById(Long id);
    TraitementResponse createTraitement(TraitementRequest request);
    TraitementResponse updateTraitement(Long id, TraitementRequest request);
    void deleteTraitement(Long id);
}
