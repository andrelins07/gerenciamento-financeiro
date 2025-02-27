package linsbr.dayofday.repository;

import linsbr.dayofday.model.Lancamento;
import linsbr.dayofday.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query("SELECT l FROM Lancamento l JOIN FETCH l.categoria WHERE l.usuario IN :usuarios")
    List<Lancamento> findByUsuarioIn(List<Usuario> usuarios);

    boolean existsByDescricaoAndValorAndDataAndUsuario(String descricao, BigDecimal valor, LocalDate data, Usuario usuario);

}

