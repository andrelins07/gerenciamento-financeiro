package linsbr.dayofday.service;

import linsbr.dayofday.model.CompartilhamentoPerfil;
import linsbr.dayofday.model.Lancamento;
import linsbr.dayofday.model.Usuario;
import linsbr.dayofday.repository.CategoriaRepository;
import linsbr.dayofday.repository.CompartilhamentoPerfilRepository;
import linsbr.dayofday.repository.LancamentoRepository;
import linsbr.dayofday.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CompartilhamentoPerfilRepository compartilhamentoPerfilRepository;

    public List<Lancamento> listarTodosLancamentos(Integer id) {

        return usuarioRepository.findById(id).map(usuario -> {
            List<Usuario> usuarios = new ArrayList<>();
            usuarios.add(usuario);
            usuarios.addAll(compartilhamentoPerfilRepository
                    .findByUsuarioCompartilhado(usuario)
                    .stream()
                    .map(CompartilhamentoPerfil::getUsuarioDono).toList());
            return lancamentoRepository.findByUsuarioIn(usuarios);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado!"));
    }

    public Lancamento cadastrarLancamento(Lancamento lancamento) {

        if (lancamentoRepository.existsByDescricaoAndValorAndDataAndUsuario(
                lancamento.getDescricao(), lancamento.getValor(), lancamento.getData(), lancamento.getUsuario())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lançamento já cadastrado!");
        }

        if (!categoriaRepository.existsById(lancamento.getCategoria().getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não encontrada!");

        return lancamentoRepository.save(lancamento);
    }

    public Optional<Lancamento> atualizarLancamento(Lancamento lancamento) {

        if (!lancamentoRepository.existsById(lancamento.getId()))
            return Optional.empty();

        if (lancamentoRepository.existsByDescricaoAndValorAndDataAndUsuario(
                lancamento.getDescricao(), lancamento.getValor(), lancamento.getData(), lancamento.getUsuario()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lançamento já cadastrado!");

        if (!categoriaRepository.existsById(lancamento.getCategoria().getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!");

        return Optional.of(lancamentoRepository.save(lancamento));
    }

    public void deletarLancamento(Long id){

        if (!lancamentoRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        lancamentoRepository.deleteById(id);
    }
}

