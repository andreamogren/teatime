package org.example.teatime.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.teatime.entities.Tea;
import org.example.teatime.repositories.TeaRepository;
import org.example.teatime.utils.MergeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/teas")
@Tag(name = "Tea API", description = "API for managing teas")
public class TeaController {
    private final TeaRepository teaRepository;

    @Autowired
    public TeaController(TeaRepository teaRepository) {
        this.teaRepository = teaRepository;
    }

    @GetMapping("/all")
    @Operation(summary = "Get all teas", description = "Retrieve a list of all teas")
    public ResponseEntity<List<Tea>> findAll(){
        return ResponseEntity.ok(teaRepository.findAll());
    }

    @PostMapping("/create")
    @Transactional
    @Operation(summary = "Create a new tea", description = "Add a new tea to the repository")
    public ResponseEntity<String> addTea(@RequestBody @Valid Tea tea) {
        try {
            teaRepository.save(tea);
            return ResponseEntity.status(HttpStatus.CREATED).body("Tea created");
        } catch(Exception e) {
            System.err.println("An error occurred while saving the tea: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create tea");
    }

    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<String> updateTea(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Tea> oldTea = teaRepository.findById(id);
        if (oldTea.isPresent()) {
            Tea updatedTea = oldTea.get();
            MergeUtils.merge(updatedTea, updates);
            try {
                teaRepository.save(updatedTea);
                return ResponseEntity.ok("Tea updated");
            } catch(Exception e) {
                System.err.println("An error occurred while saving the tea: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tea not found");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update tea");
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    @Operation(summary = "Delete a tea", description = "Delete a tea by its ID")
    public ResponseEntity<String> deleteTea(@PathVariable Long id) {
        try {
            teaRepository.deleteById(id);
            return ResponseEntity.ok("Tea deleted");
        } catch (Exception e) {
            System.err.println("An error occurred while deleting the tea: " + e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete tea");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a tea by ID", description = "Retrieve a tea by its ID")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        try {
            Optional<Tea> teaOptional = teaRepository.findById(id);
            if (teaOptional.isPresent()) {
                return ResponseEntity.ok(teaOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tea not found");
            }
        } catch (Exception e) {
                System.err.println("An error occurred while fetching the tea: " + e.getMessage());
            }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve tea");
        }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get teas by type", description = "Retrieve teas by their type")
    public ResponseEntity<?> getTeasByType(@PathVariable String type) {
        Optional<List<Tea>> teasOfType = Optional.ofNullable(teaRepository.findByType(type));
            if (teasOfType.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(teasOfType.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Either no teas found of type or " + type + "not found.");
            }
        }
    }
