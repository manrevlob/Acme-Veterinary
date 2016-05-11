package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BulletinRepository;
import domain.Bulletin;

@Service
@Transactional
public class BulletinService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BulletinRepository bulletinRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public BulletinService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Bulletin create() {
		Bulletin result;
		result = new Bulletin();
		return result;
	}

	public Bulletin findOne(int bulletinId) {
		Bulletin result;
		result = bulletinRepository.findOne(bulletinId);
		return result;
	}

	public Collection<Bulletin> findAll() {
		Collection<Bulletin> result;
		result = bulletinRepository.findAll();
		return result;
	}

	public Bulletin save(Bulletin bulletin) {
		Assert.notNull(bulletin);
		return bulletinRepository.save(bulletin);
	}
	
	public void delete(Bulletin bulletin) {
		bulletinRepository.delete(bulletin);
	}
	
	// Other business methods -------------------------------------------------
}
