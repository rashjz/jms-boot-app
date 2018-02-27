package jms.boot.app.repository;

import jms.boot.app.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Rashad on 2/26/2018.
 */
@Repository
public interface OrderTransactionRepository extends JpaRepository<Order, Long> {
}
