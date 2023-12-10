package baseAPI.API.Sistema.Repository;

import baseAPI.API.Sistema.Model.ItemReparo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemReparoRepository extends JpaRepository<ItemReparo,Long> {
}
