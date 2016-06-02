package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SpamWordRepository;
import domain.SpamWord;

@Service
@Transactional
public class SpamWordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SpamWordRepository spamWordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public SpamWordService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public SpamWord create() {
		SpamWord result;
		result = new SpamWord();
		return result;
	}

	public SpamWord findOne(int spamWordId) {
		SpamWord result;
		result = spamWordRepository.findOne(spamWordId);
		return result;
	}

	public Collection<SpamWord> findAll() {
		Collection<SpamWord> result;
		result = spamWordRepository.findAll();
		return result;
	}

	public SpamWord save(SpamWord spamWord) {
		Assert.isTrue(actorService.isAdministrator());
		Assert.notNull(spamWord);
		spamWord.setKeyWord(spamWord.getKeyWord().toLowerCase());
		Assert.isTrue(checkKey(spamWord));
		return spamWordRepository.save(spamWord);
	}

	public void delete(SpamWord spamWord) {
		Assert.notNull(spamWord);
		spamWordRepository.delete(spamWord);
	}

	// Other business methods -------------------------------------------------

	public boolean checkKey(SpamWord spamWord) {
		Assert.notNull(spamWord);
		boolean result = true;
		Collection<SpamWord> spamWords;
		spamWords = findAll();
		for (SpamWord s : spamWords) {
			if (s.getKeyWord().compareTo(spamWord.getKeyWord()) == 0) {
				result = false;
				break;
			}
		}
		return result;
	}
}
