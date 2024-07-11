package org.example.repository;

import org.example.model.OrderOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderOptionRepository extends JpaRepository<OrderOption, Long> {
    List<OrderOption> findByOrderId(Long orderId);
}
