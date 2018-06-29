package jms.boot.app.repository;

import jms.boot.app.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderTransactionRepository extends JpaRepository<Order, Long> {

    @Query("select o FROM ORDERS o WHERE o.address = :address")
    List<Order> findOrdersForAddressWherePriceIsMoreThanFifty(@Param("address") String address);

    Optional<List<Order>> findByName(String orderName);

}
