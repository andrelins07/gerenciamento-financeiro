package linsbr.dayofday.controller;

import jakarta.validation.Valid;
import linsbr.dayofday.model.Categoria;
import linsbr.dayofday.model.Lancamento;
import linsbr.dayofday.repository.CategoriaRepository;
import linsbr.dayofday.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

}
