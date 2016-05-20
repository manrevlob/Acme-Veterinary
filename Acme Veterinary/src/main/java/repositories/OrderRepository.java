package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	@Query("select o from Order o where o.customer = ?1")
	Collection<Order> findAllByCustomer(Customer customer);

	@Query("select o from Order o where o.isCanceled = 0")
	Collection<Order> findOrdersNotCanceled();
	
	@Query("select o.ticker from Order o")
	Collection<String> findAllTicker();

}
