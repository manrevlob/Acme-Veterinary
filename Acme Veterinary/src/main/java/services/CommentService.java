package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Appointment;
import domain.Bulletin;
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
	private ActorService actorService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private VeterinaryService veterinaryService;
	@Autowired
	private BulletinService bulletinService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AppointmentService appointmentService;

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

	public void delete(int commentId) {
		Assert.isTrue(actorService.isAdministrator());
		Comment comment = findOne(commentId);
		comment.setIsDeleted(true);
		commentRepository.save(comment);
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

	public Collection<Comment> findAllByBulletin(int bulletinId) {
		Collection<Comment> result;
		result = commentRepository.findAllByBulletin(bulletinId);
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

		Collection<Comment> itemComments = item.getComments();
		Comment comment = save(commentForm.getComment());
		itemComments.add(comment);

		Collection<Comment> customerComments = customerService
				.findByPrincipal().getComments();
		customerComments.add(comment);
	}

	public void saveToVeterinary(CommentForm commentForm) {
		Assert.isTrue(actorService.isCustomer());
		Assert.isTrue(checkAppointments(commentForm.getId()));

		Veterinary veterinary = veterinaryService.findOne(commentForm.getId());
		Collection<Comment> comments = veterinary.getComments();
		Comment comment = save(commentForm.getComment());
		comments.add(comment);

		Collection<Comment> customerComments = customerService
				.findByPrincipal().getComments();
		customerComments.add(comment);

	}

	// Comprobar que se ha ido a una cita con el veterinario que se quiere
	// comentar
	public boolean checkAppointments(int veterinaryId) {
		boolean result = false;
		Veterinary veterinary = veterinaryService.findOne(veterinaryId);

		Collection<Appointment> appointments = appointmentService
				.findByPrincipaExpired();
		for (Appointment a : appointments) {
			if (a.getVeterinary().equals(veterinary)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public void saveToBullletin(CommentForm commentForm) {
		Assert.isTrue(actorService.isCustomer());
		Bulletin bulletin = bulletinService.findOne(commentForm.getId());

		Collection<Comment> comments = bulletin.getComments();
		Comment comment = save(commentForm.getComment());
		comments.add(comment);

		Collection<Comment> customerComments = customerService
				.findByPrincipal().getComments();
		customerComments.add(comment);
	}

}
