package baseAPI.API.Sistema.Repository;

import baseAPI.API.Sistema.Model.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento,Long> {
}
