package baseAPI.API.Sistema.DTO;

import java.time.LocalDate;

public record ItemReparoDTO(Long idOrdemServico, Long idfornecedor, String nome,
                            String descrisao, String modelo,
                            Double valor,LocalDate dataEntrega
                              ) {
}
