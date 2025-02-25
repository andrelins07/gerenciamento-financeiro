package linsbr.dayofday.repository;

import linsbr.dayofday.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
