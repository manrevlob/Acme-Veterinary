package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BulletinRepository;
import domain.Bulletin;
import domain.Clinic;

@Service
@Transactional
public class BulletinService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BulletinRepository bulletinRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ClinicService clinicService;
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public BulletinService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Bulletin create(int clinicId) {
		Assert.isTrue(actorService.isAdministrator());
		Bulletin result;
		Clinic clinic = clinicService.findOne(clinicId);
		result = new Bulletin();
		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		result.setClinic(clinic);
		return result;
	}

	public Bulletin findOne(int bulletinId) {
		Bulletin result;
		result = bulletinRepository.findOne(bulletinId);
		return result;
	}

	public Bulletin save(Bulletin bulletin) {
		Assert.isTrue(actorService.isAdministrator());
		Assert.notNull(bulletin);
		return bulletinRepository.save(bulletin);
	}

	public void delete(Bulletin bulletin) {
		Assert.isTrue(actorService.isAdministrator());
		Assert.isTrue(!bulletin.getIsDeleted());
		bulletin.setIsDeleted(true);
		save(bulletin);
	}
	
	public Collection<Bulletin> findAllTime(){
		Assert.isTrue(actorService.isAdministrator());
		return bulletinRepository.findAll();
	}

	// Other business methods -------------------------------------------------

	public Collection<Bulletin> findAllFromClinic(int clinicId) {
		Collection<Bulletin> result;
		result = bulletinRepository.findAllFromClinic(clinicId);
		return result;
	}

	public Collection<Object[]> findAll() {
		Collection<Object[]> result;
		result = bulletinRepository.findAllBulletins();
		return result;
	}
}
