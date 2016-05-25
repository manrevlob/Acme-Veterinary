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

	@Query("select c, CAST((count(m)/(select count(m2) from Comment m2)) as float) from Customer c join c.comments m group by c")
	Collection<Object[]> avgComentPerCustomer();

	@Query("select c from Customer c join c.comments m where m.isDeleted is true group by c having count(m) >= ALL(select count(m2) from Customer c2 join c2.comments m2 where m2.isDeleted is true group by c2)")
	Collection<Customer> customerMoreCommentDel();

	@Query("select c, sum(o.voucher.value) from Customer c join c.orders o where datediff(current_timestamp(), o.moment) <= 31 group by c having sum(o.voucher.value) >= ALL(select sum(o2.voucher.value) from Customer c2 join c2.orders o2 where datediff(current_timestamp(), o2.moment) <= 31 group by c2)")
	Collection<Object[]> customerSaveVoucher();

}
