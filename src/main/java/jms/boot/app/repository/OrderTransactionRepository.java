package jms.boot.app.repository;

import jms.boot.app.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderTransactionRepository extends JpaRepository<Order, Long> {
//    List<Order> getAllAddress(@Param("address") String address);
}
