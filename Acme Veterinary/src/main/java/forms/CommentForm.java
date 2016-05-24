package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import domain.Comment;

@Access(AccessType.PROPERTY)
public class CommentForm {

	private Comment comment;
	private int id;

	@NotNull
	@Valid
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	@Valid
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
