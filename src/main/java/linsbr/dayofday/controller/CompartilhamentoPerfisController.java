package linsbr.dayofday.controller;

import jakarta.validation.Valid;
import linsbr.dayofday.model.CompartilhamentoPerfis;
import linsbr.dayofday.repository.CompartilhamentoPerfisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/compartilhamento")
public class CompartilhamentoPerfisController {

    @Autowired
    private CompartilhamentoPerfisRepository compartilhamentoPerfisRepository;

    @GetMapping
    public ResponseEntity<List<CompartilhamentoPerfis>> getAllCompartilhamentos(){
        return ResponseEntity.ok(compartilhamentoPerfisRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompartilhamentoPerfis> findLancamentoById(@PathVariable Integer id) {

        return compartilhamentoPerfisRepository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @PostMapping
    public ResponseEntity<CompartilhamentoPerfis> cadastrarCategoria(@RequestBody @Valid CompartilhamentoPerfis compartilhamentoPerfis){

        return ResponseEntity.status(HttpStatus.CREATED).body(compartilhamentoPerfisRepository.save(compartilhamentoPerfis));

    }

    @PutMapping
    public ResponseEntity<CompartilhamentoPerfis> atualizarCategoria(@Valid @RequestBody CompartilhamentoPerfis compartilhamentoPerfis) {

        if (compartilhamentoPerfisRepository.existsById(compartilhamentoPerfis.getId())) {
            return ResponseEntity.status(HttpStatus.OK).body(compartilhamentoPerfisRepository.save(compartilhamentoPerfis));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletarCategoria(@PathVariable Integer id) {

        Optional<CompartilhamentoPerfis> compartilhamentoPerfis = compartilhamentoPerfisRepository.findById(id);

        if (compartilhamentoPerfis.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        compartilhamentoPerfisRepository.deleteById(id);
    }
}
