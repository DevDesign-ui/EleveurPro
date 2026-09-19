package com.eleveurpro.service;

import com.eleveurpro.dto.VaccinationRequest;
import com.eleveurpro.dto.VaccinationResponse;
import com.eleveurpro.entity.Animal;
import com.eleveurpro.entity.Vaccination;
import com.eleveurpro.entity.enums.Sexe;
import com.eleveurpro.entity.enums.StatutAnimal;
import com.eleveurpro.repository.*;
import com.eleveurpro.service.impl.SanteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SanteServiceTest {

    @Mock private MaladieRepository maladieRepository;
    @Mock private ConsultationSanteRepository consultationRepository;
    @Mock private VaccinationRepository vaccinationRepository;
    @Mock private TraitementRepository traitementRepository;
    @Mock private AnimalRepository animalRepository;

    @InjectMocks private SanteServiceImpl santeService;

    private Animal animal;

    @BeforeEach
    void setUp() {
        animal = Animal.builder()
                .id(1L)
                .numeroIdentification("ANIM-001")
                .nom("TestAnimal")
                .sexe(Sexe.MALE)
                .statut(StatutAnimal.PRESENT)
                .build();
    }

    @Test
    void createVaccination_shouldReturnResponse() {
        VaccinationRequest request = VaccinationRequest.builder()
                .animalId(1L)
                .vaccin("Vaccin Fievre Aphteuse")
                .dateVaccination(LocalDate.now())
                .prochaineDate(LocalDate.now().plusMonths(6))
                .veterinaire("Dr. Fall")
                .observation("RAS")
                .build();

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(vaccinationRepository.save(any(Vaccination.class))).thenAnswer(inv -> {
            Vaccination v = inv.getArgument(0);
            v.setId(1L);
            return v;
        });

        VaccinationResponse response = santeService.createVaccination(request);

        assertNotNull(response);
        assertEquals("Vaccin Fievre Aphteuse", response.getVaccin());
        assertEquals("Dr. Fall", response.getVeterinaire());
        assertEquals("ANIM-001", response.getAnimalNumero());
    }
}
