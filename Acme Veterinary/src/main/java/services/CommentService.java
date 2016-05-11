package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Comment;

@Service
@Transactional
public class CommentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CommentRepository commentRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Comment create() {
		Comment result;
		result = new Comment();
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
}
