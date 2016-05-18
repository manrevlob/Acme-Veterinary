package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;
import domain.Item;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CategoryRepository categoryRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private ItemService itemService;

	// Constructors -----------------------------------------------------------

	public CategoryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Category create() {
		Assert.isTrue(actorService.isAdministrator());
		Category result;
		result = new Category();
		return result;
	}

	public Category save(Category category) {
		Assert.isTrue(actorService.isAdministrator());
		Assert.notNull(category);
		return categoryRepository.save(category);
	}

	public boolean delete(Category category) {
		Assert.isTrue(actorService.isAdministrator());
		Assert.notNull(category);
		boolean result;
		Collection<Item> items;
		items = itemService.findByCategory(category.getId());

		if (items.size() == 0) {
			categoryRepository.delete(category);
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public Collection<Category> findAll() {
		Assert.isTrue(actorService.isAdministrator());
		Collection<Category> result;
		result = categoryRepository.findAll();
		return result;
	}

	public Category findOne(int categoryId) {
		Assert.isTrue(actorService.isAdministrator());
		Assert.isTrue(categoryId != 0);
		Category result;
		result = categoryRepository.findOne(categoryId);
		Assert.notNull(result);
		return result;
	}

	// Other business methods

}
