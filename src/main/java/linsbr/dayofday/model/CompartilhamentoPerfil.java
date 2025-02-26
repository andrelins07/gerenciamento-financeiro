package linsbr.dayofday.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "compartilhamento_lancamento")
public class CompartilhamentoPerfis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonIgnoreProperties("perfisCompartilhados")
    @JoinColumn(name = "usuario_dono_id")
    @NotNull
    private Usuario usuarioDono; //usuario que compartilha o perfil

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "usuario_compartilhado_id")
    @NotNull
    private Usuario usuarioCompartilhado; //usuario que recebe o compartilhamento

    private LocalDate dataCompartilhamento = LocalDate.now();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuarioDono() {
        return usuarioDono;
    }

    public void setUsuarioDono(Usuario usuarioDono) {
        this.usuarioDono = usuarioDono;
    }

    public Usuario getUsuarioCompartilhado() {
        return usuarioCompartilhado;
    }

    public void setUsuarioCompartilhado(Usuario usuarioCompartilhado) {
        this.usuarioCompartilhado = usuarioCompartilhado;
    }

    public LocalDate getDataCompartilhamento() {
        return dataCompartilhamento;
    }

    public void setDataCompartilhamento(LocalDate dataCompartilhamento) {
        this.dataCompartilhamento = dataCompartilhamento;
    }
}
