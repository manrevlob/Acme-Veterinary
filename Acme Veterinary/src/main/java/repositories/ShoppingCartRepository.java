package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends
		JpaRepository<ShoppingCart, Integer> {

	@Query("select s from ShoppingCart s where s.customer = ?1")
	ShoppingCart findByCustomer(Customer customer);

	@Query("select sum(sc.item.price.amount * sc.quantity) from ShoppingCartLine sc where sc.shoppingCart is ?1")
	Double calculateTotalPrice(ShoppingCart shoppingCart);

}
