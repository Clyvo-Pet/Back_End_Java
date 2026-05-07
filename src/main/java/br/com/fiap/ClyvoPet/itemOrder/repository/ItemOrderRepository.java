package br.com.fiap.ClyvoPet.itemOrder.repository;

import br.com.fiap.ClyvoPet.itemOrder.model.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemOrderRepository extends JpaRepository<ItemOrder, Long> {
    List<ItemOrder> findByOrderId(Long orderId);
}
