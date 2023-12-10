package baseAPI.API.Sistema.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String sobrenome;

    @Column(unique = true)
    private Long cpf;

    @Column(unique = true)
    private Long telefone;

    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @OneToOne
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @OneToOne
    @JoinColumn(name = "documentos_id", referencedColumnName = "id")
    private Documentos documentos;

    @ManyToOne
    @JoinColumn(name = "colaborador_cargo_Id")
    private Cargo cargo;

    private LocalDate dataEntrada;

    private LocalDate dataDesligamento;

    private String motivoDesligamento;
}
