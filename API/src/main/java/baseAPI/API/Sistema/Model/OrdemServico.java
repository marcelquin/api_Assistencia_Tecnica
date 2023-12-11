package baseAPI.API.Sistema.Model;

import baseAPI.API.Sistema.DTO.OrdemServicoDTO;
import baseAPI.API.Sistema.Enum.SelecionarPagamento;
import baseAPI.API.Sistema.Enum.StatusOrdenServico;
import baseAPI.API.Sistema.Enum.StatusPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Entity
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ordemServico_orcamento_Id")
    private Orcamento orcamento;

    @Column(unique = true)
    private String codigo;

    @Enumerated(EnumType.STRING)
    private StatusOrdenServico statusOrdenServico;

    private String defeito;

    @OneToMany
    private List<ItemReparo> itemsReparo;

    private Double valor;

    private LocalDateTime dataEntrada;

    private LocalDateTime dataFinalizacaoReparo;

    private LocalDateTime dataEntrega;

    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    @Enumerated(EnumType.STRING)
    private SelecionarPagamento selecionarPagamento;

    private Double valorAdicional;

    public OrdemServico(OrdemServicoDTO dto) {
        this.defeito = dto.defeito();
        this.valor = dto.valor();
    }
}
