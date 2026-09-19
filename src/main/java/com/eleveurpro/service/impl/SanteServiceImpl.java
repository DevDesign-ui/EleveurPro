package com.eleveurpro.service.impl;

import com.eleveurpro.dto.*;
import com.eleveurpro.entity.*;
import com.eleveurpro.exception.ResourceNotFoundException;
import com.eleveurpro.mapper.*;
import com.eleveurpro.repository.*;
import com.eleveurpro.service.SanteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanteServiceImpl implements SanteService {

    private final MaladieRepository maladieRepository;
    private final ConsultationSanteRepository consultationRepository;
    private final VaccinationRepository vaccinationRepository;
    private final TraitementRepository traitementRepository;
    private final AnimalRepository animalRepository;

    public SanteServiceImpl(MaladieRepository maladieRepository,
                            ConsultationSanteRepository consultationRepository,
                            VaccinationRepository vaccinationRepository,
                            TraitementRepository traitementRepository,
                            AnimalRepository animalRepository) {
        this.maladieRepository = maladieRepository;
        this.consultationRepository = consultationRepository;
        this.vaccinationRepository = vaccinationRepository;
        this.traitementRepository = traitementRepository;
        this.animalRepository = animalRepository;
    }

    // ==================== Maladies ====================

    @Override
    public List<MaladieResponse> getAllMaladies() {
        return maladieRepository.findAll().stream().map(MaladieMapper::toResponse).toList();
    }

    @Override
    public MaladieResponse getMaladieById(Long id) {
        return MaladieMapper.toResponse(maladieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maladie non trouvée avec l'id: " + id)));
    }

    @Override
    public MaladieResponse createMaladie(MaladieRequest request) {
        return MaladieMapper.toResponse(maladieRepository.save(MaladieMapper.toEntity(request)));
    }

    @Override
    public MaladieResponse updateMaladie(Long id, MaladieRequest request) {
        Maladie maladie = maladieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maladie non trouvée avec l'id: " + id));
        maladie.setNom(request.getNom());
        maladie.setDescription(request.getDescription());
        return MaladieMapper.toResponse(maladieRepository.save(maladie));
    }

    @Override
    public void deleteMaladie(Long id) {
        Maladie maladie = maladieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maladie non trouvée avec l'id: " + id));
        maladieRepository.delete(maladie);
    }

    // ==================== Consultations ====================

    @Override
    public List<ConsultationSanteResponse> getAllConsultations() {
        return consultationRepository.findAll().stream().map(ConsultationSanteMapper::toResponse).toList();
    }

    @Override
    public ConsultationSanteResponse getConsultationById(Long id) {
        return ConsultationSanteMapper.toResponse(consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation non trouvée avec l'id: " + id)));
    }

    @Override
    public ConsultationSanteResponse createConsultation(ConsultationSanteRequest request) {
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal non trouvé avec l'id: " + request.getAnimalId()));
        Maladie maladie = null;
        if (request.getMaladieId() != null) {
            maladie = maladieRepository.findById(request.getMaladieId())
                    .orElseThrow(() -> new ResourceNotFoundException("Maladie non trouvée avec l'id: " + request.getMaladieId()));
        }
        ConsultationSante consultation = ConsultationSanteMapper.toEntity(request, animal, maladie);
        return ConsultationSanteMapper.toResponse(consultationRepository.save(consultation));
    }

    @Override
    public ConsultationSanteResponse updateConsultation(Long id, ConsultationSanteRequest request) {
        ConsultationSante consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation non trouvée avec l'id: " + id));
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal non trouvé avec l'id: " + request.getAnimalId()));
        Maladie maladie = null;
        if (request.getMaladieId() != null) {
            maladie = maladieRepository.findById(request.getMaladieId())
                    .orElseThrow(() -> new ResourceNotFoundException("Maladie non trouvée avec l'id: " + request.getMaladieId()));
        }
        consultation.setAnimal(animal);
        consultation.setMaladie(maladie);
        consultation.setDate(request.getDate());
        consultation.setSymptomes(request.getSymptomes());
        consultation.setDiagnostic(request.getDiagnostic());
        consultation.setTraitement(request.getTraitement());
        consultation.setVeterinaire(request.getVeterinaire());
        consultation.setObservation(request.getObservation());
        consultation.setStatut(request.getStatut());
        return ConsultationSanteMapper.toResponse(consultationRepository.save(consultation));
    }

    @Override
    public void deleteConsultation(Long id) {
        ConsultationSante consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation non trouvée avec l'id: " + id));
        consultationRepository.delete(consultation);
    }

    // ==================== Vaccinations ====================

    @Override
    public List<VaccinationResponse> getAllVaccinations() {
        return vaccinationRepository.findAll().stream().map(VaccinationMapper::toResponse).toList();
    }

    @Override
    public VaccinationResponse getVaccinationById(Long id) {
        return VaccinationMapper.toResponse(vaccinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccination non trouvée avec l'id: " + id)));
    }

    @Override
    public VaccinationResponse createVaccination(VaccinationRequest request) {
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal non trouvé avec l'id: " + request.getAnimalId()));
        Vaccination vaccination = VaccinationMapper.toEntity(request, animal);
        return VaccinationMapper.toResponse(vaccinationRepository.save(vaccination));
    }

    @Override
    public VaccinationResponse updateVaccination(Long id, VaccinationRequest request) {
        Vaccination vaccination = vaccinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccination non trouvée avec l'id: " + id));
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal non trouvé avec l'id: " + request.getAnimalId()));
        vaccination.setAnimal(animal);
        vaccination.setVaccin(request.getVaccin());
        vaccination.setDateVaccination(request.getDateVaccination());
        vaccination.setProchaineDate(request.getProchaineDate());
        vaccination.setVeterinaire(request.getVeterinaire());
        vaccination.setObservation(request.getObservation());
        return VaccinationMapper.toResponse(vaccinationRepository.save(vaccination));
    }

    @Override
    public void deleteVaccination(Long id) {
        Vaccination vaccination = vaccinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccination non trouvée avec l'id: " + id));
        vaccinationRepository.delete(vaccination);
    }

    // ==================== Traitements ====================

    @Override
    public List<TraitementResponse> getAllTraitements() {
        return traitementRepository.findAll().stream().map(TraitementMapper::toResponse).toList();
    }

    @Override
    public TraitementResponse getTraitementById(Long id) {
        return TraitementMapper.toResponse(traitementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Traitement non trouvé avec l'id: " + id)));
    }

    @Override
    public TraitementResponse createTraitement(TraitementRequest request) {
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal non trouvé avec l'id: " + request.getAnimalId()));
        Traitement traitement = TraitementMapper.toEntity(request, animal);
        return TraitementMapper.toResponse(traitementRepository.save(traitement));
    }

    @Override
    public TraitementResponse updateTraitement(Long id, TraitementRequest request) {
        Traitement traitement = traitementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Traitement non trouvé avec l'id: " + id));
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal non trouvé avec l'id: " + request.getAnimalId()));
        traitement.setAnimal(animal);
        traitement.setMedicament(request.getMedicament());
        traitement.setDosage(request.getDosage());
        traitement.setFrequence(request.getFrequence());
        traitement.setDateDebut(request.getDateDebut());
        traitement.setDateFin(request.getDateFin());
        traitement.setVeterinaire(request.getVeterinaire());
        traitement.setObservation(request.getObservation());
        return TraitementMapper.toResponse(traitementRepository.save(traitement));
    }

    @Override
    public void deleteTraitement(Long id) {
        Traitement traitement = traitementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Traitement non trouvé avec l'id: " + id));
        traitementRepository.delete(traitement);
    }
}
