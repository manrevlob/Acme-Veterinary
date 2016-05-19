package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Item;
import domain.ShoppingCart;
import domain.ShoppingCartLine;

@Repository
public interface ShoppingCartLineRepository extends
		JpaRepository<ShoppingCartLine, Integer> {

	@Query("select scl from ShoppingCartLine scl where scl.shoppingCart = ?1")
	Collection<ShoppingCartLine> findByShoppingCart(ShoppingCart shoppingCart);
	
	@Query("select scl from ShoppingCartLine scl where scl.shoppingCart = ?1 and scl.item = ?2")
	ShoppingCartLine findByShoppingCartAndItem(ShoppingCart shoppingCart, Item item);
	
}
