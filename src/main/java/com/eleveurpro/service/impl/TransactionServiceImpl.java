package com.eleveurpro.service.impl;

import com.eleveurpro.dto.AchatRequest;
import com.eleveurpro.dto.AchatResponse;
import com.eleveurpro.dto.VenteRequest;
import com.eleveurpro.dto.VenteResponse;
import com.eleveurpro.entity.*;
import com.eleveurpro.entity.enums.StatutAnimal;
import com.eleveurpro.entity.enums.StatutVente;
import com.eleveurpro.exception.BusinessException;
import com.eleveurpro.exception.ResourceNotFoundException;
import com.eleveurpro.mapper.AchatMapper;
import com.eleveurpro.mapper.VenteMapper;
import com.eleveurpro.repository.*;
import com.eleveurpro.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final AchatRepository achatRepository;
    private final VenteRepository venteRepository;
    private final AnimalRepository animalRepository;
    private final RevenuRepository revenuRepository;

    public TransactionServiceImpl(AchatRepository achatRepository,
                                   VenteRepository venteRepository,
                                   AnimalRepository animalRepository,
                                   RevenuRepository revenuRepository) {
        this.achatRepository = achatRepository;
        this.venteRepository = venteRepository;
        this.animalRepository = animalRepository;
        this.revenuRepository = revenuRepository;
    }

    // ==================== Achats ====================

    @Override
    public List<AchatResponse> getAllAchats() {
        return achatRepository.findAll().stream().map(AchatMapper::toResponse).toList();
    }

    @Override
    public AchatResponse getAchatById(Long id) {
        return AchatMapper.toResponse(achatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Achat non trouvé avec l'id: " + id)));
    }

    @Override
    public AchatResponse createAchat(AchatRequest request) {
        return AchatMapper.toResponse(achatRepository.save(AchatMapper.toEntity(request)));
    }

    @Override
    public AchatResponse updateAchat(Long id, AchatRequest request) {
        Achat achat = achatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Achat non trouvé avec l'id: " + id));
        achat.setCategorie(request.getCategorie());
        achat.setFournisseur(request.getFournisseur());
        achat.setDate(request.getDate());
        achat.setMontant(request.getMontant());
        achat.setDescription(request.getDescription());
        return AchatMapper.toResponse(achatRepository.save(achat));
    }

    @Override
    public void deleteAchat(Long id) {
        Achat achat = achatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Achat non trouvé avec l'id: " + id));
        achatRepository.delete(achat);
    }

    // ==================== Ventes ====================

    @Override
    public List<VenteResponse> getAllVentes() {
        return venteRepository.findAll().stream().map(VenteMapper::toResponse).toList();
    }

    @Override
    public VenteResponse getVenteById(Long id) {
        return VenteMapper.toResponse(venteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vente non trouvée avec l'id: " + id)));
    }

    @Override
    @Transactional
    public VenteResponse createVente(VenteRequest request) {
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal non trouvé avec l'id: " + request.getAnimalId()));
        Vente vente = VenteMapper.toEntity(request, animal);
        Vente savedVente = venteRepository.save(vente);

        if (request.getStatut() == StatutVente.CONFIRMEE) {
            animal.setStatut(StatutAnimal.VENDU);
            animalRepository.save(animal);
            createRevenuFromVente(savedVente);
        }

        return VenteMapper.toResponse(savedVente);
    }

    @Override
    @Transactional
    public VenteResponse updateVente(Long id, VenteRequest request) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vente non trouvée avec l'id: " + id));
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal non trouvé avec l'id: " + request.getAnimalId()));

        boolean wasConfirmed = vente.getStatut() == StatutVente.CONFIRMEE;
        boolean willBeConfirmed = request.getStatut() == StatutVente.CONFIRMEE;

        vente.setAnimal(animal);
        vente.setClient(request.getClient());
        vente.setDate(request.getDate());
        vente.setPrix(request.getPrix());
        vente.setModePaiement(request.getModePaiement());
        vente.setStatut(request.getStatut());
        vente.setObservation(request.getObservation());
        Vente savedVente = venteRepository.save(vente);

        if (!wasConfirmed && willBeConfirmed) {
            animal.setStatut(StatutAnimal.VENDU);
            animalRepository.save(animal);
            createRevenuFromVente(savedVente);
        }

        return VenteMapper.toResponse(savedVente);
    }

    @Override
    public void deleteVente(Long id) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vente non trouvée avec l'id: " + id));
        venteRepository.delete(vente);
    }

    @Override
    @Transactional
    public void confirmerVente(Long id) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vente non trouvée avec l'id: " + id));
        if (vente.getStatut() == StatutVente.CONFIRMEE) {
            throw new BusinessException("Cette vente est déjà confirmée");
        }
        vente.setStatut(StatutVente.CONFIRMEE);
        venteRepository.save(vente);

        Animal animal = vente.getAnimal();
        if (animal != null) {
            animal.setStatut(StatutAnimal.VENDU);
            animalRepository.save(animal);
        }

        createRevenuFromVente(vente);
    }

    private void createRevenuFromVente(Vente vente) {
        Revenu revenu = Revenu.builder()
                .categorie("Vente animal")
                .montant(vente.getPrix())
                .date(vente.getDate())
                .description("Vente de " + (vente.getAnimal() != null ? vente.getAnimal().getNom() : "animal") +
                             " à " + (vente.getClient() != null ? vente.getClient() : "client"))
                .build();
        revenuRepository.save(revenu);
    }
}
