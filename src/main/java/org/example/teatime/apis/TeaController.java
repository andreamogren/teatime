package org.example.teatime.apis;

import org.example.teatime.entities.Tea;
import org.example.teatime.repositories.TeaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/teas")
@RestController
public class TeaController {
    private TeaRepository teaRepository;

    public TeaController(TeaRepository teaRepository) {
        this.teaRepository = teaRepository;
    }

    @GetMapping("/")
    public List<Tea> findAll(){
        return teaRepository.findAll();
    }

    @PutMapping("/add")
    public String addTea(Tea tea) {
        teaRepository.save(tea);
        return "Tea added";
    }

    @PutMapping("/update")
    public String updateTea(Tea tea) {
        teaRepository.save(tea);
        return "Tea updated";
    }

    @PutMapping("/delete")
    public String deleteTea(Tea tea) {
        teaRepository.delete(tea);
        return "Tea deleted";
    }

    @GetMapping("/id/{id}")
    public Optional<Tea> findOne(@PathVariable Long id) {
        return teaRepository.findById(id);
    }

    @GetMapping("/type/{type}")
    public List<Tea> getTeasByType(@PathVariable String type) {
        return teaRepository.findByType(type);
    }
}
