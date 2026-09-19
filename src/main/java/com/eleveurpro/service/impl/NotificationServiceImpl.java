package com.eleveurpro.service.impl;

import com.eleveurpro.dto.NotificationResponse;
import com.eleveurpro.entity.Notification;
import com.eleveurpro.entity.User;
import com.eleveurpro.entity.enums.TypeNotification;
import com.eleveurpro.exception.ResourceNotFoundException;
import com.eleveurpro.mapper.NotificationMapper;
import com.eleveurpro.repository.*;
import com.eleveurpro.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final AlimentRepository alimentRepository;
    private final VaccinationRepository vaccinationRepository;
    private final ReproductionRepository reproductionRepository;
    private final TraitementRepository traitementRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                    UserRepository userRepository,
                                    AlimentRepository alimentRepository,
                                    VaccinationRepository vaccinationRepository,
                                    ReproductionRepository reproductionRepository,
                                    TraitementRepository traitementRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.alimentRepository = alimentRepository;
        this.vaccinationRepository = vaccinationRepository;
        this.reproductionRepository = reproductionRepository;
        this.traitementRepository = traitementRepository;
    }

    @Override
    public List<NotificationResponse> getAllForCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé: " + email));
        return notificationRepository.findByUserIdOrderByDateCreationDesc(user.getId()).stream()
                .map(NotificationMapper::toResponse)
                .toList();
    }

    @Override
    public List<NotificationResponse> getUnreadForCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé: " + email));
        return notificationRepository.findByUserIdAndLuFalseOrderByDateCreationDesc(user.getId()).stream()
                .map(NotificationMapper::toResponse)
                .toList();
    }

    @Override
    public NotificationResponse markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification non trouvée avec l'id: " + id));
        notification.setLu(true);
        return NotificationMapper.toResponse(notificationRepository.save(notification));
    }

    @Override
    @Transactional
    public void generateNotifications() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) return;
        User admin = users.getFirst();

        LocalDate today = LocalDate.now();
        LocalDate in30Days = today.plusDays(30);

        // Stock bas
        alimentRepository.findStockBas().forEach(aliment -> {
            boolean exists = notificationRepository.findByUserIdOrderByDateCreationDesc(admin.getId()).stream()
                    .anyMatch(n -> n.getType() == TypeNotification.STOCK_BAS &&
                            n.getMessage().contains(aliment.getNom()) &&
                            !n.getLu());
            if (!exists) {
                Notification notif = Notification.builder()
                        .user(admin)
                        .titre("Stock bas: " + aliment.getNom())
                        .message("Le stock de '" + aliment.getNom() + "' est bas. Quantité restante: " + aliment.getQuantiteStock() + " " + aliment.getUnite())
                        .type(TypeNotification.STOCK_BAS)
                        .lu(false)
                        .build();
                notificationRepository.save(notif);
            }
        });

        // Vaccinations proches
        vaccinationRepository.findVaccinationsProchaines(today, in30Days).forEach(vacc -> {
            boolean exists = notificationRepository.findByUserIdOrderByDateCreationDesc(admin.getId()).stream()
                    .anyMatch(n -> n.getType() == TypeNotification.VACCINATION_PROCHE &&
                            n.getMessage().contains(vacc.getVaccin()) &&
                            !n.getLu());
            if (!exists) {
                Notification notif = Notification.builder()
                        .user(admin)
                        .titre("Vaccination à venir")
                        .message("Vaccination '" + vacc.getVaccin() + "' pour l'animal " +
                                (vacc.getAnimal().getNom() != null ? vacc.getAnimal().getNom() : vacc.getAnimal().getNumeroIdentification()) +
                                " prévue le " + vacc.getProchaineDate())
                        .type(TypeNotification.VACCINATION_PROCHE)
                        .lu(false)
                        .build();
                notificationRepository.save(notif);
            }
        });

        // Mises bas proches
        reproductionRepository.findMisesBasProchaines(today, in30Days).forEach(repro -> {
            boolean exists = notificationRepository.findByUserIdOrderByDateCreationDesc(admin.getId()).stream()
                    .anyMatch(n -> n.getType() == TypeNotification.MISE_BAS_PROCHE &&
                            n.getMessage().contains(repro.getFemelle().getNumeroIdentification()) &&
                            !n.getLu());
            if (!exists) {
                Notification notif = Notification.builder()
                        .user(admin)
                        .titre("Mise bas proche")
                        .message("Mise bas prévue le " + repro.getDatePrevueMiseBas() + " pour la femelle " +
                                (repro.getFemelle().getNom() != null ? repro.getFemelle().getNom() : repro.getFemelle().getNumeroIdentification()))
                        .type(TypeNotification.MISE_BAS_PROCHE)
                        .lu(false)
                        .build();
                notificationRepository.save(notif);
            }
        });

        // Traitements arrivant à échéance
        traitementRepository.findTraitementsEcheance(today, in30Days).forEach(trait -> {
            boolean exists = notificationRepository.findByUserIdOrderByDateCreationDesc(admin.getId()).stream()
                    .anyMatch(n -> n.getType() == TypeNotification.TRAITEMENT_ECHEANCE &&
                            n.getMessage().contains(trait.getMedicament()) &&
                            !n.getLu());
            if (!exists) {
                Notification notif = Notification.builder()
                        .user(admin)
                        .titre("Traitement arrivant à échéance")
                        .message("Le traitement '" + trait.getMedicament() + "' pour l'animal " +
                                (trait.getAnimal().getNom() != null ? trait.getAnimal().getNom() : trait.getAnimal().getNumeroIdentification()) +
                                " se termine le " + trait.getDateFin())
                        .type(TypeNotification.TRAITEMENT_ECHEANCE)
                        .lu(false)
                        .build();
                notificationRepository.save(notif);
            }
        });
    }
}
