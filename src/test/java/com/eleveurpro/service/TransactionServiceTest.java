package com.eleveurpro.service;

import com.eleveurpro.dto.*;
import com.eleveurpro.entity.*;
import com.eleveurpro.entity.enums.ModePaiement;
import com.eleveurpro.entity.enums.Sexe;
import com.eleveurpro.entity.enums.StatutAnimal;
import com.eleveurpro.entity.enums.StatutVente;
import com.eleveurpro.exception.BusinessException;
import com.eleveurpro.exception.ResourceNotFoundException;
import com.eleveurpro.repository.*;
import com.eleveurpro.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock private AchatRepository achatRepository;
    @Mock private VenteRepository venteRepository;
    @Mock private AnimalRepository animalRepository;
    @Mock private RevenuRepository revenuRepository;

    @InjectMocks private TransactionServiceImpl transactionService;

    private Animal animal;
    private Vente vente;

    @BeforeEach
    void setUp() {
        animal = Animal.builder()
                .id(1L)
                .numeroIdentification("ANIM-001")
                .nom("TestAnimal")
                .sexe(Sexe.MALE)
                .statut(StatutAnimal.PRESENT)
                .build();

        vente = Vente.builder()
                .id(1L)
                .animal(animal)
                .client("Client Test")
                .date(LocalDate.now())
                .prix(BigDecimal.valueOf(50000))
                .modePaiement(ModePaiement.ESPECES)
                .statut(StatutVente.EN_ATTENTE)
                .build();
    }

    @Test
    void createVente_withConfirmedStatus_shouldChangeAnimalStatutAndCreateRevenu() {
        VenteRequest request = VenteRequest.builder()
                .animalId(1L)
                .client("Client Test")
                .date(LocalDate.now())
                .prix(BigDecimal.valueOf(50000))
                .modePaiement(ModePaiement.ESPECES)
                .statut(StatutVente.CONFIRMEE)
                .build();

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(venteRepository.save(any(Vente.class))).thenAnswer(inv -> {
            Vente v = inv.getArgument(0);
            v.setId(1L);
            return v;
        });
        when(animalRepository.save(any(Animal.class))).thenAnswer(inv -> inv.getArgument(0));
        when(revenuRepository.save(any(Revenu.class))).thenAnswer(inv -> inv.getArgument(0));

        VenteResponse response = transactionService.createVente(request);

        assertNotNull(response);
        assertEquals(StatutVente.CONFIRMEE, response.getStatut());
        assertEquals(StatutAnimal.VENDU, animal.getStatut());
        verify(revenuRepository, times(1)).save(any(Revenu.class));
    }

    @Test
    void createVente_withPendingStatus_shouldNotChangeAnimalStatut() {
        VenteRequest request = VenteRequest.builder()
                .animalId(1L)
                .client("Client Test")
                .date(LocalDate.now())
                .prix(BigDecimal.valueOf(50000))
                .modePaiement(ModePaiement.ESPECES)
                .statut(StatutVente.EN_ATTENTE)
                .build();

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(venteRepository.save(any(Vente.class))).thenAnswer(inv -> {
            Vente v = inv.getArgument(0);
            v.setId(1L);
            return v;
        });

        VenteResponse response = transactionService.createVente(request);

        assertNotNull(response);
        assertEquals(StatutVente.EN_ATTENTE, response.getStatut());
        assertEquals(StatutAnimal.PRESENT, animal.getStatut());
        verify(revenuRepository, never()).save(any());
    }

    @Test
    void confirmerVente_shouldConfirmAndCreateRevenu() {
        when(venteRepository.findById(1L)).thenReturn(Optional.of(vente));
        when(venteRepository.save(any(Vente.class))).thenAnswer(inv -> inv.getArgument(0));
        when(animalRepository.save(any(Animal.class))).thenAnswer(inv -> inv.getArgument(0));
        when(revenuRepository.save(any(Revenu.class))).thenAnswer(inv -> inv.getArgument(0));

        transactionService.confirmerVente(1L);

        assertEquals(StatutVente.CONFIRMEE, vente.getStatut());
        assertEquals(StatutAnimal.VENDU, animal.getStatut());
        verify(revenuRepository, times(1)).save(any(Revenu.class));
    }

    @Test
    void confirmerVente_shouldThrowWhenAlreadyConfirmed() {
        vente.setStatut(StatutVente.CONFIRMEE);
        when(venteRepository.findById(1L)).thenReturn(Optional.of(vente));

        assertThrows(BusinessException.class, () -> transactionService.confirmerVente(1L));
    }

    @Test
    void createAchat_shouldReturnAchatResponse() {
        AchatRequest request = AchatRequest.builder()
                .categorie("Alimentation")
                .fournisseur("Fournisseur Test")
                .date(LocalDate.now())
                .montant(BigDecimal.valueOf(25000))
                .description("Achat de foin")
                .build();

        Achat savedAchat = Achat.builder()
                .id(1L)
                .categorie("Alimentation")
                .fournisseur("Fournisseur Test")
                .date(LocalDate.now())
                .montant(BigDecimal.valueOf(25000))
                .description("Achat de foin")
                .build();

        when(achatRepository.save(any(Achat.class))).thenReturn(savedAchat);

        AchatResponse response = transactionService.createAchat(request);

        assertNotNull(response);
        assertEquals("Alimentation", response.getCategorie());
        assertEquals(BigDecimal.valueOf(25000), response.getMontant());
    }

    @Test
    void getVenteById_shouldThrowWhenNotFound() {
        when(venteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> transactionService.getVenteById(99L));
    }
}
