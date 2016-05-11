package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CategoryRepository categoryRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CategoryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Category create() {
		Category result;
		result = new Category();
		return result;
	}

	public Category findOne(int categoryId) {
		Category result;
		result = categoryRepository.findOne(categoryId);
		return result;
	}

	public Collection<Category> findAll() {
		Collection<Category> result;
		result = categoryRepository.findAll();
		return result;
	}

	public Category save(Category category) {
		Assert.notNull(category);
		return categoryRepository.save(category);
	}
	
	public void delete(Category category) {
		categoryRepository.delete(category);
	}
	
	// Other business methods -------------------------------------------------
}
