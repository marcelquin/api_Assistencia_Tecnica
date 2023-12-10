package baseAPI.API.Sistema.Model;

import baseAPI.API.Sistema.Enum.Aparelho;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Entity
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orcamento_cliente_Id")
    private Cliente cliente;

    private String relatoCliente;

    @Enumerated(EnumType.STRING)
    private Aparelho aparelho;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataEntrada;

    @ManyToOne
    @JoinColumn(name = "orcamento_colaborador_Id")
    private Colaborador colaborador;
}
