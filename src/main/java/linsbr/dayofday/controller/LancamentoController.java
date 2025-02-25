package linsbr.dayofday.controller;

import jakarta.validation.Valid;
import linsbr.dayofday.model.Lancamento;
import linsbr.dayofday.repository.CategoriaRepository;
import linsbr.dayofday.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lancamento")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LancamentoController {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Lancamento>> getAllLancamentos(){
        return ResponseEntity.ok(lancamentoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancamento> findLancamentoById(@PathVariable Long id) {

        return lancamentoRepository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @PostMapping
    public ResponseEntity<Lancamento> postLancamento(@RequestBody @Valid Lancamento lancamento){

        if(categoriaRepository.existsById(lancamento.getCategoria().getId())){
            return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoRepository.save(lancamento));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não encontrada", null);
    }

    @PutMapping
    public ResponseEntity<Lancamento> atualizarLancamento(@Valid @RequestBody Lancamento lancamento) {

        if (lancamentoRepository.existsById(lancamento.getId())) {

            if (categoriaRepository.existsById(lancamento.getCategoria().getId())) {
                return ResponseEntity.status(HttpStatus.OK).body(lancamentoRepository.save(lancamento));
            }

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePostagem(@PathVariable Long id) {

        Optional<Lancamento> lancamento = lancamentoRepository.findById(id);

        if (lancamento.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        lancamentoRepository.deleteById(id);
    }

}
