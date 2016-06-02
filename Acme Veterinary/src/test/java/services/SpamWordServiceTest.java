package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.SpamWord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class SpamWordServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private SpamWordService spamWordService;

	// Tests -------------------------------------------------

	// Comprobamos que podemos crear una palabra de spam como administrador
	@Test
	public void testCreateAndSave() {
		Collection<SpamWord> before;
		Collection<SpamWord> after;

		authenticate("admin");

		before = spamWordService.findAll();

		SpamWord spamWord;
		spamWord = spamWordService.create();
		spamWord.setKeyWord("TEST");
		spamWordService.save(spamWord);

		after = spamWordService.findAll();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Test Negativos ----------------------------------------

	// Comprobamos que no podemos crear una palabra de spam con un rol distinto
	// a
	// administrador
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNeg() {
		authenticate("customer1");

		SpamWord spamWord;
		spamWord = spamWordService.create();
		spamWord.setKeyWord("TEST");
		spamWordService.save(spamWord);

		unauthenticate();
	}

	// Comprobamos que no podemos crear una palabra de spamcon una key ya
	// existente
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNeg2() {
		authenticate("admin");

		// Creamos una clave de spam
		SpamWord spamWord1;
		spamWord1 = spamWordService.create();
		spamWord1.setKeyWord("TEST");
		spamWordService.save(spamWord1);

		// Intentamos crear la clave de spam de nuevo
		SpamWord spamWord2;
		spamWord2 = spamWordService.create();
		spamWord2.setKeyWord("TEST");
		spamWordService.save(spamWord2);

		unauthenticate();
	}

}