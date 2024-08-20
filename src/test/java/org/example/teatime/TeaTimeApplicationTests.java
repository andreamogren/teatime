package org.example.teatime;

import org.example.teatime.apis.TeaController;
import org.example.teatime.entities.Tea;
import org.example.teatime.repositories.TeaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TeaControllerTest {

    @Mock
    private TeaRepository teaRepository;

    @InjectMocks
    private TeaController teaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ReturnsListOfTeas() {
        List<Tea> teas = Arrays.asList(new Tea(), new Tea());
        when(teaRepository.findAll()).thenReturn(teas);

        ResponseEntity<List<Tea>> response = teaController.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(teas);
    }

    @Test
    void addTea_CreatesNewTea() {
        Tea tea = new Tea();
        when(teaRepository.save(any(Tea.class))).thenReturn(tea);

        ResponseEntity<String> response = teaController.addTea(tea);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo("Tea created");
    }

    @Test
    void updateTea_UpdatesExistingTea() {
        Tea oldTea = new Tea();
        Map<String, Object> updates = new HashMap<>();
        when(teaRepository.findById(anyLong())).thenReturn(Optional.of(oldTea));
        when(teaRepository.save(any(Tea.class))).thenReturn(oldTea);

        ResponseEntity<String> response = teaController.updateTea(1L, updates);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Tea updated");
    }

    @Test
    void updateTea_TeaNotFound() {
        when(teaRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<String> response = teaController.updateTea(1L, new HashMap<>());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Tea not found");
    }

    @Test
    void deleteTea_DeletesTeaById() {
        doNothing().when(teaRepository).deleteById(anyLong());

        ResponseEntity<String> response = teaController.deleteTea(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Tea deleted");
    }

    @Test
    void findOne_ReturnsTeaById() {
        Tea tea = new Tea();
        when(teaRepository.findById(anyLong())).thenReturn(Optional.of(tea));

        ResponseEntity<?> response = teaController.findOne(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(tea);
    }

    @Test
    void getTeasByType_ReturnsTeasByType() {
        List<Tea> teas = Arrays.asList(new Tea(), new Tea());
        when(teaRepository.findByType(anyString())).thenReturn(teas);

        ResponseEntity<?> response = teaController.getTeasByType("Green");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(teas);
    }
}
