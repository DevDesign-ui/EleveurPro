package com.eleveurpro.service;

import com.eleveurpro.dto.*;
import com.eleveurpro.entity.*;
import com.eleveurpro.entity.enums.TypeAliment;
import com.eleveurpro.exception.InsufficientStockException;
import com.eleveurpro.exception.ResourceNotFoundException;
import com.eleveurpro.repository.*;
import com.eleveurpro.service.impl.AlimentServiceImpl;
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
class AlimentServiceTest {

    @Mock private AlimentRepository alimentRepository;
    @Mock private AnimalRepository animalRepository;
    @Mock private ConsommationAlimentRepository consommationRepository;

    @InjectMocks private AlimentServiceImpl alimentService;

    private Aliment aliment;
    private Animal animal;

    @BeforeEach
    void setUp() {
        aliment = Aliment.builder()
                .id(1L)
                .nom("Foin")
                .type(TypeAliment.FOURRAGE)
                .unite("kg")
                .quantiteStock(100.0)
                .seuilAlerte(20.0)
                .actif(true)
                .build();
        animal = Animal.builder()
                .id(1L)
                .numeroIdentification("ANIM-001")
                .nom("TestAnimal")
                .build();
    }

    @Test
    void createConsommation_shouldDecreaseStock() {
        ConsommationAlimentRequest request = ConsommationAlimentRequest.builder()
                .animalId(1L)
                .alimentId(1L)
                .quantite(30.0)
                .date(LocalDate.now())
                .cout(BigDecimal.valueOf(1500))
                .build();

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(alimentRepository.findById(1L)).thenReturn(Optional.of(aliment));
        when(alimentRepository.save(any(Aliment.class))).thenAnswer(inv -> inv.getArgument(0));
        when(consommationRepository.save(any(ConsommationAliment.class)))
                .thenAnswer(inv -> {
                    ConsommationAliment c = inv.getArgument(0);
                    c.setId(1L);
                    return c;
                });

        ConsommationAlimentResponse response = alimentService.createConsommation(request);

        assertNotNull(response);
        assertEquals(70.0, aliment.getQuantiteStock());
        verify(alimentRepository, times(1)).save(aliment);
    }

    @Test
    void createConsommation_shouldThrowWhenInsufficientStock() {
        ConsommationAlimentRequest request = ConsommationAlimentRequest.builder()
                .animalId(1L)
                .alimentId(1L)
                .quantite(150.0)
                .date(LocalDate.now())
                .build();

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(alimentRepository.findById(1L)).thenReturn(Optional.of(aliment));

        assertThrows(InsufficientStockException.class, () -> alimentService.createConsommation(request));
    }

    @Test
    void getStockBas_shouldReturnAlimentsWithLowStock() {
        aliment.setQuantiteStock(15.0);
        when(alimentRepository.findStockBas()).thenReturn(java.util.List.of(aliment));

        var result = alimentService.getStockBas();

        assertEquals(1, result.size());
        assertTrue(result.get(0).getStockBas());
    }

    @Test
    void getById_shouldThrowWhenNotFound() {
        when(alimentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> alimentService.getById(99L));
    }
}
