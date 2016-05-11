package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ItemOrderRepository;
import domain.ItemOrder;

@Service
@Transactional
public class ItemOrderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ItemOrderRepository itemOrderRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ItemOrderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public ItemOrder create() {
		ItemOrder result;
		result = new ItemOrder();
		return result;
	}

	public ItemOrder findOne(int itemOrderId) {
		ItemOrder result;
		result = itemOrderRepository.findOne(itemOrderId);
		return result;
	}

	public Collection<ItemOrder> findAll() {
		Collection<ItemOrder> result;
		result = itemOrderRepository.findAll();
		return result;
	}

	public ItemOrder save(ItemOrder itemOrder) {
		Assert.notNull(itemOrder);
		return itemOrderRepository.save(itemOrder);
	}
	
	public void delete(ItemOrder itemOrder) {
		itemOrderRepository.delete(itemOrder);
	}
	
	// Other business methods -------------------------------------------------
}
