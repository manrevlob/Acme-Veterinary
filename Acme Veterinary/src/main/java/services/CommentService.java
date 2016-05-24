package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Comment;
import domain.Item;
import domain.Veterinary;
import forms.CommentForm;

@Service
@Transactional
public class CommentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private VeterinaryService veterinaryService;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Comment create() {
		Assert.isTrue(actorService.isCustomer());
		Comment result;
		result = new Comment();
		result.setCustomer(customerService.findByPrincipal());
		result.setMoment(new Date(System.currentTimeMillis() - 100));
		return result;
	}

	public Comment findOne(int commentId) {
		Comment result;
		result = commentRepository.findOne(commentId);
		return result;
	}

	public Collection<Comment> findAll() {
		Collection<Comment> result;
		result = commentRepository.findAll();
		return result;
	}

	public Comment save(Comment comment) {
		Assert.notNull(comment);
		return commentRepository.save(comment);
	}

	public void delete(Comment comment) {
		commentRepository.delete(comment);
	}

	// Other business methods -------------------------------------------------

	public Collection<Comment> findAllByItem(int itemId) {
		Collection<Comment> result;
		result = commentRepository.findAllByItem(itemId);
		return result;

	}

	public Collection<Comment> findAllByVeterinary(int veterinaryId) {
		Collection<Comment> result;
		result = commentRepository.findAllByVeterinary(veterinaryId);
		return result;

	}

	public CommentForm createForm(int id) {
		Assert.isTrue(actorService.isCustomer());
		CommentForm commentForm = new CommentForm();
		Comment comment = create();
		commentForm.setComment(comment);
		commentForm.setId(id);
		return commentForm;
	}

	public void saveToItem(CommentForm commentForm) {
		Assert.isTrue(actorService.isCustomer());
		Item item = itemService.findOne(commentForm.getId());
		Collection<Comment> comments = item.getComments();
		Comment comment = save(commentForm.getComment());
		comments.add(comment);
	}

	public void saveToVeterinary(CommentForm commentForm) {
		Assert.isTrue(actorService.isCustomer());
		Veterinary veterinary = veterinaryService.findOne(commentForm.getId());
		Collection<Comment> comments = veterinary.getComments();
		Comment comment = save(commentForm.getComment());
		comments.add(comment);
	}
}
