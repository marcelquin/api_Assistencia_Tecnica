package baseAPI.API.Sistema.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Entity
public class ItemReparo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "itemReparo_fornecedor_Id")
    private Fornecedor fornecedor;

    private String nome;

    private String descrisao;

    private String modelo;

    private Double valor;

    @Future
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataEntrega;

}
