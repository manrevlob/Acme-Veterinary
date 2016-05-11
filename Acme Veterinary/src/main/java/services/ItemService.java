package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ItemRepository;
import domain.Item;

@Service
@Transactional
public class ItemService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ItemRepository itemRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ItemService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Item create() {
		Item result;
		result = new Item();
		return result;
	}

	public Item findOne(int itemId) {
		Item result;
		result = itemRepository.findOne(itemId);
		return result;
	}

	public Collection<Item> findAll() {
		Collection<Item> result;
		result = itemRepository.findAll();
		return result;
	}

	public Item save(Item item) {
		Assert.notNull(item);
		return itemRepository.save(item);
	}
	
	public void delete(Item item) {
		itemRepository.delete(item);
	}
	
	// Other business methods -------------------------------------------------
}
