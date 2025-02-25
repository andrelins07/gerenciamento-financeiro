package linsbr.dayofday.controller;

import linsbr.dayofday.model.Lancamento;
import linsbr.dayofday.repository.LancamentoRepository;
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
    private LancamentoRepository lancamentoRepository;

    @GetMapping
    public ResponseEntity<List<Lancamento>> getAllLancamentos(){
        List<Lancamento> lancamentos = lancamentoRepository.findAll();

        lancamentos.forEach(System.out::println);

        return ResponseEntity.ok(lancamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancamento> findLancamentoById(@PathVariable Long id) {

        return lancamentoRepository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
