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
import domain.Comment;
import forms.CommentForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CommentServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private CommentService commentService;

	// Tests -------------------------------------------------

	// Comprobamos que podemos crear un commentario de un articulo logueado como
	// customer
	@Test
	public void testCreateAndSaveItem() {
		Collection<Comment> before;
		Collection<Comment> after;

		authenticate("customer1");

		// Obtenemos la Id del item1
		int itemId = 20;

		before = commentService.findAll();

		CommentForm commentForm = commentService.createForm(itemId);

		commentForm.getComment().setDescription("TEST");
		commentForm.getComment().setRating(2);

		commentService.saveToItem(commentForm);

		after = commentService.findAll();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Comprobamos que podemos crear un comentario de un veterinario logueado
	// como customer

	@Test
	public void testCreateAndSaveVeterinary() {
		Collection<Comment> before;
		Collection<Comment> after;

		authenticate("customer1");

		// Obtenemos la Id del veterinario1
		int veterinaryId = 55;

		before = commentService.findAll();

		CommentForm commentForm = commentService.createForm(veterinaryId);

		commentForm.getComment().setDescription("TEST");
		commentForm.getComment().setRating(2);

		commentService.saveToVeterinary(commentForm);

		after = commentService.findAll();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Comprobamos que podemos crear un comentario de un bulletin logueado como
	// customer

	@Test
	public void testCreateAndSaveBulletin() {
		Collection<Comment> before;
		Collection<Comment> after;

		authenticate("customer1");

		// Obtenemos la Id del bulletin1
		int bulletinId = 74;

		before = commentService.findAll();

		CommentForm commentForm = commentService.createForm(bulletinId);

		commentForm.getComment().setDescription("TEST");
		commentForm.getComment().setRating(2);

		commentService.saveToBullletin(commentForm);

		after = commentService.findAll();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Comprobamos que podemos eliminar un comentario logueado como
	// administrador

	@Test
	public void testDelete() {
		authenticate("admin");

		// Obtenemos la Id del comment1
		int commentId = 7;

		Comment comment;
		comment = commentService.findOne(commentId);
		commentService.delete(commentId);

		Assert.isTrue(comment.getIsDeleted());

		unauthenticate();
	}

	// Test Negativos ----------------------------------------

	// Comprobamos que no podemos crear un comentario a un veterinario con el
	// que no has tenido citas anteriormente
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveVeterinaryNeg() {
		authenticate("customer1");

		// Obtenemos la Id del veterinario3
		int veterinaryId = 57;

		CommentForm commentForm = commentService.createForm(veterinaryId);

		commentForm.getComment().setDescription("TEST");
		commentForm.getComment().setRating(2);

		commentService.saveToVeterinary(commentForm);

		unauthenticate();
	}

	// Comprobamos que no podemos crear un comentario con un rol distinto a
	// customer

	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveVeterinaryNeg2() {
		authenticate("admin");

		// Obtenemos la Id del veterinario3
		int veterinaryId = 57;

		CommentForm commentForm = commentService.createForm(veterinaryId);

		commentForm.getComment().setDescription("TEST");
		commentForm.getComment().setRating(2);

		commentService.saveToVeterinary(commentForm);

		unauthenticate();
	}

	// Comprobamos que no podemos eliminar un comentario que haya sido borrado
	// anteriormente
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNeg() {
		authenticate("admin");

		// Obtenemos la Id del comment1 que NO esta eliminado
		int commentId = 7;
		commentService.delete(commentId);

		// Una vez tenemos el comment1 eliminado intentamos volver a eliminarlo
		commentService.delete(commentId);

		unauthenticate();
	}

}