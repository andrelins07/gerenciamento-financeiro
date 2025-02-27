package linsbr.dayofday.controller;

import jakarta.validation.Valid;
import linsbr.dayofday.model.Lancamento;
import linsbr.dayofday.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/lancamento")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LancamentoController {

    @Autowired
    private LancamentoService lancamentoService;

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<List<Lancamento>> listarLancamentos(@PathVariable Integer id) {

        return ResponseEntity.ok(lancamentoService.listarTodosLancamentos(id));
    }
    @PostMapping
    public ResponseEntity<Lancamento> cadastrarNovoLancamento(@RequestBody @Valid Lancamento lancamento){

       return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoService.cadastrarLancamento(lancamento));
    }

    @PutMapping
    public ResponseEntity<Lancamento> atualizarLancamento(@Valid @RequestBody Lancamento lancamento) {

        return lancamentoService.atualizarLancamento(lancamento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePostagem(@PathVariable Long id) {
        lancamentoService.deletarLancamento(id);
    }


}
