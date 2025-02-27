package linsbr.dayofday.repository;

import linsbr.dayofday.model.CompartilhamentoPerfil;
import linsbr.dayofday.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompartilhamentoPerfilRepository extends JpaRepository<CompartilhamentoPerfil, Integer> {

    List<CompartilhamentoPerfil> findByUsuarioCompartilhado(Usuario usuarioCompartilhado);


}
