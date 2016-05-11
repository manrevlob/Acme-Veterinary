package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ShoppingCartRepository;
import domain.ShoppingCart;

@Service
@Transactional
public class ShoppingCarService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ShoppingCarService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public ShoppingCart create() {
		ShoppingCart result;
		result = new ShoppingCart();
		return result;
	}

	public ShoppingCart findOne(int shoppingCartId) {
		ShoppingCart result;
		result = shoppingCartRepository.findOne(shoppingCartId);
		return result;
	}

	public Collection<ShoppingCart> findAll() {
		Collection<ShoppingCart> result;
		result = shoppingCartRepository.findAll();
		return result;
	}

	public ShoppingCart save(ShoppingCart shoppingCart) {
		Assert.notNull(shoppingCart);
		return shoppingCartRepository.save(shoppingCart);
	}
	
	public void delete(ShoppingCart shoppingCart) {
		shoppingCartRepository.delete(shoppingCart);
	}
	
	// Other business methods -------------------------------------------------
}
