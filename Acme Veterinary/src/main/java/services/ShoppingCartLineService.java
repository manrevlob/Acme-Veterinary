package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ShoppingCartLineRepository;
import domain.ShoppingCartLine;

@Service
@Transactional
public class ShoppingCartLineService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ShoppingCartLineRepository shoppingCartLineRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ShoppingCartLineService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public ShoppingCartLine create() {
		ShoppingCartLine result;
		result = new ShoppingCartLine();
		return result;
	}

	public ShoppingCartLine findOne(int shoppingCartLineId) {
		ShoppingCartLine result;
		result = shoppingCartLineRepository.findOne(shoppingCartLineId);
		return result;
	}

	public Collection<ShoppingCartLine> findAll() {
		Collection<ShoppingCartLine> result;
		result = shoppingCartLineRepository.findAll();
		return result;
	}

	public ShoppingCartLine save(ShoppingCartLine shoppingCartLine) {
		Assert.notNull(shoppingCartLine);
		return shoppingCartLineRepository.save(shoppingCartLine);
	}
	
	public void delete(ShoppingCartLine shoppingCartLine) {
		shoppingCartLineRepository.delete(shoppingCartLine);
	}
	
	// Other business methods -------------------------------------------------
}
