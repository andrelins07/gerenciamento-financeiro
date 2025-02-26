package linsbr.dayofday.controller;

import jakarta.validation.Valid;
import linsbr.dayofday.model.CompartilhamentoPerfil;
import linsbr.dayofday.repository.CompartilhamentoPerfilRepository;
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
    private CompartilhamentoPerfilRepository compartilhamentoPerfisRepository;

    @GetMapping
    public ResponseEntity<List<CompartilhamentoPerfil>> getAllCompartilhamentos(){
        return ResponseEntity.ok(compartilhamentoPerfisRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompartilhamentoPerfil> findLancamentoById(@PathVariable Integer id) {

        return compartilhamentoPerfisRepository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @PostMapping
    public ResponseEntity<CompartilhamentoPerfil> cadastrarCategoria(@RequestBody @Valid CompartilhamentoPerfil compartilhamentoPerfis){

        return ResponseEntity.status(HttpStatus.CREATED).body(compartilhamentoPerfisRepository.save(compartilhamentoPerfis));

    }

    @PutMapping
    public ResponseEntity<CompartilhamentoPerfil> atualizarCategoria(@Valid @RequestBody CompartilhamentoPerfil compartilhamentoPerfis) {

        if (compartilhamentoPerfisRepository.existsById(compartilhamentoPerfis.getId())) {
            return ResponseEntity.status(HttpStatus.OK).body(compartilhamentoPerfisRepository.save(compartilhamentoPerfis));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletarCategoria(@PathVariable Integer id) {

        Optional<CompartilhamentoPerfil> compartilhamentoPerfis = compartilhamentoPerfisRepository.findById(id);

        if (compartilhamentoPerfis.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        compartilhamentoPerfisRepository.deleteById(id);
    }
}
