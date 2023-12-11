package baseAPI.API.Sistema.DTO;

import baseAPI.API.Sistema.Enum.Aparelho;
import baseAPI.API.Sistema.Enum.Departamento;

public record OrcamentoDTO(Long telefoneCLiente, String relatoCliente, Aparelho aparelho,
                            Long idColaborador) {
}
