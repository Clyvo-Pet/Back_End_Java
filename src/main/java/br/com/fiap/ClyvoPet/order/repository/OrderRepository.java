package br.com.fiap.ClyvoPet.order.repository;

import br.com.fiap.ClyvoPet.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    Page<Order> findByStatus(String status, Pageable pageable);

    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.user.id = :userId")
    Double sumTotalByUserId(Long userId);
}
