package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ItemRepository;
import domain.Item;
import domain.Money;

@Service
@Transactional
public class ItemService {
	// Managed repository -----------------------------------------------------

	@Autowired
	private ItemRepository itemRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public ItemService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Item create() {
		Assert.isTrue(actorService.isAdministrator());
		Item result;
		result = new Item();
		Money money = new Money();
		money.setCurrency("Euro");
		result.setPrice(money);
		return result;
	}

	public Item save(Item item) {
		Assert.isTrue(actorService.isAdministrator());
		Assert.notNull(item);

		// Checks
		checkSku(item);
		checkPrice(item);

		return itemRepository.save(item);
	}

	private void checkSku(Item item) {
		for (Item i : findAll()) {
			if (i.getSku() == item.getSku()) {
				throw new DataIntegrityViolationException("sku duplicate");
			}
		}
	}

	private void checkPrice(Item item) {
		if (item.getPrice().getAmount() <= 0.0
				|| item.getPrice().getAmount() == null) {
			throw new NullPointerException("Incorrect price");
		}
	}

	public void delete(Item item) {
		Assert.notNull(item);
		Assert.isTrue(actorService.isAdministrator());
		item.setIsDeleted(true);
		item = save(item);
	}

	public Collection<Item> findAll() {
		Assert.isTrue(actorService.isAdministrator());
		Collection<Item> result;
		result = itemRepository.findAll();
		return result;
	}

	public Item findOne(int itemId) {
		Assert.isTrue(itemId != 0);
		Item result;
		result = itemRepository.findOne(itemId);
		return result;
	}

	// Other business methods

	public Collection<Item> findAllNoDeleted() {
		Collection<Item> result = null;
		result = itemRepository.findAllNotDeleted();
		return result;
	}

	public Collection<Item> findByKeyword(String keyword) {
		Assert.notNull(keyword);
		Collection<Item> result;
		result = itemRepository.findByKeyword(keyword);
		return result;
	}

	public Collection<Item> findByCategory() {
		Collection<Item> result;
		result = itemRepository.findByCategory();
		return result;
	}

	public Collection<Item> findByCategory(int categoryId) {
		Collection<Item> result;
		result = itemRepository.findByCategory(categoryId);
		return result;
	}

	public Item findBySKU(String sku) {
		Assert.notNull(sku);
		Item result;

		result = itemRepository.findBySKU(sku);

		return result;
	}

}
