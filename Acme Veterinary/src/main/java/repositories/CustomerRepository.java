package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id = ?1")
	Customer findByPrincipal(int id);

	@Query("select c from Customer c where c.name like %?1% or c.surname like %?1%")
	Collection<Customer> findByKeyword(String keyword);
	
	@Query("select count(c) from Customer c where c in (select c from Customer c where datediff(current_timestamp(), c.registerMoment) <= 31)")
	Integer numberNewCustomers();

	@Query("select c from Customer c join c.orders o where o.isCanceled is false and datediff(current_timestamp(), o.moment) <= 31) group by c having sum(o.totalPrice.amount) >= ALL(select sum(o2.totalPrice.amount) from Customer c2 join c2.orders o2 where o2.isCanceled is false and datediff(current_timestamp(), o.moment) <= 31) group by c2)")
	Collection<Customer> customerMorePay();

}
